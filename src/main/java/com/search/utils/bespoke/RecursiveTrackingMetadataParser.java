package com.search.utils.bespoke;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaMetadataKeys;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ParserDecorator;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RecursiveTrackingMetadataParser extends ParserDecorator 
{
	
	private String location;
    private int unknownCount = 0;

    public RecursiveTrackingMetadataParser(Parser parser, String location) {
        super(parser);
        this.location = location;
        if (! this.location.endsWith("/")) {
           this.location += "/";
        }
    }

    @Override
    public void parse(
            InputStream stream, ContentHandler ignore,
            Metadata metadata, ParseContext context)
            throws IOException, SAXException, TikaException {
        // Work out what this thing is
        String objectName = null;
        if (metadata.get(TikaMetadataKeys.RESOURCE_NAME_KEY) != null) 
        {
           objectName = metadata.get(TikaMetadataKeys.RESOURCE_NAME_KEY);
        } else if (metadata.get(TikaMetadataKeys.EMBEDDED_RELATIONSHIP_ID) != null) {
           objectName = metadata.get(TikaMetadataKeys.EMBEDDED_RELATIONSHIP_ID);
        } else {
           objectName = "embedded-" + (++unknownCount);
        }
        String objectLocation = this.location + objectName;

        // Fetch the contents, and recurse if possible
        ContentHandler content = new BodyContentHandler();
        Parser preContextParser = context.get(Parser.class);
        context.set(Parser.class, new RecursiveTrackingMetadataParser(getWrappedParser(), objectLocation));
        super.parse(stream, content, metadata, context);
        context.set(Parser.class, preContextParser);

        // Report what this one is
        System.out.println("----");
        System.out.println("Resource is " + objectLocation);
        System.out.println("----");
        System.out.println(metadata);
        System.out.println("----");
        System.out.println(content.toString());
    }
    
    public static void main(String[] args) throws IOException, SAXException, TikaException
    {
    	
    	Parser parser = new RecursiveTrackingMetadataParser(new AutoDetectParser(), "C:/Users/oonyimadu/PERSONAL/Attachments_2016922.zip");
    	
    	
	       ParseContext context = new ParseContext();
	       context.set(Parser.class, parser);
	       

	      ContentHandler handler = new DefaultHandler();
	      // ContentHandler handler = new BodyContentHandler();
	       
	       Metadata metadata = new Metadata();
	       
	       
	       

	       FileSystem fs = FileSystems.getDefault();
	      Path path = fs.getPath("C:/Users/oonyimadu/PERSONAL/Attachments_2016922.zip");
	      
	      // Path path = fs.getPath("ACM_PAPER_1.docx");
	       InputStream stream = TikaInputStream.get(path);
	       try {
	          parser.parse(stream, handler, metadata, context);
	    	   
	       } finally {
	           stream.close();
	       }
	       }
	       
	      
	       
    	/**
	       File f = new File("C:/Users/oonyimadu/PERSONAL/updata");
	       String[] strl = f.list();
	       System.out.println(strl.length);
	       //FileUtils.
	        * */
	        
    
}

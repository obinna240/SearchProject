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
import org.apache.tika.mime.MediaTypeRegistry;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ParserDecorator;
import org.apache.tika.parser.image.ImageParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



public class RecursiveMetadataParser extends ParserDecorator
{

	public RecursiveMetadataParser(Parser parser) {
		super(parser);
		// TODO Auto-generated constructor stub
	}
	
	 @Override
     public void parse(
             InputStream stream, ContentHandler ignore,
             Metadata metadata, ParseContext context)
             throws IOException, SAXException, TikaException 
	 {
        ContentHandler content = new BodyContentHandler();
        super.parse(stream, content, metadata, context);

        System.out.println("----");
       System.out.println(metadata);
       System.out.println("resource name   " + metadata.get(TikaMetadataKeys.RESOURCE_NAME_KEY));
       System.out.println("resource name   " + metadata.get(TikaMetadataKeys.EMBEDDED_RELATIONSHIP_ID));
      System.out.println("resource name   " + metadata.get(TikaMetadataKeys.EMBEDDED_STORAGE_CLASS_ID));
       System.out.println("resource name   " + metadata.get(TikaMetadataKeys.EMBEDDED_RESOURCE_TYPE));
       
     //  MediaTypeRegistry.getDefaultRegistry().
       //metadata.
        //System.out.println("----");
       //System.out.println(content.toString());
        
       
       
         /**
         super.parse(stream, ignore, metadata, context);

         System.out.println("----");
         System.out.println(metadata);
         System.out.println("----");
         System.out.println(ignore.toString());
         */
     }
	 
	 public static void main(String[] args) throws Exception {
	       Parser parser = new RecursiveMetadataParser(new AutoDetectParser());
	       ParseContext context = new ParseContext();
	       context.set(Parser.class, parser);
	       
	      
	       
	      ContentHandler handler = new DefaultHandler();
	       //ContentHandler handler = new BodyContentHandler();
	       
	       Metadata metadata = new Metadata();
	       
	       /**
	        * try and delete
	        */
	       

	      FileSystem fs = FileSystems.getDefault();
	     // Path path = fs.getPath("C:/Users/oonyimadu/PERSONAL/Attachments_2016922_1.7z");
	      
	      Path path = fs.getPath("ACM_PAPER_1.docx");
	       InputStream stream = TikaInputStream.get(path);
	       try {
	          parser.parse(stream, handler, metadata, context);
	    	   
	       } finally {
	           stream.close();
	       }
	   }
	
}

package com.search.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Date;
import java.util.EnumSet;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.sis.internal.jdk7.Objects;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import com.search.docs.DocumentIndexer;

import com.search.documentMapping.DocumentHistory;
import com.search.documentMapping.DocumentObject;
import com.search.repository.DocumentHistoryRepository;
import com.search.repository.DocumentObjectRepository;

/**
 * 
 * @author oonyimadu
 *
 */
@Component
public class DirectoryWalker 
{
	
	
	@Autowired
	DocumentIndexer documentIndexer;
	@Autowired
	DocumentHistoryRepository history;
	@Autowired
	DocumentObjectRepository objectRepo;
	@Autowired
	GridFsTemplate gridFsTemplate;

	private static final Logger logger = LogManager.getLogger(DirectoryWalker.class);
	
	private static int failedFiles, successfulFiles = 0;
	
	//@Value("${_index_directory}")
	private String _index_directory;
	

	//@Value("${_files_to_index}")
	private String[] _filesToIndex;
			
	public String get_index_directory() {
		return _index_directory;
	}

	@Value("${_index_directory}")
	public void set_index_directory(String _index_directory) {
		this._index_directory = _index_directory;
		DirectoryWalker.DIR_NAME = _index_directory;
	}

	static String DIR_NAME ;
	static String[] FILETYPEARRAY ;
	static int count = 90;
	static DocumentIndexer solrIndexer;
	static DocumentHistoryRepository aHistory;

	static DocumentObjectRepository aObjectRepo;
	static GridFsTemplate aGridFSTemplate;
	
	@PostConstruct
    public void init(){
		solrIndexer = documentIndexer;
		aHistory = history;
		aObjectRepo = objectRepo;
		aGridFSTemplate = gridFsTemplate;
    }
	
	public String[] get_filesToIndex() {
		
		return _filesToIndex;
	}

	@Value("${_files_to_index}")
	public void set_filesToIndex(String[] _filesToIndex) {
		
		this._filesToIndex = _filesToIndex;
		DirectoryWalker.FILETYPEARRAY = _filesToIndex;
	}
	
	public static class Finder extends SimpleFileVisitor<Path> 
	{
		 @Override
	     public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)       
		 {
			 InputStream inputStream = null;
			 String fileName = file.getFileName().toString();
			 
			 if(StringUtils.endsWithAny(fileName, FILETYPEARRAY))
			 {
				 //DocumentHistoryID
				 String documentHistoryID = WalkerUtils.generateMD5CheckSum(file.toString().toLowerCase());
				 try{
				 //create a document parser and get the documentBin object
				 DocumentParser parser = new DocumentParser();
				 DocumentHistory dHistory = aHistory.findByFileLocation(documentHistoryID);
				 parser.parseDocument(file);
				 
				 DocumentObject documentObject = parser.getDocumentObject();
				 String body = documentObject.getBody();
				 String newCheckSum = WalkerUtils.generateMD5CheckSum(body);
				
				//first check if the file exists in the database
				 if(dHistory != null)
				 {
					 
					 //get the checksum of the document (this is usually the checksum of the
					 //most recent document. This will be replaced with the new one
					 String oldCheckSum = dHistory.getCheckSum();
					
					 //create a new object for an old object
					 if(!Objects.equals(newCheckSum, oldCheckSum))
					 {
						 
						 //create the new document history and document object
						 //relegate the current document history object 
						 //and replace it with this one
						 //ETL classification etc
						 //then index
						 //commit
												 
						 documentObject = FileAttributeUtils.addFeatures(file, attrs, documentObject, 
							false, documentHistoryID, dHistory, newCheckSum,
							true, false);
					 }
				 }
				 else
				 {
					 //create a new document history
					 //create a new document object
					 //carry out ETL and classification
					 //index and commit
					 //save new DocumentObject
					 //save new DocumentHistory
					 dHistory = new DocumentHistory();
					 documentObject = FileAttributeUtils.addFeatures(file, attrs, documentObject, 
								true, documentHistoryID, dHistory, newCheckSum,
								true, false);
				 }
				 

					
				 //try
				 //{

								
					 logger.info("Saving in DB Document Object "+ documentObject.getDocId()+ " "+documentObject.getFileLocation());
					
					 logger.info("Save as GridFS object "+ documentObject.getDocId()+ " "+documentObject.getFileLocation());
					//fileName
					inputStream = new FileInputStream(file.toString());
					
					DBObject metaData = new BasicDBObject();
					metaData.put("user", "Default User");
					
					System.out.println("######################################################################");
					String gridFSId = aGridFSTemplate.store(inputStream, documentObject.getFileName(), documentObject.getMediaType(), metaData).getId().toString();
					System.out.println("GridFS ID= "+gridFSId);
					System.out.println("######################################################################");
					
					UpdateResponse up = solrIndexer.indexDocument(documentObject);
					int status = up.getStatus();
					if(status == 0)
					{
						documentObject.setIsIndexed(true);
						logger.info("Saving in DB Document HISTORY "+ documentObject.getObjectID());
						aObjectRepo.save(documentObject);
						Date dhs = new Date();
						dHistory.setDateOfIndexing(dhs);
						dHistory.setDateOfSaving(dhs);
						aHistory.save(dHistory);
					}
														
					successfulFiles++;
				 }
				 catch(IOException | SAXException | TikaException exception)
				 {
					 exception.printStackTrace();
				 }
				 finally
				 {
					 try {
						inputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 }
			}
			 return FileVisitResult.CONTINUE;
		 }
		 
		 
		  @Override
		  public FileVisitResult postVisitDirectory(Path dir, IOException exc)                                    
		  {
		      //print or log all visited directories 
			  //System.out.format("Directory: %s%n", dir);
			 // System.out.println("Ihave visited directory "+dir.getFileName());
		      return FileVisitResult.CONTINUE;
		  }
		  
		  public FileVisitResult preVisitDirectory(Path dir, IOException exc)
		  {
			// System.out.println("I am about to visit directory "+dir.getFileName());
			return FileVisitResult.CONTINUE;
			  
		  }
		  
		  @Override
		  public FileVisitResult visitFileFailed(Path file,
		                                       IOException exc) 
		  {
		        System.err.println(exc);
		        //log the name of the file that was not visited
		        failedFiles++;
		        return FileVisitResult.CONTINUE;
		  }
		  
		  public static void run() throws IOException//main(String[] args) throws IOException
		  {
			  
			  Path startingDirectory = Paths.get(DIR_NAME);
			  Finder finder = new Finder();
			  EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
			  Files.walkFileTree(startingDirectory, opts, Integer.MAX_VALUE, finder);
			  
		  }
		  
	}
	
	public void run() throws IOException
	{
	
		Finder.run();
		
	}
}

/**
 *  
   public static void processFolder(Path folder) {
        try {
            Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    try {
                        process(file);
                        successfulFiles++;
                    } catch (Exception e) {
                        failedFiles++;
                        // ignore this file
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    failedFiles++;
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            // ignore failure
        }
    }
    */

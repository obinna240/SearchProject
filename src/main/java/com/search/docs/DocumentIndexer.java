package com.search.docs;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.search.SearchProjectApplication;
import com.search.documentMapping.DocumentObject;
import com.search.indexer.Indexer;

//@Component //we create a bean in java config instead of using @Component
public class DocumentIndexer implements Indexer
{
	private String indexUrl;
	private String indexName;
	private String fullUrl;
	static SolrClient solr;
	
	private static final Logger logger = LogManager.getLogger(DocumentIndexer.class);
	

	public DocumentIndexer(String indexUrl, String indexName)
	{
		
		logger.info("Creating a new Document Indexer");
		this.setIndexUrl(indexUrl);
		this.setIndexName(indexName);
		setFullUrl(indexUrl+"/"+indexName);
		solr = new HttpSolrClient.Builder(getFullUrl()).build();
	}
	


	@Override
	public void index() 
	{
		
	}

	@Override
	public UpdateResponse indexDocument(DocumentObject documentObject)
	{
		UpdateResponse up = null;
		// TODO Auto-generated method stub
		if(documentObject != null)
		{
			SolrInputDocument sDoc = new SolrInputDocument();
			String docId = documentObject.getDocId();
			
			sDoc.addField("id", docId);
			sDoc.addField("objectId", documentObject.getObjectID());
			
			sDoc.addField("body", documentObject.getBody());
			String fileLoc = documentObject.getFileLocation();
			
			sDoc.addField("fileLocation", fileLoc);
			sDoc.addField("fileName", documentObject.getFileName());
			
			logger.info("Logging document with id "+ docId +" AND filelocation "+ fileLoc);
			
			sDoc.addField("creationTime", documentObject.getCreationTime());
			
			sDoc.addField("lastAccessTime", documentObject.getLastAccessTime());
			
			sDoc.addField("lastModifiedTime", documentObject.getLastModifiedTime());
			
			sDoc.addField("size", documentObject.getDocSize());
			
			sDoc.addField("fileType", documentObject.getDocType());
			sDoc.addField("mediaBaseType", documentObject.getMediaBaseType());
			sDoc.addField("mediaType", documentObject.getMediaType());
			sDoc.addField("mediaSubType", documentObject.getMediaSubType());
			sDoc.addField("fileParent", documentObject.getFileParent());
			
			sDoc.addField("version", documentObject.getVersionNumber());////
			sDoc.addField("pageCount", documentObject.getPageCount());
			
			List<String> authors = documentObject.getAuthors();
			
			if(CollectionUtils.isNotEmpty(authors))
			{
				for(String auth:authors)
				{
					if(StringUtils.isNotBlank(auth)){
					sDoc.addField("authors", auth);}
				}
				
			}
			List<String> title = documentObject.getTitle();
			if(CollectionUtils.isNotEmpty(title))
			{
				for(String titles:title)
				{
					if(StringUtils.isNotBlank(titles)){
					sDoc.addField("title", titles);}
				}
				
			}
			sDoc.addField("indexDate", new Date());
			try {
				up = solr.add(sDoc);
				logger.info("Indexed document in location " + fileLoc);
			} catch (SolrServerException | IOException e) {
				System.out.println("Cannot add document "+documentObject.getFileName());
				
				e.printStackTrace();
				
			}
			try {
				up = solr.commit();
				logger.info("Commit Successful " + fileLoc);
				
			} catch (SolrServerException | IOException e) {
				
				logger.fatal("Cannot commit document "+documentObject.getFileName());
				e.printStackTrace();
			}
			
		}
		return up;
	}

	public String getIndexUrl() {
		return indexUrl;
	}

	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getFullUrl() {
		return fullUrl;
	}

	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}


	

}

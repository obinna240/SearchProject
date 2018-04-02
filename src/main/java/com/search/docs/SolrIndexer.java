package com.search.docs;

import java.io.IOException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

import com.search.documentMapping.DocumentObject;

public class SolrIndexer
{
	
	private static String url = "http://localhost:8984/solr/wordDocCore";
	private static SolrClient solr = new HttpSolrClient.Builder(url).build();
	
	public void indexDocument(DocumentObject documentObject)
	{
		if(documentObject != null)
		{
			SolrInputDocument sDoc = new SolrInputDocument();
			sDoc.addField("id", documentObject.getDocId());
			sDoc.addField("body", documentObject.getBody());
			sDoc.addField("fileLocation", documentObject.getFileLocation());
			sDoc.addField("creationTime", documentObject.getCreationTime());
			sDoc.addField("creationTimeString", documentObject.getCreationTimeString());
			sDoc.addField("lastAccessTime", documentObject.getLastAccessTime());
			sDoc.addField("lastAccsessTimeString", documentObject.getLastAccsessTimeString());
			sDoc.addField("lastModifiedTime", documentObject.getLastModifiedTime());
			sDoc.addField("lastModifiedTimeString", documentObject.getLastModifiedTimeString());
			sDoc.addField("size", documentObject.getDocSize());
			sDoc.addField("fileType", documentObject.getDocType());
			sDoc.addField("mediaBaseType", documentObject.getMediaBaseType());
			sDoc.addField("mediaType", documentObject.getMediaType());
			sDoc.addField("mediaSubType", documentObject.getMediaSubType());
			sDoc.addField("fileParent", documentObject.getFileParent());
			sDoc.addField("lastSaveDate", documentObject.getLastSaveDate());
			sDoc.addField("pageCount", documentObject.getPageCount());
			sDoc.addField("fileName", documentObject.getFileName());
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
			
			try {
				solr.add(sDoc);
			} catch (SolrServerException | IOException e) {
				System.out.println("Cannot add document "+documentObject.getFileName());
				
				e.printStackTrace();
				
			}
			try {
				solr.commit();
				
			} catch (SolrServerException | IOException e) {
				
				System.out.println("Cannot commit document "+documentObject.getFileName());
				e.printStackTrace();
			}
			
		}
	}
	
	public static void main(String[] args)
	{
		System.out.println(url);
	}
	
}

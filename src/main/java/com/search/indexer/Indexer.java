package com.search.indexer;

import org.apache.solr.client.solrj.response.UpdateResponse;

import com.search.documentMapping.DocumentObject;



public interface Indexer {
	void index();
	UpdateResponse indexDocument(DocumentObject documentObject);
}

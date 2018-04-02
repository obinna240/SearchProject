package com.ob.interfaces;

import java.util.List;

public interface IndexerInterface {
	Integer indexAll();
	Integer indexDocument(IDocument document);
	List<Integer> indexDocuments(List<IDocument> documents);
	Integer deleteDocument(IDocument document);
	Integer deleteDocuments(List<IDocument> documents);
	
}

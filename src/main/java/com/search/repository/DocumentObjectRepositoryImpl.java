package com.search.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import com.search.documentMapping.DocumentObject;

/**
 * We can include other methods here to suit our needs, same as in 
 * DocumentHistoryOperations
 * @author oonyimadu
 *
 */
public class DocumentObjectRepositoryImpl implements DocumentObjectOperations
{
	@Autowired 
	MongoOperations mongoOperations;
	
	@Override
	public Boolean isLargeDoc(String docId) {
		// TODO Auto-generated method stub
		Boolean isLarge = true;
		DocumentObject docObject = mongoOperations.findById(docId, DocumentObject.class);
		long size = docObject.getDocSize();
		if(size > 1000000)
		{
			
			isLarge = true;
			
		}
		return isLarge;
	}
	
}

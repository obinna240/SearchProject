package com.search.repository;

import org.springframework.data.repository.CrudRepository;

import com.search.documentMapping.DocumentHistory;
import com.search.documentMapping.DocumentObject;

import java.lang.String;
import java.util.Date;
import java.util.List;
import java.lang.Boolean;

public interface DocumentObjectRepository extends CrudRepository<DocumentObject, String>, DocumentObjectOperations
{
	
	List<DocumentObject> findByDocId(String docid);
	List<DocumentObject> findByObjectIDAndDocId(String objectid);
	List<DocumentObject> findByTitleContainingIgnoreCase(String title);
	
	
	List<DocumentObject> findByIsCurrent(Boolean isCurrent);
	List<DocumentObject> findByIsIndexed(Boolean isCurrent);
	
	List<DocumentObject> findBySavingDateBetween(Date start, Date end);
	

}


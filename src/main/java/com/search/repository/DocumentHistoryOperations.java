package com.search.repository;

import java.util.Date;
import java.util.List;

import com.search.documentMapping.DocumentHistory;

/**
 * We implement this interface to carry out complex queries
 * that are not handled by SpringMonogRepository Implementation
 * As such it is also extended by DocumentHistoryRepository
 * which extends CrudRepository
 * @author oonyimadu
 *
 */
public interface DocumentHistoryOperations {
	
	List<DocumentHistory> getDocumentsWithLargeHistories();

	List<DocumentHistory> flushOldDocuments();
	List<DocumentHistory> flushOldDocumentsWithinDuration(Date startDate, Date endDate);
	Integer computeHistorySize(String historyID);
	
}

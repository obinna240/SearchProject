package com.search.repository;

import org.springframework.data.repository.CrudRepository;

import com.search.documentMapping.DocumentHistory;
import java.lang.String;
import java.util.List;
import java.util.Date;

/**
 * We will use this repository interface and not its implementation i.e DocumentHistoryRepositoryImpl
 * This is why it also extends DocumentHistoryOperations
 * @author oonyimadu
 *
 */
public interface DocumentHistoryRepository extends CrudRepository<DocumentHistory, String>, DocumentHistoryOperations
{
	List<DocumentHistory> findByHistoryID(String historyid);
	List<DocumentHistory> findByCheckSum(String checksum);
	
	List<DocumentHistory> findByDateOfIndexing(Date dateofindexing);
	List<DocumentHistory> findByDateOfSaving(Date dateofsaving);

	
	List<DocumentHistory> findByDateOfIndexingBetween(Date start, Date end);
	
	List<DocumentHistory> findByDateOfSavingBetween(Date start, Date end);
	
	
	List<DocumentHistory> findByHistoryIDAndCheckSum(String historyid, String checkSum);
	DocumentHistory findByFileLocation(String fileLocation);
}

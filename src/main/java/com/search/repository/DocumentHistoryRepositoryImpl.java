package com.search.repository;

import java.util.Date;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import com.search.documentMapping.DocumentHistory;
import com.search.documentMapping.DocumentObject;

/**
 * Spring will automatically detect that DocumentHistoryRepositoryImpl implements DocumentHistoryOperations
 * @author oonyimadu
 *
 */
public class DocumentHistoryRepositoryImpl implements DocumentHistoryOperations
{
	@Autowired
	MongoOperations mongoOperations;
	

	@Override
	public List<DocumentHistory> getDocumentsWithLargeHistories() {
		// TODO Auto-generated method stub
		return null;
	}
	


	@Override
	public List<DocumentHistory> flushOldDocuments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DocumentHistory> flushOldDocumentsWithinDuration(
			Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer computeHistorySize(String historyID) {
		Integer sumOfSize = 0;
		DocumentHistory docHistory = mongoOperations.findById(historyID, DocumentHistory.class);
		if(docHistory != null)
		{
			Map<String, DocumentObject> docMaps = docHistory.getDocumentAndVersion();
			if(MapUtils.isNotEmpty(docMaps))
			{
				for(DocumentObject obj:docMaps.values())
				{
					long docSize = obj.getDocSize();
					sumOfSize = (int) (sumOfSize+docSize);
				}
				/**
				Iterator<Entry<String, DocumentObject>> iterators = docMaps.entrySet().iterator();
				while(iterators.hasNext())
				{
					Entry<String, DocumentObject> entry = iterators.next();
					//String key = entry.getKey();
					DocumentObject obj = entry.getValue();
					long docSize = obj.getDocSize();
					sumOfSize = (int) (sumOfSize+docSize);
					
				}
				*/
			}
		}
		return sumOfSize;
	}

	
}

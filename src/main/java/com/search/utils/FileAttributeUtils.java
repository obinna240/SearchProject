package com.search.utils;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.data.annotation.Id;

import com.search.documentMapping.DocumentHistory;
import com.search.documentMapping.DocumentObject;

public class FileAttributeUtils
{
	public static DocumentObject addFeatures(Path file, BasicFileAttributes attrs, DocumentObject documentObject, 
			boolean isNew, String documentHistoryID, DocumentHistory dHistory, String checkSum,
			boolean isCurrent, boolean isDeletedFromIndex)
	{
		documentObject=documentObject==null?new DocumentObject():documentObject;
		// do parse here and include the following in metadata
		 FileTime ftime = attrs.creationTime();
		 if(ftime!=null)
		 {
			 String creationTime = WalkerUtils.fileTimeToString(ftime);
			 documentObject.setCreationTimeString(creationTime);
			 Date creationDate = WalkerUtils.fileTimeToDate(ftime);
			 documentObject.setCreationTime(creationDate);
		 }
		 
		 FileTime faccesstime = attrs.lastAccessTime();
		 if(faccesstime!=null)
		 {
			 String lastAccsessTime = WalkerUtils.fileTimeToString(faccesstime);
			 documentObject.setLastAccsessTimeString(lastAccsessTime );
			 Date lastAccsessDate = WalkerUtils.fileTimeToDate(faccesstime);
			 documentObject.setLastAccessTime(lastAccsessDate);
		 }
		 
		 FileTime fmodtime = attrs.lastModifiedTime();
		 if(fmodtime!=null)
		 {
			 String lastModifiedTime = WalkerUtils.fileTimeToString(fmodtime);
			 documentObject.setLastModifiedTimeString(lastModifiedTime);
			 Date lastModifiedDate = WalkerUtils.fileTimeToDate(fmodtime);
			 documentObject.setLastModifiedTime(lastModifiedDate);
		 }
		 
		 long fsize = attrs.size();
		 documentObject.setDocSize(fsize);
		 
		 
		 String fileType = WalkerUtils.checkDocumentType(attrs);
		 documentObject.setDocType(fileType);
		 documentObject.setCheckSum(checkSum);
		 
		 String fileParent = file.getParent().toString();
		 documentObject.setFileParent(fileParent);
		 String actualFileLocation  = file.toString();
		 documentObject.setFileLocation(actualFileLocation);
		 documentObject.setFileName(file.getFileName().toString());
		 documentObject.setObjectID(documentHistoryID);
		 
		 dHistory.setCheckSum(checkSum); //checkSum of the most recent document
		 
		 Map<String, DocumentObject> mapO = dHistory.getDocumentAndVersion();
		 Integer versionNumber = 0;
		 documentObject.setIsCurrent(isCurrent);
		documentObject.setIsDeletedFromIndex(isDeletedFromIndex);
		String docID = "";
		if(isNew)
		{
			dHistory.setHistoryID(documentHistoryID);
			dHistory.setFileLocation(actualFileLocation);
			docID = documentHistoryID+"_ID_"+versionNumber+1;
			documentObject.setDocId(docID);
			documentObject.setVersionNumber(Integer.toString(versionNumber+1));
			dHistory.setIsModified(false);
			dHistory.setNumberOfVersions(versionNumber++);
			dHistory.setTotalSizeOfHistoryOnDisk(fsize);
			
		}
		else
		{
			versionNumber = dHistory.getNumberOfVersions();
			docID = documentHistoryID+"_ID_"+versionNumber++;
			documentObject.setDocId(docID);
			documentObject.setVersionNumber(Integer.toString(versionNumber++));
			dHistory.setIsModified(true);
			dHistory.setNumberOfVersions(versionNumber++);
			Long newSize = dHistory.getTotalSizeOfHistoryOnDisk();
			
			newSize = newSize!=null?newSize+fsize:0+fsize;
			
			dHistory.setTotalSizeOfHistoryOnDisk(newSize);
			
		}
		
		if(MapUtils.isEmpty(mapO))
		{
			mapO = new HashMap<String, DocumentObject>();
			mapO.put(docID, documentObject);
			
		}
		else
		{
			mapO.put(docID, documentObject);
		}
		dHistory.setDocumentAndVersion(mapO);
		
		return documentObject;
	}
}



//private Integer totalSizeOfHistoryOnDisk;





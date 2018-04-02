package com.search.documentMapping;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Stores every DocumentObject
 * Each document, whether old or new is stored as a DocumetObject in
 * the DocumentObject collection. It is then referenced in the 
 * DocumentHistory Collection.
 * @author oonyimadu
 *
 */
@Document
public class DocumentObject 
{
	@Id
	private String docId;
	private String objectID; //used to reference DocumentHistory
	
	private Date creationTime;
	private String creationTimeString;
		
	private List<String> title;
	private String fileName;

	private Date lastAccessTime;

	private String lastAccsessTimeString;
	private Date lastModifiedTime;
	private String lastModifiedTimeString;
	private String mediaBaseType;
	private String mediaType;
	private String mediaSubType;
	
	
	
	private String body;
	private String fileLocation;
	
	private long docSize;
	private List<String> authors;
	private Integer pageCount;
	private String fileParent;
	
	private String docType;
	private String checkSum;

	private List<Object> namedEntities;
	
	
	private String versionNumber;
	
	private Boolean isCurrent; //is it the most recent document
	private Boolean isIndexed; //is it indexed
	
	
	private Boolean isDeletedFromIndex; //tells us if the document has been deleted from the index
	
	private Date savingDate;

	
	private List<String> classification;
	private Map<String, Double> LDAClassPercentages;
	
	
	public Date getSavingDate() {
		return savingDate;
	}
	public void setSavingDate(Date savingDate) {
		this.savingDate = savingDate;
	}
	
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public long getDocSize() {
		return docSize;
	}
	public void setDocSize(long docSize) {
		this.docSize = docSize;
	}
	public List<String> getAuthors() {
		return authors;
	}
	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public String getFileParent() {
		return fileParent;
	}
	public void setFileParent(String fileParent) {
		this.fileParent = fileParent;
	}
	public String getCheckSum() {
		return checkSum;
	}
	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}
	public List<String> getTitle() {
		return title;
	}
	public void setTitle(List<String> title) {
		this.title = title;
	}
	public List<Object> getNamedEntities() {
		return namedEntities;
	}
	public void setNamedEntities(List<Object> namedEntities) {
		this.namedEntities = namedEntities;
	}

	public Boolean getIsCurrent() {
		return isCurrent;
	}
	public void setIsCurrent(Boolean isCurrent) {
		this.isCurrent = isCurrent;
	}
	public String getObjectID() {
		return objectID;
	}
	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}
	public String getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	public Boolean getIsIndexed() {
		return isIndexed;
	}
	public void setIsIndexed(Boolean isIndexed) {
		this.isIndexed = isIndexed;
	}

	public Boolean getIsDeletedFromIndex() {
		return isDeletedFromIndex;
	}
	public void setIsDeletedFromIndex(Boolean isDeletedFromIndex) {
		this.isDeletedFromIndex = isDeletedFromIndex;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getCreationTimeString() {
		return creationTimeString;
	}
	public void setCreationTimeString(String creationTimeString) {
		this.creationTimeString = creationTimeString;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Date getLastAccessTime() {
		return lastAccessTime;
	}
	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	public String getLastAccsessTimeString() {
		return lastAccsessTimeString;
	}
	public void setLastAccsessTimeString(String lastAccsessTimeString) {
		this.lastAccsessTimeString = lastAccsessTimeString;
	}
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public String getLastModifiedTimeString() {
		return lastModifiedTimeString;
	}
	public void setLastModifiedTimeString(String lastModifiedTimeString) {
		this.lastModifiedTimeString = lastModifiedTimeString;
	}
	public String getMediaBaseType() {
		return mediaBaseType;
	}
	public void setMediaBaseType(String mediaBaseType) {
		this.mediaBaseType = mediaBaseType;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public String getMediaSubType() {
		return mediaSubType;
	}
	public void setMediaSubType(String mediaSubType) {
		this.mediaSubType = mediaSubType;
	}
	public List<String> getClassification() {
		return classification;
	}
	public void setClassification(List<String> classification) {
		this.classification = classification;
	}
	public Map<String, Double> getLDAClassPercentages() {
		return LDAClassPercentages;
	}
	public void setLDAClassPercentages(Map<String, Double> lDAClassPercentages) {
		LDAClassPercentages = lDAClassPercentages;
	}
	public Object getLastSaveDate() {
		// TODO Auto-generated method stub
		return null;
	}


}




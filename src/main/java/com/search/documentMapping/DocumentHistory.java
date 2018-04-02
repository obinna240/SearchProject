package com.search.documentMapping;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * DocumentHistory collection is a catalogue of Saved Documents and 
 * everything that has happened to them. Each document has a shared historyID
 * For instance, a document titled 'my-phd-thesis' could have 10 versions which will be part
 * of its history
 * @author oonyimadu
 *
 */
@Document
public class DocumentHistory 
{
	@Id
	private String historyID;  //same as objectID in DocumentObject
	private String checkSum; //this is the checksum of the most recent version
	private String fileLocation;
	
	//number of versions should be equal to count(documentAndVersion/2)
	private Integer numberOfVersions; 
	
	private Long totalSizeOfHistoryOnDisk;
	
	private Boolean isModified; //tells us if the document is modified
		
	private Map<String, DocumentObject> documentAndVersion;
	
	private Date dateOfIndexing; //date of last index
	//private Date dateOfDeletion; //date of last deletion
	private Date dateOfSaving; //date of last save
	
	
	
	
	//private Map<String, String> listOfVersionsAndStates; //{"1":"exists"}, {"2":"deleted"}
	
	public String getHistoryID() {
		return historyID;
	}
	public void setHistoryID(String historyID) {
		this.historyID = historyID;
	}
	
	public String getCheckSum() {
		return checkSum;
	}
	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	public Boolean getIsModified() {
		return isModified;
	}
	public void setIsModified(Boolean isModified) {
		this.isModified = isModified;
	}

	public Map<String, DocumentObject> getDocumentAndVersion() {
		return documentAndVersion;
	}
	public void setDocumentAndVersion(Map<String, DocumentObject> documentAndVersion) 
	{
		this.documentAndVersion = documentAndVersion;
	}
	public Date getDateOfIndexing() {
		return dateOfIndexing;
	}
	public void setDateOfIndexing(Date dateOfIndexing) {
		this.dateOfIndexing = dateOfIndexing;
	}
	
	public Date getDateOfSaving() {
		return dateOfSaving;
	}
	public void setDateOfSaving(Date dateOfSaving) {
		this.dateOfSaving = dateOfSaving;
	}
	public Integer getNumberOfVersions() {
		return numberOfVersions;
	}
	public void setNumberOfVersions(Integer numberOfVersions) {
		this.numberOfVersions = numberOfVersions;
	}



	public Long getTotalSizeOfHistoryOnDisk() {
		return totalSizeOfHistoryOnDisk;
	}
	public void setTotalSizeOfHistoryOnDisk(Long totalSizeOfHistoryOnDisk) {
		this.totalSizeOfHistoryOnDisk = totalSizeOfHistoryOnDisk;
	}

	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

}

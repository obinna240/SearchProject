package com.search.query;

public class QueryObject {
	
	private String queryString;
	private String queryType;
	private String querySuggestion;
	private boolean saveSearch;
	
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getQuerySuggestion() {
		return querySuggestion;
	}
	public void setQuerySuggestion(String querySuggestion) {
		this.querySuggestion = querySuggestion;
	}
	public boolean isSaveSearch() {
		return saveSearch;
	}
	public void setSaveSearch(boolean saveSearch) {
		this.saveSearch = saveSearch;
	}
	
	
	
}

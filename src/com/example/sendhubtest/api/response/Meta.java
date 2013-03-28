package com.example.sendhubtest.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Response calss for the Meta object
 * 
 * @author vpenemetsa
 *
 */
public class Meta {
	
	private String limit;
	
	private String next;
	
	private String offset;
	
	private String previous;
	
	@SerializedName("total_count")
	private String totalCount;

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	
	
}

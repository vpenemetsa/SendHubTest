package com.example.sendhubtest.api.response;

/**
 * The contact data model
 * 
 * @author vpenemetsa
 *
 */
public class Contact {
	
	private String id;
	
	private String name;
	
	private String number;
	
	private String userId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}

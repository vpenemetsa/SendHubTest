package com.example.sendhubtest.api.response;

import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * Base response model 
 * 
 * @author vpenemetsa
 *
 */
public class BaseContactResponse {

	private Meta meta;
	
	private List<Contact> objects;
	
	private HttpStatus responseCode;
	
	public Meta getMeta() {
		return meta;
	}
	public void setMeta(Meta meta) {
		this.meta = meta;
	}
	
	public List<Contact> getObjects() {
		return objects;
	}
	public void setObjects(List<Contact> contacts) {
		this.objects = contacts;
	}
	
	public HttpStatus getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(HttpStatus responseCode) {
		this.responseCode = responseCode;
	}
}

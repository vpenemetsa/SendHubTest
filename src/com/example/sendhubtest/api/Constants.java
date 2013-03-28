package com.example.sendhubtest.api;

/**
 * Constants used across classes
 * 
 * @author vpenemetsa
 *
 */
public class Constants {

	public static final String API_KEY = "ENTER YOUR API KEY HERE";
	public static final String USERNAME = "ENTER YOUR PHONE NUMBER HERE";
	
	public static final String BASE_API = "https://api.sendhub.com";
	public static final String GET_CONTACTS = "https://api.sendhub.com/v1/contacts/";
	public static final String SEND_MESSAGE = "https://api.sendhub.com/v1/messages/?username=" + USERNAME + "&api_key=" + API_KEY;
	
	public static final String CONTACTS_API = "https://api.sendhub.com/v1/contacts/?username=" + USERNAME + "&api_key=" + API_KEY;
	public static final String CONTACT_ID = "CONTACT_ID";
	public static final String RESULT_STATE = "RESULT_STATE";
}

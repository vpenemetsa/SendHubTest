package com.example.sendhubtest.api;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;

import com.example.sendhubtest.api.response.BaseContactResponse;
import com.example.sendhubtest.database.DatabaseControl;
import com.google.gson.GsonBuilder;

/**
 * Api class. Handles http requests
 * 
 * @author vpenemetsa
 *
 */
public class SendHubApi {
	
	Context mContext;
	DatabaseControl dbControl;
	
	public SendHubApi(Context context) {
		mContext = context;
		dbControl = new DatabaseControl(mContext);
	}
	
	public void getContacts(String url) {
		BaseContactResponse bcr = doRequest(mContext, HttpMethod.GET, url, BaseContactResponse.class);
		dbControl.saveContacts(bcr);
		if (bcr.getMeta().getNext() != null) {
			this.getContacts(Constants.BASE_API + bcr.getMeta().getNext());
		}
	}
	
	public HttpStatus postMessage(ArrayList<Integer> contacts, String message) {
		MessagePostData postData = new MessagePostData();
		postData.setContacts(contacts);
		postData.setText(message);
		
		return doMessagePostRequest(mContext, Constants.SEND_MESSAGE, BaseContactResponse.class, postData);
	}
	
	public HttpStatus postNewUser(String name, String number) {
		UserPostData postData = new UserPostData();
		postData.setName(name);
		postData.setNumber(number);
		
		return doUserCreatePostRequest(mContext, Constants.CONTACTS_API, BaseContactResponse.class, postData);
	}
	
	
	protected static <T extends BaseContactResponse> BaseContactResponse doRequest(Context context, HttpMethod method, String url, Class<T> clazz) {
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
		
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		RestTemplate restTemplate = new RestTemplate();
		
		GsonBuilder builder = new GsonBuilder();
		
//		 Add the Gson message converter
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter(builder.create()));
		
		ResponseEntity<T> responseEntity = null;
		responseEntity = restTemplate.exchange(url, method, requestEntity, clazz); 
		
		T response = responseEntity.getBody();
		
		response.setResponseCode(responseEntity.getStatusCode());
		return response;
	}

	protected static <T extends BaseContactResponse> HttpStatus doMessagePostRequest(Context context, String url, Class<T> clazz, MessagePostData postParams) {
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(new MediaType("application","json"));
		HttpEntity<MessagePostData> requestEntity = new HttpEntity<MessagePostData>(postParams, requestHeaders);
		
		RestTemplate restTemplate = new RestTemplate();
		
		GsonBuilder builder = new GsonBuilder();
		
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter(builder.create()));
		restTemplate.setErrorHandler(new CustomErrorHandler());
		
		return restTemplate.exchange(url, HttpMethod.POST, requestEntity, null).getStatusCode();
	}
	
	protected static <T extends BaseContactResponse> HttpStatus doUserCreatePostRequest(Context context, String url, Class<T> clazz, UserPostData postParams) {
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(new MediaType("application","json"));
		HttpEntity<UserPostData> requestEntity = new HttpEntity<UserPostData>(postParams, requestHeaders);
		
		RestTemplate restTemplate = new RestTemplate();
		GsonBuilder builder = new GsonBuilder();
		
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter(builder.create()));
		restTemplate.setErrorHandler(new CustomErrorHandler());
		
		return restTemplate.exchange(url, HttpMethod.POST, requestEntity, null).getStatusCode();
	}
	
	class MessagePostData {
		private ArrayList<Integer> contacts;
		private String text;
		
		public ArrayList<Integer> getContacts() {
			return contacts;
		}
		public void setContacts(ArrayList<Integer> contacts) {
			this.contacts = contacts;
		}
		
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
	}
	
	class UserPostData {
		private String name;
		
		private String number;

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
	}
}

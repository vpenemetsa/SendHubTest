package com.example.sendhubtest.api;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

/**
 * Dummy error handler. This class is used instead of the default spring 
 * error handler which for some reason hates a 400 response
 * 
 * @author vpenemetsa
 *
 */
public class CustomErrorHandler implements ResponseErrorHandler {
	public CustomErrorHandler() {}
	@Override
	public void handleError(ClientHttpResponse arg0) throws IOException {
	}

	@Override
	public boolean hasError(ClientHttpResponse arg0) throws IOException {
		return false;
	}
}

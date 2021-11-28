package entity;

import burp.IHttpRequestResponse;
import burp.IHttpService;

public class Message implements IHttpRequestResponse {
	
	private final byte[] request;
	private final byte[] response;
	private final IHttpService httpService;
	
	public Message(byte[] request, byte[] response, IHttpService httpService) {
		this.request = request;
		this.response = response;
		this.httpService = httpService;
	}

	public byte[] getRequest() {
		return request;
	}

	public void setRequest(byte[] message) {}

	public byte[] getResponse() {
		return response;
	}

	public void setResponse(byte[] message) {}

	public String getComment() {
		return null;
	}

	public void setComment(String comment) {}

	public String getHighlight() {
		return null;
	}

	public void setHighlight(String color) {}

	public IHttpService getHttpService() {
		return httpService;
	}

	public void setHttpService(IHttpService httpService) {		
	}
}
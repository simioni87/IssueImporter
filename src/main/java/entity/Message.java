package entity;

import burp.IHttpRequestResponse;
import burp.IHttpService;

public class Message implements IHttpRequestResponse {
	
	final byte[] request;
	final byte[] response;
	
	public Message(String request, String response) {
		this.request = request.getBytes();
		this.response = response.getBytes();
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
		return null;
	}

	public void setHttpService(IHttpService httpService) {		
	}
}
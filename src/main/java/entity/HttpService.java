package entity;

import burp.IHttpService;

public class HttpService implements IHttpService {
	
	private final String host;
	private final int port;
	private final String protocol;
	
	public HttpService(String host, int port, String protocol) {
		this.host = host;
		this.port = port;
		this.protocol = protocol;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getProtocol() {
		return protocol;
	}
}
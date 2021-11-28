package entity;

import java.net.URL;

import burp.IHttpRequestResponse;
import burp.IHttpService;
import burp.IScanIssue;

public class Issue implements IScanIssue {
	
	private IHttpService httpService = null;
	private URL url = null;
	private String name = null;
	private int type = 0x08000000;
	private String severity = null;
	private String confidence = null;
	private String issueBackground = null;
	private String remediationBackground = null;
	private String issueDetail = null;
	private String remediationDetail = null;
	private IHttpRequestResponse[] httpMessages = null;

	public void setUrl(URL url) {
		this.url = url;
	}
	
	public URL getUrl() {
		return url;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getIssueName() {
		return name;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public int getIssueType() {
		return type;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}
	
	public String getSeverity() {
		return severity;
	}

	public void setConfidence(String confidence) {
		this.confidence = confidence;
	}
	
	public String getConfidence() {
		return confidence;
	}

	public void setIssueBackground(String issueBackground) {
		this.issueBackground = issueBackground;
	}
	
	public String getIssueBackground() {
		return issueBackground;
	}

	public void setRemediationBackground(String remediationBackground) {
		this.remediationBackground = remediationBackground;
	}
	
	public String getRemediationBackground() {
		return remediationBackground;
	}

	public void setIssueDetail(String issueDetail) {
		this.issueDetail = issueDetail;
	}
	
	public String getIssueDetail() {
		return issueDetail;
	}
	
	public void setRemediationDetail(String remediationDetail) {
		this.remediationDetail = remediationDetail;
	}

	public String getRemediationDetail() {
		return remediationDetail;
	}

	public void setHttpMessages(IHttpRequestResponse[] httpMessages) {
		this.httpMessages = httpMessages;
	}
	
	public IHttpRequestResponse[] getHttpMessages() {
		return httpMessages;
	}
	
	public void setHttpService(IHttpService httpService) {
		this.httpService = httpService;
	}

	public IHttpService getHttpService() {
		return httpService;
	}
}
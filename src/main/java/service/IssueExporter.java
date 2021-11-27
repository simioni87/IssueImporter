package service;

import java.awt.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

import javax.swing.JFileChooser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import burp.IHttpRequestResponse;
import burp.IScanIssue;
import util.Helper;

public class IssueExporter {
	
	private Component parentComponent = null;
	
	public IssueExporter(IScanIssue[] issues) {
		if(issues == null || issues.length == 0) {
			Helper.showErrorMessage(parentComponent, "No Issues Selected");
		}
		else {
			JFileChooser chooser = new JFileChooser();
			chooser.setSelectedFile(new File("issues.json"));
			int status = chooser.showSaveDialog(parentComponent);
			if(status == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				try {
					FileWriter writer = new FileWriter(file);
					writer.write(getJson(issues).toString());
					writer.close();
				} catch (IOException e) {
					Helper.showErrorMessage(parentComponent, e.getMessage());
				}
			}
		}
	}
	
	private JsonObject getJson(IScanIssue[] issues) {
		JsonObject mainObject = new JsonObject();
		JsonArray issueArray = new JsonArray();
		mainObject.add("issues", issueArray);
		for(IScanIssue issue : issues) {
			JsonObject issueObject = new JsonObject();
			issueArray.add(issueObject);
			issueObject.addProperty("url", issue.getUrl().toString());
			issueObject.addProperty("host", issue.getHttpService().getHost());
			issueObject.addProperty("port", issue.getHttpService().getPort());
			issueObject.addProperty("protocol", issue.getHttpService().getProtocol());
			issueObject.addProperty("name", issue.getIssueName());
			issueObject.addProperty("type", issue.getIssueType());
			issueObject.addProperty("severity", issue.getSeverity());
			issueObject.addProperty("confidence", issue.getConfidence());
			issueObject.addProperty("issueBackground", issue.getIssueBackground());
			issueObject.addProperty("remediationBackground", issue.getRemediationBackground());
			issueObject.addProperty("issueDetail", issue.getIssueDetail());
			issueObject.addProperty("remediationDetail", issue.getRemediationDetail());
			JsonArray messageArray = new JsonArray();
			issueObject.add("httpMessages", messageArray);
			for(IHttpRequestResponse message : issue.getHttpMessages()) {
				String base64Request = null;
				String base64Response = null;
				if(message.getRequest() != null) {
					base64Request = new String(Base64.getEncoder().encode(message.getRequest()));
				}
				if(message.getResponse() != null) {
					base64Response = new String(Base64.getEncoder().encode(message.getResponse()));
				}
				JsonObject messageObject = new JsonObject();
				messageObject.addProperty("requestBase64", base64Request);
				messageObject.addProperty("responseBase64", base64Response);
				messageArray.add(messageObject);
			}
		}
		return mainObject;
	}

}

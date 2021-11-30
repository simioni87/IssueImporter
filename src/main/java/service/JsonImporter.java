package service;

import java.awt.Component;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.Scanner;
import javax.swing.JFileChooser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import burp.BurpExtender;
import burp.IHttpRequestResponse;
import burp.IHttpService;
import entity.HttpService;
import entity.Issue;
import entity.Message;
import util.Helper;

public class JsonImporter {
	
	private final Component parentComponent = null;
	
	public JsonImporter() {
		JFileChooser chooser = new JFileChooser();
		int status = chooser.showOpenDialog(parentComponent);
		if (status == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			if(selectedFile != null) {
				Scanner scanner;
				String jsonString = "";
				try {
					scanner = new Scanner(selectedFile);
					while (scanner.hasNextLine()) {
						jsonString += scanner.nextLine();
					}
					scanner.close();
					parseJson(jsonString);
					Helper.showInfoMessage(parentComponent, "Issues Successfully Imported");
				} catch (Exception e) {
					/*StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);*/
					Helper.showErrorMessage(parentComponent, "Invalid JSON File\n" + e.getMessage());
				}
			}
		}
	}
	
	private void parseJson(String jsonString) throws MalformedURLException {
		JsonArray issueArray = JsonParser.parseString(jsonString)
				.getAsJsonObject()
				.get("issues")
				.getAsJsonArray();
		for(JsonElement el : issueArray) {
			JsonObject issueObject = el.getAsJsonObject();
			Issue issue = new Issue();
			if(getStringValue(issueObject, "url") == null) {
				issue.setUrl(null);
			}
			else {
				issue.setUrl(new URL(getStringValue(issueObject, "url")));
			}
			issue.setName(getStringValue(issueObject, "name"));
			issue.setType(getIntValue(issueObject, "type"));//https://portswigger.net/kb/issues
			issue.setSeverity(getStringValue(issueObject, "severity"));
			issue.setConfidence(getStringValue(issueObject, "confidence"));
			issue.setIssueBackground(getStringValue(issueObject, "issueBackground"));
			issue.setRemediationBackground(getStringValue(issueObject, "remediationBackground"));
			issue.setIssueDetail(getStringValue(issueObject, "issueDetail"));
			issue.setRemediationDetail(getStringValue(issueObject, "remediationDetail"));
			JsonArray messageArray = issueObject.get("httpMessages").getAsJsonArray();
			IHttpRequestResponse[] messages = new IHttpRequestResponse[messageArray.size()];
			for(int i=0; i<messageArray.size(); i++) {
				JsonObject messageObject = messageArray.get(i).getAsJsonObject();
				if(messageObject.get("requestBase64") != null && messageObject.get("responseBase64") != null) {
					byte[] request = Base64.getDecoder().decode(messageObject.get("requestBase64").getAsString());
					byte[] response = Base64.getDecoder().decode(messageObject.get("responseBase64").getAsString());
					IHttpService httpService = new HttpService(getStringValue(messageObject, "host"), 
							getIntValue(messageObject, "port"), getStringValue(messageObject, "protocol"));
					messages[i] = new Message(request, response, httpService);
				}
			}
			issue.setHttpMessages(messages);
			IHttpService httpService = new HttpService(getStringValue(issueObject, "host"), 
					getIntValue(issueObject, "port"), getStringValue(issueObject, "protocol"));
			issue.setHttpService(httpService);
			BurpExtender.callbacks.addScanIssue(issue);
		} 
		
	}
	
	private String getStringValue(JsonObject object, String key) {
		if(object.get(key) != null && object.get(key).isJsonPrimitive()) {
			return object.get(key).getAsString();
		}
		else {
			return null;
		}
	}
	
	private int getIntValue(JsonObject object, String key) {
		if(object.get(key) != null && object.get(key).isJsonPrimitive()) {
			return object.get(key).getAsInt();
		}
		else {
			return 0;
		}
	}
}
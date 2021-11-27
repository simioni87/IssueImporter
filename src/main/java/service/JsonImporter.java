package service;

import java.awt.Component;
import java.io.File;
import java.net.URL;
import java.util.Scanner;
import javax.swing.JFileChooser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import burp.BurpExtender;
import burp.IHttpRequestResponse;
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
				} catch (Exception e) {
					Helper.showErrorMessage(parentComponent, "Can not read File");
				}
			}
		}
	}
	
	private void parseJson(String jsonString) {
		try {
			BurpExtender.callbacks.printOutput(jsonString);
			JsonArray issueArray = JsonParser.parseString(jsonString)
					.getAsJsonObject()
					.get("issues")
					.getAsJsonArray();
			for(JsonElement el : issueArray) {
				JsonObject issueObject = el.getAsJsonObject();
				Issue issue = new Issue();
				issue.setUrl(new URL(issueObject.get("url").getAsString()));
				issue.setName(issueObject.get("name").getAsString());
				issue.setType(issueObject.get("type").getAsInt());
				issue.setSeverity(issueObject.get("severity").getAsString());
				issue.setConfidence(issueObject.get("confidence").getAsString());
				issue.setIssueBackground(issueObject.get("issueBackground").getAsString());
				issue.setRemediationBackground(issueObject.get("remediationBackground").getAsString());
				issue.setIssueDetail(issueObject.get("issueDetail").getAsString());
				issue.setRemediationDetail(issueObject.get("remediationDetail").getAsString());
				JsonArray messageArray = issueObject.get("httpMessages").getAsJsonArray();
				IHttpRequestResponse[] messages = new IHttpRequestResponse[messageArray.size()];
				for(int i=0; i<messageArray.size(); i++) {
					JsonObject messageObject = messageArray.get(i).getAsJsonObject();
					BurpExtender.callbacks.printOutput("asdasd");
					messages[i] = new Message(messageObject.get("request").getAsString(), messageObject.get("response").getAsString());
				}
				
				issue.setHttpMessages(messages);
				BurpExtender.callbacks.printOutput("bbb");//issue hat http service null
				BurpExtender.callbacks.addScanIssue(issue);
			} 
		}
		catch (Exception e) {
			Helper.showErrorMessage(parentComponent, "Invalid JSON File\n" + e.getMessage());
		}
	}
}
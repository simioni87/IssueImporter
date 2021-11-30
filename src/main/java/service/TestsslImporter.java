package service;

import java.awt.Component;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Scanner;
import javax.swing.JFileChooser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import burp.BurpExtender;
import burp.IHttpService;
import entity.HttpService;
import entity.Issue;
import util.Helper;

public class TestsslImporter {
	
	private final Component parentComponent = null;
	
	public TestsslImporter() {
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
	
	private void parseJson(String jsonString) throws Exception {
		JsonReader reader = new JsonReader(new StringReader(jsonString));
		reader.setLenient(true);
		JsonArray testsslFindings = JsonParser.parseReader(reader).getAsJsonArray();
		for(JsonElement el : testsslFindings) {
			JsonObject finding = el.getAsJsonObject();
			if(finding.get("id") != null && finding.get("ip") != null && finding.get("port") != null &&
					finding.get("severity") != null && finding.get("finding") != null) {
				if(!finding.get("severity").getAsString().equals("OK") && !finding.get("severity").getAsString().equals("WARN")) {
					//Only create issues for CVE Findings
					if(finding.get("cve") != null) {
						String host = finding.get("ip").getAsString().split("/")[0];
						int port = finding.get("port").getAsInt();
						String protocol = "https";
						URL url = new URL(protocol+"://"+host+":"+port);
						Issue issue = new Issue();
						issue.setUrl(url);
						issue.setName("TLS Misconfiguration - " + finding.get("id").getAsString());
						issue.setType(0x08000000);
						String severity = finding.get("severity").getAsString();
						if(severity.equals("CRITICAL") || severity.equals("HIGH")) {
							severity = "High";
						}
						if(severity.equals("MEDIUM")) {
							severity = "Medium";
						}
						if(severity.equals("LOW")) {
							severity = "Low";
						}
						if(severity.equals("INFO")) {
							severity = "Information";
						}
						issue.setSeverity(severity);
						issue.setConfidence("Certain");
						issue.setIssueDetail(finding.get("finding").getAsString());
						IHttpService httpService = new HttpService(host, port, protocol);
						issue.setHttpService(httpService);
						BurpExtender.callbacks.addScanIssue(issue);
					}
				}
			}
			else {
				throw new Exception("Expected JSON attributes not found");
			}
		}
	}
}
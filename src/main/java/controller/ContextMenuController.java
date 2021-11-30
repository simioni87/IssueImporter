package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import burp.IContextMenuFactory;
import burp.IContextMenuInvocation;
import service.IssueExporter;
import service.JsonImporter;
import service.TestsslImporter;
import util.Globals;

public class ContextMenuController implements IContextMenuFactory {

	public List<JMenuItem> createMenuItems(final IContextMenuInvocation invocation) {
		List<JMenuItem> menuItems = new ArrayList<JMenuItem>();
		JMenu issueImporterMenu = new JMenu(Globals.EXTENSION_NAME);
		menuItems.add(issueImporterMenu);
		
		JMenuItem importJsonItem = new JMenuItem("Import from JSON");
		importJsonItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new JsonImporter();
			}
		});
		issueImporterMenu.add(importJsonItem);
		
		JMenuItem importTestsslItem = new JMenuItem("Import from Testssl (JSON Format)");
		importTestsslItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new TestsslImporter();
			}
		});
		issueImporterMenu.add(importTestsslItem);
		
		JMenuItem exportIssueItem = new JMenuItem("Export Issue(s)");
		exportIssueItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new IssueExporter(invocation.getSelectedIssues());
			}
		});
		issueImporterMenu.add(exportIssueItem);
		
		return menuItems;
	}
}
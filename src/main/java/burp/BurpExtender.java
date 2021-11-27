package burp;

import controller.ContextMenuController;
import util.Globals;

public class BurpExtender implements IBurpExtender { 

	public static IBurpExtenderCallbacks callbacks;

	public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
		BurpExtender.callbacks = callbacks;
		callbacks.registerContextMenuFactory(new ContextMenuController());
		callbacks.setExtensionName(Globals.EXTENSION_NAME);
		callbacks.printOutput(Globals.EXTENSION_NAME + " successfully started");
		callbacks.printOutput("Version " + Globals.VERSION);
        callbacks.printOutput("Created by Simon Reinhart");
        callbacks.printOutput("Protect7 GmbH");
	}
}
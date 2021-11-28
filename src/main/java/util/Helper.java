package util;

import java.awt.Component;

import javax.swing.JOptionPane;

public class Helper {

	public static void showErrorMessage(Component parent, String text) {
		JOptionPane.showMessageDialog(parent, text, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showInfoMessage(Component parent, String text) {
		JOptionPane.showMessageDialog(parent, text, "Info", JOptionPane.INFORMATION_MESSAGE);
	}
	
}

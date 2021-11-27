package util;

import java.awt.Component;

import javax.swing.JOptionPane;

public class Helper {

	public static void showErrorMessage(Component parent, String text) {
		JOptionPane.showMessageDialog(parent, text);
	}
	
}

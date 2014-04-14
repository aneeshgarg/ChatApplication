package com.ttd.interfaces;

/**
 * @author Aneesh Garg
 *
 */
public interface Context {

	void displayFatalError(String message, String title);

	String getClientName();

	void displayMessage(String message);

}

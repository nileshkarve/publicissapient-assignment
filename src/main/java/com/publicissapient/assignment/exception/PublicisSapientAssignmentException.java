/**
 * 
 */
package com.publicissapient.assignment.exception;

/**
 * 
 */
public class PublicisSapientAssignmentException extends Exception {

	private static final long serialVersionUID = -4424164355554583077L;

	protected String[] errorParams;

	public PublicisSapientAssignmentException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public PublicisSapientAssignmentException(String msg) {
		super(msg);
	}
	
	public PublicisSapientAssignmentException(String msg, String... params) {
		super(msg);
		this.errorParams = params;
	}
	
	public PublicisSapientAssignmentException(String msg, Throwable throwable, String... params) {
		super(msg, throwable);
		this.errorParams = params;
	}

	public String[] getErrorParams() {
		return errorParams;
	}
}

package csc.customexception;



public class InvalidJSON extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidJSON(String message){
		super(message);
	}

}

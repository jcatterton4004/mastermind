package mastermindgui;
/**
 * Exception thrown when a guess does not have 4 characters.
 */
public class MastermindIllegalLengthException extends Exception {
	
	/** 
	 * Creates an instance of the class.
	 * 
	 * This constructor is used to create a new instance of
	 * the MastermindIllegalLengthException that can be thrown
	 * by the Mastermind class if a user's guess is not 4
	 * charactesr.
	 * 
	 * @param message is a string error message to deliver if the exception is thrown
	 */
	public MastermindIllegalLengthException(String message) {
		super(message);
	}
}

package mastermindgui;
/**
 * Exception thrown when a guess contains an invalid character.
 */
public class MastermindIllegalColorException extends Exception {
	
	/** 
	 * Creates an instance of the class.
	 * 
	 * This constructor is used to create a new instance of
	 * the MastermindIllegalColorException that can be thrown
	 * by the Mastermind class if a user's guess has an invalid
	 * color.
	 * 
	 * @param message is a string error message to deliver if the exception is thrown
	 */
	public MastermindIllegalColorException(String message) {
		super(message);
	}
}

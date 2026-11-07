package mastermindgui;

/**
 * Represents the Mastermind game state.
 * 
 * This class holds fields and methods to manage the game
 * state of Mastermind. The game state includes the current
 * correct colors for the user to guess along with the 
 * number of guesses that the user has made.
 */
public class MastermindModel {
	
	private String correctColors;
	private int guessNumber = 1;

	/**
	 * Sets the correctColors field.
	 * 
	 * A string of 4 letters represents the sequence of colors
	 * that the user is attempting to guess. The model holds this
	 * sequence in the field correctColors, which this method 
	 * sets based on the argument given.
	 * 
	 * @param correctColors is a string representation of the correct color sequence.
	 */
	public void setCorrectColors(String correctColors) {
		this.correctColors = correctColors;
	}
	
	/**
	 * Returns the correctColors field.
	 * 
	 * This method returns the string representation of the correct
	 * color sequence that the user is attempting to guess.
	 * 
	 * @return a string sequence of 4 colors.
	 */
	public String getCorrectColors() {
		return correctColors;
	}
	
	/**
	 * Resets the guess number.
	 * 
	 * This method sets the guess number equal to 1 so that it
	 * is reset for when a new game of Mastermind is started.
	 */
	public void resetGuessNumber() {
		guessNumber = 1;
	}
	
	/**
	 * Returns the guess number.
	 * 
	 * This method returns the field guessNumber which indicates
	 * how many times the user has made a guess this game. This is
	 * important because the user is only allowed 10 guesses.
	 * 
	 * @return the number of guesses the user has made.
	 */
	public int getGuessNumber() {
		return guessNumber;
	}
	
	/**
	 * Increments the guess count.
	 * 
	 * This method increments guessNumber by 1 to indicate that 
	 * the user has made a guess. It is important to keep track of
	 * this because the user is only allowed 10 guesses.
	 */
	public void makeGuess() {
		guessNumber++;
	}
 }

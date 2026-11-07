package mastermindgui;

import java.util.Random;

/**
 * Represents the Mastermind game behavior.
 * 
 * This method contains the methods to respond to user
 * actions made during the game of Mastermind. It generates
 * the correct color sequence and can determine a user's 
 * guess's correctness. It also contains the methods to
 * update the model's game state.
 */
public class MastermindController {
	
	private MastermindModel model;
	
	/**
	 * Creates a new instance of MastermindController.
	 * 
	 * This method is a constructor for the MastermindController class. It
	 * creates a new instance of MastermindModel to hold the game state of
	 * the game of Mastermind.
	 */
	public MastermindController() {
		this.model = new MastermindModel();
	}

	/**
	 * Generates a random string of colors for the user to guess.
	 * 
	 * This method is used to generate a random sequence of 4 colors that the
	 * player will be attempting to guess in the current game. It produces a string
	 * of length 4 containing a random combination of the letters r, o, y, g, b, or 
	 * p, which each represent a color. It chooses the colors randomly by selecting
	 * a random integer index between 0 and 6 (exclusive) and adding the letter at
	 * that index in the string "roygbp" to a new string.
	 * 
	 * @return correctColors is a string of length 4 of a random combination of colors (r/o/y/g/b/p)
	 */
	public String generateColors() { 
		String colors = "roygbp";
		String correctColors = ""; // will hold the random combination of colors
		Random random = new Random();
		for (int i = 0; i < 4; i++) {
			int randomIndex = random.nextInt(6); // generates random index to choose next random color
			correctColors += colors.charAt(randomIndex);
		}
		return correctColors;
	}
	
	/**
	 * Evaluates the result of a user's guess and determines if an error occurred.
	 * 
	 * This method evaluates whether a user's guess is valid and correct by calling
	 * the methods validateGuess and checkGuess respectively. If the guess was invalid,
	 * it catches the exception and throws an error to be caught by another method. It
	 * returns the result of checkGuess().
	 *
	 * @param guess is a string inputted by the user to be validated and checked as a guess
	 * @return a string indicating the result of the guess
	 * @throws MastermindIllegalLengthException if the guess isn't 4 characters
	 * @throws MastermindIllegalColorException if the guess contains an invalid character
	 */
	public String getGuessResult(String guess) throws MastermindIllegalLengthException, MastermindIllegalColorException {
		try {
			validateGuess(guess); // ensures that guess is valid, may throw one of two exceptions
			return checkGuess(guess); // checks if guess is correct and returns "Correct" result
		} catch (MastermindIllegalLengthException lengthException) { // catches exception if the guess wasn't 4 characters
			throw lengthException;
		} catch (MastermindIllegalColorException colorException) { // catches exception if the guess had an invalid character
			throw colorException;
		}
	}
	
	/** 
	 * Checks that the inputted guess is has valid length and contents.
	 * 
	 * This method ensures that the user enters a valid guess. It checks if the user's 
	 * input is of length 4 and consists of only letters representing valid colors in
	 * the game (r/o/y/g/b/p). It will throw one of two exceptions if the guess is
	 * not valid, MastermindIllegalLengthException or MastermindIllegalColorException.
	 *
	 * @param guess is a string inputted by the user to be validated
	 * @throws MastermindIllegalLengthException if the guess isn't 4 characters
	 * @throws MastermindIllegalColorException if the guess contains an invalid character
	 */
	public void validateGuess(String guess) throws MastermindIllegalLengthException, MastermindIllegalColorException {
		if (guess.length() != 4) { // checks if guess length is 4 characters
			throw new MastermindIllegalLengthException("Guess must be 4 characters long.\n"); 
		}
		String colors = "roygbp";
		for (int i = 0; i < 4; i++) {
			if (!colors.contains(String.valueOf(guess.charAt(i)))) { // checks that each character is a valid color to guess
				throw new MastermindIllegalColorException("Guess may only contain the letters r, o, y, g, b, and p.\n");
			}
		}
	}
	
	/** 
	 * Checks how many colors are correct and how many are correct but in the wrong place.
	 * 
	 * This method is used to check if a guess is correct. It calls the methods checkCorrectPlaces
	 * and checkIncorrectPlaces to see how many colors are in the correct places and how many are in
	 * the sequence but are in the incorrect places. If all 4 colors are in the correct place, it 
	 * returns an empty string. If not, it returns a message with the number of colors in the 
	 * correct/incorrect places.
	 * 
	 * @param guess is a valid string inputted by the user to be checked as a guess
	 * @return an empty string if the guess was correct and a correctness message if incorrect
	 */
	public String checkGuess(String guess) {
		int[] correctPlaces = checkCorrectPlaces(guess); // calls method to determine colors in correct places
		int correctCount = correctPlaces[0] + correctPlaces[1] + correctPlaces[2] + correctPlaces[3];
		int incorrectCount = checkIncorrectPlaces(guess, correctPlaces); // calls method to determine incorrectly placed colors
		
		if (correctCount == 4) { // returns an empty string if the guess was correct
			return "";
		} else { // returns correct color count and incorrect color count as feedback for the user
			return "Colors in the correct place: " + correctCount + "\n" + "Colors correct but in wrong position: " + incorrectCount;
		}
	}
	
	/** 
	 * Checks which colors in the guess are in the correct place.
	 * 
	 * This method is used to check which colors in the guess match the correct sequence 
	 * of colors. It stores the results in an integer array of length 4, with a 1 at index i 
	 * indicating the color at index i of the guess is correct and a 0 at index i indicating 
	 * the color at index i of the guess is incorrect. This array will be used to determine
	 * if the whole guess is correct and to avoid counting duplicate incorrect colors in the
	 * wrong place.
	 *
	 * @param guess is a valid string inputted by the user to be checked as a guess
	 * @return an integer array of length 4 with 1's indicating a correct color/place and 0's indicating an incorrect color/place
	 */
	public int[] checkCorrectPlaces(String guess) {
		int[] correctPlaces = new int[4]; // creates an array to hold correct/incorrect places
		
		for (int i = 0; i < 4; i++) {
			if (guess.charAt(i) == model.getCorrectColors().charAt(i)) {
				correctPlaces[i] = 1; // puts 1 at index if that index has the correct color
			} else {
				correctPlaces[i] = 0; // puts 0 at index if that index has the incorrect color
			}
		}
		return correctPlaces;
	}
	
	/** 
	 * Checks which colors in the guess are in the answer but not in the correct place.
	 * 
	 * This method is used to determine how many colors in the guess are in the sequence 
	 * but are in the incorrect place. It locates an incorrect color and iterates through
	 * correctColors to see if it is present at any other spot in the sequence. It uses the 
	 * array produced by checkCorrectGuessed to check if any incorrect colors are present 
	 * in another place in the sequence that have not been guessed correctly already.
	 *
	 * @param guess is a valid string inputted by the user to be checked as a guess
	 * @param correctPlaces is an integer array of length 4 with 1's indicating a correct color/place and 0's indicating an incorrect color/place
	 * @return incorrectPlaces is an integer indicating how many colors in the guess are in the answer but not in the correct place
	 */
	public int checkIncorrectPlaces(String guess, int[] correctPlaces) {
		int incorrectPlaces = 0;
		for (int i = 0; i < 4; i++) { 
			if (correctPlaces[i] == 0) { // checks only incorrect colors
				for (int j = 0; j < 4; j++) {
					if (guess.charAt(i) == model.getCorrectColors().charAt(j) && correctPlaces[j] == 0) {
						incorrectPlaces++; // increments incorrect count if color exists at other place in sequence that wasn't already guessed
						break;
					}
				}
			}
		}
		return incorrectPlaces;
	}
	
	/**
	 * Calls the model's method to sets the correctColors field.
	 * 
	 * A string of 4 letters represents the sequence of colors
	 * that the user is attempting to guess. The model holds this
	 * sequence in the field correctColors, which this method 
	 * sets based on the argument given.
	 * 
	 * @param correctColors is a string representation of the correct color sequence.
	 */
	public void setCorrectColors(String correctColors) {
		model.setCorrectColors(correctColors);
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
		return model.getCorrectColors();
	}
	
	/**
	 * Resets the guess number.
	 * 
	 * This method sets the guess number equal to 1 so that it
	 * is reset for when a new game of Mastermind is started.
	 */
	public void resetGuessNumber() {
		model.resetGuessNumber();
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
		return model.getGuessNumber();
	}
	
	/**
	 * Increments the guess count.
	 * 
	 * This method increments guessNumber by 1 to indicate that 
	 * the user has made a guess. It is important to keep track of
	 * this because the user is only allowed 10 guesses.
	 */
	public void makeGuess() {
		model.makeGuess();
	}
}

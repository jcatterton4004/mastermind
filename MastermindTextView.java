package mastermindgui;
import java.util.Scanner;

/**
 * Implements the game of Mastermind in a text view.
 * 
 * This class contains the methods to facilitate multiple games of Mastermind for
 * a user to play by printing prompts and receiving user input in the console. A
 * game of Mastermind consists of a player guessing a 4 color sequence, adjusting
 * their guesses based on what colors they get correct and what colors are in the
 * incorrect place.
 */
public class MastermindTextView {
	private MastermindController controller;
	private Scanner input;

	/**
	 * Creates a new instance of MastermindTextView.
	 * 
	 * This method is a constructor for the MastermindTextView class. It creates
	 * a new instance of MastermindController to manage the game along with a
	 * new Scanner object to read input in from the user.
	 */
	public MastermindTextView() {
		this.controller = new MastermindController();
		this.input = new Scanner(System.in);
	}
	
	/** 
	 * Calls the necessary methods to start and continue playing games of Mastermind.
	 * 
	 * This method is called to start a game of Mastermind and it will continue prompting
	 * the user to play Mastermind until they reply with a response of "no". It calls
	 * the methods to prompt the user and ask if they want to play, generate the new
	 * colors for the next game, and check their guesses. 
	 */
	public void startGame() {
		System.out.println("Welcome to Mastermind!");
		boolean firstGame = true; // boolean used to tell promptUser method whether this is the first game the user is playing
		
		while (promptUser(firstGame)) { // loops until the user replies no to the prompt of "Would you like to play (again)?"
			controller.setCorrectColors(controller.generateColors());
			System.out.println("\n" + playGame()); // prints the result of playGame ("You win!" or "You lose!")
			firstGame = false; // sets to false after first game has concluded
		} 
	}

	
	/** 
	 * Prompts the user and determines if they'd like to play a game of Mastermind.
	 * 
	 * This method is used to determine whether the user would like start a new game 
	 * of Mastermind. It prompts them for a response and continues prompting them
	 * until they enter a valid response of either "yes" or "no". If they respond with
	 * yes, then the method will return true, but if they respond with no, then the 
	 * method prints a goodbye message and returns false.
	 *
	 * @param firstGame is a boolean indicating if it's the first game so the method can decide which prompt should be used
	 * @return a boolean true if the user responds yes or a boolean false if they respond no
	 */
	public boolean promptUser(boolean firstGame) {
		String prompt = firstGame ? "Would you like to play? " : "Would you like to play again? "; // alters prompt
		System.out.print(prompt);
		String response = input.nextLine().toLowerCase().strip(); // converts user input to lowercase
		
		while (!response.equals("yes")) { // loops until the user responds yes
			if (response.equals("no")) { // terminates loop if the user doesn't want to play anymore
				System.out.println("\nOkay, goodbye!");
				return false; 
			} else { // requests new input if the user said anything other than yes or no
				System.out.println("Invalid response, please reply yes or no.");
				System.out.print(prompt);
				response = input.nextLine().toLowerCase().strip();
			}
		}
		return true;
	}
	
	/** 
	 * Facilitates the gameplay of a single game of Mastermind.
	 * 
	 * This method is called to play one game of Mastermind. It retrieves input
	 * from the user and calls a method to get the result of that guess. If it is
	 * correct, this method will return a result of "You win!" but if it's incorrect,
	 * then it will ask the user to input another guess until they have either entered
	 * the correct guess or reached 10 incorrect guesses. Upon entering 10 incorrect
	 * guesses, it will return a result of "You lose!"
	 * 
	 * @return result is a string indicating whether the user won or lost this game
	 */
	public String playGame() {
		controller.resetGuessNumber();
		String result = "You lose! The correct answer was " + controller.getCorrectColors(); // result is You lose! by default, will be changed to You win! if user guesses correctly
		
		while (controller.getGuessNumber() <= 10) { // gives user 10 guesses before relaying You lose! message
			System.out.println();
			System.out.printf("Enter guess number %d: ", controller.getGuessNumber());
			String guess = input.nextLine().toLowerCase(); // converts user input to lowercase
			try {
				String guessResult = controller.getGuessResult(guess);
				if (guessResult.equals("")) { // ends current game if the guess was correct
					result = "You win!";
					break;
				} else if (!guessResult.equals("Error")){ // increments guess count if the guess was incorrect
					System.out.println(guessResult);
					controller.makeGuess();
				}
			} catch (MastermindIllegalLengthException lengthException) { // catches exception if the guess wasn't 4 characters
				System.out.printf(lengthException.getMessage());
			} catch (MastermindIllegalColorException colorException) { // catches exception if the guess had an invalid character
				System.out.printf(colorException.getMessage());
			}
		}
		return result;
	}
}

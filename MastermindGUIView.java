package mastermindgui;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/** 
 * Launches and facilitates a GUI view for the game of Mastermind.
 * 
 * This class holds the methods to create a GUI interface for a user
 * to play Mastermind on. It allows the user to make each guess by 
 * selecting colors at the bottom, along with including the options
 * to submit guess, remove last peg, and restart game. 
 */
public class MastermindGUIView extends Application {
	private BorderPane window = new BorderPane();
	private GridPane board = new GridPane();
	private Circle[][] answerGrid = new Circle[10][4];
	private GridPane[] pegBoards = new GridPane[10];
	private MastermindController controller;
	private Button submit;
	private Button remove;
	
	/**
	 * Builds the game window and creates a new game controller.
	 * 
	 * This method starts off a game of Mastermind with a GUI view 
	 * by creating a new stage that consists of BorderPane that displays
	 * the title of the game, a GridPane wrapped in a ScrollPane that
	 * holds the game board, and a BorderPane holding two GridPanes which
	 * each hold different game controls. All of these Panes create 
	 * the Mastermind game window which is launched at the end of the
	 * method. Finally, it creates a new MastermindController.
	 * 
	 * @param stage is a Stage object that will be displayed for the GUI.
	 * @throws Exception if an error occurs with the GUI. Caught in main.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Mastermind");
		
		BorderPane header = new BorderPane(); // creates pane to hold title
		makeHeader(header);
		
		ScrollPane boardPane = new ScrollPane(); // creates pane to hold game board
		makeBoard(boardPane);
		
		BorderPane controlPane = new BorderPane(); // creates pane to hold controls
		GridPane colors = new GridPane();
		GridPane controls = new GridPane();
		makeControlPanel(controlPane, colors, controls);
		addControls(colors, controls);
		
		Scene scene = new Scene(window, 500, 500);
		stage.setScene(scene);
		stage.show(); 
		
		this.controller = new MastermindController();
		controller.setCorrectColors(controller.generateColors());
	}
	
	/**
	 * Creates Mastermind title for window.
	 * 
	 * This method creates a new Label object that will be at the top 
	 * of the window to indicate the title of the game, Mastermind. It
	 * sets the BorderPane's desired height, title formatting, and 
	 * positioning in the window.
	 * 
	 * @param header is a BorderPane that will hold the game's title.
	 */
	public void makeHeader(BorderPane header) {
		Label title = new Label("MASTERMIND");
		header.setPrefHeight(50);
		title.setFont(new Font("System", 24));
		header.setCenter(title);
		window.setTop(header);
	}
	
	/**
	 * Creates the Mastermind game board.
	 * 
	 * This method creates the GridPane that will hold the game board and
	 * wraps it in a Scroll Pane. It sets the GridPane's size and gaps then
	 * puts it into a StackPane which can be put into a ScrollPane. It then
	 * adds the first row of peg slots and a peg board.
	 * 
	 * @param boardPane is a ScrollPane that holds the GridPane/game board.
	 */
	public void makeBoard(ScrollPane boardPane) {
		window.setCenter(boardPane);
		board.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		board.setHgap(10);
		board.setVgap(10);
		
		StackPane boardWrapper = new StackPane(board);
		boardWrapper.setAlignment(Pos.CENTER);
	    boardPane.setContent(boardWrapper);
	    boardPane.setPrefHeight(500);
	    boardPane.setFitToWidth(true);
		                 
		addNewRow(0);
		addPegBoard(0);
	}
	
	/**
	 * Creates control panel at the bottom of the window.
	 * 
	 * This method adds a panel to the bottom of the window that will hold
	 * the controls that allow the user to make moves in the game. It creates
	 * two separate areas that will hold buttons: a line of color buttons at the
	 * top and a line of submit, remove, and restart buttons at the bottom.
	 * 
	 * @param controlPane is a BorderPane to hold both sets of game controls.
	 * @param colors is a GridPane containing the controls to add a color to a guess.
	 * @param controls is a GridPane containing the submit, remove, and restart buttons.
	 */
	public void makeControlPanel(BorderPane controlPane, GridPane colors, GridPane controls) {
		controlPane.setPrefHeight(75);
		window.setBottom(controlPane);
		
		colors.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		colors.setHgap(10);
		colors.setVgap(10);
		controlPane.setTop(colors);
		BorderPane.setAlignment(colors, Pos.CENTER);
		BorderPane.setMargin(colors, new Insets(5, 5, 5, 5));
		
		controls.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		controls.setHgap(10);
		controls.setVgap(10);
		controlPane.setBottom(controls);
		BorderPane.setAlignment(controls, Pos.CENTER);
		BorderPane.setMargin(controls, new Insets(5, 5, 5, 5));
	}
	
	/**
	 * Adds the control buttons to the control panel.
	 * 
	 * This method adds a button of each valid Mastermind color to the 
	 * colors GridPane so that the user may form each guess. When a 
	 * button is pressed, it calls the handleColorClicked() method. It
	 * also creates buttons to a submit a guess, remove the last entered
	 * peg on the current guess, and restart a new game of Mastermind.
	 * 
	 * @param colors is a GridPane containing the controls to add a color to a guess.
	 * @param controls is a GridPane containing the submit, remove, and restart buttons.
	 */
	public void addControls(GridPane colors, GridPane controls) {
		Color[] colorArr = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.PURPLE};
		for (int i = 0; i < 6; i++) { // loops to add each valid Mastermind color
			Button button = new Button();
			button.setOnAction((event) -> {handleColorClicked(button);});
			
			Circle circle = new Circle(10);
			circle.setFill(colorArr[i]);
			button.setGraphic(circle);
			button.setPrefSize(20, 20);
			button.setMaxSize(20, 20);
			colors.add(button, i, 0);
		}
		
		submit = new Button("Submit Guess");
		submit.setOnAction((event) -> {handleSubmitGuess();});
		controls.add(submit, 0, 0);
		
		remove = new Button("Remove Last Peg");
		remove.setOnAction((event) -> {handleRemovePeg();});
		controls.add(remove, 1, 0);
		
		Button restart = new Button("Restart");
		restart.setOnAction((event) -> {handleRestart();});
		controls.add(restart, 2, 0);
	}
	
	/**
	 * Constructs a new row of empty spots and adds it to the board.
	 * 
	 * This method creates a new row for a user to input a guess. It 
	 * adds a Label with the row's number, add four transparent circles 
	 * for the user to fill in with their guess, and stores a reference
	 * to each circle on the answerGrid for easy access.
	 * 
	 * @param rowNum is the number row to be added to the board.
	 */
	public void addNewRow(int rowNum) {
		board.add(new Label(String.valueOf(rowNum + 1)), 0, rowNum);
		
		for (int colNum = 1; colNum < 5; colNum++) { // loops to add 4 empty spots
			Circle circle = new Circle(10);
			circle.setFill(Color.TRANSPARENT);
			board.add(circle, colNum, rowNum);
			answerGrid[rowNum][colNum - 1] = circle;
		}
	}
	
	/**
	 * Adds a spot to hold the pegs indicating the guess's correctness.
	 * 
	 * This method creates a GridPane that will display black and white
	 * pegs that show how many colors are correct and in the incorrect
	 * places respectively in the guess. It puts 4 transparent circles
	 * in the square to be filled by pegs and stores the GridPane in the
	 * pegBoards array for easy access.
	 * 
	 * @param rowNum is the number row to be added to the board.
	 */
	public void addPegBoard(int rowNum) {
		GridPane pegs = new GridPane(); 
		pegs.setHgap(5);
		pegs.setVgap(5);
		pegs.setStyle("-fx-border-color: black; -fx-border-width: 1;");
		pegs.setAlignment(Pos.CENTER);
		pegs.setPrefSize(30, 30);
		
		Circle peg1 = new Circle(4);
		peg1.setFill(Color.TRANSPARENT);
		pegs.add(peg1, 0, 0);
		Circle peg2 = new Circle(4);
		peg2.setFill(Color.TRANSPARENT);
		pegs.add(peg2, 1, 0);
		Circle peg3 = new Circle(4);
		peg3.setFill(Color.TRANSPARENT);
		pegs.add(peg3, 0, 1);
		Circle peg4 = new Circle(4);
		peg4.setFill(Color.TRANSPARENT);
		pegs.add(peg4, 1, 1);
		
		pegBoards[rowNum] = pegs;
		board.add(pegs, 5, rowNum);
	}

	/**
	 * Handles the event that a color button is clicked.
	 * 
	 * This method determines which color button was clicked and adds it
	 * to the current guess as long as the guess isn't full. It fills the
	 * next open spot in the guess with that color. If the guess already
	 * has four colors in it, it does nothing.
	 * 
	 * @param button is the button that was clicked to add a color.
	 */
	public void handleColorClicked(Button button) {
		Circle circle = (Circle) button.getGraphic();
		Color color = (Color) circle.getFill();
		int curRow = controller.getGuessNumber() - 1;
		
		for (int curCol = 0; curCol < 4; curCol++) { // loops to check if each spot is open
			Circle spot = answerGrid[curRow][curCol];
			if (spot.getFill() == Color.TRANSPARENT) { // adds the color only if the spot is empty
				spot.setFill(color);
				break;
			} 
		}
	}
	
	/**
	 * Handles the event that the "Submit Guess" button is clicked.
	 * 
	 * This method submits a user's guess and identifies how many colors
	 * the user had in the correct spot and how many were in the guess but
	 * in the incorrect spot. It delivers a win message if the user guessed
	 * correctly, a loss message if that was the user's final guess, or 
	 * displays the proper pegs and adds a new row otherwise. If the guess 
	 * was incomplete, then the method does nothing.
	 */
	public void handleSubmitGuess() {
		String guess = getGuess();
		if (guess == null) return; // exits method if guess was incomplete
		
		String result = "";
		try {
			result = controller.getGuessResult(guess);
		} catch (MastermindIllegalLengthException e) { // catches illegal length exception
			showMsg("ILLEGAL LENGTH ERROR: " + e.getMessage());
			e.printStackTrace();
			return;
		} catch (MastermindIllegalColorException e) { // catches illegal color exception
			showMsg("ILLEGAL COLOR ERROR: " + e.getMessage());
			e.printStackTrace();
			return;
		}
		
		if (result.equals("")) { // displays win message if the user guessed correctly
			showPegs(guess);
			showMsg("You won!");
		} else { 
			showPegs(guess); // shows result of guess
			if (controller.getGuessNumber() >= 10) { // displays loss message if that was user's final/tenth guess
				showMsg("You lost!");
			} else {
				controller.makeGuess(); // increments guess count
				int guessNum = controller.getGuessNumber() - 1;
				addNewRow(guessNum);
				addPegBoard(guessNum);
			}
		}
	} 
	
	/**
	 * Converts the user's guess to the string form.
	 * 
	 * This method takes the user's most recent guess from the answerGrid
	 * and adds the letter representation of each color to a string. If
	 * one or more spots in the guess were empty, then the method returns
	 * null. The string guess will consist of 4 letters (r/o/y/b/g/p).
	 * 
	 * @return the string form of the guess is returned.
	 */
	public String getGuess() {
		String guess = "";
		int curRow = controller.getGuessNumber() - 1;
		for (int curCol = 0; curCol < 4; curCol++) { // loops to get letter for each color
			Circle spot = answerGrid[curRow][curCol];
			Color color = (Color) spot.getFill();
			String letter = getColorLetter(color);
			if (letter.equals("EMPTY")) return null; // returns null if guess had empty spots
			guess += letter;
		}
		return guess;
	}

	/**
	 * Returns letter representation of each valid Mastermind color.
	 * 
	 * This method takes in a Color object and returns a the first letter
	 * of that color as a string so it can form a string representation
	 * of the user's guess. If the color passed in isn't one of the
	 * valid Mastermind colors (transparent), then it returns "EMPTY" to
	 * indicate that one or more slot are empty.
	 * 
	 * @param color is a Color object that is a part of the user's guess.
	 * @return a string representation of the color.
	 */
	public String getColorLetter(Color color) {
		if (color == Color.RED) return "r";
		if (color == Color.ORANGE) return "o";
		if (color == Color.YELLOW) return "y";
		if (color == Color.GREEN) return "g";
		if (color == Color.BLUE) return "b";
		if (color == Color.PURPLE) return "p";
		return "EMPTY";
	}
	
	/**
	 * Adds pegs to the peg board indicating the guess's correctness.
	 * 
	 * This method takes in the user's recent guess and adds the corresponding
	 * pegs to the peg board based on the correctness of the guess. It adds a
	 * black peg for every color in the guess that is in the correct spot and
	 * a white peg for every color in the guess that is in the guess but in the
	 * incorrect spot. It colors in the proper amount pegs in the GridPane.
	 * 
	 * @param guess is a string representation f the user's guess.
	 */
	public void showPegs(String guess) {
		int curRow = controller.getGuessNumber() - 1;
		GridPane pegBoard = pegBoards[curRow];
		ObservableList<Node> pegs = pegBoard.getChildren();
		int[] correctPlaces = controller.checkCorrectPlaces(guess);  // calls cmethod to determine correctly placed colors
		int correctCount = correctPlaces[0] + correctPlaces[1] + correctPlaces[2] + correctPlaces[3]; // number of colors in correct spot
		int incorrectCount = controller.checkIncorrectPlaces(guess, correctPlaces); // calls method to determine incorrectly placed colors
		
		int nextPos = 0;
		for (int i = 0; i < correctCount; i++) { // loops to add proper amount of black pegs for correct colors
			((Circle) pegs.get(nextPos)).setFill(Color.BLACK);
			((Circle) pegs.get(nextPos)).setStroke(Color.BLACK);
			nextPos++;
		}
		for (int i = 0; i < incorrectCount; i++) { // loops to add proper amount of white pegs for incorrect colors
			((Circle) pegs.get(nextPos)).setFill(Color.WHITE);
			((Circle) pegs.get(nextPos)).setStroke(Color.BLACK);
			nextPos++;
		}
	}
	
	/**
	 * Displays message to the user.
	 * 
	 * This method can display a popup window with a message for the 
	 * user. It is used to indicate when the user has won or lost, along
	 * with when an error occurs. If the user has won or lost a game, then
	 * it disables the submit and remove buttons so they can't affect the
	 * game board once the game is over.
	 * 
	 * @param message is string to be displayed in the alert
	 */
	public void showMsg(String message) {
		Alert msg = new Alert(Alert.AlertType.INFORMATION);
		msg.setTitle("Message"); 
		msg.setHeaderText("Message");
		msg.setContentText(message);
		msg.showAndWait();
		if (message.startsWith("You")) { // disables buttons if You won or You lost message is delivered
			submit.setDisable(true);
			remove.setDisable(true);
		}
	}

	/**
	 * Removes last color from the guess.
	 * 
	 * This method removes the most recently placed color from the
	 * current guess. It starts at the end of the guess and removes
	 * the last color inputted into the guess. If the guess has had
	 * no colors entered, then it does nothing.
	 */
	public void handleRemovePeg() {
		int curRow = controller.getGuessNumber() - 1;
		for (int curCol = 3; curCol > -1; curCol--) { // loops from end of guess until color is reached
			Circle spot = answerGrid[curRow][curCol];
			if (spot.getFill() != Color.TRANSPARENT) { // clears most recent color
				spot.setFill(Color.TRANSPARENT);
				break;
			} 
		}
	}
	
	/**
	 * Handles the event that the restart button is pressed.
	 * 
	 * This method resets all necessary parts to start a new game 
	 * of Mastermind. It clears all of the guesses from the board,
	 * adds a new first row and peg board, reenables the submit and
	 * remove buttons, resets the guess number, and generates a new
	 * sequence of correct colors. 
	 */
	public void handleRestart() {
		board.getChildren().clear();
		answerGrid = new Circle[10][4];
		addNewRow(0);
		addPegBoard(0);
		submit.setDisable(false);
		remove.setDisable(false);
		controller.resetGuessNumber();
		controller.setCorrectColors(controller.generateColors());
	}

	/**
	 * Launches a game of Mastermind.
	 * 
	 * This method launches the GUI view of Mastermind by calling launch
	 * with the argument args which calls the start method.
	 * 
	 * @param args is is a String array of the command line arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

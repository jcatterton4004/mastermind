/**
 * @author Jesse Catterton
 * 
 * CSC 335 Spring 26 Project 4: Mastermind GUI
 * Purpose: This program has the capability of launching two different
 * versions of the Mastermind game, a text view and a GUI view. The default
 * view is the GUI view, but the text view can be launched by setting the
 * first command line argument to "-text". In order to ensure that the GUI
 * version launched, I needed to go into the run configurations and set
 * the vm arguments to --module-path /pathtoJavaFX/lib --add-modules
 * javafx.controls,javafx.fxml --enable-native-access=javafx.graphics. In 
 * addition, I also unchecked the "Use the -XstartOnFirstThread when 
 * launching with SWT". Without these changes, I was unable to get the GUI
 * to launch. Both views faciliate as many games of Mastermind as the user 
 * desires.
 */

package mastermindgui;
import javafx.application.Application;

/**
 * Implements the game of Mastermind with two different possible views.
 * 
 * This class contains the main method that can launch two separate views of the
 * Mastermind game, a text view and a GUI view. A game of Mastermind consists of a 
 * player trying to guess a 4 color sequence, adjusting their guesses based on what 
 * colors they get correct and what colors are in the incorrect place.
 */
public class Mastermind {
	
	/**
	 * Starts the Mastermind program.
	 * 
	 * This method reads in the user's desired version of the Mastermind game from
	 * the first command line argument, then launches that version. The default 
	 * version will be the GUI whereas the text version will only launch if the 
	 * argument is the string "-text". 
	 * 
	 * @param args is a String array of the command line arguments.
	 */
	public static void main(String[] args) {
		String version = "";
		if (args.length > 0) version = args[0];
		
		if (version.equals("-text")) { // launches text view if arg is "-text"
			MastermindTextView textGame = new MastermindTextView();
			textGame.startGame();
		} else { // launches GUI view if arg is "-window" or anything else as the default is to launch GUI view
			try {
				Application.launch(MastermindGUIView.class, args);
			} catch (Exception e) { // catches generic exception automatically thrown by start
				System.out.println("GUI ERROR: " + e.getMessage());
			}
		}
	}
}

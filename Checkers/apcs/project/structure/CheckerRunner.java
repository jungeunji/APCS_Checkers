package apcs.project.structure;

import java.util.concurrent.Semaphore;

/**
 * CheckerRunner class
 * 
 * Creates a new CheckerGame object and runs the game from it.
 * Calls the playGame() method from the the CheckerGame class.
 * 
 * @author Darren Yang
 * @version May 12, 2014
 */
public class CheckerRunner 
{
	/**
	 * Runs the CheckerGame object
	 * @param args command-line args
	 */
	public static void main( String[] args )
	{
		CheckerGame game = new CheckerGame();
		char a = game.playGame();
		while( a != 'z' ) // returns not 'z' if new game is called
		{
			game = new CheckerGame( a );
			a = game.playGame();
		}
		
		CheckerRunner check = new CheckerRunner();
		while (true)
		{
			game = check.checkForNewGame( game );
		}
	}
	
	/**
	 * This method is played in a loop to keep checking for new games after the current game has ended.
	 * The while loop is similar to the while loop in the main() method.
	 * 
	 * @param game
	 * @return
	 */
	private CheckerGame checkForNewGame( CheckerGame game )
	{
		char lastStatus = game.getNewGame();
		while ( lastStatus == 'z' )
		{
			try 
			{
				Thread.sleep(100);
			} 
			catch (InterruptedException e) {}
			lastStatus = game.getNewGame();
		}
		
		CheckerGame newGame = new CheckerGame( lastStatus );
		char gameCode = newGame.playGame();
		while ( gameCode != 'z' )
		{
			newGame = new CheckerGame( gameCode );
			gameCode = newGame.playGame();
		}
		
		return newGame;
	}
}

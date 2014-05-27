package apcs.project.structure;

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
		CheckerGame game = null;
		InitialGameInput startGame = new InitialGameInput();
		char gameChoice = startGame.gameSelection();
		if ( gameChoice == 'z' )
		{
			System.exit(0);
		}
		else if ( gameChoice != 'n' )
		{
			game = new CheckerGame( gameChoice );
		}
		else
		{
			game = new NetworkGame();
			if ( ( (NetworkGame)game ).getNetworkWorld() == null ) // if player cancels network game creation
			{
				game = new CheckerGame();
			}
		}
		
		char a = game.playGame();
		while( a != 'z' ) // returns not 'z' if new game is called
		{
			if ( a != 'n' )
			{
				game = new CheckerGame( a );
			}
			else
			{
				game = new NetworkGame();
				if ( ( (NetworkGame)game ).getNetworkWorld() == null ) // if player cancels network game creation
				{
					game = new CheckerGame();
				}
			}
			
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
	 * @param game current game
	 * @return new game to start
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
		
		CheckerGame newGame;
		if ( lastStatus != 'n' )
		{
			newGame = new CheckerGame( lastStatus );
		}
		else
		{
			newGame = new NetworkGame();
			if ( ( (NetworkGame)newGame ).getNetworkWorld() == null ) // if player cancels network game creation
			{
				newGame = new CheckerGame();
			}
		}
		char gameCode = newGame.playGame();
		while ( gameCode != 'z' )
		{
			if ( gameCode != 'n' )
			{
				newGame = new CheckerGame( gameCode );
			}
			else
			{
				newGame = new NetworkGame();
				if ( ( (NetworkGame)newGame ).getNetworkWorld() == null ) // if player cancels network game creation
				{
					newGame = new CheckerGame();
				}
			}
			gameCode = newGame.playGame();
		}
		
		return newGame;
	}
}

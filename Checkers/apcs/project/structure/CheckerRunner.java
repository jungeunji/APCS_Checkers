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
		CheckerGame game = new CheckerGame();
		char a = game.playGame();
		while( a != 'z' ) // returns not 'z' if new game is called
		{
			game = new CheckerGame( a );
			a = game.playGame();
		}
	}
}
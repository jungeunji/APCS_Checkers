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
	public static void main( String[] args )
	{
		CheckerGame game = new CheckerGame();
		game.playGame();
	}
}

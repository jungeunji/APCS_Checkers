import info.gridworld.world.World;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;
import java.awt.Color;
import java.util.concurrent.Semaphore;

/**
 * OthelloWorld.java
 * 
 * An <CODE>OthelloWorld</CODE> object represents an Othello world.
 * 
 *  @author  Darren Yang
 *  @version Apr 2, 2014
 *  @author  Period: 2
 *  @author  Assignment: GWOthello
 * 
 *  @author  Sources: I received help from ...
 */
public class CheckerWorld extends World<Piece>
{
	/** The Othello game */
	private CheckerGame game;

	/** A semaphore to prevent getPlayerLocation from executing
	 *  before setPlayerLocation */
	private Semaphore lock;

	/** The last selected player location */
	private Location playerLocation;
	
	/** If a player's piece is selected */
	private boolean pieceSelected;

	/**
	 * Construct an Othello world
	 * game The Othello game
	 */
	public CheckerWorld(CheckerGame game)
	{
		super(new BoundedGrid<Piece>(8, 8));
		
		this.game = game;
		lock = new Semaphore(0);
		playerLocation = null;
		setMessage("Othello - You are blue.  Click a cell to play.");

		System.setProperty("info.gridworld.gui.tooltips", "hide");
		System.setProperty("info.gridworld.gui.watermark", "hide");
		
		for (int bRow = 0; bRow<3; bRow++) // this is to set black color
		{
			for (int bCol = 0; bCol < 8; bCol ++)
			{
				add(new Location( bRow, bCol), new Piece(Color.BLACK, 
						new Location( bRow, bCol), this ));
				bCol = bCol + 3; // need offset for row
			}
		}
		
		for (int rRow =6; rRow < 8; rRow++ )
		{
			for (int rCol =0; rCol < 8;rCol++)
			{
				add(new Location(rRow, rCol), new Piece(Color.RED, 
						new Location(rRow, rCol), this ));
				rCol = rCol + 3;
			}
		}
		
		pieceSelected = false;
		
		
		
//		add(new Location(3, 3), new Piece(Color.RED));
//		add(new Location(3, 4), new Piece(Color.BLUE));
//		add(new Location(4, 3), new Piece(Color.BLUE));
//		add(new Location(4, 4), new Piece(Color.RED));
	}

	/**
	 * Handles the mouse location click.
	 * @param loc the location that was clicked
	 * @return true because the click has been handled
	 */
	@Override
	public boolean locationClicked(Location loc)
	{
		setPlayerLocation(loc);
		if ( !pieceSelected )
		{
			game.displayMoves( getGrid().get( loc ) );
		}
		return true;
	}

	/**
	 * Sets <CODE>playerLocation</CODE>.
	 * @param loc the location to be used to set the player location
	 */
	private void setPlayerLocation(Location loc)
	{
		lock.drainPermits();	// Remove all permits
		playerLocation = loc;
		lock.release();			// Allow getPlayerLocation to run once
	}

	/**
	 * Gets the last player location chosen by the human player.
	 * @return the last location chosen by the human player
	 */
	public Location getPlayerLocation()
	{
		try
		{
			lock.acquire();		// Block until setPlayerLocation runs
			return playerLocation;
		}
		catch (InterruptedException e)
		{
			throw new RuntimeException(
				"Had catastrophic InterruptedException");
		}
	}

	/**
	 * Sets the message in the GridWorld GUI.<br>
	 * Postcondition: At least a second has elapsed before returning.
	 * @param msg the message text
	 */
	@Override
	public void setMessage(String msg)
	{
		super.setMessage(msg);
		try
		{ Thread.sleep(2000); }
		catch (InterruptedException e)
		{ System.out.println("InterruptedException occurred."); }
	}
}

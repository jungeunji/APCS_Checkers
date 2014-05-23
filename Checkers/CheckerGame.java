import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;

//Hubert Tsen

public class CheckerGame 
{
	/** The world */
    private CheckerWorld world;

    /** The array of two players (human, computer) */
    private CheckerPlayer[] players;	

    /** The index into players for the next player to play */
    private int playerIndex;
    
    /**
     * Constructs an Othello game and displays the grid<br>
     */
    public CheckerGame()
    {
        this(true);
    }

    /**
     * Constructs an Othello game.<br>
     * Postcondition: <CODE>players.length == 2</CODE>;
     *     <CODE>players[0]</CODE> contains a human Othello player;
     *     <CODE>players[1]</CODE> contains a computer Othello player;
     *     The world has been shown.
     *     
     *  @param show if true world is displayed. Used for testing
     */
    public CheckerGame(boolean show) //SAVE
    {
        world = new CheckerWorld(this);
        players = new CheckerPlayer[2];
        players[0] = new HumanCheckerPlayer(world, "Human 1", Color.RED, world.getRed());
        players[1] = new HumanCheckerPlayer(world, "Human 2", Color.BLACK, world.getBlack());
        playerIndex = 0;
        
        if (show)
        {
            world.show();
        }
    }

	/**
	 * Plays the game until it is over
	 * (no player can play).
	 */
	public void playGame() //SAVE
	{
	    while ( players[0].canPlay() && players[1].canPlay() )
	    {
    	    CheckerPlayer player = players[playerIndex];
    	    if ( player.canPlay() )
    	    {
    	        player.makeMove();
    	    }
    	    playerIndex = 1 - playerIndex;
    	    players[playerIndex].updatePieces();
    	    world.setMessage( toString() );
	    }
	}

	/**
	 * Creates a string with the current game state.
	 * (used for the GUI message).
	 */
	public String toString()
	{
		Grid<Piece> board = world.getGrid();

		String result = players[1-playerIndex].getName() + " moved to "
				+ players[1-playerIndex].getLastMove().getLocation() + ".\n";
		if (! players[0].canPlay() || ! players[1].canPlay())
			if ( )
				result += players[0].getName() + " won!";
			else if (numBlue < numRed)
				result += "I won!";
			else
				result += "It's a tie!";
		else
			result += players[playerIndex].getName() + "'s turn.";

		return result;
	}
	
	public void displayMoves( Piece p )
	{
		players[playerIndex].displayMoves( p );
	}
	
   //accessors used primarily for testing
    
    protected CheckerWorld getCheckerWorld()
    {
        return world;
    }
    
    protected CheckerPlayer[] getCheckerPlayer()
    {
        return players;
    }
    
    protected int getPlayerIndex()
    {
        return playerIndex;
    }

}

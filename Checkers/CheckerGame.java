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
    public CheckerGame(boolean show)
    {
        world = new CheckerWorld(this);
        players = new CheckerPlayer[2];
        players[0] = new SmartComputerChecker(world);
        players[1] = new HumanCheckerPlayer(world, "Human2", Color.RED);
        //players[1] = new StupidComputerOthelloPlayer(world);
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
	public void playGame()
	{
	    while ( players[0].canPlay() || players[1].canPlay() )
	    {
    	    OthelloPlayer player = players[playerIndex];
    	    if ( player.canPlay() )
    	    {
    	        player.play();
    	    }
    	    playerIndex = 1 - playerIndex;
    	    world.setMessage( toString() );
	    }
	}

	/**
	 * Creates a string with the current game state.
	 * (used for the GUI message).
	 */
	public String toString()
	{
		int numBlue = 0;
		int numRed = 0;

		Grid<Piece> board = world.getGrid();

		for (Location loc : board.getOccupiedLocations())
			if (board.get(loc).getColor().equals(Color.BLUE))
				numBlue++;
			else
				numRed++;

		String result = "Blues: " + numBlue + "    Reds: " + numRed + "\n";
		if (! players[0].canPlay() && ! players[1].canPlay())
			if (numBlue > numRed)
				result += "You won!";
			else if (numBlue < numRed)
				result += "I won!";
			else
				result += "It's a tie!";
		else
			result += players[playerIndex].getName() + " to play.";

		return result;
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

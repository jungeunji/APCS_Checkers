package apcs.project.structure;
import java.awt.Color;

//Hubert Tsen

/**
 * The class representing the underlying structure of the game. Initializes each move sequence
 * and checks for the end of the game.
 *
 */
public class CheckerGame 
{
	/** The world */
    private CheckerWorld world;

    /** The array of two players (human, computer) */
    private CheckerPlayer[] players;	

    /** The index into players for the next player to play */
    private int playerIndex;
    
    /** If playGame() was ended by a new game */
    private char endByNewGame;

    
    /**
     * Constructs a Checker game and displays the grid<br>
     */
    public CheckerGame()
    {
        this(true);
    }

    /**
     * Constructs a Checker game.<br>
     * Postcondition: <CODE>players.length == 2</CODE>;
     *     The world has been shown.
     *     
     *  @param show if true world is displayed. Used for testing
     */
    public CheckerGame(boolean show) //SAVE
    {
        world = new CheckerWorld(this);
        players = new CheckerPlayer[2];
        players[0] = new HumanCheckerPlayer(world, "Red", Color.RED, world.getRed());
        players[1] = new HumanCheckerPlayer(world, "Black", Color.BLACK, world.getBlack());
        playerIndex = 0;
        endByNewGame =  'z';
        
        if (show)
        {
            world.show();
        }
    }
    
    /**
     * Creates a new Checker game based on the type
     * @param type type of Checker game
     */
    public CheckerGame( char type )
    {
    	world = new CheckerWorld(this);
    	players = new CheckerPlayer[2];
    	players[0] = new HumanCheckerPlayer( world, "Red", Color.RED, world.getRed() );
    	switch( type )
    	{
    		case 'h':
    			players[1] = new HumanCheckerPlayer(world, "Black", Color.BLACK, world.getBlack());
    			break;
    		case 'c':
    			players[1] = new SmartComputerCheckerPlayer(world, "CPU", Color.BLACK, world.getBlack());
    			break;
    	}
    	playerIndex = 0;
    	endByNewGame = 'z';
    	
    	world.show();
    }
    
    /**
     * Sets the CheckerWorld for this game
     * @param w CheckerWorld
     */
    public void setWorld( CheckerGame cg, int port )
    {
    	world = new NetworkWorld( cg, port );
    	world.show();
    }
    
    /**
     * Returns the array of players
     * @return players array
     */
    public CheckerPlayer[] getPlayers()
    {
    	return players;
    }
    
    /**
     * Sets the players of this game
     * @param cp players array
     */
    public void setPlayers( CheckerPlayer[] cp )
    {
    	players = cp;
    }
    
    /**
     * Sets endByNewGame to a descriptive char to signal a new game
     * 
     * @param a new game type
     */
    public void newGame( char a )
    {
    	endByNewGame = a;
    }
    
    /**
     * Returns the new game type
     * @return new game
     */
    public char getNewGame()
    {
    	return endByNewGame;
    }
    
    /**
     * Returns which player's turn it is
     * @return player turn
     */
    public CheckerPlayer getTurn()
    {
    	return players[playerIndex];
    }
    
    /**
     * Returns the index of the array corresponding to the player's turn
     * @return player turn index
     */
    public int getTurnIndex()
    {
    	return playerIndex;
    }
    
    /**
     * Returns the world hosting the checker game
     * @return world
     */
    public CheckerWorld getWorld()
    {
    	return world;
    }

	/**
	 * Plays the game until it is over
	 * (no player can play).
	 * 
	 * @return the end game and new game condition
	 */
	public char playGame() //SAVE
	{
	    while ( players[0].canPlay() && players[1].canPlay() )
	    {
    	    CheckerPlayer player = players[playerIndex];
    	    if ( player.canPlay() )
    	    {
    	        player.makeMove();
    	    }
    	    
    	    if ( endByNewGame != 'z' ) // if null move was sent, start new game
    	    {
    	    	return endByNewGame;
    	    }
    	    playerIndex = 1 - playerIndex;
    	    players[playerIndex].updatePieces();
    	    world.setMessage( toString() );
	    }
	    
	    return endByNewGame;
	}

	/**
	 * Creates a string with the current game state.
	 * (used for the GUI message).
	 * 
	 * @return returns the status of the CheckerGame
	 */
	public String toString()
	{
		String result = players[1-playerIndex].getName() + " moved to "
				+ players[1-playerIndex].getLastMove().getLocation() + ".\n";
		if (! players[0].canPlay() || ! players[1].canPlay())
			if ( players[0].getPieces().size() > players[1].getPieces().size() )
				result += players[0].getName() + " won!";
			else
				result += players[1].getName() + " won!";
		else
			result += players[playerIndex].getName() + "'s turn.";

		return result;
	}
	
	/**
	 * Displays the player's available moves on the board with Piece p
	 * @param p Piece's moves to be shown
	 */
	public void displayMoves( Piece p )
	{
		players[playerIndex].displayMoves( p );
	}
	
	/**
	 * Hides the player's available moves on the board with Piece p
	 * @param p Piece's moves to be hidden
	 */
	public void undisplayMoves( Piece p )
	{
		players[playerIndex].undisplayMoves( p );
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

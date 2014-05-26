package apcs.project.structure;
import info.gridworld.world.World;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;
import java.awt.Color;
import java.util.concurrent.Semaphore;
import java.util.*;

/**
 * 
 * A <CODE>CheckerWorld</CODE> object represents a Checker world.
 * 
 *  @author  Darren Yang
 *  @version May 24, 2014
 *  @author  Period: 2
 */
public class CheckerWorld extends World<Piece>
{
	/** The Checker game */
	private CheckerGame game;

	/** A semaphore to prevent getPlayerLocation from executing
	 *  before setPlayerLocation */
	private Semaphore lock;

	/** The last selected player piece */
	private Piece playerPiece;
	
	/** The last selected player location */
	private Location playerLocation;
	
	/** If a player's piece is selected */
	private boolean pieceSelected;
	
	/** Last move played */
	private MoveInfo lastMove;
	
	/** Black pieces on the board */
	private ArrayList<Piece> blackPieces;
	
	/** Red pieces on the board */
	private ArrayList<Piece> redPieces;
	
	/** New type of game to start */
	private char newGame;

	/**
	 * Construct a Checker world
	 * @param game The Checker game
	 */
	public CheckerWorld(CheckerGame game)
	{
		super(new BoundedGrid<Piece>(8, 8));
		
		this.game = game;
		lock = new Semaphore(0);
		playerLocation = null;
		lastMove = null;
		setMessage("Red starts. Click on a piece to select it, and click on a valid location to move the piece.");

		System.setProperty("info.gridworld.gui.selection", "hide");
		System.setProperty("info.gridworld.gui.tooltips", "hide");
		System.setProperty("info.gridworld.gui.watermark", "hide");
		
		blackPieces = new ArrayList<Piece>();
		redPieces = new ArrayList<Piece>();
		
		addPieces( blackPieces, redPieces );
		
		pieceSelected = false;
		newGame = 'z';
	}
	
	/**
	 * Sets the pieces on the board
	 * @param black black pieces
	 * @param red red pieces
	 */
	private void addPieces( ArrayList<Piece> black, ArrayList<Piece> red )
	{
		for ( int bRow = 0; bRow<3; bRow++ ) // this is to set black color
		{
			for  ( int bCol = 1 - ( bRow % 2 ); bCol < 8; bCol += 2 )
			{
				Piece p = new Piece( Color.BLACK, new Location( bRow, bCol ), this );
				add(new Location( bRow, bCol ), p );
				black.add( p );
			}
		}
		
		for ( int rRow = 5; rRow < 8; rRow++ )
		{
			for ( int rCol = 1 - ( rRow % 2 ); rCol < 8; rCol += 2 )
			{
				Piece piece = new Piece(Color.RED, new Location(rRow, rCol), this );
				add(new Location(rRow, rCol), piece );
				red.add( piece );
			}
		}
	}
	
	/**
	 * Returns the black pieces on the board for initialization purposes
	 * This array is no longer used after initializing
	 * @return initial set of black pieces
	 */
	public ArrayList<Piece> getBlack()
	{
		return blackPieces;
	}
	
	/**
	 * Returns the red pieces on the board for initialization purposes
	 * This array is no longer used after initializing
	 * @return initial set of red pieces
	 */
	public ArrayList<Piece> getRed()
	{
		return redPieces;
	}
	
	/**
	 * Returns the game hosted by this world
	 * @return Checker game
	 */
	public CheckerGame getGame()
	{
		return game;
	}
	
	/**
	 * Sets the last move played on the board
	 * @param mi last move played
	 */
	public void setLastMove( MoveInfo mi )
	{
		lastMove = mi;
	}

	/**
	 * Handles the mouse location click.
	 * @param loc the location that was clicked
	 * @return true because the click has been handled
	 */
	@Override
	public boolean locationClicked(Location loc)
	{
		//causing too many problems, so I commented it out
		if ( lastMove != null && lastMove.isJump() && lastMove.getPiece().canJump() ) //locks selection onto jump-chaining piece
		{
			Piece lastPiece = lastMove.getPiece();

			if ( !loc.equals( lastPiece.getLocation() ) 
					&& !lastPiece.getAllowedMoves().contains( loc ) ) //consumes irrelevant clicks
			{
				return true;
			}
		}
		if ( getGrid().get(loc) != null && !( getGrid().get(loc) instanceof PieceTile ) ) 
		{
			Piece p = getGrid().get( loc );
			if ( pieceSelected )
			{
				game.undisplayMoves( playerPiece );
			}
			game.displayMoves(p); //selection and highlight available moves
			playerPiece = p;
			pieceSelected = true;
		}
		else if ( pieceSelected && !playerPiece.getAllowedMoves().contains(loc) )
		{
			pieceSelected = false;
			game.undisplayMoves( playerPiece ); //undo selection and highlight
			playerPiece = null;
		}
		else if ( pieceSelected && playerPiece.getAllowedMoves().contains(loc) )
		{
			pieceSelected = false;
			game.undisplayMoves( playerPiece ); //undo selection and highlight
			setPlayerLocation( loc );
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
		lock.release();			// Allow getPlayerMove to run once
	}
	
	/**
	 * Overrides World<T> newGame(char). Shoots a MoveInfo with null piece and null location
	 * to the CheckerGame if the game is still running. If the game has ended, directly 
	 * changes the CheckerGame's game status for CheckerRunner to detect.
	 * 
	 * @param a the new game status
	 */
	public void newGame( char a )
	{
		newGame = a;
		playerPiece = null;
		setPlayerLocation(null);
		if ( !lock.hasQueuedThreads() )
		{
			game.newGame( a );
		}
		super.newGame(); //World's newGame() disposes the frame
	}
	/**
	 * Overrides key presses to take no action
	 * 
	 * @param s String
	 * @param loc Location
	 */
	public boolean keyPressed( String s, Location loc )
	{
		return true;
	}

	/**
	 * Gets the last player location chosen by the human player.
	 * @return the last location chosen by the human player
	 */
	public MoveInfo getPlayerMove()
	{
		try
		{
			lock.acquire();		// Block until setPlayerLocation runs
			MoveInfo move = new MoveInfo( playerPiece, playerLocation );
			if ( newGame != 'z' ) return new MoveInfo( newGame );
			playerPiece = null;
			return move;
		}
		catch (InterruptedException e)
		{
			throw new RuntimeException(
				"Had catastrophic InterruptedException");
		}
	}

	/**
	 * Sets the message in the GridWorld GUI.<br>
	 * @param msg the message text
	 */
	@Override
	public void setMessage(String msg)
	{
		super.setMessage(msg);
		show(); //refreshes message box. w/o show, previous message would sometimes remain
	}
}

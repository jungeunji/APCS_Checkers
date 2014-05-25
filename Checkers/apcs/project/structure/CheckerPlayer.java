package apcs.project.structure;
import java.awt.Color;
import java.util.ArrayList;

import info.gridworld.grid.*;

/**
 * A CheckerPlayer class that contains the basic functionalities of
 * a player in the Checkers game.
 * 
 * @author Darren Yang
 * @version May 12, 2014
 */
public abstract class CheckerPlayer 
{
	/** The player world */
	private CheckerWorld world;
	
	/** The grid */
	private Grid<Piece> board;
	
	/** The player name */
	private String name;
	
	/** The player color */
	private Color color;
	
	/** The player's pieces */
	private ArrayList<Piece> pieces;
	
	/** Last move for display purposes */
	private MoveInfo lastMove;
	
	
	
	/**
	 * Three-parameter constructor for the Player.
	 * @param w CheckerWorld of the player
	 * @param n Name of the player
	 * @param c Color of the player's pieces
	 * @param p List of the player's pieces
	 */
	public CheckerPlayer( CheckerWorld w, String n, Color c, ArrayList<Piece> p )
	{
		world = w;
		board = w.getGrid();
		name = n;
		color = c;
		pieces = p;
	}
	

	/**
	 * Gets location of next move
	 * @return location of next move
	 */
	public abstract MoveInfo getPlay();
	
	/**
	 * Returns player name
	 * @return player name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns the player world
	 * @return player world
	 */
	public CheckerWorld getWorld()
	{
		return world;
	}
	
	/**
	 * Returns the player's pieces
	 * @return player's pieces
	 */
	public ArrayList<Piece> getPieces()
	{
		return pieces;
	}
	
	/**
	 * Updates the player's pieces to check for eaten pieces
	 */
	public void updatePieces()
	{
		for ( int i = 0; i < pieces.size(); i++ )
		{
			Piece p = pieces.get( i );
			if ( board.get( p.getLocation() ) == null )
			{
				p.setLocation( null );
				pieces.remove( p );
				i--;
			}
		}
	}
	
	/**
	 * Returns the last move made by the player
	 * @return last move made by the player
	 */
	public MoveInfo getLastMove()
	{
		return lastMove;
	}
	
	/**
	 * Checks to see if the player can play (if he has pieces and has available moves for them)
	 * @return true if the player can play
	 */
	public boolean canPlay()
	{
		return !pieces.isEmpty() && !getMoves().isEmpty();
	}
	
	/**
	 * Moves the piece to the selected location and appropriately promotes to King and
	 * removes eaten pieces from the board (not from the player's pieces list yet)
	 * @param p Piece to be moved
	 */
	public void makeMove()
	{
		MoveInfo mi = getPlay();
		lastMove = mi;
		Piece p = mi.getPiece();
		Location loc = mi.getLocation();
		
		if ( !mi.isJump() )
		{
			board.put( loc, board.remove( p.getLocation() ) );
			p.setLocation( loc );
			p.setKing(); //promotes to King if can
		}
		else
		{
			while ( p.canJump() ) //while-loop in case of jump chaining
			{
				board.put( loc, board.remove( p.getLocation() ) );
				eatPiece( p, loc );
				p.setLocation( loc );
				p.setKing(); //promotes to King if can
				if ( p.canJump() ) //if there is a next jump, get next move
				{
					world.setLastMove( mi );
					world.setMessage( name + " moved to " + getLastMove().getLocation() + ".\n"
							+ "You must jump again." );
					world.locationClicked( p.getLocation() ); //automatically select piece again
					mi = getPlay();
					lastMove = mi;
					loc = mi.getLocation();
				}
			}
		}
	}
	
	/**
	 * Gets the total list of moves that the player can perform with their pieces
	 * @return list of allowed moves
	 */
	public ArrayList<MoveInfo> getMoves()
	{
		ArrayList<MoveInfo> jumpLocations = new ArrayList<MoveInfo>();
		ArrayList<MoveInfo> normalMoves = new ArrayList<MoveInfo>();
		
		for ( Piece p : pieces )
		{
			for ( Location loc : p.getAllowedMoves() )
			{
				MoveInfo nextMove = new MoveInfo( p, loc );
				if ( nextMove.isJump() )
				{
					jumpLocations.add( nextMove );
				}
				else
				{
					normalMoves.add( nextMove );
				}
			}
		}
		
		if ( !jumpLocations.isEmpty() )
		{
			return jumpLocations;
		}
		return normalMoves;
	}
	
	/**
	 * Highlights the squares of the legal moves for Piece p on the board
	 * @param p Piece's moves to be shown
	 */
	public void displayMoves( Piece p )
	{
		if ( !pieces.contains( p ) ) //in case of wrong player
		{
			return;
		}
		
		p.setSelected( true );
		ArrayList<Location> availableMoves = p.getAllowedMoves();
		
		for ( Location loc : availableMoves )
		{
			if ( getMoves().contains( new MoveInfo( p, loc ) ) )
			{
				//the if-statement is needed in case a player must jump
				//this will prevent normal moves from showing up
				board.put( loc , new PieceTile( Color.GRAY, loc, world ) );
			}
		}
	}
	
	/**
	 * Hides the available moves for Piece p on the board
	 * @param p Piece's moves to be hidden
	 */
	public void undisplayMoves( Piece p )
	{
		if ( !pieces.contains( p ) ) //in case of wrong player
		{
			return;
		}
		
		p.setSelected( false );
		ArrayList<Location> availableMoves = p.getAllowedMoves(); 
		//we can call this now because available moves
		//are undisplayed before the piece is actually moved
		
		for ( Location loc : availableMoves )
		{
			if ( board.get( loc ) instanceof PieceTile )
			{
				board.get( loc ).setLocation( null );
				board.remove( loc );
			}
		}
	}
	
	/**
	 * Private helper method to remove eaten enemy in between piece and targeted location 
	 * @param piece Eater piece
	 * @param loc Target location
	 */
	private void eatPiece( Piece piece, Location loc )
	{
		Location eat = piece.getLocation().getAdjacentLocation( piece.getLocation().getDirectionToward( loc ) );
		board.remove( eat );
	}
}

import java.awt.Color;
import java.util.ArrayList;

import info.gridworld.grid.*;

/**
 * Contains the necessary data for representing a Piece on 
 * the Checkers board
 * 
 * @author Darren Yang
 * @version May 14, 2014
 */
public class Piece 
{
	/** The piece color */
	private Color color;
	
	/** King status */
	private boolean isKing;
	
	/** Location of piece */
	private Location location;
	
	/** The piece's Checkers board */
	private Grid<Piece> board;
	
	/**
	 * Constructs a default Piece that is not a King
	 * 
	 * @param color Color of the piece
	 * @param location Starting location of the piece
	 * @param w CheckerWorld that the piece is in
	 */
	public Piece(Color color, Location location, CheckerWorld w)
	{
		this.color = color;
		this.location = location;
		isKing = false;
		board = w.getGrid();
	}
	
	/**
	 * Returns the piece's color
	 * 
	 * @return The piece's color
	 */
	public Color getColor()
	{
		return color;
	}
	
	/**
	 * Returns the current location of the piece
	 * 
	 * @return current location of piece
	 */
	public Location getLocation()
	{
		return location;
	}
	
	/**
	 * Sets a new location for the piece
	 * 
	 * @param newLocation new location of the piece
	 */
	public void setLocation( Location newLocation )
	{
		location = newLocation;
	}
	
	/**
	 * Sets the piece to king if allowed and appropriate
	 */
	public void setKing()
	{
		if ( isKing )
		{
			return;
		}
		
		if ( ( color == Color.RED && location.getRow() == 0 ) // Checks to see if 
				|| ( color == Color.BLACK && location.getRow() == 7 ) ) // piece is on end row
		{
			isKing = true;
		}
	}
	
	/**
	 * Returns the king status of the piece
	 * 
	 * @return true if piece is king
	 * 		   false if piece is not
	 */
	public boolean king()
	{
		return isKing;
	}
	
	/**
	 * Returns whether or not the piece can eat and jump an enemy
	 * 
	 * @return whether or not piece can jump
	 */
	public boolean canJump()
	{
		return !jumpLocs().isEmpty();
	}
	
	/**
	 * Adds a move to the move list if the location is valid and not occupied
	 * 
	 * @param loc target location
	 * @param list move list
	 */
	public void addMove( Location loc, ArrayList<Location> list )
	{
		if ( board.isValid( loc ) && board.get( loc ) == null )
		{
			list.add( loc );
		}
	}
	
	/**
	 * Creates an ArrayList of allowed locations for the next move
	 * The moves are valid on the board and are not occupied
	 * 
	 * @return move list for next turn
	 */
	public ArrayList<Location> getAllowedMoves()
	{
		ArrayList<Location> jump = jumpLocs();
		if ( !jump.isEmpty() )
		{
			return jumpDestinations( jump );
		}
		
		ArrayList<Location> moves = new ArrayList<Location>();
		
		if ( color == Color.RED || king() )
		{
			Location loc1 = new Location( getLocation().getRow() - 1, getLocation().getCol() - 1 );
			addMove( loc1, moves );
			
			Location loc2 = new Location( getLocation().getRow() - 1, getLocation().getCol() + 1 );
			addMove( loc2, moves );
		}
		if ( color == Color.BLACK || king() ) // adds all four directions if it is a king
		{
			Location loc3 = new Location( getLocation().getRow() + 1, getLocation().getCol() - 1 );
			addMove( loc3, moves );
			
			Location loc4 = new Location( getLocation().getRow() + 1, getLocation().getCol() + 1 );
			addMove( loc4, moves );
		}
		
		return moves;
	}
	
	/**
	 * Returns the available jump locations for the piece
	 * Empty if piece cannot jump
	 * 
	 * @return jump locations for next turn
	 */
	private ArrayList<Location> jumpLocs()
	{
		ArrayList<Location> jumps = board.getOccupiedAdjacentLocations( getLocation() );

		for ( int i = 0; i < jumps.size(); i++ )
		{
			// removes adjacent occupied locations that cause the piece to
			// jump off the board or onto another piece
			if ( board.get( jumps.get( i ) ).getColor() == color || !jumpLocValid( jumps.get( i ) ) )
			{
				jumps.remove( i );
				i--;
			}
		}
		
		if ( getColor() == Color.RED && !king() ) // removes pieces behind unless it is a king
		{
			for ( int i = 0; i < jumps.size(); i++ )
			{
				if ( jumps.get( i ).getRow() - getLocation().getRow() != -1 )
				{
					jumps.remove( i );
					i--;
				}
			}
		}
		else if ( getColor() == Color.BLACK && !king() ) // removes pieces behind unless it is a king
		{
			for ( int i = 0; i < jumps.size(); i++ )
			{
				if ( jumps.get( i ).getRow() - getLocation().getRow() != 1 )
				{
					jumps.remove( i );
					i--;
				}
			}
		}
		
		return jumps;
	}
	
	private ArrayList<Location> jumpDestinations( ArrayList<Location> jumpPieces )
	{
		ArrayList<Location> destinations = new ArrayList<Location>();
		for ( Location loc : jumpPieces ) 
		{
			destinations.add( loc.getAdjacentLocation( ( loc
					.getDirectionToward( getLocation() ) + 180 ) % 360 ) );
		}
		
		return destinations;
	}
	
	/**
	 * Checks to see if the jump will be valid
	 * 
	 * @param loc Location of eaten piece
	 * @return if jump is valid
	 */
	private boolean jumpLocValid( Location loc )
	{
		Location otherSide = loc.getAdjacentLocation( ( loc.getDirectionToward( getLocation() ) + 180 ) % 360 );
		return board.isValid( otherSide ) && board.get( otherSide ) == null;
	}
}

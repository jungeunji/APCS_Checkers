package apcs.project.structure;
import info.gridworld.grid.*;

/**
 * A class to carry the information for each player's moves:
 * The piece to move, and where to move it
 * 
 * @author Darren Yang
 * @version May 22, 2014
 */
public class MoveInfo 
{
	/** The piece to move */
	private Piece piece;
	
	/** Where to move it */
	private Location location;
	
	/** Type of new game to start */
	private char newGameTrigger;
	
	/**
	 * @param p Piece to move
	 * @param loc Location to move to
	 */
	public MoveInfo( Piece p, Location loc )
	{
		piece = p;
		location = loc;
		newGameTrigger = 'z';
	}
	
	/**
	 * Constructor explicitly for sending new game triggers
	 * @param a type of new game
	 */
	public MoveInfo( char a )
	{
		newGameTrigger = a;
	}
	
	/**
	 * Returns type of new game to start
	 * @return type of new game
	 */
	public char getNewGame()
	{
		return newGameTrigger;
	}
	
	/**
	 * Returns the moving piece
	 * @return moving piece
	 */
	public Piece getPiece()
	{
		return piece;
	}
	
	/**
	 * Returns the destination
	 * @return destination
	 */
	public Location getLocation()
	{
		return location;
	}
	
	/**
	 * Checks to see if the move is a jump
	 * @return true if jump
	 */
	public boolean isJump()
	{
		int rowDiff = Math.abs( piece.getLocation().getRow() - location.getRow() );
		return rowDiff == 2;
	}
	

	/**
	 * Overrides the equals method to appropriately compare moves.
	 * @param the move to be compared to
	 * @return true if moves are the same
	 */
	@Override
	public boolean equals( Object obj )
	{
		if ( ! ( obj instanceof MoveInfo ) )
		{
			return false;
		}
		
		return ( piece.equals( ( (MoveInfo)obj ).piece ) ) && ( location.equals( ( (MoveInfo)obj ).location ) );
	}
	
	
	/**
	 * Overrides the hash code method to work accordingly with the new equals method.
	 * @return hash code
	 */
	@Override
	public int hashCode()
	{
		return piece.hashCode() + location.getRow() * location.getRow() + location.getCol();
	}

}

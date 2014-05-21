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
			if ( !board.get( p.getLocation() ).equals( p ) )
			{
				pieces.remove( p );
				i--;
			}
		}
	}
	
	public boolean canPlay()
	{
		return !pieces.isEmpty();
	}
	
	/**
	 * Moves the piece to the selected location and appropriately promotes to King and
	 * removes eaten pieces from the board (not from the player's pieces list yet)
	 * @param p Piece to be moved
	 */
	public void makeMove()
	{
		MoveInfo mi = getPlay();
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
					mi = getPlay();
					loc = mi.getLocation();
				}
			}
		}
	}
	
	public void displayMoves( Piece p )
	{
		if ( !pieces.contains( p ) )
		{
			return;
		}
		ArrayList<Location> availableMoves = p.getAllowedMoves();
		
		
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

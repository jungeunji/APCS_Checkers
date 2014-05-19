import info.gridworld.grid.*;

public class MoveInfo 
{
	private Piece piece;
	private Location location;
	
	public MoveInfo( Piece p, Location loc )
	{
		piece = p;
		location = loc;
	}
	
	public Piece getPiece()
	{
		return piece;
	}
	
	public Location getLocation()
	{
		return location;
	}
	
	public boolean isJump()
	{
		int rowDiff = Math.abs( piece.getLocation().getRow() - location.getRow() );
		return rowDiff == 2;
	}

}

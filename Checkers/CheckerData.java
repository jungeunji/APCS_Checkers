import info.gridworld.world.World;


public class CheckerData extends World<Piece>
{
	static final int
    EMPTY = 0,           // Value representing an empty square.
    RED = 1,             // A regular red piece.
    RED_KING = 2,        // A red king.
    BLACK = 3,           // A regular black piece.
    BLACK_KING = 4;      // A black king.
	
	/**
	 * Make the move from (fromRow,fromCol) to (toRow,toCol).  It is
	 * ASSUMED that this move is legal!  If the move is a jump, the
	 * jumped piece is removed from the board.  If a piece moves
	 * to the last row on the opponent's side of the board, the 
	 * piece becomes a king.
	 */
	public void makeMove(int fromRow, int fromCol, int toRow, int toCol) {

	   board[toRow][toCol] = board[fromRow][fromCol]; // Move the piece.
	   board[fromRow][fromCol] = EMPTY;

	   if (fromRow - toRow == 2 || fromRow - toRow == -2) {
	         // The move is a jump.  Remove the jumped piece from the board.
	      int jumpRow = (fromRow + toRow) / 2; // Row of the jumped piece.
	      int jumpCol = (fromCol + toCol) / 2; // Column of the jumped piece.
	      board[jumpRow][jumpCol] = EMPTY;
	   }

	   if (toRow == 0 && board[toRow][toCol] == RED)
	      board[toRow][toCol] = RED_KING;  // Red piece becomes a king.
	   if (toRow == 7 && board[toRow][toCol] == BLACK)
	      board[toRow][toCol] = BLACK_KING;  // Black piece becomes a king.

	}  // end makeMove()

}

import info.gridworld.gui.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class PieceDisplay extends AbstractDisplay
{
	private BufferedImage redPiece;
	private BufferedImage redKing;
	
	private BufferedImage blackPiece;
	private BufferedImage blackKing;
	
	public PieceDisplay()
	{
		try 
		{
		    redPiece = ImageIO.read( new File( "redpiece.png" ) );
		    redKing = ImageIO.read( new File( "redking.png" ) );
		    
		    blackPiece = ImageIO.read( new File( "blackpiece.png" ) );
		    blackKing = ImageIO.read( new File( "blackking.png" ) );
		} 
		catch (IOException e) 
		{
			System.out.println( "Incompatible image. Pieces will not be displayed." );
		}
	}
	
	public void draw( Object obj, Component comp, Graphics2D g2 )
	{
		Piece p = (Piece)obj;
		
		if ( p.getColor() == Color.RED && !p.king() )
		{
			g2.drawImage( redPiece, comp.getWidth() / 8, comp.getHeight() / 8, null );
		}
	}
}

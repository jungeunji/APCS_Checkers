package apcs.project.structure;

import java.net.*;
import java.io.*;

import network.reference.SocketName;

/**
 * This Receiver class receives messages sent over the socket connection and sends
 * them to the display (NetworkWorld) to process.
 * 
 * @author Darren Yang
 * @version May 26, 2014
 * 
 * @source TileGame
 */
public class Receiver extends Thread
{
	/** NetworkWorld to send to */
	protected NetworkWorld world;
	
	/** Name of the connection socket */
	protected SocketName name;
	
	/** Socket used for connection */
	protected Socket socket;
	
	/** Input line from the connection */
	protected BufferedReader input;
	
    /**
     * Constructor for the Receiver. The passed socket is already functional.
     * 
     * @param w NetworkWorld
     * @param n name of the socket
     * @param sock socket
     */
    public Receiver( NetworkWorld w, SocketName n, Socket sock )
    {
        super( "ChatReceiver-" + sock.getInetAddress() + ":" + sock.getPort() );
        world = w;
        name = n;
        socket = sock;

        try
        {
            input = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

        }
        catch ( IOException e )
        {
        	e.printStackTrace();
        }

        start();
    }
    
    /**
     * Kills the receiver and ends the connection.
     * Should also be performed before exiting.
     */
    public void kill()
    {
    	try
    	{
    		socket.close();
    	}
    	catch ( IOException e )
    	{
    		e.printStackTrace();
    	}
    }
    
    /**
     * Main run loop for the Receiver. Continuously reads from input
     * until a null is passed, terminating the receiver.
     */
    public void run()
    {
    	try //main run loop
    	{
    		String line;
    		
    		while ( ( line = input.readLine() ) != null )
    		{
    			world.receive( name, line );
    		}
    	}
    	catch ( SocketException se )
    	{
    		world.setMessage( "Your opponent has disconnected." );
    	}
    	catch ( Exception e )
    	{
    		e.printStackTrace();
    	}
    	//main loop has ended
    	
    	try //terminating
    	{
    		world.destroySocket( name );
    		input.close();
    		socket.close();
    	}
    	catch ( Exception e )
    	{}
    	
    	System.out.println( getName() + " terminating" );
    }
}

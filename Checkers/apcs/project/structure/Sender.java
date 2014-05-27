package apcs.project.structure;

import java.io.*;
import java.net.*;

import network.reference.SocketName;

public class Sender extends Thread
 {
	/** NetworkWorld to send display notifications to */
	protected NetworkWorld world;

	/** Name of the socket */
	protected SocketName name;

	/** Socket used to communicate with host */
	protected Socket socket;

	/** Writer used to send information */
	protected PrintWriter output;

	/** Whether or not this sender is still active */
	protected boolean alive = false;

	/**
	 * Constructor. The given Socket is already connected and ready for use.
	 * 
	 * @param w The NetworkWorld object to display notifications
	 * @param n The name of the socket this thread goes with
	 * @param sock The socket to send data out to
	 */
	public Sender(NetworkWorld w, SocketName n, Socket sock) 
	{
		super("ChatSender-" + sock.getInetAddress() + ":" + sock.getPort());
		world = w;
		name = n;
		socket = sock;

		try 
		{
			output = new PrintWriter(socket.getOutputStream(), true);
			alive = true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		start();
	}
	
    /**
     * Sends a string over the connection with the PrintWriter
     * @param s String to be sent
     */
    public void send( String s )
    {
        try
        {
            output.println( s );
        }
        catch ( Exception e )
        {
            alive = false; //ends the socket
            e.printStackTrace();
        }
    }
    
    /**
     * Terminates this sender and stops input
     */
    public void kill()
    {
    	alive = false;
    }
    
    /**
     * Main loop of the Sender. Checks every second for alive.
     * Closes sockets once alive is set to false.
     */
    public void run()
    {
    	while ( alive ) //main loop run
    	{
	    	try
	    	{
	    		//check every second for change in alive
	    		sleep( 1000 );
	    	}
	    	catch ( InterruptedException ie )
	    	{}
	    	catch ( Exception e )
	    	{
	    		alive = false; //end the sender
	    		e.printStackTrace();
	    	}
    	}
    	
    	try //destroy the sockets
    	{
    		output.close();
    		socket.close();
    	}
    	catch ( Exception e )
    	{}
    	
    	System.out.println( getName() + " terminating" );
    }
}

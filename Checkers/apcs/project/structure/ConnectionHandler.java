package apcs.project.structure;

import java.io.*;
import java.net.*;
import java.util.*;

import network.reference.SocketName;

/**
 * This ConnectionHandler manages send/receive through
 * a specified port and keeps a list of active connections
 * 
 * @author Darren Yang
 * @version May 26, 2014
 */
public class ConnectionHandler extends Thread
{
	/** Network message processor */
	protected NetworkWorld world;
	
	/** Socket to communicate on */
	protected ServerSocket serverSocket;
	
	/** Map of senders */
	protected Map<SocketName, Sender> senders;
	
	/** Map of receivers */
	protected Map<SocketName, Receiver> receivers;
	
	/** Number of incoming connections */
	protected int count = 1;
	
	/**
	 * Creates a new ConnectionHandler to handle communications on a 
	 * specific port
	 * 
	 * @param w NetworkWorld using the port
	 * @param p port
	 */
	public ConnectionHandler( NetworkWorld w, int p )
	{
		super( "ConnectionHandler-" + p );
		
        senders = new HashMap<SocketName, Sender>();
        receivers = new HashMap<SocketName, Receiver>();

        world = w;

        try
        {
            serverSocket = new ServerSocket( p );
            start();
        }
        catch ( IOException e )
        {
            System.err.println( "Could not listen on port " + p );
        }
	}
	
    /**
     * Spawns and creates a new setting for socket communication
     * @param sock socket for communication
     */
    protected synchronized void spawn( Socket sock )
    {
        SocketName name = new SocketName( sock.getInetAddress().toString(),
            sock.getPort(),
            "Incoming " + count );
        count++;
        spawn( name, sock );
    }

    /**
     * Creates the setting for a new socket communication
     * @param name name of the Socket
     * @param sock socket of communication
     */
    protected void spawn( SocketName name, Socket sock )
    {
        Sender s = new Sender( world, name, sock );
        Receiver r = new Receiver( world, name, sock );

        senders.put( name, s );
        receivers.put( name, r );

        world.createSocket( name );
    }
    
    /**
     * Continuously checks for new incoming connections
     */
    public void run()
    {
    	//continuously scan for new socket connections
    	try
    	{
    		while ( true )
    		{
    			Socket socket = serverSocket.accept();
    			spawn( socket );
    		}
    	}
    	catch ( IOException io )
    	{
    		io.printStackTrace();
    	}
    }
    
    /**
     * Connects to a new socket
     * @param name new socket name
     * @throws ConnectException throws if connects first
     */
    public void connect( SocketName name ) throws ConnectException
    {
    	try
    	{
    		Socket socket = new Socket( name.getHost(), name.getPort() );
    		spawn( name, socket );
    	}
    	catch ( ConnectException ce )
    	{
    		throw new ConnectException();
    	}
        catch ( UnknownHostException uh )
        {
            System.err.println( "Unknown host: " + name.getHost() );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Removes the socket data from the maps
     * @param name socket to be removed
     */
    public void disconnect( SocketName name )
    {
    	System.out.println( "Disconnecting from '" + name + "'" );
    	
    	Sender s = senders.remove( name );
    	Receiver r = receivers.remove( name );
    	
    	s.kill();
    	r.kill();
    	
    	world.destroySocket( name );
    }
    
    /**
     * Tells all senders to send string s through their writers
     * @param s Message to be sent
     */
    public void send( String s )
    {
    	Iterator iter = senders.keySet().iterator();
    	
    	while ( iter.hasNext() )
    	{
    		Sender sender = senders.get( iter.next() );
    		sender.send(s);
    	}
    }
    
    //testing methods
    protected Map<SocketName, Sender> getSenders()
    {
    	return senders;
    }
    
    protected Map<SocketName, Receiver> getReceivers()
    {
    	return receivers;
    }
}

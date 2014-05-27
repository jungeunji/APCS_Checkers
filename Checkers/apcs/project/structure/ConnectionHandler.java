package apcs.project.structure;

import java.io.*;
import java.net.*;
import java.util.*;

import network.reference.SocketName;

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
	
    protected synchronized void spawn( Socket sock )
    {
        SocketName name = new SocketName( sock.getInetAddress().toString(),
            sock.getPort(),
            "Incoming " + count );
        count++;
        spawn( name, sock );
    }

    protected void spawn( SocketName name, Socket sock )
    {
        Sender s = new Sender( world, name, sock );
        Receiver r = new Receiver( world, name, sock );

        senders.put( name, s );
        receivers.put( name, r );

        world.createSocket( name );
    }
    
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
    
    public void disconnect( SocketName name )
    {
    	System.out.println( "Disconnecting from '" + name + "'" );
    	
    	Sender s = senders.remove( name );
    	Receiver r = receivers.remove( name );
    	
    	s.kill();
    	r.kill();
    	
    	world.destroySocket( name );
    }
    
    public void send( String s )
    {
    	Iterator iter = senders.keySet().iterator();
    	
    	while ( iter.hasNext() )
    	{
    		Sender sender = senders.get( iter.next() );
    		sender.send(s);
    	}
    }
}

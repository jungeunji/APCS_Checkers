package apcs.project.structure;

import java.awt.Color;

import java.net.*;
import network.reference.SocketName;

public class NetworkGame extends CheckerGame 
{
	private String localName;
	
	private int talkPort = -1;
	
	private int listenPort = -1;
	
	private NetworkWorld world;
	
	private int ownPlayerIndex = 1;
	
	public NetworkGame()
	{
		super(false);
		getWorld().newGame();
		
		String[] ports = new String[3];
		ConnectionInput connInfo = new ConnectionInput();
		do
		{
			ports = connInfo.getInput();
			if ( ports[0].length() == 0 )
			{
				return;
			}
			try
			{
				talkPort = Integer.parseInt( ports[1] );
				listenPort = Integer.parseInt( ports[2] );
			}
			catch( NumberFormatException e )
			{}
			
			if ( talkPort < 1024 || talkPort > 65535 || listenPort < 1024 || listenPort > 65535 )
			{
				connInfo.portErrorMessage();
			}
		}
		while ( talkPort < 1024 || talkPort > 65535 || listenPort < 1024 || listenPort > 65535 );
		localName = ports[0];
		setWorld( this, listenPort );
		setPlayers( resetPlayers() );
		world = (NetworkWorld)getWorld();
		
		connect();
		getPlayers()[ownPlayerIndex].setName( localName );
	}
	
	public NetworkWorld getNetworkWorld()
	{
		return world;
	}
	
	public int getOwnIndex()
	{
		return ownPlayerIndex;
	}
	
	private CheckerPlayer[] resetPlayers()
	{
		CheckerPlayer[] players = new CheckerPlayer[2];
		players[0] = new HumanCheckerPlayer( getWorld(), "Red", Color.RED, getWorld().getRed() );
		players[1] = new HumanCheckerPlayer( getWorld(), "Black", Color.BLACK, getWorld().getBlack() );
		return players;
	}
	
	protected void connect() 
	{
		try
		{
			SocketName sock = new SocketName( InetAddress.getLocalHost().getHostAddress(),
					talkPort + "",
					"port_" + talkPort);
			
			if (world.getConnections().contains(sock)) 
			{
				world.setMessage("Cannot connect to " + sock
						+ ": already connected");
			} 
			else 
			{
				world.getHandler().connect(sock);
				world.setMessage("Connection successful! Connected to " + sock);
				world.getHandler().send(localName);
			}
		}
		catch (ConnectException ce)
		{
			ownPlayerIndex = 0;
			world.setMessage("Please wait for your opponent to connect...");
		}
		catch (IllegalArgumentException iae)
		{
			world.setMessage("Cannot connect: " + iae.getMessage());
		}
		catch (UnknownHostException uhe )
		{
			uhe.printStackTrace();
		}
	}
	
	protected void disconnect( SocketName name ) 
	{
		world.getHandler().disconnect( name );
	}
}

package apcs.project.structure;

import java.awt.Color;

import java.net.*;
import network.reference.SocketName;

/**
 * This class is an extension of CheckerGame that
 * explicitly deals with network games.
 * 
 * @author Darren Yang
 * @version May 26, 2014
 */
public class NetworkGame extends CheckerGame 
{
	/** Name of player on local machine */
	private String localName;
	
	/** IP Address of opponent */
	private String ipAddress;
	
	/** Listen port */
	private int listenPort = -1;
	
	/** Talk port */
	private int talkPort = -1;
	
	/** World in which the game is taking place */
	private NetworkWorld world;
	
	/** Player index of local machine player */
	private int ownPlayerIndex = 1;
	
	/**
	 * Creates a new NetworkGame by querying the appropriate
	 * network connection info from the player.
	 * If the player fails to provide adequate information, a
	 * Human Checkers game is created by default.
	 */
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
				ipAddress = ports[3];
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
	
	/**
	 * Returns the network world of this game
	 * @return network world
	 */
	public NetworkWorld getNetworkWorld()
	{
		return world;
	}
	
	/**
	 * Returns the player index of the local player
	 * @return local player's index
	 */
	public int getOwnIndex()
	{
		return ownPlayerIndex;
	}
	
	/**
	 * Resets the players by creating them with the correct NetworkWorld after initializing
	 * all other elements of the CheckerGame
	 * @return array of new CheckerPlayers
	 */
	private CheckerPlayer[] resetPlayers()
	{
		CheckerPlayer[] players = new CheckerPlayer[2];
		players[0] = new HumanCheckerPlayer( getWorld(), "Red", Color.RED, getWorld().getRed() );
		players[1] = new HumanCheckerPlayer( getWorld(), "Black", Color.BLACK, getWorld().getBlack() );
		return players;
	}
	
	/**
	 * Attempts to connect to the given socket in the constructor
	 */
	protected void connect() 
	{
		try
		{
			SocketName sock = new SocketName( ipAddress,
					talkPort + "",
					"port_" + talkPort );
			
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
	}
	
	/**
	 * Disconnects from the specified socket
	 * @param name socket
	 */
	protected void disconnect( SocketName name ) 
	{
		world.getHandler().disconnect( name );
	}
}

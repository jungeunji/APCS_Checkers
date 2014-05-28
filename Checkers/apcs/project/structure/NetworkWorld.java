package apcs.project.structure;

import network.reference.*;
import info.gridworld.grid.*;
import java.util.*;

/**
 * This class is an extension of CheckerWorld that
 * explicitly deals with network games
 * @author Darren Yang
 * @version May 26, 2014
 */
public class NetworkWorld extends CheckerWorld 
{
	/** ConnectionHandler of the game */
	protected ConnectionHandler networker;
	
	/** List of ongoing connections */
	protected List<SocketName> connections;
	
	/** Default listen port if none specified */
	private static final int DEFAULT_LISTEN_PORT = 1338;
	
	/**
	 * Creates a new NetworkWorld with the specified game and default listen port
	 * @param game NetworkGame
	 */
	public NetworkWorld( CheckerGame game )
	{
		this( game, DEFAULT_LISTEN_PORT );
	}
	
	/**
	 * Creates a new NetworkWorld with the specified game and listen port
	 * @param game NetworkGame
	 * @param listenPort specified listen port
	 */
	public NetworkWorld( CheckerGame game, int listenPort )
	{
		super(game);
		networker = new ConnectionHandler( this, listenPort );
		connections = new LinkedList<SocketName>();
	}
	
	/**
	 * This method receives a message sent over the socket and processes it
	 * @param name name of the socket
	 * @param message received message
	 */
	public void receive(SocketName name, String message) 
	{
		int space = message.indexOf(",");
		int dot = message.indexOf(".");
		if ( space == -1 && dot == -1 )
		{
			getGame().getPlayers()[1 - ((NetworkGame)getGame()).getOwnIndex()].setName(message);
			setMessage( "Connection successful! Connected to " + connections.get(0) + "\n" 
					+ "You control the red pieces. "
					+ getGame().getPlayers()[((NetworkGame)getGame()).getOwnIndex()].getName() + " moves first." );
			networker.send( "name." + getGame().getPlayers()[((NetworkGame)getGame()).getOwnIndex()].getName() );
		}
		else if ( dot == 4 )
		{
			getGame().getPlayers()[1 - ((NetworkGame)getGame()).getOwnIndex()].setName(message.substring(5));
			setMessage( getMessage() + "\n"
					+ "You control the black pieces. "
					+ getGame().getPlayers()[1 - ((NetworkGame)getGame()).getOwnIndex()].getName() + " moves first." );
		}
		else
		{
			int r = Integer.parseInt(message.substring(0, space));
			int c = Integer.parseInt(message.substring(space + 1));
			locationClicked(new Location(r, c), false);
		}
	}

	/**
	 * Processes a click on the grid and declares it a local click.
	 */
	public boolean locationClicked(Location loc) 
	{
		locationClicked(loc, true);
		return true;
	}

	/**
	 * Processes a click on the grid. If it is local, it sends it to the opponent.
	 * @param loc Location clicked
	 * @param local whether or not the click was local
	 */
	public void locationClicked(Location loc, boolean local) 
	{
		if (local) 
		{
			// send message
			networker.send(loc.getRow() + "," + loc.getCol());
		}
		if ( ( ((NetworkGame)getGame()).getOwnIndex() == getGame().getTurnIndex() && local )
				|| ((NetworkGame)getGame()).getOwnIndex() != getGame().getTurnIndex() && !local )
		{
			//restricts unfair control over opponent's pieces during their turn
			super.locationClicked(loc);
		}
	}
	
	/**
	 * Returns the list of ongoing connections
	 * @return list of connections
	 */
	public List<SocketName> getConnections()
	{
		return connections;
	}
	
	/**
	 * Returns the connection handler
	 * @return the world's connection handler
	 */
	public ConnectionHandler getHandler()
	{
		return networker;
	}
	
	/**
	 * Creates a new socket and adds it to the list
	 * @param name new socket name
	 */
	public synchronized void createSocket( SocketName name )
	{
		connections.add( name );
	}
	
	/**
	 * Destroys a socket and removes it from the list
	 * @param name socket to be destroyed
	 */
	public void destroySocket( SocketName name )
	{
		connections.remove( name );
	}
}

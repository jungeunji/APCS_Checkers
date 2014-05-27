package apcs.project.structure;

import network.reference.*;
import info.gridworld.grid.*;
import java.util.*;

public class NetworkWorld extends CheckerWorld 
{
	protected ConnectionHandler networker;
	
	protected List<SocketName> connections;
	
	private static final int DEFAULT_LISTEN_PORT = 1338;
	
	public NetworkWorld( CheckerGame game )
	{
		this( game, DEFAULT_LISTEN_PORT );
	}
	
	public NetworkWorld( CheckerGame game, int listenPort )
	{
		super(game);
		networker = new ConnectionHandler( this, listenPort );
		connections = new LinkedList<SocketName>();
	}
	
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

	public boolean locationClicked(Location loc) 
	{
		locationClicked(loc, true);
		return true;
	}

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
	
	public List<SocketName> getConnections()
	{
		return connections;
	}
	
	public ConnectionHandler getHandler()
	{
		return networker;
	}
	
	public synchronized void createSocket( SocketName name )
	{
		connections.add( name );
	}
	
	public void destroySocket( SocketName name )
	{
		connections.remove( name );
	}
}

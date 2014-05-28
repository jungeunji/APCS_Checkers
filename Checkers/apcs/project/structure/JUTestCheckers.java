package apcs.project.structure;

import network.reference.SocketName;

import org.junit.*;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;

import java.awt.*;
import java.util.*;
import java.io.*;
import java.net.*;

import info.gridworld.grid.*;

public class JUTestCheckers 
{
	//CheckerGame's test methods
	CheckerGame game;

	@Test
	public void testGameConstructorNoArgs()
	{
		game = new CheckerGame();
		for ( CheckerPlayer cp : game.getPlayers() )
		{
			assertEquals( cp instanceof HumanCheckerPlayer, true );
		}
		assertEquals( game.getPlayerIndex(), 0 );
		assertEquals( game.getNewGame(), 'z' );
	}
	
	@Test
	public void testGameConstructorBool()
	{
		game = new CheckerGame(false); //previous one tested true
		for ( CheckerPlayer cp : game.getPlayers() )
		{
			assertEquals( cp instanceof HumanCheckerPlayer, true );
		}
		assertEquals( game.getPlayerIndex(), 0 );
		assertEquals( game.getNewGame(), 'z' );
	}
	
	@Test
	public void testGameConstructorChar()
	{
		game = new CheckerGame( 'h' );
		CheckerGame game1 = new CheckerGame( 'c' );
		CheckerGame game2 = new CheckerGame( 'a' );
		
		assertEquals( game.getPlayers()[1] instanceof HumanCheckerPlayer, true );
		assertEquals( game1.getPlayers()[1] instanceof SmartComputerCheckerPlayer, true );
		assertEquals( game2.getPlayers()[1] == null, true );
	}
	
	@Test
	public void testGameSetWorld()
	{
		game = new CheckerGame();
		game.setWorld( game, 1337 );
		assertEquals( game.getWorld() instanceof NetworkWorld, true );
	}
	
	@Test
	public void testGameSetPlayers()
	{
		game = new CheckerGame();
		game.setPlayers( new CheckerPlayer[2] );
		assertTrue( game.getPlayers()[0] == null );
		assertTrue( game.getPlayers()[1] == null );
	}
	
	@Test
	public void testGameNewGame()
	{
		game = new CheckerGame();
		game.newGame( 'e' );
		assertTrue( game.getNewGame() == 'e' );
	}
	
	@Test
	public void testGamePlayGame()
	{
		game = new CheckerGame();
		game.getPlayers()[0].getPieces().clear();
		game.newGame( 'i' );
		assertEquals( game.playGame(), 'i' );
	}
	
	//CheckerPlayer's test methods
	CheckerPlayer player;
	CheckerWorld world;
	
	@Test
	public void testPlayerConstructor()
	{
		world = new CheckerWorld( new CheckerGame() );
		player = new HumanCheckerPlayer( world, "Hi", Color.BLACK, new ArrayList<Piece>() );
		assertEquals( player.getName(), "Hi" );
		assertEquals( player.getWorld(), world );
		assertEquals( player.getColor(), Color.BLACK );
		assertTrue( player.getPieces().isEmpty() );	
	}
	
	@Test
	public void testPlayerSetName()
	{
		world = new CheckerWorld( new CheckerGame() );
		player = new HumanCheckerPlayer( world, "Hi", Color.BLACK, new ArrayList<Piece>() );
		player.setName("Hello");
		assertEquals( player.getName(), "Hello" );
	}
	
	@Test
	public void testPlayerUpdatePieces()
	{
		world = new CheckerWorld( new CheckerGame() );
		player = new HumanCheckerPlayer( world, "Hi", Color.BLACK, world.getBlack() );
		int oldSize = player.getPieces().size();
		player.getPieces().get(0).setLocation( new Location( 4, 4 ) );
		player.updatePieces();
		assertEquals( player.getPieces().size(), oldSize - 1 );
	}
	
	@Test
	public void testPlayerCanPlay()
	{
		world = new CheckerWorld( new CheckerGame() );
		player = new HumanCheckerPlayer( world, "Hi", Color.BLACK, new ArrayList<Piece>() );
		assertFalse( player.canPlay() );
	}
	
	@Test
	public void testPlayerGetMoves()
	{
		world = new CheckerWorld( new CheckerGame() );
		player = new HumanCheckerPlayer( world, "Hi", Color.BLACK, new ArrayList<Piece>() );
		assertTrue( player.getMoves().isEmpty() );
	}

	//CheckerWorld test methods
	
	@Test
	public void testWorldConstructor()
	{
		world = new CheckerWorld( game = new CheckerGame() );
		assertEquals( world.getGame(), game );
	}
	
	@Test
	public void testWorldSetLastMove()
	{
		MoveInfo move = new MoveInfo( null, null );
		world = new CheckerWorld( new CheckerGame() );
		world.setLastMove( move );
		assertTrue( world.getLastMove().getPiece() == null );
		assertTrue( world.getLastMove().getLocation() == null );
	}
	
	@Test
	public void testWorldLocationClicked()
	{
		world = new CheckerWorld( new CheckerGame() );
		world.locationClicked( new Location( 5, 6 ) );
		assertTrue( world.getGrid().get( new Location( 4, 7 ) ) == null );
	}
	
	@Test
	public void testWorldNewGame()
	{
		world = new CheckerWorld( new CheckerGame() );
		world.newGame( 'j' );
		assertEquals( world.getNewGame(), 'j' );
		assertEquals( world.getLocation(), null );
	}
	
	@Test
	public void testWorldSetMessage()
	{
		world = new CheckerWorld( new CheckerGame() );
		world.setMessage("Hello");
		assertEquals( world.getMessage(), "Hello" );
	}
	
	//ConnectionHandler test methods
	ConnectionHandler conn;
	
	@Test
	public void testConnConstructor()
	{
		conn = new ConnectionHandler( new NetworkWorld( new CheckerGame() ), 1337 );
		assertTrue( conn.getSenders().isEmpty() );
		assertTrue( conn.getReceivers().isEmpty() );
	}
	
	//MoveInfo test methods
	MoveInfo move;
	
	@Test
	public void testMoveConstructorNorm()
	{
		Location loc = new Location( 4, 5 );
		move = new MoveInfo( null, loc);
		assertTrue( move.getPiece() == null );
		assertEquals( move.getLocation(), loc );
	}
	
	@Test
	public void testMoveConstructorChar()
	{
		move = new MoveInfo( 'a' );
		assertEquals( move.getPiece(), null );
		assertEquals( move.getLocation(), null );
	}
	
	@Test
	public void testMoveJump()
	{
		move = new MoveInfo( new Piece( Color.BLACK, new Location(4,5), 
				new CheckerWorld( new CheckerGame() ) ), new Location(6, 7) );
		assertTrue( move.isJump() );
	}
	
	@Test
	public void testMoveEquals()
	{
		move = new MoveInfo( new Piece( Color.BLACK, new Location(4,5), 
				new CheckerWorld( new CheckerGame() ) ), new Location(6, 7) );
		MoveInfo move1 = move = new MoveInfo( new Piece( Color.BLACK, 
				new Location(4,5), new CheckerWorld( new CheckerGame() ) ), new Location(6, 7) );
		assertTrue( move.equals(move1) );
	}
	
	//Piece test methods
	Piece piece;
	
	@Test
	public void testPieceConstructor()
	{
		Location loc = new Location( 4, 5 );
		piece = new Piece( Color.RED, loc, world = new CheckerWorld( new CheckerGame() ) );
		assertEquals( piece.getColor(), Color.RED );
		assertEquals( piece.getLocation(), loc );
		assertFalse( piece.king() );
		assertEquals( piece.getGrid(), world.getGrid() );
	}

	@Test
	public void testPieceSetLocation()
	{
		piece = new Piece( Color.RED, new Location( 4, 5 ), world = new CheckerWorld( new CheckerGame() ) );
		Location loc = new Location( 1, 6 );
		piece.setLocation( loc );
		assertEquals( piece.getLocation(), loc );
	}
	
	@Test
	public void testPieceSetKing()
	{
		piece = new Piece( Color.RED, new Location( 4, 5 ), world = new CheckerWorld( new CheckerGame() ) );
		Piece piece1 = new Piece( Color.BLACK, new Location( 7, 0 ), world );
		piece.setKing();
		piece1.setKing();
		assertFalse( piece.king() );
		assertTrue( piece1.king() );
	}
	
	@Test
	public void testPieceJumpLocs()
	{
		piece = new Piece( Color.RED, new Location( 4, 5 ), world = new CheckerWorld( new CheckerGame() ) );
		assertFalse( piece.canJump() );
	}
	
	@Test
	public void testPieceAllowedMoves()
	{
		piece = new Piece( Color.RED, new Location( 4, 5 ), new CheckerWorld( new CheckerGame() ) );
		assertEquals( piece.getAllowedMoves().size(), 2 );
	}
	
	@Test
	public void testPieceSuffix()
	{
		piece = new Piece( Color.RED, new Location( 0, 1 ), new CheckerWorld( new CheckerGame() ) );
		piece.setKing();
		assertEquals( piece.getImageSuffix(), "_redking" );
	}
	
	@Test
	public void testPieceSetSelected()
	{
		piece = new Piece( Color.RED, new Location( 4, 5 ), new CheckerWorld( new CheckerGame() ) );
		piece.setSelected(true);
		assertTrue( piece.getSelected() );
	}
}

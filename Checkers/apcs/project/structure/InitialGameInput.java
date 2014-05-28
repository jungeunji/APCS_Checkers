package apcs.project.structure;

import java.awt.*;

import javax.swing.*;

/**
 * This class deals with the player's choice of game
 * when he starts up Checkers
 * 
 * @author Darren Yang
 * @version May 27, 2014
 */
public class InitialGameInput 
{
	/** Game choices */
	private String[] games = { "New Game against Human...",
			   "New Game against Computer...",
			   "New Network Game..." };
	
	/** Panel to hold the list */
	private JPanel panel;
	/** List containing the options */
	private JComboBox dropDownList;
	
	/**
	 * Creates the Panel, but doesn't show it yet
	 */
	public InitialGameInput()
	{
		panel = new JPanel();
		//panel.setLayout( new BoxLayout( panel, BoxLayout.Y_AXIS ) );
		panel.setLayout( new BorderLayout() );
		
		JLabel intro = new JLabel( "Hello! Please select a game.");
		panel.add( intro, BorderLayout.WEST );
		panel.add(Box.createVerticalStrut(5));
		
		dropDownList = new JComboBox( games );
		panel.add( dropDownList, BorderLayout.SOUTH );
	}
	
	/**
	 * Shows a confirm dialog for the player to choose from.
	 * If he closes or cancels, the game will terminate.
	 * 
	 * @return player's choice of game
	 */
	public char gameSelection()
	{
		char gameType = 'h';
		int result = JOptionPane.showConfirmDialog( null, panel, "New Game", 
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE );
		if ( result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION )
		{
			gameType = 'z';
		}
		else if ( result == JOptionPane.OK_OPTION )
		{
			String choice = dropDownList.getSelectedItem().toString();
			if ( choice.equals( games[1] ) )
			{
				gameType = 'c';
			}
			else if ( choice.equals( games[2] ) )
			{
				gameType = 'n';
			}
			else
			{
				gameType = 'h';
			}
		}
		return gameType;
	}
}

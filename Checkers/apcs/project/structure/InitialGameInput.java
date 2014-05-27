package apcs.project.structure;

import java.awt.*;

import javax.swing.*;

public class InitialGameInput 
{
	private String[] games = { "New Game against Human...",
			   "New Game against Computer...",
			   "New Network Game..." };
	
	private JPanel panel;
	private JComboBox dropDownList;
	
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

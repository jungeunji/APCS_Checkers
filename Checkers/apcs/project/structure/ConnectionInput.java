package apcs.project.structure;

import javax.swing.*;


public class ConnectionInput 
{
	private JPanel panel;
	
	private JTextField name;
	private JTextField talk;
	private JTextField listen;
	private JTextField ip;
	
	private String[] input = { "", "", "", "" };
	private String[] cancel = { "", "", "", "" };
	
	public ConnectionInput()
	{
		panel = new JPanel();
		
		name = new JTextField(7);
		talk = new JTextField(7);
		listen = new JTextField(7);
		ip = new JTextField(7);
		
		panel.setLayout( new BoxLayout( panel, BoxLayout.Y_AXIS) );
		panel.add(new JLabel("Name: "));
		panel.add(name);
		panel.add(Box.createVerticalStrut(5));
		panel.add(new JLabel("Talk Port: "));
		panel.add(talk);
		panel.add(Box.createVerticalStrut(5));
		panel.add(new JLabel("Listen Port: "));
		panel.add(listen);
		panel.add(Box.createVerticalStrut(5));
		panel.add(new JLabel("Opponent's IPv4 Address:"));
		panel.add(ip);
	}
	
	public String[] getInput()
	{
		int result = JOptionPane.showConfirmDialog(null, panel, 
				"Input Connection Information", JOptionPane.OK_CANCEL_OPTION);
		while ( result == JOptionPane.OK_OPTION 
				&& ( input[0].isEmpty() || input[1].isEmpty() || input[2].isEmpty() ) )
		{
			if ( name.getText().isEmpty()
					|| talk.getText().isEmpty()
					|| listen.getText().isEmpty()
					|| ip.getText().isEmpty() )
			{
				JOptionPane.showMessageDialog(null,
						"Please enter in all the values.", "Error",
						JOptionPane.WARNING_MESSAGE);
				result = JOptionPane.showConfirmDialog(null, panel, 
						"Input Connection Information", JOptionPane.OK_CANCEL_OPTION);
			}
			else if ( talk.getText().equals( listen.getText() ) )
			{
				JOptionPane.showMessageDialog(null,
						"The talk and listen ports must be different.", "Error",
						JOptionPane.WARNING_MESSAGE);
				result = JOptionPane.showConfirmDialog(null, panel, 
						"Input Connection Information", JOptionPane.OK_CANCEL_OPTION);
			}
			else if ( ip.getText().length() < 9 
					|| ip.getText().indexOf('.') < 0 
					|| ip.getText().indexOf('.') == ip.getText().lastIndexOf('.') )
			{
				JOptionPane.showMessageDialog(null,
						"Please enter a valid IP Address", "Error",
						JOptionPane.WARNING_MESSAGE);
				result = JOptionPane.showConfirmDialog(null, panel, 
						"Input Connection Information", JOptionPane.OK_CANCEL_OPTION);
			}
			else
			{
				input[0] = name.getText();
				input[1] = talk.getText();
				input[2] = listen.getText();
				input[3] = ip.getText();
			}
		}
		
		if ( result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION )
		{
			return cancel;
		}
		
		return input;
	}
	
	public void portErrorMessage()
	{
		JOptionPane.showMessageDialog(null, "Port values must be in between 1024-65535", 
				"Incorrect Port Numbers", JOptionPane.ERROR_MESSAGE );
	}
}

import javax.swing.*;
import java.awt.Button;
import java.awt.Panel;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.util.Observable;
import java.awt.event.ActionListener;

class View implements java.util.Observer
{
	private JTextArea handsField;
	private Button button;
	private JTextField numPlayersTextField;
	private JScrollPane scrollArea;
	private JTextField numPlayersLabel;
	private Frame frame;
	
	View()
	{
		frame = new Frame("Poker");
		handsField = new JTextArea();
		scrollArea = new JScrollPane(handsField);
		numPlayersTextField = new JTextField(20);
		numPlayersLabel = new JTextField("Number of Players:");
		Panel southPanel = new Panel();
		Panel eastPanel = new Panel();
		button = new Button("Deal Hands");

		southPanel.add(numPlayersLabel);
		southPanel.add(numPlayersTextField);
		eastPanel.add(button);

		frame.add("North", new Label("Player Hands"));
		frame.add("Center", scrollArea);
		frame.add("South", southPanel);
		frame.add("East", eastPanel);
		frame.addWindowListener(new CloseListener());	
		frame.setSize(1000,500);
		frame.setLocation(100,100);
		frame.setVisible(true);
	}

	public void update(Observable obs, Object obj)
	{
		if(obj instanceof StringBuffer)
		{
			handsField.setText(obj.toString());
		}
	}

	public void addController(ActionListener controller)
	{
		button.addActionListener(controller);
	}

	public String getNumPlayers()
	{
		return numPlayersTextField.getText();
	}

	public void displayError(String err)
	{
		JOptionPane.showMessageDialog(frame, err);
	}
	
	public static class CloseListener extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			e.getWindow().setVisible(false);
			System.exit(0);
		}
	}

}

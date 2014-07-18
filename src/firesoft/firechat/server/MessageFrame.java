package firesoft.firechat.server;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

 /**
 * <p>Message frame</p>
 * <p>Provides messages to the operator of the server</p>
 *
 */
public class MessageFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//create a reference in order to access the parent JFrame from within
	//the inner class of the constructor.
	private JFrame parentFrame;

	public MessageFrame(JFrame parent, String message, String title)
	{

		Container frameContent = this.getContentPane();
		parentFrame = parent;
		//Handle the event generated when the user presses
		//the close (x) button of the frame
		this.addWindowListener(new WindowAdapter()//anonymus inner class
		{
			public void windowClosing(WindowEvent ev)
			{
				if (parentFrame != null)
					parentFrame.setEnabled(true);
				setVisible(false);
			}
		}
		);

		JLabel label = new JLabel(message);
		label.setFont(new Font("Courier New", 1, 12));
		label.setForeground(Color.RED);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setVisible(true);

		frameContent.add(label, BorderLayout.CENTER);
		frameContent.setBackground(SystemColor.control);
		this.setLocationRelativeTo(parentFrame);
		this.setResizable(false);
		this.setSize(350, 100);
		this.setTitle(title);

		if (parentFrame != null)
			parentFrame.setEnabled(false);

		this.setTitle(title);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}

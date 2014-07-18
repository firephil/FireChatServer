package firesoft.firechat.server;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ServerUI
    extends JFrame
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8215397023855348568L;
	private JButton startBtn, stopBtn, clearBtn, printBtn;
    private JPanel buttonsPanel;
    private JTextArea textArea;
    private JScrollPane textPane;
    private Server server;
    private JFrame thisFrame;
	private JLabel label;
	Thread runServer;

	private final String title = "Chat Server 1.0, by Philip Papadatos";
    public ServerUI()
    {

	try
	{
		this.setTitle(title);
		thisFrame = this;
		// setup the action when close button (X) of the JFrame  is clicked
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent ev)
			{
				if(server!=null)
					server.stop();
				System.out.println("exiting program");
				System.exit(0);
			}
		}
		);
		label = new JLabel("Output Console");
		label.setFont(new java.awt.Font("Courier New", 1, 16));
		label.setForeground(SystemColor.inactiveCaption);
		label.setBounds(new Rectangle(60, 30, 140, 25));
		label.setVisible(true);

//**************************SETUP BUTTONS*****************************************

	    startBtn = new JButton("Start Server");
	    startBtn.setVisible(true);
	    startBtn.setBorder(BorderFactory.createRaisedBevelBorder());
	    startBtn.setBounds(new Rectangle(11, 5, 94, 30));

	    stopBtn = new JButton("Stop Server");
	    stopBtn.setEnabled(false);
		stopBtn.setBounds(new Rectangle(104, 5, 94, 30));
	    stopBtn.setBorder(BorderFactory.createRaisedBevelBorder());
	    stopBtn.setVisible(true);

	    clearBtn = new JButton("Clear");
	    clearBtn.setBounds(new Rectangle(197, 5, 87, 30));
	    clearBtn.setEnabled(false);
	    clearBtn.setBorder(BorderFactory.createRaisedBevelBorder());
	    clearBtn.setVisible(true);

	    printBtn = new JButton("Print Statistics");
	    printBtn.setBounds(new Rectangle(65, 40, 140, 35));
		printBtn.setBorder(BorderFactory.createRaisedBevelBorder());
		printBtn.setEnabled(false);
	    printBtn.setVisible(true);

	    buttonsPanel = new JPanel();
	    buttonsPanel.setOpaque(false);
	    buttonsPanel.setBounds(new Rectangle(66, 354, 291, 105));
	    buttonsPanel.setLayout(null);
	    buttonsPanel.add(startBtn);
	    buttonsPanel.add(stopBtn);
	    buttonsPanel.add(clearBtn);
	    buttonsPanel.add(printBtn);

//*********************************Buttons END**********************************

	    textArea = new JTextArea();
	    textArea.setLineWrap(true);
	    textArea.setEditable(false);
	    textArea.setBackground(Color.WHITE);
		textArea.setWrapStyleWord(true);
	    textArea.setVisible(true);
		textArea.setEnabled(true);

	    textPane = new JScrollPane(textArea);
	    textPane.setVerticalScrollBarPolicy(JScrollPane.
						VERTICAL_SCROLLBAR_ALWAYS);
	    textPane.setHorizontalScrollBarPolicy(JScrollPane.
						  HORIZONTAL_SCROLLBAR_NEVER);
	    textPane.setBorder(BorderFactory.createLoweredBevelBorder());
	    textPane.setBounds(new Rectangle(61, 56, 305, 264));
	    textArea.setFont(new java.awt.Font("Courier New", 0, 12));

	    //inner class which implements the events generated when the
	    //button are pressed.
	    class ServerEvent implements ActionListener
	    {
			public void actionPerformed(ActionEvent event)
			{
				if (event.getSource() == startBtn)
				{
					start();
					return;
				}

				if (event.getSource() == stopBtn)
				{
					stop();
					return;
				}
				if (event.getSource() == clearBtn)
				{
					clearScreen();
					return;
				}
				if (event.getSource() == printBtn)
				{
					printStats(server.getStats());
					return;
				}
			}
		}

		ServerEvent serverEvent = new ServerEvent();
		startBtn.addActionListener(serverEvent);
		stopBtn.addActionListener(serverEvent);
		clearBtn.addActionListener(serverEvent);
		printBtn.addActionListener(serverEvent);

		Container content = this.getContentPane();
		content.setLayout(null);
		content.add(label);
		content.add(textPane);
		content.add(buttonsPanel);
		content.setBackground(SystemColor.control); //light gray
		//Setup the properties of the frame
		this.setLocationRelativeTo(null);
		this.setSize(420, 470);
		this.setVisible(true);
		this.setResizable(false);
	}

	catch (Exception e)
	{
	    System.out.println(e.getMessage());
	}

    }

	final void start()
	{
		this.setTitle(title+"(Server Started)");
		startBtn.setEnabled(false);
		stopBtn.setEnabled(true);
		clearBtn.setEnabled(true);
		printBtn.setEnabled(true);
		server = new Server();
		if(server!=null)//exception is not thrown
		{
			runServer = new Thread(server);
			runServer.start();
		}
	}

	public final void stop()
	{
		if (this.server != null)
		{
			this.runServer.stop();
			server.stop(); //stop the server
		}
		textArea.setText("");
		textArea.updateUI();
		startBtn.setEnabled(true);
		stopBtn.setEnabled(false);
		clearBtn.setEnabled(false);
		printBtn.setEnabled(false);
		this.setTitle(title);
	}

	private final void clearScreen()
	{
		textArea.setText("");
		textArea.updateUI();
	}

	private final void printStats(String[] stats)
	{
		if(stats==null)
			return;

		for (int i = 0; i < stats.length; i++)
			textArea.append(stats[i]+"\n");
		textArea.updateUI();
	}
	public final void print(String message)
	{
		textArea.append(message+"\n");
    }
}

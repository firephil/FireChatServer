package firesoft.firechat.server;

//import ServerUI;
//import MessageFrame;

import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.text.DateFormat;

public final class Server implements Runnable
{
	private final int PORT = 6060;
	private ServerSocket serverSocket;
	static UserRecords userRecords;
	static boolean STOP;
	private boolean isOn;
	private DateFormat dateFormat;
	static ServerUI gui;

	/**
	 * Constructs a new server object and bind it to port 6060.
	 * If it cant's bind to the specified port an error is generated.
	 */
	public Server()
    {
	try
	{
	    serverSocket = new ServerSocket(PORT);
		userRecords =new UserRecords();
	    this.STOP = false;
	    dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT,
		DateFormat.SHORT);
	}
	catch(BindException e)
	{
		MessageFrame m = new MessageFrame(gui,ERROR.BIND,"ERROR");
		this.STOP = true;
		gui.stop();
		return;
	}
	catch (IOException e)
	{
		System.exit(0);
	}
    }

	public void run()
	{
		try
		{
			//listens for clients until stop flag becomes false
			while (this.STOP == false)
			{
				//create a new thread to handle each client connecting.
				ClientHandler handler = new ClientHandler(serverSocket.accept());
				handler.start();
			}
		}

		catch (IOException e)
		{
			MessageFrame m = new MessageFrame(gui,ERROR.SERVER_ERROR,"ERROR");
			//System.exit(1);
		}
}
/**
 * Stops the server which also stop all the ClientHandler Threads.
 *
 *
 */
public void stop()
{
	if(this!=null &&userRecords!=null)
	{
		if (userRecords.size() != 0)
		{
			Iterator iterator = userRecords.getIterator();
			ClientHandler handler;

			while (iterator.hasNext())
			{
				handler = (ClientHandler) iterator.next();
				handler.disconnectClient();
			}
		userRecords.clear();
		}
		try
		{
			this.serverSocket.close();
			userRecords = null;
		}
		catch (IOException e)
		{
			System.exit(1);
		}
	}//end of if
}

	public String[] getStats()
	{
		Iterator it = userRecords.getIterator();
		ClientHandler handler;
		int counter = 0;
		int size = userRecords.size();
		if(size==0)
		{
			MessageFrame m = new MessageFrame(null,ERROR.NO_STATS,"ERROR");
			return null;
		}
		String stats[] = new String[size];
		while (it.hasNext())
		{

			handler = (ClientHandler) it.next();
			stats[counter]= (counter+1)+"."
				             + handler.getUserName()
				             +" @ IP:" + handler.clientIp
							 +" and Port:"+handler.clientPort
							 +" is connected at local port "
							 +handler.localPort
							 +" since "
							 + dateFormat.format(handler.loginDate);
			counter++;
		}
		return stats;
	}

    public static void main(String args[])
    {
      gui = new ServerUI();
    }

}


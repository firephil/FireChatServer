package firesoft.firechat.server;

import java.util.LinkedHashMap;
import java.util.Iterator;

/**
 * <p>Protocol class</p>
 * </p>The protocol that the client and server applications understand.
 */
final class PROTOCOL
 {
	  private PROTOCOL(){}
	  static final int LOGGON         =0;
	  static final int LOGOFF         =1;
	  static final int PUBLIC         =2;
	  static final int PRIVATE        =3;
	  static final int LOGGIN_SUCCESS =4;
	  static final int LOGGIN_FAILED  =5;
	  static final int UPDATE_USERS   =6;
	  static final int ADD_USER       =7;
	  static final int REMOVE_USER    =8;
	  static final int SERVER_STOPPED = 9;
 }
 /**
  * A class that lists all the possible errors that can occur
  * when running the server application. Common to the client application.
  */
 class ERROR
   {
		  private ERROR(){}
		  static final String BIND    ="Failed to bind in the specified port";
		  static final String GENERAL ="Error ocurred";
		  static final String READ    ="Failed to receive data connection lost";
		  static final String WRITE   ="Failed to send data connection lost";
		  static final String LOGIN   ="Loggin failed. Please choose a different user name";
		  static final String CONNECT ="Failed to connect to the specified client";
		  static final String NO_STATS ="There are no users, nothing to print";
		  static final String SERVER_ERROR ="Failed to listen for connections";
   }
   /*
	*
	* <p>NetPacket class</p>
	* <p>Stores the messages that are send and received from the server and</p>
	*
	*/
   final class NetPacket
   {
	   public int command;
	   public String message;
	   public String recipient;
	   public String from;
	   public String userName;
	   public String password; //used to authenticate the super user

	   //create an empty packet
	   public NetPacket()
	   {
		   this.command = 0;
		   this.message = "";
		   this.recipient = "";
		   this.from = ""; //remove this
		   this.userName = ""; //remove this
		   this.password = ""; //remove this
	   }

	   public void clear() //clear all the values of the NetPacket's fields
	   {
		   this.command = 0;
		   this.message = "";
		   this.recipient = "";
		   this.from = "";
		   this.userName = "";
		   this.password = "";
	   }
   }
   /**
	*
	* <p>UserRecords class</p>
	* <p>Stores the users information in a LinkedHashMap by storing the
	* the client handler that are assigned to each client</p>
	* <p>Hides some complexity i.e boxing-unboxing of ClientHandler objects is
	* handled internally</p>
	*/
   final class UserRecords
   {
	   //unsynchronized version of hashtable which maintains the order
	   //which the objects where inserted.
	   private LinkedHashMap container;

	   public UserRecords()
	   {
		   container = new LinkedHashMap();
	   }

	   //return true if the user name is not taken
	   synchronized boolean add(ClientHandler handler)
	   {
		   if (container.containsKey(handler.getUserName()) == true)
			   return false;

		   container.put(handler.getUserName(), handler);
		   return true;
	   }

	   synchronized void remove(ClientHandler handler)
	   {
		   container.remove(handler.getUserName());
	   }

	   synchronized int size()
	   {
		   return container.values().size();
	   }

	   synchronized ClientHandler getHandler(String userName)
	   {
		   return (ClientHandler) container.get(userName);
	   }

	   synchronized Iterator getIterator() 
	   {
		   
		   return container.values().iterator();
	   }

	   synchronized void clear()
	   {
		   this.container.clear();
	   }
   }

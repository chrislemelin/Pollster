import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Responder 
{
	
	public static void main(String args[]) throws Exception
	{
		/*
		if (args.length != 2)
      	{
    	  return;
      	}
      	String serverhost = args[0];
      	int serverport = Integer.parseInt (args[1]);

      	// Set up server mailbox.
      	DatagramSocket mailbox = new DatagramSocket
         (new InetSocketAddress (serverhost, serverport));

      	// Set up model and client reader thread.
      	ResponderUI ui = ResponderUI.create();
      		
      	//ServerReaderThread reader = new ServerReaderThread (mailbox, model);
      	//reader.start();
		*/
      			
        String serverhost = args[0];
        int serverport = Integer.parseInt (args[1]);
        String clienthost = args[2];
        int clientport = Integer.parseInt (args[3]);

        DatagramSocket mailbox =
           new DatagramSocket
              (new InetSocketAddress (clienthost, clientport));

      	ResponderUI ui = ResponderUI.create();
        
        ModelProxy proxy =new ModelProxy
              (new InetSocketAddress (serverhost, serverport), mailbox);
        ClientModel model = new ClientModel();
        model.setModelListener(ui);
        model.setViewListener(proxy);
        ui.setViewListener(model);
        
        ClientReaderThread tr = new ClientReaderThread(mailbox,model);
        tr.start();
        
	}
	
	
}

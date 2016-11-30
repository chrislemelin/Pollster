import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Pollster 
{
	public static void main(String args[]) throws Exception
	{
		PollsterUI ui = PollsterUI.create();
		PollsterModel model = new PollsterModel();
		
		ui.addListener(model);
	    
		String serverhost = args[0];
	    int serverport = Integer.parseInt (args[1]);

	    DatagramSocket mailbox = new DatagramSocket(new InetSocketAddress (serverhost, serverport));
		
		ServerReaderThread in = new ServerReaderThread(mailbox,model);
		in.start();
		
		System.out.println("okey master");
		
		
	}
	
	
}

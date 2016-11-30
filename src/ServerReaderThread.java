import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerReaderThread extends Thread 
{
	private DatagramSocket mailbox;
	private ViewListener viewListener;
	
	public ServerReaderThread(DatagramSocket mailbox, ViewListener viewListener)
	{
		this.mailbox = mailbox;
		this.viewListener = viewListener;
	}
	
	
	@Override
	public void run() 
	{
	  byte[] buf = new byte [128];
	  DatagramPacket packet = new DatagramPacket (buf, buf.length);
	  ByteArrayInputStream bais;
	  DataInputStream in;
	  byte b;
	  String host;
	  try
	     {
	     for (;;)
	       	{
	        mailbox.receive (packet);
	        bais = new ByteArrayInputStream (buf, 0, packet.getLength());
	        in = new DataInputStream (bais);
	        b = in.readByte();
	        switch (b)
	           {
	           case 'A':
	        	   String question = in.readUTF();
	        	   int yes = in.readInt();
	        	   long timeStamp = in.read();
	        	   viewListener.answer(new ViewProxy (packet.getSocketAddress(), mailbox), packet.getSocketAddress(),timeStamp, question, yes);
	        	   break;
	           default:
	        	   System.err.println ("Bad message");
	        	   break;
	           }
	        }
	     }
	  catch (IOException exc)
	     {
	     }
	  finally
	     {
	     mailbox.close();
	     }
	  }
		
	

}

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientReaderThread extends Thread 
{
	private DatagramSocket mailbox;
	private ModelListener modelListener;
	
	public ClientReaderThread(DatagramSocket mailbox, ModelListener modelListener)
	{
		this.mailbox = mailbox;
		this.modelListener = modelListener;
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
			while(true)
	       	{
				mailbox.receive (packet);
				bais = new ByteArrayInputStream (buf, 0, packet.getLength());
	        	in = new DataInputStream (bais);
	        	b = in.readByte();
	        	switch (b)
	        	{
	        		case 'A':
	        			String question = in.readUTF();
	        			long timeStamp = in.readLong();
	        			modelListener.getNewQuestion(question, timeStamp);
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

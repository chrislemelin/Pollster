import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class ModelProxy implements ViewListener{

	private InetSocketAddress serverAddress;
	private DatagramSocket mailbox;
	
	
	public ModelProxy(InetSocketAddress serverAddress, DatagramSocket mailbox)
	{
		this.serverAddress = serverAddress;
		this.mailbox = mailbox;
	}
	
	@Override
	public void newQuestion(ViewProxy listener, String question) throws IOException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void answer(ViewProxy listener, SocketAddress address, long timeStamp, String question, int yes)
			throws IOException 
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream (baos);
		out.writeByte ('A');
		out.writeLong(timeStamp);
		out.flush();
		byte[] buf = baos.toByteArray();
		DatagramPacket packet = new DatagramPacket (buf, buf.length, serverAddress);
		mailbox.send (packet);
		
	}

}

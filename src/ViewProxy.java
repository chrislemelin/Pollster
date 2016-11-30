import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

public class ViewProxy implements ModelListener{

	private SocketAddress clientAddress;
	private DatagramSocket mailbox;
	
	
	public ViewProxy(SocketAddress clientAddress, DatagramSocket mailbox)
	{
		this.clientAddress = clientAddress;
		this.mailbox = mailbox;
	}
	
	@Override
	public void getNewQuestion(String s, long timeStamp) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream (baos);
		out.writeByte ('A');
		out.writeUTF (s);
		out.writeLong(timeStamp);
		out.flush();
		byte[] buf = baos.toByteArray();
		DatagramPacket packet = new DatagramPacket (buf, buf.length, clientAddress);
		mailbox.send (packet);
	}

	@Override
	public void setAgree(int value) 
	{	
	}

	@Override
	public void setDisagree(int value) 
	{		
	}
	
	public boolean equals(ViewProxy v)
	{
		return(clientAddress.equals(v.clientAddress));
	}

}

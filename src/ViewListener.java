import java.io.IOException;
import java.net.SocketAddress;

public interface ViewListener 
{
	public void newQuestion(ViewProxy listener, String question) throws IOException;
	public void answer(ViewProxy listener,SocketAddress address,long timeStamp,String question, int yes) throws IOException;

}

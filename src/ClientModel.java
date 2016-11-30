import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import javax.swing.Timer;

public class ClientModel implements ViewListener,ModelListener
{
	private final int TIMESTOTRY = 10;
	
	private ModelListener modelListener;
	private ViewListener viewListener;
	
	private ScheduledExecutorService pool;
	private ScheduledFuture<?> timeout;
	
	private String question;
	private boolean active;
	private int answer;
	
	
	
	public ClientModel()
	{
		active = false;
		answer = 0;
		pool = Executors.newScheduledThreadPool (1);
		
		Timer timer = new Timer (1000, new ActionListener()
        {
			public void actionPerformed (ActionEvent e)
			{
				report();
			}
        });
		timer.start();
		
	}
	
	public synchronized void setViewListener(ViewListener listener)
	{
		viewListener = listener;
	}
	
	public synchronized void setModelListener(ModelListener listener)
	{
		modelListener = listener;
	}

	public synchronized void report() 
	{
		try
		{
			if(viewListener != null)
			{
				viewListener.answer(null, null, System.currentTimeMillis(), question, answer);
			}
		}
		catch(Exception e)
		{	
		}
		
	}

	@Override
	public synchronized void getNewQuestion(String s,long timeStamp) throws IOException 
	{
		modelListener.getNewQuestion(s, timeStamp);
	}

	@Override
	public synchronized void setAgree(int value) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void setDisagree(int value) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void newQuestion(ViewProxy listener,String question) throws IOException 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void answer(ViewProxy listener,SocketAddress address,long timeStamp, String question, int yes) throws IOException 
	{
		this.answer = yes;
	}
	
	
	
}

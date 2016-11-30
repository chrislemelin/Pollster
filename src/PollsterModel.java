import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.Timer;

public class PollsterModel implements ViewListener
{	
	private HashMap<SocketAddress,ResponderModel> responders;
	private ArrayList<ModelListener> listeners;
	private String currentQuestion = "this is the example question master";
	
	private class ResponderModel
	{
		private int state;
		private ViewProxy viewProxy;
		private int lastUpdate;
		private long lastTimeStamp;
		private boolean active;
		
		private ResponderModel()
		{
			state = 0;
			Timer timer = new Timer (1000, new ActionListener()
	        {
				public void actionPerformed (ActionEvent e)
				{
					updateResponders();
				}
	        });
			timer.start();
		}
		
		private void updateResponders()
		{
			//lemaSystem.out.println("lemango "+ responders.size());
		    for (Map.Entry<SocketAddress, ResponderModel> entry : responders.entrySet())
		    {
		    	try
		    	{
		    		entry.getValue().viewProxy.getNewQuestion(currentQuestion, System.currentTimeMillis());
		    	}
		    	catch(IOException e)
		    	{
		    		
		    	}
		    }
		}
		
		private void report(String question, long timeStamp, int yes)
		{
			if(question.equals(currentQuestion) && timeStamp > lastTimeStamp)
			{
				lastTimeStamp = timeStamp;
					
				if(yes == 1 && state!= 1)
				{
					state = 1;
					//recalc()
				}
				else if(yes == -1 && state != -1)
				{
					state = -1;
					//recalc()
				}
			}
		}
	}

	public PollsterModel()
	{
		responders = new HashMap<SocketAddress, ResponderModel>();
		listeners = new ArrayList<ModelListener>();
	}
	
	private void notifyListeners()
	{
		
	}
	
	@Override
	public void answer(ViewProxy listener,SocketAddress address,long timeStamp, String question, int yes) throws IOException
	{
		if(responders.containsKey(address))
		{
			
			responders.get(address).report(question,timeStamp, yes);
		}
		else
		{
			System.out.println("new message");
			ResponderModel mod = new ResponderModel();
			mod.viewProxy = listener;
			mod.state = 0;
			responders.put(address, mod);
		}
	}
	
	public void newQuestion(ViewProxy listener, String Question)
	{
		/*
		System.out.println("new Question:" +Question);
	    Iterator it = responders.entrySet().iterator();
	    for (Map.Entry<SocketAddress, ResponderModel> entry : responders.entrySet())
	    {
	    	try
	    	{
	    		entry.getValue().viewProxy.getNewQuestion(Question, System.currentTimeMillis());
	    	}
	    	catch(IOException e)
	    	{
	    		
	    	}
	    }
	    */
		currentQuestion = Question;
	}
	
	
	
	
	
	
}

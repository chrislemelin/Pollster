import java.io.IOException;

public interface ModelListener 
{
	public void getNewQuestion(String s,long timeStamp) throws IOException;
	public void setAgree(int value);
	public void setDisagree(int value);
}

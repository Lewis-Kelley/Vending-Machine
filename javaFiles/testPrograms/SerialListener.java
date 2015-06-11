import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.*;

public class SerialListener implements Runnable
{
  InputStream in;
  OutputStream out;
  static String msg;
  public SerialListener()
  {
  	try {
		CommPortIdentifier portID = CommPortIdentifier.getPortIdentifier("COM3");
		CommPort commPort = portID.open("name" , 6000);
		SerialPort serialPort = (SerialPort) commPort;
			  
		out = serialPort.getOutputStream();
		in = serialPort.getInputStream();
	} catch (NoSuchPortException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (PortInUseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
  }
  public void run()
  {
        byte[] buffer = new byte[1024];
        int len = -1; 
        
        try {
			while(( len = this.in.read(buffer)) > -1)
			{
			     this.msg = new String(buffer);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
  public void send(String msg)
	{
		byte[] buffer = new byte[1024];
		buffer = msg.getBytes();
		
		try {
			this.out.write(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
  public void finished()
  {
	  this.serialPort.close();
  }
  public static void main(String[] args)
  {
	  SerialListener sl = new SerialListener();
	  sl.send("a");
	  System.out.println("finished");
	  
  }
}

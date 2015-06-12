import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.*;
//\_('~')_/ platform independance.
//sending works
//recieving wip
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
	  //Good Proper Code for sending
	  /*
	  sl.send("STRT");
	  System.out.println("finished");
	  sl.finished();
	  */
	  byte[] buffer = new byte[2];
	  while(true)
	  {
		  try {
			SerialListener.in.wait();
			buffer[0] = (byte) SerialListener.in.read();
			SerialListener.in.wait();
			buffer[1] = (byte) SerialListener.in.read(); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(buffer[0]);
		System.out.println((char)buffer[1]);
		System.out.println("");
			  
		  
	  }
	  
	  
	  
	  
	  
	  
	  /*
	  Thread t = new Thread(sl, "Listener");
	  t.start();
	  boolean isNotFinished = true;
	  while(isNotFinished)
	  {
	  //if(SerialListener.msg != "")
	  //{
		  System.out.println(SerialListener.msg);
		  //isNotFinished = false;
	 // }
	  }
	  sl.finished();
	  t.interrupt();
	  System.out.println("done");
	  
	  /*
	  String current = "";
	  String last = "";
	  while(true)
	  {
		  current = sl.msg;
		  if(current != last)
		  {
			  last = current;
			  System.out.println("done good things");
			  try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
	  }
	  */
	  
	  
  }

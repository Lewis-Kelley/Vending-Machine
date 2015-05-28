import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class ClassOfDestiny 
{
	
	InputStream in;
	OutputStream out;
	public ClassOfDestiny()
	{
		try {
			CommPortIdentifier portID = CommPortIdentifier.getPortIdentifier("COM3");
			CommPort commPort = portID.open("thing" , 6000);
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
	
	public String read()
	{
		byte[] buffer = new byte[1024];
		
		try {
			this.in.read(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (new String(buffer));
		
	
	}
	public send(String msg)
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

}

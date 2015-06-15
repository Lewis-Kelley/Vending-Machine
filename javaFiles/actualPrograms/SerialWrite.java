import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import gnu.io.*;

public class SerialWrite {

    SerialPort serialPort;
    OutputStream out;
    static String msg;

    public SerialWrite() {
  	try {
	    CommPortIdentifier portID = CommPortIdentifier.getPortIdentifier("COM3");
	    CommPort commPort = portID.open("name" , 6000);
	    serialPort = (SerialPort) commPort;
			  
	    out = serialPort.getOutputStream();
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

  public void send(String msg) {
      byte[] buffer = new byte[1024];
      buffer = msg.getBytes();
      
      try {
	  this.out.write(buffer);
      } catch (IOException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
      }
  }
  public void close() {
      this.serialPort.close();
  }
}

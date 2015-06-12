import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.IOException;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.util.Enumeration;

import gnu.io.*;
//\_('~')_/ platform independance.
//sending works
//recieving wip
public class SerialMerge implements SerialPortEventListener
{
    BufferedReader in;
    OutputStream out;
    SerialPort serialPort;

    private static final String PORT_NAMES[] = {
	"/dev/tty.usbserial-A9007UX1", // Mac OS X
	"/dev/ttyACM0", // Raspberry Pi
	"/dev/ttyUSB0", // Linux
	"COM3", // Windows
    };

    /** Milliseconds to block while waiting for port open */
    private static final int TIME_OUT = 2000;
    /** Default bits per second for COM port. */
    private static final int DATA_RATE = 9600;

    
    static String msg;
    public SerialMerge()
    {
	CommPortIdentifier portId = null;
	Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
	
	//First, Find an instance of serial port as set in PORT_NAMES.
	while (portEnum.hasMoreElements()) {
	    CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
	    for (String portName : PORT_NAMES) {
		if (currPortId.getName().equals(portName)) {
		    portId = currPortId;
		    break;
		}
	    }
	}
	if (portId == null) {
	    System.out.println("Could not find COM port.");
	    return;
	} else
	    System.out.println("Found port named " + portId);
	
	try {
	    // open serial port, and use class name for the appName.
	    serialPort = (SerialPort) portId.open(this.getClass().getName(),
						  TIME_OUT);
	    
	    // set port parameters
	    serialPort.setSerialPortParams(DATA_RATE,
					   SerialPort.DATABITS_8,
					   SerialPort.STOPBITS_1,
					   SerialPort.PARITY_NONE);
	    
	    // open the streams
	    in = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
	    out = serialPort.getOutputStream();
	    
	    // add event listeners
	    serialPort.addEventListener(this);
	    serialPort.notifyOnDataAvailable(true);
	} catch (Exception e) {
	    System.err.println(e.toString());
	}
    }

    /**
     * Handle an event on the serial port. Read the data and print it.
     */
    public synchronized void serialEvent(SerialPortEvent oEvent) {
	if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
	    try {
		String inputLine = in.readLine();
		System.out.println(inputLine);
	    } catch (Exception e) {
		System.err.println(e.toString());
	    }
	}
	// Ignore all the other eventTypes, but you should consider the other ones.
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
  public void finished() {
      this.serialPort.close();
  }

    public static void main(String[] args) throws Exception {
	SerialTest main = new SerialTest();
	main.initialize();
	Thread t=new Thread() {
		public void run() {
		    //the following line will keep this app alive for 1000 seconds,
		    //waiting for events to occur and responding to them (printing incoming messages to console).
		    try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
		}
	    };
	t.start();
	main.send("STRT");
	main.close();
	System.out.println("Started");
    }
}

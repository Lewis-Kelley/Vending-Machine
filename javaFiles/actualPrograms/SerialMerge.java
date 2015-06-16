import java.io.*;
import gnu.io.*;
import java.util.Enumeration;

public class SerialMerge implements SerialPortEventListener {

    SerialPort serialPort;
    OutputStream out;
    static String msg;
    /** Milliseconds to block while waiting for port open */
    private static final int TIME_OUT = 2000;
    /** Default bits per second for COM port. */
    private static final int DATA_RATE = 9600;
    private BufferedReader in;

    private static final String PORT_NAMES[] = {
	"/dev/tty.usbserial-A9007UX1", // Mac OS X
	"/dev/ttyACM0", // Raspberry Pi
	"/dev/ttyUSB0", // Linux
	"COM3", // Windows
    };

    
    /*public SerialMerge() {
  	try {
	    CommPortIdentifier portID = CommPortIdentifier.getPortIdentifier("COM3");
	    CommPort commPort = portID.open("name" , 6000);
	    serialPort = (SerialPort) commPort;
			  
	    out = serialPort.getOutputStream();
	    in = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
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
		
    }*/

    public void initialize() {
	// the next line is for Raspberry Pi and
	// gets us into the while loop and was suggested here was suggested http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
	//System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");
	
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
	    serialPort = (SerialPort) portId.open(this.getClass().getName(),TIME_OUT);
	    
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

    /**
     * Handle an event on the serial port. Read the data and print it.
     */
    public synchronized void serialEvent(SerialPortEvent oEvent) {
	if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
	    try {
		String inputLine=in.readLine();
		System.out.println(inputLine);
	    } catch (Exception e) {
		System.err.println(e.toString());
	    }
	}
	// Ignore all the other eventTypes, but you should consider the other ones.
    }
}

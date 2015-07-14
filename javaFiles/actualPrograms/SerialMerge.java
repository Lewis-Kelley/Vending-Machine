import java.io.*;
import gnu.io.*;
import java.util.Enumeration;

public class SerialMerge implements SerialPortEventListener {
    private String input;
    SerialPort serialPort;
    OutputStream out;
    static String msg;
    /** Milliseconds to block while waiting for port open */
    private static final int TIME_OUT = 2000;
    /** Default bits per second for COM port. */
    private static final int DATA_RATE = 9600;
    private BufferedReader in;

    private static final String PORT_NAMES[] = {
	"COM6",
	"COM7"
    };

    public void initialize() {
	input = "";
	CommPortIdentifier portId = null;
	Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

	//First, Find an instance of serial port as set in PORT_NAMES.
	while (portEnum.hasMoreElements()) {
	    CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
	    System.out.println("Checking " + currPortId.getName());
	    for (String portName : PORT_NAMES) {
		if (currPortId.getName().equals(portName)) {
		    portId = currPortId;
		    break;
		}
	    }
	}
	if (portId == null) {
	    System.out.println("Could not find COM port. Quitting");
	    VendingMachineRunner vmr = new VendingMachineRunner(true);
	    return;
	} else {
	    System.out.println("Found port named " + portId.getName());
	    System.out.println("=========================================");
	}

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
	    System.err.println("Failed to open port. Quitting");
	    VendingMachineRunner vmr = new VendingMachineRunner(true);
	    System.err.println(e.toString());
	}
    }

    public void send(String msg) throws Exception {
	System.out.println("C: " + msg);
	byte[] buffer = new byte[1024];
	buffer = msg.getBytes();

	this.out.write(buffer);
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
		input = new String(inputLine);
		System.out.println("A: " + input);
	    } catch (Exception e) {
		//System.err.println(e.toString());
	    }
	}
	// Ignore all the other eventTypes, but you should consider the other ones.
    }

    public String getLine() {
	String holder = new String(input);
	input = "";
	return holder;
    }
}

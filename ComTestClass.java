import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ComTestClass
{

    


void connect ( String portName ) throws Exception
{
    CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
    if ( portIdentifier.isCurrentlyOwned() )
    {
        System.out.println("Error: Port is currently in use");
    }
    else
    {
        //System.out.println("Connect 1/2");
        CommPort commPort = portIdentifier.open(this.getClass().getName(),6000);

        if ( commPort instanceof SerialPort )
        {
            //System.out.println("Connect 2/2");
            SerialPort serialPort = (SerialPort) commPort;
            //System.out.println("BaudRate: " + serialPort.getBaudRate());
            System.out.println("DataBIts: " + serialPort.getDataBits());
            //System.out.println("StopBits: " + serialPort.getStopBits());
            //System.out.println("Parity: " + serialPort.getParity());
            //System.out.println("FlowControl: " + serialPort.getFlowControlMode());
           // serialPort.setSerialPortParams(4800,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_ODD);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
            //System.out.println("BaudRate: " + serialPort.getBaudRate());
            //System.out.println("DataBIts: " + serialPort.getDataBits());
            //System.out.println("StopBits: " + serialPort.getStopBits());
            //System.out.println("Parity: " + serialPort.getParity());
            //System.out.println("FlowControl: " + serialPort.getFlowControlMode());
            InputStream in = serialPort.getInputStream();
            OutputStream out = serialPort.getOutputStream();

            (new Thread(new SerialReader(in))).start();
            (new Thread(new SerialWriter(out))).start();

        }
        else
        {
            System.out.println("Error: Only serial ports are handled by this example.");
        }
    }     
}


/** */
public static class SerialReader implements Runnable 
{
    InputStream in;

    public SerialReader ( InputStream in )
    {
        this.in = in;
    }

    public void run ()
    {
        byte[] buffer = new byte[1024];
        int len = -1;
        try
        {
            while ( ( len = this.in.read(buffer)) > -1 )
            {
                //System.out.println("Received a signal.");
            	SerialWriter.out.write(7);
            	System.out.print(new String(buffer,0,len));
                
            }
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }            
    }
}

/** */
public static class SerialWriter implements Runnable 
{
    static OutputStream out;

    public SerialWriter ( OutputStream out )
    {
        this.out = out;
    }

    public void run ()
    {
        try
        {                

            byte[] array = {0x1B, 0x50, 0x0D, 0x0A};
            while ( true )
            {
               this.out.write(new byte[]{0x1B, 0x50, 0x0D, 0x0A});
               this.out.flush();
               Thread.sleep(1000);  
            }                
        }
        catch ( IOException | InterruptedException e )
        {
            e.printStackTrace();
        }            
    }
}

public static void main ( String[] args )
{
    try
    {
        
    	(new ComTestClass()).connect("COM3");
        //SerialWriter.out.write(7);
    	
    }
    catch ( Exception e )
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}
}

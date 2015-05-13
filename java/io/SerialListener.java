public class SerialListener
{
  InputStream in;
  OutputStream out;
  static String msg;
  public SerialListener()
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
  void run()
  {
        byte[] buffer = new byte[1024];
        int len = -1; 
        
        while(( len = this.in.read(buffer)) > -1)
        {
             this.msg = new String(buffer);
        }
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

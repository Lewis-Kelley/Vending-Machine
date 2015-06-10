import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
public class BenSerialListener
{
	/*
	 * We're doing this the
	 * "Object Oriented Way"
	 *
	 * grumble grumble
	 */

	private Process p;
	private BufferedInputStream in;
	private BufferedOutputStream out;

	/*
	 * This class needs an device to communicate with,
	 * i.e. /dev/ttyACM0
	 * It opens it and then opens a GNU Screen process to communicate.
	 */
	public BenSerialListener(String portname) throws IOException, InterruptedException
	{
		Runtime.getRuntime().exec("stty -F " + portname + " cs8 9600 ignbrk -brkint -icrnl -imaxbel -opost -onlcr -isig -icanon -iexten -echo -echoe -echok -echoctl -echoke noflsh -ixon -crtscts").waitFor();
		p = Runtime.getRuntime().exec("screen " + portname + " 9600");
		in = new BufferedInputStream(p.getInputStream());
		out = new BufferedOutputStream(p.getOutputStream());
	}

	/*
	 * Sends the position to the Arduino
	 */
	public void setPosition(Coordinate c) throws IOException
	{
		this.println(c.toString());
	}
	/*
	 * I will gladly let someone else take care of the errors.
	 */
	public String getLine() throws IOException
	{
		String output = "", s = "";
		byte buffer[] = new byte[4];
		while (in.read(buffer, 0, 4) >= 0) {
			s = new String(buffer);
			if (s.indexOf('\n') < 0)
				output += s;
			else
				return output + s.substring(0, s.indexOf('\n'));
		}
		return output;
	}
	/*
	 * The letter 'q' is the de facto 'null terminator',
	 * so it must be defined that way
	 */
	public void println(String input) throws IOException
	{
		String s = input + "q";
		byte[] array = s.getBytes();

		for(byte num : array)
		    System.out.print(num);
		System.out.println();
		
		out.write(array);
		out.flush();
	}
	public void kill()
	{
		p.destroy();
	}
}

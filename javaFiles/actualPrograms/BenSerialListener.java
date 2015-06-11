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

	public Process p;

	/*
	 * This class needs an device to communicate with,
	 * i.e. /dev/ttyACM0
	 * It opens it and then opens a GNU Screen process to communicate.
	 */
	public BenSerialListener(String portname) throws IOException, InterruptedException
	{
		Runtime.getRuntime().exec("stty -F " + portname + " cs8 9600 ignbrk -brkint -icrnl -imaxbel -opost -onlcr -isig -icanon -iexten -echo -echoe -echok -echoctl -echoke noflsh -ixon -crtscts").waitFor();
		p = Runtime.getRuntime().exec("screen " + portname + " 9600");
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
		String output = "";
		int buffer = -1;
		while (p.getInputStream().available() > 0) {
				buffer = p.getInputStream().read();
				if ((char)buffer == '\n')
						return output;
				output += (char)buffer;
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
		p.getOutputStream().write(s.getBytes());
	}
	public void kill()
	{
		p.destroy();
	}
}

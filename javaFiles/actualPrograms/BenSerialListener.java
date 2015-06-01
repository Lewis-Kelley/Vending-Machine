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

    public BenSerialListener(String portname) throws IOException, InterruptedException
    {
	/* Open the tty */
	Runtime.getRuntime().exec("stty -F " + portname + " cs8 9600 ignbrk -brkint -icrnl -imaxbel -opost -onlcr -isig -icanon -iexten -echo -echoe -echok -echoctl -echoke noflsh -ixon -crtscts").waitFor();
	p = Runtime.getRuntime().exec("screen " + portname + " 9600");
	p.waitFor();
	in = new BufferedInputStream(p.getInputStream());
	out = new BufferedOutputStream(p.getOutputStream());
    }

    String getLine() throws IOException
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
    void println(String input) throws IOException
    {
	String s = input + "q";
	byte[] array = s.getBytes();
	out.write(array);
	out.flush(); /* make sure it prints the buffer in the stream class */
    }
    void kill()
    {
	p.destroy();
    }
}

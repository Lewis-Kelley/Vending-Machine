public class BenSerialListener
{
    /*
     * We're doing this the 
     * "Object Oriented Way"
     *
     * grumble grumble
     */

    private Process p;
    private java.io.BufferedInputStream in;
    private java.io.BufferedOutputStream out;

    public BenSerialListener(String portname) throws java.io.IOException, InterruptedException
    {
	/* Open the tty */
	Runtime.getRuntime().exec("stty -F " + portname + " cs8 9600 ignbrk -brkint -icrnl -imaxbel -opost -onlcr -isig -icanon -iexten -echo -echoe -echok -echoctl -echoke noflsh -ixon -crtscts").waitFor();
	p = Runtime.getRuntime().exec("screen " + portname + " 9600");
	p.waitFor();
	in = new BufferedInputStream(p.getOutputStream());
	out = new BufferedOutputStream(p.getInputStream());
    }

    String getLine()
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
    void println(String input)
    {
	String s = input + "q";
	byte[] array = s.getBytes(Charset.forName("UTF-8"));
	out.write(array);
	out.flush(); /* make sure it prints the buffer in the stream class */
    }
    void kill()
    {
	p.destroy();
    }
}

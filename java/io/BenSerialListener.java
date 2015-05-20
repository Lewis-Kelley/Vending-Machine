public class BenSerialListener
{
    private String portname;
    private static byte buffer[];
    
    public BenSerialListener(String portname)
    {
	this.portname = portname;
	buffer = new byte[1024];
	/* Open the tty */
	Runtime.getRuntime().exec("stty -F " + portname + " cs8 9600 ignbrk -brkint -icrnl -imaxbel -opost -onlcr -isig -icanon -iexten -echo -echoe -echok -echoctl -echoke noflsh -ixon -crtscts");
    }
    public int send(String s)
    {
	return Runtime.getRuntime().exec("echo " + s + "> " + portname).waitFor();
    }
    public String getResponse()
    {
	String response = "";
	Process p = Runtime.getRuntime().exec("tail -f " + portname);
	InputStream is = p.getInputStream();
	int num = 0;
	while ((num = is.available()) > 0) {
	    num = is.read(buffer);
	    if (num < 0)
		break;
	    response += buffer.toString();
	    for (int i = 0; i < num; i++)
		buffer[num] = 0;
	}
	p.waitFor();
	return response;
    }
}

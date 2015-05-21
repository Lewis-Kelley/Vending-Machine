public class BenSerialListener
{
    private Process p;
    java.io.InputStream in;
    java.io.OutputStream out;
    
    public BenSerialListener(String portname) throws java.io.IOException, InterruptedException
    {
	/* Open the tty */
	Runtime.getRuntime().exec("stty -F " + portname + " cs8 9600 ignbrk -brkint -icrnl -imaxbel -opost -onlcr -isig -icanon -iexten -echo -echoe -echok -echoctl -echoke noflsh -ixon -crtscts").waitFor();
	Runtime.getRuntime().exec("screen " + portname + " 9600").waitFor();
    }
}

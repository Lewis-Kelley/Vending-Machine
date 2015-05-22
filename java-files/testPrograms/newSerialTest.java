public class newSerialTest
{
    public static void main(String[] args) throws java.io.IOException, InterruptedException
    {
	BenSerialListener sl = new BenSerialListener("/dev/ttyACM0");
	sl.println("Hi");
	System.out.println("Response to \"Hi\":\t" + sl.getLine());
    }
}

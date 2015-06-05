public class NewOldSerialTest
{
    public static void main(String[] args)
    {
        SerialListener sl = new SerialListener();
        Thread t = new Thread(SerialListener, "thing");
        t.start();
        String oldMsg = "";
        String msg = "";
        sl.send("nuts");
        msg = sl.msg;
        oldMsg = msg;
        while(oldMsg == msg)
        {
            oldMsg = msg;
        }
        System.out.println(msg);
    }
}

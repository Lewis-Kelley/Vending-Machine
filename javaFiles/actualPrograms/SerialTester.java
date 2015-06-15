public class SerialTester {
    static SerialWrite writer;
    static SerialRead reader;

    public static void main(String[] args) {
	writer = new SerialWrite();
	
	Thread t = new Thread() {
		public void run() {
		    try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
		}
	    };
	t.start();
	for(short ct = 0; ct < 100; ct++) {
	//while(true) {
	    writer.send("STRT");
	    try {Thread.sleep(50);} catch (InterruptedException ie) {}
	}
	System.out.println("Closing");
	writer.close();

	reader = new SerialRead();
	reader.initialize();

	System.out.println("Started");
    }
}

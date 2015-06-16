public class SerialTester {
    //static SerialWrite writer;
    //static SerialRead reader;
    static SerialMerge serial;
    
    public static void main(String[] args) {
	//writer = new SerialWrite();
	serial = new SerialMerge();
	serial.initialize();
	
	Thread t = new Thread() {
		public void run() {
		    try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
		}
	    };
	t.start();
	for(short ct = 0; ct < 100; ct++) {
	    //while(true) {
	    serial.send("STRT");
	    try {Thread.sleep(50);} catch (InterruptedException ie) {}
	}

	System.out.println("Started");
    }
}

class Echo {
    public static void main(String[] args) {
	BenSerialListener serial;

	try {
	    serial = new BenSerialListener("/dev/ttyACM0");

	    for(short i = 0; i < 10; i++) {
		serial.println("Test " + i);
		System.out.println(serial.getLine());
		
		try {
		    Thread.sleep(500);
		} catch(InterruptedException e) {
		    System.err.println(e);
		}
	    }

	} catch(Exception e) {
	    System.out.println("Couldn't create serial listener program");
	}
    }
}

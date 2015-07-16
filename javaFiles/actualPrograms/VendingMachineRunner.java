import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.plaf.FontUIResource;

public class VendingMachineRunner {
    private final boolean DEBUG = false;
    private final int STRT_WAIT = 100;
    
    private static boolean cont;

    private Coordinate coord;
    private Inventory inv;

    private Soda selCan;

    private JFrame gui;
    private static VendingGUI vGUI;

    private static SerialMerge serial;

    private String input;

    private short commCounter = 1; //A counter that is incremented on every tick. Used to prevent the same message from being sent many more times than necessary
    private short pingCounter = 1000; //A counter to measure how long it's been since the last ping. Pings on 0.

    public VendingMachineRunner(boolean nothing) { //Just used to access the static cont variable
	cont = false;
    }
    
    public VendingMachineRunner() {
	cont = true;

	coord = null;
	inv = new Inventory(true);

	selCan = null;

	Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	gui = new JFrame();
	gui.setSize(size);
	gui.setUndecorated(true);
	vGUI = new VendingGUI();
	vGUI.setUp();
	gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	gui.add(vGUI);
	gui.pack();
	gui.setVisible(true);

	serial = new SerialMerge();
	serial.initialize();

	int ct = 0;

	input = "";
	do {
	    try {
		serial.send("STRT");
	    } catch(Exception e) {
		System.err.println("Failed to send STRT to arduino. Quitting.");
		cont = false;
		break;
	    }
	    input = serial.getLine();
	    try {Thread.sleep(50);} catch (InterruptedException ie) {}
	} while(!input.equals("STRT") && ++ct <= STRT_WAIT);

	if(input.equals("STRT"))
	    System.out.println("Received start signal from arduino");
	else {
	    System.err.println("Failed to receive start signal. Quitting");
	    cont = false;
	}
    }

    public static void main(String[] args) {
	Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		public void run() {
		    try {
			serial.send("STOP");
		    } catch(Exception e) {}
		}
	    }, "Shutdown-thread"));
	
	VendingMachineRunner vmr = new VendingMachineRunner();

	while(cont) {
	    vmr.getInput();
	    vmr.run();

	    try {
		Thread.sleep(15);
	    } catch(InterruptedException e) {
		System.err.println(e);
	    }
	}

	System.err.println("Showing disabled screen");
	vGUI.showDisabled();
    }

    private void run() {
	if(--pingCounter <= 0) {
	    try {
		serial.send("PING");
		try { Thread.sleep(50); } catch(InterruptedException e) {}
	    } catch(Exception e) {
		System.err.println("Failed to send PING to arduino. Quitting.");
		cont = false;
	    }
	    
	    if(pingCounter < -100) {
		try {
		    serial.send("STOP");
		} catch(Exception e) {
		    System.err.println("Failed to send STOP. Quitting");
		    cont = false;
		}
		System.err.println("Arduino failed to respond to PING. Quitting.");
		cont= false;
	    }
	}

	if(--commCounter < 0)
	    commCounter = 0;
	selCan = vGUI.getSoda();

	if(input.equals("RSET")) {
	    inv.reset();
	    int ct = 0;
	    
	    do {
		try {
		    serial.send("STRT");
		} catch(Exception e) {
		    System.err.println("Failed to send STRT to arduino. Quitting.");
		    cont = false;
		    break;
		}
		input = serial.getLine();
		try {Thread.sleep(50);} catch (InterruptedException ie) {}
	    } while(!input.equals("STRT") && ++ct <= STRT_WAIT);

	    if(input.equals("STRT"))
		System.out.println("Received start signal from arduino");
	    else {
		System.err.println("Failed to receive start signal. Quitting");
		cont = false;
	    } 
	}

	if(input.equals("PONG"))
	   pingCounter = 1000;

	if(!selCan.equals(Soda.EMPTY)) {
	    coord = inv.findSoda(selCan);

	    if(coord == null)
		vGUI.setFoundStatus((byte)-1);
	    else
		vGUI.setFoundStatus((byte)1);
	}

	if(vGUI.getMoneyStatus()) { //If at pay screen
	    if(input.equals("GMNY") || DEBUG) {
		vGUI.setReceivedStatus(true);
		inv.removeSoda(coord);
		
		try {
		    serial.send("#" + coord.x + "" + (char)coord.y + "" + coord.z);
		} catch(Exception e) {
		    System.err.println("Failed to send coordinates. Quitting");
		    cont = false;
		}
		return;
	    }

	    if(commCounter <= 0) {
		try {
		    serial.send("NMNY");
		} catch(Exception e) {
		    System.err.println("Failed to send NMNY signal. Quitting");
		    cont = false;
		}
		commCounter = 200;
	    }
	}

	if(vGUI.getCancelStatus()) {
	    try {
		serial.send("CNCL");
	    } catch(Exception e) {
		System.err.println("Failed to send CNCL. Quitting");
		cont = false;
	    }
	}

	if(input.equals("ATCO"))
	    vGUI.setReachedFront(true);

	if(input.equals("ATBK"))
	    vGUI.setReachedBack(true);
	
	if(input.equals("FNDL")) {
	    vGUI.setFinishedDelivery(true);
	    int ct = 0;
	    
	    do {
		try {
		    serial.send("STRT");
		} catch(Exception e) {
		    System.err.println("Failed to send STRT to arduino. Quitting.");
		    cont = false;
		    break;
		}
		input = serial.getLine();
		try {Thread.sleep(50);} catch (InterruptedException ie) {}
	    } while(!input.equals("STRT") && ++ct <= STRT_WAIT);
	    
	    if(input.equals("STRT"))
		System.out.println("Received start signal from arduino");
	    else {
		System.err.println("Failed to receive start signal. Quitting");
		cont = false;
	    }
	}
    }

    private void getInput() {
	input = serial.getLine();
	if(input.length() > 0)
	    pingCounter = 1000;
	
	if(input.equals("STOP")) {
	    System.err.println("Arduino has signalled all-stop. Quitting.");
	    cont = false;
	}
    }
}

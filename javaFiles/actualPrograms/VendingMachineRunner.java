import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.plaf.FontUIResource;

public class VendingMachineRunner {
    public enum State { MENU, DELIVERING, DELIVERED, DISABLED };

    private static boolean cont;
    
    private State state;
    private Coordinate coord;
    private Inventory inv;

    private Soda selCan;
    
    private JFrame gui;
    private VendingGUI vGUI;

    private BenSerialListener serial;

    //TODO implement IO handler class

    public VendingMachineRunner()
    {
	cont = true;
	
	state = State.MENU;
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

	try {
	    serial = new BenSerialListener("/dev/ttyACM0");
	} catch(Exception e) {
	    System.out.println("Couldn't create serial listener program");
	}

	int ct = 0;
	
	String line = "";
	do {
	    try {
		serial.println("STRT");
	    } catch(Exception e) {
		    System.err.println("Error writing STRT to arduino\n" + e.toString() + ":\t" + e.getStackTrace());
	    }
	    
	    if(ct++ % 100 == 0)
		System.out.println(ct);
	    try {
		line = serial.getLine();
	    } catch(Exception e) {
		    System.err.println("Error reading from arduino\n" + e.toString());
	    }
	    try {
		Thread.sleep(5);
	    } catch(InterruptedException e) {
		System.err.println(e);
	    }
	} while(!line.equals("STRT"));

	System.out.println("Received start signal from arduino");
    }
    
    public static void main(String[] args) {
	VendingMachineRunner vmr = new VendingMachineRunner();

	while(cont) {
	    switch(vmr.getState()) {
	    case MENU:
		vmr.menu();
		break;
	    case DELIVERING:
		vmr.delivering();
		break;
	    case DELIVERED:
		vmr.delivered();
		break;
	    case DISABLED:
		vmr.disabled();
		break;
	    }
	    
	    try {
		Thread.sleep(500);
	    } catch(InterruptedException e) {
		System.err.println(e);
	    }
	}
    }

    /**
     * Changes the state to the given value.
     */
    public void setState(State nState) {
	state = nState;
    }

    /**
     * Gets the current state of the machine.
     */
    public State getState()
    {
	return state;
    }
    
    /**
     * Ends when option selected.
     * If timeout is reached, return to sleeping state.
     */
    private void menu() {
	selCan = vGUI.getSoda();
	
	if(!selCan.equals(Soda.EMPTY)) {
	    coord = inv.findSoda(selCan);
	    
	    if(coord == null)
		vGUI.setFoundStatus((byte)-1);
	    else
		vGUI.setFoundStatus((byte)1);
	}

	if(vGUI.getMoneyStatus()) { //If at pay screen
	    String comm = "";

	    try {
		serial.println("NMNY");
	    } catch(Exception e) {
		System.out.println("Error sending \"NMNY\" with arduino");
	    }

	    while(vGUI.getMoneyStatus()) {
		try {
		    comm = serial.getLine();
		} catch(Exception e) {
		    System.out.println("Error reading from arduino");
		}

		if(!comm.equals("")) {
		    if(comm.equals("STOP"))
			cont = false;
		    else if(comm.equals("GMNY"))
			vGUI.setReceivedStatus(true);
		    else
			System.out.println("Arduino says: " + comm);
		}

		try {
		    Thread.sleep(100);
		} catch(Exception e) {
		    System.out.println("Couldn't wait for moneyStatus");
		}
	    }
	}
	    
    }

    /**
     * Changes screen to waiting.
     * Moves arms to grab can.
     * Ends when can reaches customer or timeout is reached.
     * Arm will end up in release position, not home position.
     * If timeout is reached, return money and display "Needs maintainence" and disable.
     */
    private void delivering() {
	//TODO implement
    }

    /**
     * Changes screen to "Come again" and moves arm back to home position.
     * Ends when arm reaches home position or when timeout is reached.
     * If timeout is reached, display "Needs maintainence" and disable.
     */
    private void delivered() {
	//TODO implement
    }

    /**
     * Display "Needs maintaince" never leave state until rebooted.
     */
    private void disabled() {
	//TODO implement
    }
}

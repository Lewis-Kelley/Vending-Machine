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

    private SerialMerge serial;

    private String input;

    public VendingMachineRunner() {
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

	serial = new SerialMerge();
	serial.initialize();

	int ct = 0;

	input = "";
	do {
	    serial.send("STRT");
	    input = serial.getLine();
	    try {Thread.sleep(50);} catch (InterruptedException ie) {}
	} while(!input.equals("STRT"));

	System.out.println("Received start signal from arduino");
    }

    public static void main(String[] args) {
	VendingMachineRunner vmr = new VendingMachineRunner();

	while(cont) {
	    vmr.getInput();
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
    public State getState() {
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
	    if(input.equals("GMNY")) {
		vGUI.setReceivedStatus(true);
		inv.removeSoda(coord);
		serial.send("#" + coord.x + "" + coord.y + "" + coord.z);
		return;
	    }

	    serial.send("NMNY");
	}

	if(vGUI.getCancelStatus()) {
	    serial.send("CNCL");
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

    private void getInput() {
	input = serial.getLine();
	if(input.equals("STOP"))
	    cont = false;
    }
}

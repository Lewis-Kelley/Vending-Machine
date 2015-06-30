import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.plaf.FontUIResource;

public class VendingMachineRunner {
    private static boolean cont;

    private Coordinate coord;
    private Inventory inv;

    private Soda selCan;

    private JFrame gui;
    private static VendingGUI vGUI;

    private SerialMerge serial;

    private String input;

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
	    serial.send("STRT");
	    input = serial.getLine();
	    try {Thread.sleep(50);} catch (InterruptedException ie) {}
	} while(!input.equals("STRT"));

	if(input.equals("STRT"))
	    System.out.println("Received start signal from arduino");
	else {
	    System.out.println("Failed to receive start signal. Quitting");
	    cont = false;
	}
    }

    public static void main(String[] args) {
	VendingMachineRunner vmr = new VendingMachineRunner();

	while(cont) {
	    vmr.getInput();
	    vmr.run();

	    try {
		Thread.sleep(500);
	    } catch(InterruptedException e) {
		System.err.println(e);
	    }
	}

	vGUI.showDisabled();
    }

    private void run() {
	selCan = vGUI.getSoda();

	if(input.equals("RSET"))
	    inv.reset();

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
		serial.send("#" + coord.x + "" + coord.y + "" + coord.z); //Might be problematic
		return;
	    }

	    serial.send("NMNY");
	}

	if(vGUI.getCancelStatus()) {
	    serial.send("CNCL");
	}

	if(input.equals("FNDL")) {
	    vGUI.setFinishedDelivery(true);
	}
    }

    private void getInput() {
	input = serial.getLine();
	if(input.equals("STOP"))
	    cont = false;
    }
}

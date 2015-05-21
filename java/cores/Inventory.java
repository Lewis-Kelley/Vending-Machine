import java.io.*;

class Inventory {
    private final int  ROWS = 15;
    private final int COLUMNS = 5;

    private String fileVar;
	
    private PrintWriter writer; //The object that will read from and write to the file
	
    private Soda[][][] inv; //The array that actually holds the current inventory
	
    /**
     * Initializes writer and each element in the array to Soda.EMPTY.
     */
    public Inventory() {
	this("inventory.txt"); //TODO confirm filename
    }

    public Inventory(String fileVar) {
	this.fileVar = fileVar;
	inv = new Soda[COLUMNS][ROWS][2];

	try {
	    writer = new PrintWriter(fileVar); //Creates the object for the given filename
	} catch (Exception e) {
	    System.out.println("Failed to open PrintWriter");
	}
	
	//Sets every member in the array to Soda.EMPTY
	for(Soda[][] z : inv)
	    for(Soda[] y : z)
		for(short i = 0; i < y.length; i++)
		    y[i] = Soda.EMPTY;
    }
    
    /**
     * Returns the coordinate object of the can if it was removed, returns null if it couldn't find it
     */
    public Coordinate removeSoda(Soda soda) {
	Coordinate retVal = findSoda(soda);

	if(retVal == null)
	    return null;
	
	inv[retVal.x][retVal.y][retVal.z] = Soda.EMPTY;
	return retVal;
    }
    
    /**
     * Returns true if the can at the given coordinates existed, else returns false
     */
    public boolean removeSoda(Coordinate coord) {
	if(inv[coord.x][coord.y][coord.z] == Soda.EMPTY)
	    return false;
	else {
	    inv[coord.x][coord.y][coord.z] = Soda.EMPTY;
	    return true;
	}
    }
    
    /**
     * Adds a given soda to a given coordinate
     */
    public boolean addSoda(Coordinate coord, Soda soda) {
	if(inv[coord.x][coord.y][coord.z] == Soda.EMPTY) {
	    inv[coord.x][coord.y][coord.z] = soda;
	    return true;
	} else
	    return false;
    }
    
    /**
     * Returns Coordinate if can was found, returns null if it couldn't find it
     */
    private Coordinate findSoda(Soda soda) {
	for(short ctZ = 0; ctZ < inv[0][0].length; ctZ++)
	    for(short ctY = 0; ctY < inv[0].length; ctY++)
		for(short ctX = 0; ctX < inv.length; ctX++)
		    if(inv[ctX][ctY][ctZ] == soda)
			return new Coordinate(ctX, ctY, ctZ);
	return null;
    }
    
    /**
     * Updates file with the current status of the inventory
     */
    public void updateFile() {
	try {
	    writer = new PrintWriter(fileVar); //Creates an object that reads from fileVar.
	} catch(Exception e) {
	    System.out.println("Failed to open PrintWriter");
	}

	for(short column = 0; column < COLUMNS; column++)
	    for(short row = 0; row < ROWS; row++)
		writer.print(inv[column][row][0].toString() + " "); //Writes the Soda string and a space

	for(short column = 0; column < COLUMNS; column++)
	    for(short row = 0; row < ROWS; row++)
		writer.print(inv[column][row][1].toString() + " "); //Writes the Soda string and a space
    }
    
    /**
     * Reads data from file into current inventory
     */
    private void readFile() {
	String data;
	try {
	    data = readFile(fileVar); //gets all the data in the file as one String
	} catch(Exception e) {
	    System.out.println("Failed to read from file");
	    data = null;
	}
	
	short lastIndex = 0; //Start of substring
	short newIndex = 0; //End of substring
	
	for(short column = 0; column < COLUMNS; column++)
	    for(short row = 0; row < ROWS; row++) {
		while(data.charAt(newIndex) != ' ') //Waits to see a space
		    newIndex++;
		inv[column][row][0] = Soda.valueOf(data.substring(lastIndex, newIndex)); //Puts the substring between the last space and the next one into inv
		lastIndex = ++newIndex;
	    }
	
	for(short column = 0; column < COLUMNS; column++)
	    for(short row = 0; row < ROWS; row++) {
		while(data.charAt(newIndex) != ' ') //Waits to see a space
		    newIndex++;
		inv[column][row][1] = Soda.valueOf(data.substring(lastIndex, newIndex)); //Puts the substring between the last space and the next one into inv
		lastIndex = ++newIndex;
	    }
	
    }
    
    /**
     * Returns the full contents of a given file as a String
     */
    public String readFile(String fileName) throws IOException { //Stolen from the internet
	BufferedReader br = new BufferedReader(new FileReader(fileName));
	try {
	    StringBuilder sb = new StringBuilder();
	    String line = br.readLine();
	    
	    while (line != null) {
		sb.append(line);
		sb.append("\n");
		line = br.readLine();
	    }
	    return sb.toString();
	} finally {
	    br.close();
	}
    }
}

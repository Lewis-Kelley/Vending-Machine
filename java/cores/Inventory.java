import java.io.*;

class Inventory {
    private final String FILENAME = "inventory.txt"; //TODO make actual file
    private final int  ROWS = 15;
    private final int COLUMNS = 5;
	
    private PrintWriter writer; //The object that will read from and write to the file
	
    private Soda[][][] inv = new Soda[2][ROWS][COLUMNS]; //The array that actually holds the current inventory
	
    /**
     * Initializes writer and each element in the array to null.
     */
    public Inventory() {
	try {
	    writer = new PrintWriter(FILENAME); //Creates the object for the given filename
	} catch (Exception e) {
	    System.out.println("Failed to open PrintWriter");
	}
	
	//Sets every member in the array to null
	for(Soda[][] plane : inv)
	    for(Soda[] line : plane)
		for(Soda holder : line)
		    holder = null;
    }
    
    /**
     * Returns the coordinate object of the can if it was removed, returns null if it couldn't find it
     */
    public Coordinate removeSoda(Soda soda) {
	Coordinate retVal = findSoda(soda);

	if(retVal == null)
	    return null;
	
	inv[retVal.z][retVal.y][retVal.x] = null;
	return retVal;
    }
    
    /**
     * Returns true if the can at the given coordinates existed, else returns false
     */
    public boolean removeSoda(Coordinate coord) {
	if(inv[coord.z][coord.y][coord.x] == null)
	    return false;
	else {
	    inv[coord.z][coord.y][coord.x] = null;
	    return true;
	}
    }
    
    /**
     * Adds a given soda to a given coordinate
     */
    public boolean addSoda(Coordinate coord, Soda soda) {
	if(inv[coord.z][coord.y][coord.x] == null) {
	    inv[coord.z][coord.y][coord.x] = soda;
	    return true;
	} else
	    return false;
    }
    
    /**
     * Returns Coordinate if can was found, returns null if it couldn't find it
     */
    private Coordinate findSoda(Soda soda) {
	for(short out = 0; out < inv[0][0].length; out++)
	    for(short mid = 0; mid < inv[0].length; mid++)
		for(short in = 0; in < inv.length; in++)
		    if(inv[out][mid][in] == soda)
			return new Coordinate(out, mid, in);
	return null;
    }
    
    /**
     * Updates file with the current status of the inventory
     */
    private void updateFile() {
	try {
	    writer = new PrintWriter(FILENAME); //Creates an object that reads from FILENAME.
	} catch(Exception e) {
	    System.out.println("Failed to open PrintWriter");
	}
	
	for(short column = 0; column < COLUMNS; column++)
	    for(short row = 0; row < ROWS; row++)
		writer.print(inv[0][row][column].toString() + " "); //Writes the Soda string and a space
	
	for(short column = 0; column < COLUMNS; column++)
	    for(short row = 0; row < ROWS; row++)
		writer.print(inv[0][row][column].toString() + " "); //Writes the Soda string and a space
    }
    
    /**
     * Reads data from file into current inventory
     */
    private void readFile() {
	String data;
	try {
	    data = readFile(FILENAME); //gets all the data in the file as one String
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
		inv[0][row][column] = Soda.valueOf(data.substring(lastIndex, newIndex)); //Puts the substring between the last space and the next one into inv
		lastIndex = ++newIndex;
	    }
	
	for(short column = 0; column < COLUMNS; column++)
	    for(short row = 0; row < ROWS; row++) {
		while(data.charAt(newIndex) != ' ') //Waits to see a space
		    newIndex++;
		inv[1][row][column] = Soda.valueOf(data.substring(lastIndex, newIndex)); //Puts the substring between the last space and the next one into inv
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

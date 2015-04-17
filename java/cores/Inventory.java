package java.cores;

import java.io.*;

class Inventory {
	private final String FILENAME = "filename.txt"; //TODO make actual file
	private final ROWS = 15;
	private final COLUMN = 5;
	
	private FileWriter writer;
	private FileReader reader;
	
	private Soda[][][] inv = new Soda[2][ROWS][COLUMNS];
	
	public Inventory() {
		writer = new FileWriter(FILENAME);
		reader = new FileReader(FILENAME);
		
		for(Soda[][] 2d : inv)
			for(Soda[] 1d : 2d)
				for(Soda holder : 1d)
					holder = null;
	}
	
	//Returns true if can was removed, returns false if it couldn't find it
	public boolean removeCan(Soda soda) {
		for(Soda[][] 2d : inv)
			for(Soda[] 1d : 2d)
				for(Soda holder : 1d)
					if(holder == soda) {
						holder = null;
						return true;
					}
		return false;
	}
	
	public boolean addSoda(Coordinate coord, Soda soda) {
		if(inv[coord.z][coord.y][coord.x] == null) {
			inv[coord.z][coord.y][coord.x] = soda;
			return true;
		} else{
			return false;
	}
	
	//Returns Coordinate if can was removed, returns null if it couldn't find it
	private Coordinate findSoda(Soda soda) {
		for(short out = 0; ct < inv[][].length; out++)
			for(short mid = 0; ct < inv[].length; mid++)
				for(short in = 0; ct < inv.length; in++)
					if(inv[out][mid][in] == soda)
						return new Coordinate(out, mid, in);
		return null;
	}
	
	//Updates file with the current status of the inventory
	private void updateFile() [
		//TODO implement
	}
	
	//Reads data from file into current inventory
	private void readFile() {
		//TODO implement
	}
}

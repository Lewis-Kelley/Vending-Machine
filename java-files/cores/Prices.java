package javaFiles.cores;

class Prices {
	private short[] prices;

	/**
	 * Initializes prices to the prices for each type of soda.
	 * Note: This is where the prices must be changed in case of any future alterations.
	 * Also, there are no checks that each soda has a price and each price has a soda; programmer beware.
	 */
	public Prices() {
		prices = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}; //TODO confirm prices
	}
	
	/**
	 * Returns the price for a given Soda object.
	 */
	public short getPrice(Soda soda) {
		return prices[soda.ordinal()];
	}
}

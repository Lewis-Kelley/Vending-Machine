package java.io;

class MoneyHandler {
	private class Money {
		public byte nickels;
		public byte dimes;
		public byte quarters;
		public byte oneDollar;
		public byte fiveDollar;
		public byte tenDollar;
		public byte twentyDollar;
	}

	private Money inputCash;

	public MoneyHandler() {
		//TODO implement
	}
	
	public void returnMoney() {
		//TODO implement
	}
	
	public void returnChange(float goal) {
		//TODO implement
	}
	
	private Money calcChange(Money input, float goal) {
		//TODO implement
	}
}

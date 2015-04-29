package java.io;

class MoneyHandler {
	private class Money {
		public byte nickels;
		public byte dimes;
		public byte quarters;
		public byte oneDollars;
		public byte fiveDollars;
		public byte tenDollars;
		public byte twentyDollars;
		
		public Money() {
			nickels = 0;
			dimes = 0;
			quarters = 0;
			oneDollars = 0;
			fiveDollars = 0;
			tenDollars = 0;
			twentyDollars = 0;
		}
		
		protected float getValue() {
			return nickels * 0.05f + dimes * 0.1f + quarters * 0.25f
			       + oneDollars * 1.0f + fiveDollars * 5.0f + tenDollars * 10.0f + twentyDollars * 20.0f;
		}
	}

	private Money inputCash;

	public MoneyHandler() {
		inputCash = new Money();
		
		//TODO implement money machine port
	}
	
	public void addMoney(Money added) {
		inputCash.nickels += added.nickels;
		inputCash.dimes += added.dimes;
		inputCash.quarters += added.quarters;
		inputCash.oneDollars += added.oneDollars;
		inputCash.fiveDollars += added.fiveDollars;
		inputCash.tenDollars += added.tenDollars;
		inputCash.twentyDollars += added.twentyDollars;
	}
	
	public void returnMoney() {
		//TODO implement
	}
	
	public void returnChange() {
		//TODO implement
	}
	
	public Money calcChange(Money input, float goal) {
		Money change = new Money();
		float difference = goal - inputCash.getValue();
		
		change.quarters += (byte)(difference / 0.25);
		difference = goal - inputCash.getValue() - change.getValue();
		
		change.dimes += (byte)(difference / 0.10);
		difference = goal - inputCash.getValue() - change.getValue();
		
		change.nickels += (byte)(difference / 0.05);
		
		return change;
	}
}

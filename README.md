# Vending-Machine
SLUH Robotics misguided attempt at making an extravagant vending machine with an excessive amount of over-complicated features. Designed to work as a PC running Java controlling an Arduino via serial I/O controlling a Pololu Maestro running scripts to control the motors.

# Instructions
First, connect the arduino to the computer, and, using the arduino IDE, download the code; it will run automatically. Go to Tools->Port and verify that the listed port matches one of the ports listed in javaFiles/actualPrograms/SerialMerge.java. If not, add the port name to the list.
Confirm that the necessary maestro scripts are set up on the maestro.
In a command prompt, compile and run javaFiles/actualPrograms/VendingMachineRunner.java. Everything after that should run automatically.
If the program needs to be terminated, Alt-Tab to the command prompt and type Ctrl-C. Assuming that communications are still running, this will stop both the java program and the arduino. If the arduino does not stop, communications have been lost and you must press the brown reset button on the top of the arduino.
To restock, first confirm that the java program is running, then fill in the appropriate cans in the correct rows according the javaFiles/actualPrograms/DefaultInventory.txt. When no orders are going through, press the front limit switch. The rail should quickly move forward and backward to confirm that the inventory has been reset.
If the inventory needs to be changed (Add a new type of soda or remove an old one), you must hand-edit the DefaultInventory.txt file and, using the method described above, reset the current inventory. Be careful when doing this, as that file is the only thing that tells the computer where each type of soda is. In addition to this, the whole GUI must be edited to add a new button or change the functionality of an old one, in addition to changing the Soda enum.

# Directories

## javaFiles
Contains all programs in java needed for the program running on the PC. Main is in actualPrograms/VendingMachineRunner.java.

## arduino
Contains both the final program and small test programs to test various systems.

## lib
Storage for both 32-bit and 64-bit files needed for serial communication. The current PC is 32-bit, but if that must change, copy the appropriate files into javaFiles/actualPrograms.

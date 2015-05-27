#include <Stepper.h>
#include <Servo.h>

const int stepsPerRevolution = 50;  // change this to fit the number of steps per revolution
                          // for your motor

// initialize the stepper library on the motor shield
Stepper myStepper(stepsPerRevolution, 12,13);     

Servo center;
Servo middle;
Servo far;

// give the motor control pins names:
const int pwmA = 3;
const int pwmB = 11;
const int brakeA = 9;
const int brakeB = 8;
const int dirA = 12;
const int dirB = 13;

int x = 0;

/**
 * Called once to set everything up.
 */
void setup() {
	Serial.begin(9600);
	// set the PWM and brake pins so that the direction pins  // can be used to control the motor:
	pinMode(pwmA, OUTPUT);
	pinMode(pwmB, OUTPUT);
	pinMode(brakeA, OUTPUT);
	pinMode(brakeB, OUTPUT);
	digitalWrite(pwmA, HIGH);
	digitalWrite(pwmB, HIGH);
	digitalWrite(brakeA, LOW);
	digitalWrite(brakeB, LOW);

	// initialize the serial port:
	Serial.begin(9600);
	// set the motor speed (for multiple steps only):
	myStepper.setSpeed(100);

	center.attach(9);
	middle.attach(10);
	far.attach(11);
}

/**
 * Called every tick. Place a delay if need be.
 */
void loop() {
	
}

/**
 * Moves stepper back until both limit switches are triggered.
 */
bool railReturnHome() {
    /** Pseudocode:
     *  if(!Buttons pressed) {
     *      railToPos(-5);
     *      return false;
     *  } else
     *      return true;
     */
}

/**
 * Steps the steppers forward for a set value.
 * Value 0 is home position.
 */
void railToPos(int value) {
     myStepper.step(value); //May need to change direction
}

/**
 * Moves arms back to home position.
 */
void armsReturnHome() {
	
}

/**
 * Moves arms into dropoff position.
 */
void armsToDropoff() {
	
}

/**
 * Moves arms to 3 values. First is central arm, and so on.
 */
void setArms(int first, int second, int third) { //May need to stagger into steps so the arms don't bang against the sides.
     center.writeMicroseconds(first);
     middle.writeMicroseconds(second);
     far.writeMicroseconds(third);
}

/**
 * Opens claw.
 */
void openClaw() {
	
}

/**
 * Closes claw.
 */
void closeClaw() {
	
}

/**
 * Returns signal to PC saying that the current step is completed.
 */
void sayFinished() {
	
}

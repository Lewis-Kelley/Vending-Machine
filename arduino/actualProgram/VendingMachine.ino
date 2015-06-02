#include <Stepper.h>
#include <Servo.h>

const int STEPPER_MAIN = 9;
const int STEPPER_DIR  = 8;

const int BACK_LIM_SWITCH = 2;

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
 * Moves stepper back until the limit switch is triggered.
 */
void railReturnHome() {
	digitalWrite(STEPPER_DIR, HIGH);

	while(true) {
		if(analogRead(1) > 950)
			break;

		for(short i = 0; i < 10; i++) {
			digitalWrite(STEPPER_MAIN, LOW);
			delayMicroseconds(TIME_DELAY);
			digitalWrite(STEPPER_MAIN, HIGH);
			delayMicroseconds(TIME_DELAY);
		}
	}
}

/**
 * Steps the steppers forward for a set value.
 * Value 0 is home position.
 */
void railToPos(int value) {
	digitalWrite(STEPPER_DIR, LOW);

	for(short i = 0; i < value; i++) {
		digitalWrite(STEPPER_MAIN, LOW);
		delayMicroseconds(TIME_DELAY);
		digitalWrite(STEPPER_MAIN, HIGH);
		delayMicroseconds(TIME_DELAY);
	}
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

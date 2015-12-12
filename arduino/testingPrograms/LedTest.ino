#include <Wire.h>//wouldn't compile without, probably a dependancy for the below
#include <Adafruit_PWMServoDriver.h>//library for pwm/servo contorller we are using for the leds; Found at here https://www.adafruit.com/products/1411 

//COLORZ
int blue[] = {0, 0, 255};
int green[] = {0, 255, 0};
int red[] = {255, 0, 0};
int yellow[] = {255, 255, 0};
int brown[] = {139, 69, 19};
int orange[] = {255, 165, 0};
int pink[] = {255, 105, 180};
int purple[] = {160, 32, 240};
int white[] = {255, 255, 255};
int off[] = {0, 0, 0};
		
public void setup()
{
		//pwm controller for the leds
		Adafruit_PWMServoDriver pwm = Adafruit_PWMServoDriver();
		pwm.begin();
		pwm.setPWMFreq(1600);
}
public void loop()
{
	//technically doesnt need to be called every "tick," but its here because why not
	setColor(green);//put the color in
}
//make it EZ to do the color thing
public void setColor(int color[])
{
	pwm.setPWM(0, (4096 - ((color[0] + 1) * 16)) - 1, 4095);//RED
	pwm.setPWM(1, (4096 - ((color[1] + 1) * 16)) - 1, 4095);//GREEN
	pwm.setPWM(2, (4096 - ((color[2] + 1) * 16)) - 1, 4095);//BLUE
}

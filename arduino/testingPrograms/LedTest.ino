#include <Wire.h>
#include <Adafruit_PWMServoDriver.h>

public void setup()
{
		//pwm controller for the leds
		Adafruit_PWMServoDriver pwm = Adafruit_PWMServoDriver();
		pwm.begin();
		pwm.setPWMFreq(1600);
		
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
		
		
}
public void loop()
{
	setColor(green);
}
//make it EZ to color
public void setColor(int[] color)
{
	pwm.setPWM(0, 4095, (4096 - ((color[0] + 1) * 16)) - 1);//RED
	pwm.setPWM(0, 4095, (4096 - ((color[1] + 1) * 16)) - 1);//GREEN
	pwm.setPWM(0, 4095, (4096 - ((color[2] + 1) * 16)) - 1);//BLUE
}

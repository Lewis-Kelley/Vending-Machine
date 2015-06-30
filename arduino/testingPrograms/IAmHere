const int BUF_SIZE = 256;

int bufLen;
int sentString;
char commBuffer[BUF_SIZE];
String msg;

void setup() {
  msg = "I didn't hear you...";
  pinMode(13, OUTPUT);
  Serial.begin(9600);
}

void loop() {
  Serial.println(msg);
  delay(100);
}

void serialEvent() {
    bufLen = 0;
    char c;
    while (Serial.available() && bufLen < BUF_SIZE) {
	c = (char)Serial.read();
	if (c == (char)'q') {
	    sentString = 1;
	    return;
	}
	commBuffer[bufLen++] = c;
    }
    
    msg = "I heard you!";
    
    while(true) {
      digitalWrite(13, HIGH);
      delay(1000);
      digitalWrite(13, LOW);
      delay(500);
    }
}

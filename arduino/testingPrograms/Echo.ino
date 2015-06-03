#define BUF_SIZE 256
char buffer[BUF_SIZE]; /* buffer for serial messages from computer (maybe from Pi?) */
int buf_len = 0;
int sent_string = 0;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
 
}

void serialEvent()
{
  char c;
	while (Serial.available() && buf_len < BUF_SIZE) {
		c = (char)Serial.read();
                Serial.println("Input: \"" + (String)c + "\"");
		if (c == (char)'q') {
			sent_string = 1;
                        Serial.println("String: \"" + String(buffer) + "\"");
			return;
		}
		buffer[buf_len++] = c;
	}
}

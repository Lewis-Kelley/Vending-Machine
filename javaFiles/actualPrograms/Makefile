JAR = jar
JARFLAGS = cf
JAVAC = javac
JAVAFLAGS = -g
SRC =	BenSerialListener.java \
	Coordinate.java \
	Display.java \
	Inventory.java \
	MoneyHandler.java \
	Prices.java \
	SerialListener.java \
	Soda.java \
	Test.java \
	TouchScreen.java \
	VendingGUI.java \
	VendingMachineRunner.java
RES =	back.png \
	codeRed.png \
	crush.png \
	DefaultInventory.txt \
	dietDew.png \
	dietPepsi.png \
	genericBrisk.png \
	half.png \
	inventory.txt \
	lemonade.png \
	masterBrisk.png \
	masterPepsi.png \
	max.png \
	mountainDew.png \
	mug.png \
	no.png \
	pepsi.png \
	raspberry.png \
	sodaSpin.gif \
	wildCherry.png \
	yes.png \
	youSure.png \
	VendingGUI.html

CLS = $(SRC:.java=.class)

all: $(CLS)
	$(JAR) $(JARFLAGS) VendingMachine.jar ($CLS) ($RES)
.SUFFIXES : .class .java
.java.class :
	$(JAVAC) $(JAVAFLAGS) $<
clean:
	$(RM) *.class
	$(RM) VendingMachine.jar

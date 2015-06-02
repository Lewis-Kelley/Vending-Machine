JAR = jar
JARFLAGS = cvfm
JAVAC = javac
JAVAFLAGS = -g
SRC =	BenSerialListener.java \
	Coordinate.java \
	Inventory.java \
	Soda.java \
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

CLS = $(SRC:.java=.class)

all: $(CLS)

.SUFFIXES : .class .java
.java.class :
	$(JAVAC) $(JAVAFLAGS) $<
clean:
	$(RM) *.class

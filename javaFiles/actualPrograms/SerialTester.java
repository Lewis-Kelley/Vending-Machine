public class SerialTester {
    static SerialWrite writer;
    static SerialRead reader;

    public static void main(String[] args) {
	writer = new SerialWrite();
	reader = new SerialRead();
	reader.initialize();
    }
}

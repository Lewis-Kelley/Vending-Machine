public class Item
{
    public String name;
    public String filename;
    public ImageIcon image;
    public Soda soda;

    public Item(String name, String filename, Soda soda)
    {
	this.name = name;
	this.filename = filename;
	this.image = new ImageIcon(filename);
	this.soda = soda;
    }
    public String toString()
    {
	return name;
    }
}

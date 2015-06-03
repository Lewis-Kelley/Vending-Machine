public class Menu extends JPanel implements ActionListener
{
    private HashMap<JButton, Item> items;
    private HashMap<Item, Menu> map;
    
    private ActionListener al;
    
    public Menu(ActionListener al)
    {
	items = new HashMap<JButton, Item>();
	map = new HashMap<Item, Menu>();
	this.al = al;
    }
    public void link(Item i, Menu m)
    {
	JButton jb = new JButton(i.image);
	jb.addActionListener(this);
	add(jb);
	items.put(jb, i);
	map.put(i, m);
    }
    public void actionPerformed(ActionEvent e)
    {
	JButton jb = (JButton)e.getSource();
	if (jb == null || !items.containsKey(jb)) {
	    al(e);
	    return;
	}
	Item i = items.get(jb);
	ActionEvent ae = new ActionEvent(this, 0, i.toString());
	al.actionPerformed(ae);
    }
    public Menu getLink(Item i)
    {
	return map.get(i);
    }
}

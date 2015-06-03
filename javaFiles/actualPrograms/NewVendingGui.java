public class NewVendingGui extends JPanel
{
    /* should always be a pointer to another menu */
    private Menu current_item;
    private Menu main_menu;

    private Menu spinning_can;

    private HashMap<String,Item> items;
    public static final String[] keys = {"Mountain Dew", "Diet Mug", "Pepsi", "Brisk Lemonade", "Brisk Raspberry", "Brisk Half and Half", "Diet Pepsi", "Mountain Dew Code Red", "Diet Orange Crush", "Pepsi Wild Cherry", "Pepsi Max", "Brisk Iced Tea", "Diet Mountain Dew", "[MASTER] Pepsi", "[MASTER] Brisk", "[MASTER] Mountain Dew", "Back", "No", "Yes", "Confirmation"};
    
    public NewVendingGui()
    {
	int i = 0;
	items = new HashMap<String,Item>(20);
	items.put(keys[i], new Item(keys[i++], "mountainDew.png", Soda.MOUNTAIN_DEW));
	items.put(keys[i], new Item(keys[i++], "mug.png", Soda.DIET_MUG));
	items.put(keys[i], new Item(keys[i++], "pepsi.png", Soda.PEPSI));
	items.put(keys[i], new Item(keys[i++], "lemonade.png", Soda.BRISK_LEMONADE));
	items.put(keys[i], new Item(keys[i++], "raspberry.png", Soda.BRISK_RASPBERRY));
	items.put(keys[i], new Item(keys[i++], "half.png", Soda.BRISK_HALF_AND_HALF));
	items.put(keys[i], new Item(keys[i++], "dietPepsi.png", Soda.DIET_PEPSI));
	items.put(keys[i], new Item(keys[i++], "codeRed.png", Soda.MOUNTAIN_DEW_CODE_RED));
	items.put(keys[i], new Item(keys[i++], "crush.png", Soda.DIET_CRUSH));
	items.put(keys[i], new Item(keys[i++], "wildCherry.png", Soda.PEPSI_WILD_CHERRY));
	items.put(keys[i], new Item(keys[i++], "max.png", Soda.PEPSI_MAX));
	items.put(keys[i], new Item(keys[i++], "genericBrisk", Soda.BRISK_SWEET_TEA));
	items.put(keys[i], new Item(keys[i++], "dietDew.png", Soda.DIET_MOUNTAIN_DEW));
	items.put(keys[i], new Item(keys[i++], "masterPepsi.png", null));
	items.put(keys[i], new Item(keys[i++], "masterBrisk.png", null));
	items.put(keys[i], new Item(keys[i++], "mountainDew.png", null));
	items.put(keys[i], new Item(keys[i++], "back.png", null));
	items.put(keys[i], new Item(keys[i++], "no.png", null));
	items.put(keys[i], new Item(keys[i++], "yes.png", null));
	items.put(keys[i], new Item(keys[i++], "youSure.png", null));


	main_menu = new Menu(this);
	confirm_menu = new Menu(this);
	spinning_can = new Menu(this);
	current_item = main_menu;
	pepsi_menu = new Menu(this);
	brisk_menu = new Menu(this);
	dew_menu = new Menu(this);

	confirm_menu.link(items.get("Yes"), spinning_can);
	confirm_menu.link(items.get("No"), main_menu);
	
       	pepsi_menu.link(items.get("Back"), main_menu);
	pepsi_menu.link(items.get("Pepsi"), confirm_menu);
       	pepsi_menu.link(items.get("Diet Pepsi"), confirm_menu);
	pepsi_menu.link(items.get("Pepsi Wild Cherry"), confirm_menu);
	pepsi_menu.link(items.get("Pepsi Max"), confirm_menu);

	brisk_menu.link(items.get("Back"), main_menu);
	brisk_menu.link(items.get("Brisk Lemonade"), confirm_menu);
       	brisk_menu.link(items.get("Brisk Raspberry"), confirm_menu);
	brisk_menu.link(items.get("Brisk Half and Half"), confirm_menu);
	brisk_menu.link(items.get("Brisk Iced Tea"), confirm_menu);
	
	main_menu.link(items.get("Diet Mug"), confirm_menu);
	main_menu.link(items.get("Diet Orange Crush"), confirm_menu);
	main_menu.link(items.get("[MASTER] Pepsi"), pepsi_menu);
	main_menu.link(items.get("[MASTER] Brisk"), brisk_menu);
	main_menu.link(items.get("[MASTER] Mountain Dew"), dew_menu);
	
	add(main_menu);
    }
    public void actionPerformed(ActionEvent e)
    {
	/* TODO: don't forget about the case when 'e' is the forwarded event */
	Item i = items.get(e.paramString());
	Menu m = (Menu)e.getSource();

	Menu next_menu = m.getLink(i);
	if (next_menu == confirm_menu) {
	    /* set the picture on button to i.image */
	}
	
	/* TODO: set the screen to the menu 'next_menu' */
    }
}

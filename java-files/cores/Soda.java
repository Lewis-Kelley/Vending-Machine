package javaFiles.cores;

enum Soda {
    BRISK_HALF_AND_HALF("Brisk Half-and-Half"), BRISK_RASPBERRY("Brisk Raspberry"), BRISK_LEMONADE("Brisk Lemonade"), BRISK_SWEET_TEA("Brisk Lemonade"),
	PEPSI("Pepsi"), DIET_PEPSI("Diet Pepsi"), PEPSI_WILD_CHERRY("Pepsi Wild Cherry"), PEPSI_MAX("Pepsi Max"),
	MOUNTAIN_DEW("Mountain Dew"), DIET_MOUNTAIN_DEW("Diet Mountain Dew"), MOUNTAIN_DEW_CODE_RED("Mountain Dew Code Red"), DIET_MOUNTAIN_DEW_CODE_RED("Diet Mountain Dew Code Red"),
	DIET_CRUSH("Diet Crush"), DIET_MUG ("Diet Mug"),
	EMPTY("");

    private final String title;

    private Soda(String value) {
	title = value;
    }

    public String toString() {
	return title;
    }
}

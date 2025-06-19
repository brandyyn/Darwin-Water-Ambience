package watersplash;

import cpw.mods.fml.common.FMLCommonHandler;

public class ConfigurationMoD {

public static boolean Enable_Splash;
public static boolean Enable_Splash_for_ITEMS;
public static boolean Enable_Splash_for_ENTITYS;
public static boolean Enable_Splash_for_ENTITYS_MP;
public static boolean Enable_SPLASH_LAVA;
public static boolean Enable_Ripple;
public static boolean SPLASH_LOWPERFORMANCEMOD;

public static boolean Enable_Trail;
public static boolean Enable_Trail_Transparancy;

public static double SPLASH_SCALE_MODIFIER_SIZE, SPLASH_SCALE_MODIFIER_HEIGHT;
public static boolean SPLASH_SCALE_DOUBLE_ITEM;
public static int SPLASH_SCALE_MAXTRACKEDITEMS,TRAIL_RARITY_MODIFIER;
public static double RIPPLE_SCALE_MODIFIER_SIZE,RIPPLE_RARITY_MODIFIER,RIPPLE_AREA_MODIFIER;
public static double TRAIL_SCALE_MODIFIER_SIZE;


public static final String 	Enable_Splash_DES = "This Option enable the rendering of the Water Splashes.(enable the whole Splash System) true/false";
public static final String 	Enable_Ripple_DES = "This Option enable the rendering of the Water Ripples. true/false";
public static final String 	Enable_Trail_DES = "This Option enable the rendering of the Water Trails. true/false";
public static final String 	Enable_Splash_for_ITEMS_DES = "This Option enable the rendering of the Water Splashes for ITEMS. true/false";
public static final String 	Enable_Splash_for_ENTITYS_MP_DES = "This Option enable the rendering of the Water Splashes for other players. true/false";
public static final String 	Enable_Splash_for_ENTITYS_DES = "This Option enable the rendering of the Water Splashes for Entitys. true/false";




public static void loadConfig(){
	FMLCommonHandler.instance().bus().register(WATERMAIN.instance);
	  
	final String CATEGORY_WATERMOD = WATERMAIN.config.CATEGORY_GENERAL + WATERMAIN.config.CATEGORY_SPLITTER + "Watermod";
	WATERMAIN.config.addCustomCategoryComment(CATEGORY_WATERMOD, "GENERAL");
	Enable_Splash = WATERMAIN.config.get(CATEGORY_WATERMOD,Enable_Splash_DES, true).getBoolean();  
	Enable_Ripple = WATERMAIN.config.get(CATEGORY_WATERMOD,Enable_Ripple_DES,true).getBoolean();  
	Enable_Trail = WATERMAIN.config.get(CATEGORY_WATERMOD,Enable_Trail_DES,true).getBoolean();  
	Enable_Splash_for_ITEMS = WATERMAIN.config.get(CATEGORY_WATERMOD,Enable_Splash_for_ITEMS_DES, true).getBoolean();
	Enable_Splash_for_ENTITYS_MP = WATERMAIN.config.get(CATEGORY_WATERMOD, Enable_Splash_for_ENTITYS_MP_DES, true).getBoolean(); //DEFAULT VALUE CAN BE CHANGED
	Enable_Splash_for_ENTITYS = WATERMAIN.config.get(CATEGORY_WATERMOD,Enable_Splash_for_ENTITYS_DES,true).getBoolean();  
	Enable_SPLASH_LAVA = WATERMAIN.config.get(CATEGORY_WATERMOD,"---->Experimental<---- enable Splash for lava, it will try to load the Texture of Lava and create a new color for the SPLASH",false).getBoolean();  
	
	final String CATEGORY_WATERMOD2 = WATERMAIN.config.CATEGORY_GENERAL + WATERMAIN.config.CATEGORY_SPLITTER + "WATERSPLASH";
	WATERMAIN.config.addCustomCategoryComment(CATEGORY_WATERMOD2, "GENERAL");
	SPLASH_SCALE_MODIFIER_SIZE = WATERMAIN.config.get(CATEGORY_WATERMOD2, "Scales the Size of the Splash (1.0 = 100%)", 1.00).getDouble();  
	SPLASH_SCALE_MODIFIER_HEIGHT = WATERMAIN.config.get(CATEGORY_WATERMOD2, "Scales the Height of the Splash (1.0 = 100%)", 1.00).getDouble();
	SPLASH_SCALE_DOUBLE_ITEM = WATERMAIN.config.get(CATEGORY_WATERMOD2, "Doubles the size of the splash for items", false).getBoolean();
	SPLASH_SCALE_MAXTRACKEDITEMS = WATERMAIN.config.get(CATEGORY_WATERMOD2, "Sets the Max Amount of Tracked Items around the Player)", 20).getInt();  
	SPLASH_LOWPERFORMANCEMOD = WATERMAIN.config.get(CATEGORY_WATERMOD2,"Enable this to get a bit more performance, but it will spawn a Splash on EVERY contact with water",false).getBoolean(); 
	
	final String CATEGORY_WATERMOD3 = WATERMAIN.config.CATEGORY_GENERAL + WATERMAIN.config.CATEGORY_SPLITTER + "WATERRIPPLE";
	WATERMAIN.config.addCustomCategoryComment(CATEGORY_WATERMOD3, "GENERAL");
	RIPPLE_SCALE_MODIFIER_SIZE = WATERMAIN.config.get(CATEGORY_WATERMOD3, "Scales the Size of the Ripple (1.0 = 100%)", 1.00).getDouble();  
	RIPPLE_RARITY_MODIFIER = WATERMAIN.config.get(CATEGORY_WATERMOD3, "Sets the chance of spawning the Ripple (1.0 = 100%)", 1.00).getDouble();  
	RIPPLE_AREA_MODIFIER = WATERMAIN.config.get(CATEGORY_WATERMOD3, "Sets the range as RADIUS where Ripples spawns from the Player (20 = 20 Blocks)", 20).getInt();  
	
	final String CATEGORY_WATERMOD4 = WATERMAIN.config.CATEGORY_GENERAL + WATERMAIN.config.CATEGORY_SPLITTER + "WATERTRAIL";
	WATERMAIN.config.addCustomCategoryComment(CATEGORY_WATERMOD4, "GENERAL");
	
	Enable_Trail_Transparancy = WATERMAIN.config.get(CATEGORY_WATERMOD4,"Enable the System to higher the Transparency of the Trail to the lifeend", true).getBoolean();  
	TRAIL_SCALE_MODIFIER_SIZE = WATERMAIN.config.get(CATEGORY_WATERMOD4, "Scales the Size of the Trail (1.0 = 100%)", 1.00).getDouble();  
	TRAIL_RARITY_MODIFIER = WATERMAIN.config.get(CATEGORY_WATERMOD4, "Sets the amount of spawning particles in the Trail (1 = 300%) to (6 = 50%)", 3).getInt();  
	
	
	if(WATERMAIN.config.hasChanged()){

		WATERMAIN.config.save();
		}
	}

}
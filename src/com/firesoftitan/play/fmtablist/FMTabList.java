package com.firesoftitan.play.fmtablist;


import com.firesoftitan.play.fmtablist.listeners.FMTTabComplete;
import com.firesoftitan.play.fmtablist.listeners.GPREListener;
import com.firesoftitan.play.fmtablist.mystuff.CustomConfiguration;
import com.firesoftitan.play.fmtablist.slimefun.CustomCategories;
import com.firesoftitan.play.fmtablist.slimefun.SFItems;
import com.firesoftitan.play.fmtablist.slimefun.machines.AncientAltarCrafter;
import com.firesoftitan.play.fmtablist.slimefun.machines.AutomatedAncientAltarCrafter;
import com.firesoftitan.play.fmtablist.slimefun.machines.AutomatedVanillaCraftingChamber;
import com.firesoftitan.play.fmtablist.slimefun.machines.ElectricCobbletoDust;
import com.firesoftitan.play.fmtablist.timers.mainBrain;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.World.CustomSkull;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.Research;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.SlimefunStartup;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import me.mrCookieSlime.Slimefun.api.energy.ChargableBlock;
import me.mrCookieSlime.Slimefun.api.energy.EnergyTicker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

public class FMTabList extends JavaPlugin {

    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;
    public static FMTabList plugin;
    Logger log;
    public long evtime = 0;
    public long evcount = 0;
    public long lbtime = 0;
    public long vtime = 0;
    public int ranonce = 0;
    public ArrayList<String> mobList = new ArrayList<String>();
    public HashMap<UUID, ItemStack[]> playerListSaves = new HashMap<UUID, ItemStack[]>();
    public ArrayList<UUID> protectedList = new ArrayList<UUID>();
    public CustomConfiguration voter;
    public File voterFile;
    public static boolean WhosFirst = false;
    public static Map<String, ItemStack> recipesV = new HashMap<String, ItemStack>();
    // Dependencies Variables
    public void onDisable()
    {
        if (getIP().startsWith("69.30.206.")) {
            this.sendMail("Shutdown", "Server is Shutting down...");
        }
    }

    public void onEnable(){
        plugin = this;

        if (getIP().startsWith("69.30.206.")) {
            this.sendMail("Startup", "Server is Starting up...");
        }
        this.log = getLogger();

        voterFile = new File( plugin.getDataFolder()+File.separator+"_Config.yml") ;
        voter = new CustomConfiguration(this,voterFile);
        voter.load();
        mobList.add("sheep");
        mobList.add("zombie");
        mobList.add("zombie");
        mobList.add("zombie");
        mobList.add("skeleton");
        mobList.add("skeleton");
        mobList.add("skeleton");
        mobList.add("witch");
        mobList.add("witch");
        mobList.add("witch");
        mobList.add("blaze");
        mobList.add("blaze");
        mobList.add("blaze");
        mobList.add("ghast");
        mobList.add("ghast");
        mobList.add("ghast");
        mobList.add("endermite");
        mobList.add("slime");
        mobList.add("slime");
        mobList.add("slime");

        GPREListener tmp =  new GPREListener(this);
        tmp.registerEvents();

        SFItems.TitanStone = makeTitanStone();
        SFItems.TitanBookAll = makeTitanBook(0);
        SFItems.TitanBookSoulbound = makeTitanBook(1);
        SFItems.TitanBookUnbreakable = makeTitanBook(2);
        SFItems.TitanBookUndroppable = makeTitanBook(3);

        lbtime = System.currentTimeMillis();
        vtime = System.currentTimeMillis();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new mainBrain(tmp),25, 25);
///give freethemice 214 1 0 {display:{Name:"&fTitan Stone"},ench:[{id:34,lvl:10}]}
        registerItems();

        setupVanillaCraft();
    }
    public static void setupVanillaCraft()
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(SlimefunStartup.instance, new Runnable() {
            @Override
            public void run() {
                Iterator iterator2 = Bukkit.recipeIterator();
                while (iterator2.hasNext()) {
                    Recipe r = (Recipe) iterator2.next();
                    if (r instanceof ShapedRecipe)
                    {
                        ShapedRecipe SR = (ShapedRecipe)r;
                        String[] shapeS = SR.getShape();
                        Map<Character, ItemStack> MapCM = SR.getIngredientMap();
                        ItemStack[] Reci = {null, null, null, null, null, null, null, null, null};
                        String myName = "";
                        Character[] key = new Character[9];
                        int counter = 0;
                        int[] yH = {0,1,2,3,4,5,6,7,8};//{0,3,6,1,4,7,2,5,8};
                        String teShape = "";
                        for (int o = 0; o < shapeS.length;o++ )
                        {
                            shapeS[o] = shapeS[o] + "***********";
                            shapeS[o] = shapeS[o].substring(0, 3);

                            for (int p = 0; p < shapeS[o].length();p++ )
                            {
                                key[yH[counter]] = shapeS[o].charAt(p);
                                counter++;
                            }
                            teShape = teShape + shapeS[o]  + "<>";
                        }
                        for (int o = shapeS.length; o < 3;o++ )
                        {
                            String missed = "***";

                            for (int p = 0; p < missed.length();p++ )
                            {
                                key[yH[counter]] = missed.charAt(p);
                                counter++;
                            }
                            teShape = teShape + "XXX"  + "<>";
                        }
                        Short Dura = SR.getResult().getDurability();

                        boolean goodRec = false;
                        for (int o = 0; o < 9;o++ )
                        {

                            Reci[o] = MapCM.get(key[o]);

                            if (Reci[o] == null || Reci[o].getType() == Material.AIR)
                            {
                                myName = myName + "null" + ChatColor.GRAY;
                            }
                            else {
                                goodRec = true;
                                myName = myName + Reci[o].getType().toString() + ":" + Reci[o].getDurability() + ChatColor.GRAY;
                            }
                        }
                        if (goodRec) {
                            recipesV.put(myName, r.getResult());
                        }
                    }
                }
                System.out.println("[Slimefun4]: All vinilla recipes are loaded!");
            }
        }, 300);
    }
    public  String getIP()
    {
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

            String ip = in.readLine(); //you get the IP as a String
            return ip;
        }
        catch (Exception e)
        {
            return "";
        }
    }
    private  ItemStack makeTitanBook(int booktype)
    {
        List<String> lore = new ArrayList<String>();
        if (booktype == 1 || booktype == 0) {
            lore.add(ChatColor.RED + "Soulbound");
        }
        if (booktype == 2 || booktype == 0) {
            lore.add(ChatColor.DARK_PURPLE + "Unbreakable");
        }
        if (booktype == 3 || booktype == 0) {
            lore.add(ChatColor.DARK_GREEN + "Undroppable");
        }
        ItemStack Soulbound = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta Soulboundmeta = Soulbound.getItemMeta();
        Soulboundmeta.setDisplayName(ChatColor.AQUA + "Titan Book");
        Soulboundmeta.setLore(lore);
        Soulbound.setItemMeta(Soulboundmeta);

        return Soulbound;
    }
    private ItemStack makeTitanStone() {
        getCommand("pm").setTabCompleter(new FMTTabComplete());
        ItemStack Soulbound = new ItemStack(Material.NETHER_WART_BLOCK);
        ItemMeta Soulboundmeta =  Soulbound.getItemMeta();
        Soulboundmeta.setDisplayName(ChatColor.AQUA + "Titan Stone");
        Soulboundmeta.addEnchant(Enchantment.LUCK, 10, true);
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GOLD + "Forged in the heart of Admin Mountain.");
        Soulboundmeta.setLore(lore);
        Soulbound.setItemMeta(Soulboundmeta);
        return Soulbound;
    }
    public static ItemStack getHead(String Texture)
    {
        try
        {
            return CustomSkull.getItem(Texture);
        }catch (Exception e)
        {
            return null;
        }
    }
    public void registerItems()
    {

        try {
            SFItems.LuckyBlock = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjNiNzEwYjA4YjUyM2JiYTdlZmJhMDdjNjI5YmEwODk1YWQ2MTEyNmQyNmM4NmJlYjM4NDU2MDNhOTc0MjZjIn19fQ=="), "&rVery lucky Block", new String[]{"&7Luck: &a+80"});
        } catch (Exception e) {
            e.printStackTrace();
        }
        ItemStack Reward =  new ItemStack(SFItems.TitanStone);
        Reward.setAmount(16);

        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, SFItems.TitanStone, "TitanStone", RecipeType.ANCIENT_ALTAR, new ItemStack[] {SlimefunItems.POWER_CRYSTAL, SlimefunItems.POWER_CRYSTAL, SlimefunItems.POWER_CRYSTAL,SlimefunItems.POWER_CRYSTAL,SlimefunItems.ESSENCE_OF_AFTERLIFE,SlimefunItems.POWER_CRYSTAL,SlimefunItems.POWER_CRYSTAL, SlimefunItems.POWER_CRYSTAL,SlimefunItems.POWER_CRYSTAL}, Reward).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN, SFItems.TitanBookSoulbound, "TitanBookSoulbound", RecipeType.ANCIENT_ALTAR, new ItemStack[] {SFItems.TitanStone, SlimefunItems.SOULBOUND_SWORD, SFItems.TitanStone, SlimefunItems.SOULBOUND_SWORD,new ItemStack(Material.BOOK), SlimefunItems.SOULBOUND_SWORD,SFItems.TitanStone, SlimefunItems.SOULBOUND_SWORD, SFItems.TitanStone }).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN, SFItems.TitanBookUnbreakable, "TitanBookUnbreakable", RecipeType.ANCIENT_ALTAR, new ItemStack[] {SFItems.TitanStone, SlimefunItems.SOULBOUND_CHESTPLATE, SFItems.TitanStone, SlimefunItems.SOULBOUND_CHESTPLATE, SFItems.TitanBookSoulbound, SlimefunItems.SOULBOUND_CHESTPLATE,SFItems.TitanStone, SlimefunItems.SOULBOUND_CHESTPLATE, SFItems.TitanStone }).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN, SFItems.TitanBookUndroppable, "TitanBookUndroppable", RecipeType.ANCIENT_ALTAR, new ItemStack[] {SFItems.TitanStone, SlimefunItems.SOULBOUND_HOE, SFItems.TitanStone, SlimefunItems.SOULBOUND_HOE, SFItems.TitanBookSoulbound, SlimefunItems.SOULBOUND_HOE,SFItems.TitanStone, SlimefunItems.SOULBOUND_HOE, SFItems.TitanStone }).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN, SFItems.TitanBookAll, "TitanBookAll", RecipeType.ANCIENT_ALTAR, new ItemStack[] {SFItems.TitanStone, SFItems.TitanBookUndroppable, SFItems.TitanStone, SFItems.TitanBookUnbreakable, SFItems.TitanBookSoulbound, SFItems.TitanBookUnbreakable,SFItems.TitanStone, SFItems.TitanBookUndroppable, SFItems.TitanStone }).register();


        setupLuckySet();

        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, SFItems.EclipseNugget, "EclipseNugget", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.TitanStone, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot}).register();
        setupEclipseSet();

        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, SFItems.TitanNugget, "TitanNugget", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {null, SFItems.EclipseIngot, null, SFItems.EclipseIngot, SFItems.TitanStone, SFItems.EclipseIngot, null, SFItems.EclipseIngot, null}).register();
        setupTitanSet();


        new ElectricCobbletoDust(CustomCategories.ELECTRICITY, SFItems.ELECTRIC_COBBLE_TO_DUST, "ELECTRIC_COBLE_TO_DUST", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, SlimefunItems.ELECTRIC_DUST_WASHER,  SFItems.LuckyIngot,  SFItems.LuckyIngot,  SFItems.LuckyIngot,  SFItems.LuckyIngot}) {

            @Override
            public int getEnergyConsumption() {
                return 15;
            }

            @Override
            public int getSpeed() {
                return 2;
            }
        }.registerChargeableBlock(true, 512);


        new AutomatedVanillaCraftingChamber(CustomCategories.ELECTRICITY, SFItems.AUTOMATED_VANILLA_CRAFTING_CHAMBER, "AUTOMATED_VANILLA_CRAFTING_CHAMBER", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {null, new ItemStack(Material.WORKBENCH), null, SlimefunItems.CARGO_MOTOR, SlimefunItems.COPPER_INGOT, SlimefunItems.CARGO_MOTOR, null, SlimefunItems.ELECTRIC_MOTOR, null}) {

            @Override
            public int getEnergyConsumption() {
                return 10;
            }
        }.registerChargeableBlock(true, 256);

        new AncientAltarCrafter(CustomCategories.ELECTRICITY, SFItems.ANCIENT_ALTAR_CRAFTER, "ANCIENT_ALTAR_CRAFTER_CHAMBER", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {null, SlimefunItems.ANCIENT_PEDESTAL, null, SlimefunItems.CARGO_MOTOR, SlimefunItems.ANCIENT_ALTAR, SlimefunItems.CARGO_MOTOR, SlimefunItems.ANCIENT_PEDESTAL, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.ANCIENT_PEDESTAL}) {

            @Override
            public int getEnergyConsumption() {
                return 50;
            }
        }.registerChargeableBlock(true, 256);

        new AutomatedAncientAltarCrafter(CustomCategories.ELECTRICITY, SFItems.AUTOMATED_ANCIENT_ALTAR_CRAFTER, "AUTOMATED_ANCIENT_ALTAR_CRAFTER_CHAMBER", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {null, new ItemStack(Material.WORKBENCH), null, SlimefunItems.CARGO_MOTOR, SFItems.ANCIENT_ALTAR_CRAFTER, SlimefunItems.CARGO_MOTOR, null, SlimefunItems.ELECTRIC_MOTOR, null}) {

            @Override
            public int getEnergyConsumption() {
                return 50;
            }
        }.registerChargeableBlock(true, 256);

        new SlimefunItem(CustomCategories.ELECTRICITY, SFItems.THERMAL_GENERATOR, "THERMAL_GENERATOR", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SlimefunItems.HEATING_COIL, SlimefunItems.SOLAR_GENERATOR_4, SlimefunItems.HEATING_COIL, SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.LARGE_CAPACITOR, SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.HEATING_COIL, SlimefunItems.SOLAR_GENERATOR_4, SlimefunItems.HEATING_COIL})
                .register(true, new EnergyTicker() {

                    @Override
                    public double generateEnergy(Location l, SlimefunItem item, Config data) {
                        try {
                            if (l == null) {
                                return 0;
                            }
                            Location lavaCheck = l.clone().add(0, -1, 0);
                            Location AirCheck = l.clone().add(0, 1, 0);
                            boolean Run = true;
                            boolean explode = false;
                            if (l.getChunk().isLoaded()) {
                                try {
                                    for (int x = -1; x < 2; x++) {
                                        for (int z = -1; z < 2; z++) {
                                            if (lavaCheck.clone().add(x, 0, z).getBlock().getType() != Material.STATIONARY_LAVA) {
                                                Run = false;
                                            }
                                            if (AirCheck.clone().add(x, 0, z).getBlock().getType() != Material.AIR) {
                                                explode = true;
                                            }
                                        }
                                    }
                                } catch (Exception e) {

                                }
                            }
                            if (Run && !explode) {
                                double past = 256 * (1D - (l.getBlockY() / 100D));
                                return past;
                            } else {
                                if (explode && Run) {
                                    Bukkit.getScheduler().scheduleSyncDelayedTask(SlimefunStartup.instance, new Runnable() {

                                        @Override
                                        public void run() {
                                            AirCheck.getWorld().createExplosion(AirCheck.add(0, 7, 0).clone(), 6);
                                            BlockStorage.clearBlockInfo(l);

                                            for (int y = 0; y < 100; y++) {
                                                for (int x = -1; x < 2; x++) {
                                                    for (int z = -1; z < 2; z++) {
                                                        Location exp = l.clone().add(x, y, z);
                                                        if (BlockStorage.hasBlockInfo(exp)) {
                                                            BlockStorage.clearBlockInfo(exp);
                                                        }
                                                        AirCheck.getWorld().getBlockAt(exp).setType(Material.AIR);
                                                    }
                                                }
                                            }
                                        }
                                    }, 20);

                                }
                                return 0;
                            }
                        }
                        catch (Exception e)
                        {
                            return 0;
                        }
                    }
                    //8192
                    @Override
                    public boolean explode(Location l) {
                        return false;
                    }

                });
        ChargableBlock.registerChargableBlock("THERMAL_GENERATOR", 8192, false);


        Slimefun.registerResearch(new Research(79001, "Thermal Power Plant", 89), SFItems.THERMAL_GENERATOR);
        Slimefun.registerResearch(new Research(79002, "Ancient Altar Crafter", 75), SFItems.ANCIENT_ALTAR_CRAFTER);
        Slimefun.registerResearch(new Research(79003, "Vanilla Auto Crafter", 25), SFItems.AUTOMATED_VANILLA_CRAFTING_CHAMBER);
        Slimefun.registerResearch(new Research(79004, "Automated Ancient Altar Crafter", 25), SFItems.AUTOMATED_ANCIENT_ALTAR_CRAFTER);


        Slimefun.registerResearch(new Research(7500, "Titan Stone", 250), new ItemStack[] { SFItems.TitanStone });
        Slimefun.registerResearch(new Research(7501, "TitanBook Soulbound", 350), new ItemStack[] { SFItems.TitanBookSoulbound });
        Slimefun.registerResearch(new Research(7502, "TitanBook Unbreakable", 400), new ItemStack[] { SFItems.TitanBookUnbreakable });
        Slimefun.registerResearch(new Research(7503, "TitanBook Undroppable", 150), new ItemStack[] { SFItems.TitanBookUndroppable });
        Slimefun.registerResearch(new Research(7504, "TitanBook All", 150), new ItemStack[] { SFItems.TitanBookAll });

        Slimefun.registerResearch(new Research(7505, "Lucky Nugget", 50), new ItemStack[] { SFItems.LuckyNugget,  SFItems.LuckyNuggetB});
        //Slimefun.registerResearch(new Research(7506, "Lucky Axe", 0), new ItemStack[] { SFItems.LuckyAxe });
        //Slimefun.registerResearch(new Research(7507, "Lucky Sword", 0), new ItemStack[] { SFItems.LuckySword });
        //Slimefun.registerResearch(new Research(7508, "Lucky Pickaxe", 0), new ItemStack[] { SFItems.LuckyPickaxe });
        //Slimefun.registerResearch(new Research(7509, "Lucky Helmet", 0), new ItemStack[] { SFItems.LuckyHelmet });
        //Slimefun.registerResearch(new Research(7510, "Lucky Chestplate", 0), new ItemStack[] { SFItems.LuckyChestplate });
        //Slimefun.registerResearch(new Research(7511, "Lucky Leggings", 0), new ItemStack[] { SFItems.LuckyLeggings });
        //Slimefun.registerResearch(new Research(7512, "Lucky Boots", 0), new ItemStack[] { SFItems.LuckyBoots });

        Slimefun.registerResearch(new Research(7513, "Eclipse Nugget", 50), new ItemStack[] { SFItems.EclipseNugget, SFItems.EclipseNuggetB });
        Slimefun.registerResearch(new Research(7514, "Eclipse Axe", 100), new ItemStack[] { SFItems.EclipseAxe });
        Slimefun.registerResearch(new Research(7515, "Eclipse Sword", 125), new ItemStack[] { SFItems.EclipseSword });
        Slimefun.registerResearch(new Research(7516, "Eclipse Pickaxe", 175), new ItemStack[] { SFItems.EclipsePickaxe });
        Slimefun.registerResearch(new Research(7517, "Eclipse Helmet", 100), new ItemStack[] { SFItems.EclipseHelmet });
        Slimefun.registerResearch(new Research(7518, "Eclipse Chestplate", 175), new ItemStack[] { SFItems.EclipseChestplate });
        Slimefun.registerResearch(new Research(7519, "Eclipse Leggings", 150), new ItemStack[] { SFItems.EclipseLeggings });
        Slimefun.registerResearch(new Research(7520, "Eclipse Boots", 100), new ItemStack[] { SFItems.EclipseBoots });

        Slimefun.registerResearch(new Research(7521, "Titan Nugget", 50), new ItemStack[] { SFItems.TitanNugget, SFItems.TitanNuggetB });
        Slimefun.registerResearch(new Research(7522, "Titan Axe", 100), new ItemStack[] { SFItems.TitanAxe });
        Slimefun.registerResearch(new Research(7523, "Titan Sword", 125), new ItemStack[] { SFItems.TitanSword });
        Slimefun.registerResearch(new Research(7524, "Titan Pickaxe", 175), new ItemStack[] { SFItems.TitanPickaxe });
        Slimefun.registerResearch(new Research(7525, "Titan Helmet", 100), new ItemStack[] { SFItems.TitanHelmet });
        Slimefun.registerResearch(new Research(7526, "Titan Chestplate", 175), new ItemStack[] { SFItems.TitanChestplate });
        Slimefun.registerResearch(new Research(7527, "Titan Leggings", 150), new ItemStack[] { SFItems.TitanLeggings });
        Slimefun.registerResearch(new Research(7528, "Titan Boots", 100), new ItemStack[] { SFItems.TitanBoots });

        Slimefun.registerResearch(new Research(7529, "Lucky Ingot", 50), new ItemStack[] { SFItems.LuckyIngot });
        Slimefun.registerResearch(new Research(7530, "Eclipse Ingot", 50), new ItemStack[] { SFItems.EclipseIngot });
        Slimefun.registerResearch(new Research(7531, "Titan Ingot", 50), new ItemStack[] { SFItems.TitanIngot });

        Slimefun.registerResearch(new Research(7532, "Electric Cobble to Dust", 50), new ItemStack[] { SFItems.ELECTRIC_COBBLE_TO_DUST });

    }
    private void setupTitanSet() {
        ItemStack Reward =  new ItemStack(SFItems.TitanNugget);
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, SFItems.TitanNuggetB, "TitanNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {null,null,SFItems.TitanAxe, SFItems.TitanBoots, SFItems.TitanChestplate, SFItems.TitanHelmet, SFItems.TitanLeggings, SFItems.TitanSword, SFItems.TitanPickaxe}).register();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.TitanNugget, "TitanNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.TitanAxe, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(4);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.TitanNugget, "TitanNugget ", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.TitanBoots, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(5);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.TitanNugget, "TitanNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.TitanChestplate, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(4);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.TitanNugget, "TitanNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.TitanHelmet, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(4);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.TitanNugget, "TitanNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.TitanLeggings, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.TitanNugget, "TitanNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.TitanSword, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.TitanNugget, "TitanNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.TitanPickaxe, null, null, null, null, null, null, null, null}, Reward).register();

        Reward = SFItems.TitanNugget.clone();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, SFItems.TitanIngot, "TitanIgnot", RecipeType.COMPRESSOR,  new ItemStack[] {Reward, null, null, null, null, null, null, null, null}).register();

        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN_GEAR, SFItems.TitanAxe, "TitanAxe", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.TitanIngot, SFItems.TitanIngot, null, SFItems.TitanIngot, new ItemStack(Material.STICK), null, null, new ItemStack(Material.STICK), null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN_GEAR, SFItems.TitanSword, "TitanSword", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {null, SFItems.TitanIngot, null, null, SFItems.TitanIngot,null, null, new ItemStack(Material.STICK), null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN_GEAR, SFItems.TitanPickaxe, "TitanPickaxe", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.TitanIngot, SFItems.TitanIngot, SFItems.TitanIngot, null, new ItemStack(Material.STICK), null, null, new ItemStack(Material.STICK), null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN_GEAR, SFItems.TitanHelmet, "TitanHelmet", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.TitanIngot, SFItems.TitanIngot, SFItems.TitanIngot, SFItems.TitanIngot, null, SFItems.TitanIngot, null, null, null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN_GEAR, SFItems.TitanChestplate, "TitanChestplate", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.TitanIngot, null, SFItems.TitanIngot, SFItems.TitanIngot, SFItems.TitanIngot, SFItems.TitanIngot, SFItems.TitanIngot, SFItems.TitanIngot, SFItems.TitanIngot}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN_GEAR, SFItems.TitanLeggings, "TitanLeggings", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.TitanIngot, SFItems.TitanIngot, SFItems.TitanIngot, SFItems.TitanIngot,null, SFItems.TitanIngot, SFItems.TitanIngot, null, SFItems.TitanIngot}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN_GEAR, SFItems.TitanBoots, "TitanBoots", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {null, null, null, SFItems.TitanIngot, null, SFItems.TitanIngot, SFItems.TitanIngot, null, SFItems.TitanIngot}).register();
    }
    private void setupEclipseSet() {
        ItemStack Reward =  new ItemStack(SFItems.EclipseNugget);
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, SFItems.EclipseNuggetB, "EclipseNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {null,null,SFItems.EclipseAxe, SFItems.EclipseBoots, SFItems.EclipseChestplate, SFItems.EclipseHelmet, SFItems.EclipseLeggings, SFItems.EclipseSword, SFItems.EclipsePickaxe}).register();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.EclipseNugget, "EclipseNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.EclipseAxe, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(4);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.EclipseNugget, "EclipseNugget ", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.EclipseBoots, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(5);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.EclipseNugget, "EclipseNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.EclipseChestplate, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(5);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.EclipseNugget, "EclipseNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.EclipseHelmet, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(4);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.EclipseNugget, "EclipseNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.EclipseLeggings, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.EclipseNugget, "EclipseNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.EclipseSword, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.EclipseNugget, "EclipseNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.EclipsePickaxe, null, null, null, null, null, null, null, null}, Reward).register();

        Reward = SFItems.EclipseNugget.clone();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, SFItems.EclipseIngot, "EclipseIgnot", RecipeType.COMPRESSOR,  new ItemStack[] {Reward, null, null, null, null, null, null, null, null}).register();

        new SlimefunItem(CustomCategories.SLIMEFUN_ECLIPSE, SFItems.EclipseAxe, "EclipseAxe", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.EclipseIngot, SFItems.EclipseIngot, null, SFItems.EclipseIngot, new ItemStack(Material.STICK), null, null, new ItemStack(Material.STICK), null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_ECLIPSE, SFItems.EclipseSword, "EclipseSword", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {null, SFItems.EclipseIngot, null, null, SFItems.EclipseIngot,null, null, new ItemStack(Material.STICK), null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_ECLIPSE, SFItems.EclipsePickaxe, "EclipsePickaxe", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot, null, new ItemStack(Material.STICK), null, null, new ItemStack(Material.STICK), null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_ECLIPSE, SFItems.EclipseHelmet, "EclipseHelmet", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot, null, SFItems.EclipseIngot, null, null, null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_ECLIPSE, SFItems.EclipseChestplate, "EclipseChestplate", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.EclipseIngot, null, SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_ECLIPSE, SFItems.EclipseLeggings, "EclipseLeggings", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot,null, SFItems.EclipseIngot, SFItems.EclipseIngot, null, SFItems.EclipseIngot}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_ECLIPSE, SFItems.EclipseBoots, "EclipseBoots", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {null, null, null, SFItems.EclipseIngot, null, SFItems.EclipseIngot, SFItems.EclipseIngot, null, SFItems.EclipseIngot}).register();
    }
    private void setupLuckySet() {
        ItemStack Reward = SFItems.LuckyBlock.clone();

        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, SFItems.LuckyNugget.clone(), "LuckyNugget", RecipeType.COMPRESSOR,  new ItemStack[] {Reward, null, null, null, null, null, null, null, null}).register();

        Reward =  new ItemStack(SFItems.LuckyNugget);
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, SFItems.LuckyNuggetB, "LuckyNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {null,null,SFItems.LuckyAxe, SFItems.LuckyBoots, SFItems.LuckyChestplate, SFItems.LuckyHelmet, SFItems.LuckyLeggings, SFItems.LuckySword, SFItems.LuckyPickaxe}).register();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.LuckyNugget, "LuckyNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.LuckyAxe, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(4);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.LuckyNugget, "LuckyNugget ", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.LuckyBoots, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(5);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.LuckyNugget, "LuckyNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.LuckyChestplate, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(4);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.LuckyNugget, "LuckyNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.LuckyHelmet, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(4);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.LuckyNugget, "LuckyNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.LuckyLeggings, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.LuckyNugget, "LuckyNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.LuckySword, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.LuckyNugget, "LuckyNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.LuckyPickaxe, null, null, null, null, null, null, null, null}, Reward).register();

        Reward = SFItems.LuckyNugget.clone();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, SFItems.LuckyIngot, "LuckyIgnot", RecipeType.COMPRESSOR,  new ItemStack[] {Reward, null, null, null, null, null, null, null, null}).register();

        new SlimefunItem(CustomCategories.SLIMEFUN_LUCKY, SFItems.LuckyAxe, "LuckyAxe", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.LuckyIngot, SFItems.LuckyIngot, null, SFItems.LuckyIngot, new ItemStack(Material.STICK), null, null, new ItemStack(Material.STICK), null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_LUCKY, SFItems.LuckySword, "LuckySword", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {null, SFItems.LuckyIngot, null, null, SFItems.LuckyIngot,null, null, new ItemStack(Material.STICK), null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_LUCKY, SFItems.LuckyPickaxe, "LuckyPickaxe", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, null, new ItemStack(Material.STICK), null, null, new ItemStack(Material.STICK), null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_LUCKY, SFItems.LuckyHelmet, "LuckyHelmet", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, null, SFItems.LuckyIngot, null, null, null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_LUCKY, SFItems.LuckyChestplate, "LuckyChestplate", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.LuckyIngot, null, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_LUCKY, SFItems.LuckyLeggings, "LuckyLeggings", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot,null, SFItems.LuckyIngot, SFItems.LuckyIngot, null, SFItems.LuckyIngot}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_LUCKY, SFItems.LuckyBoots, "LuckyBoots", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {null, null, null, SFItems.LuckyIngot, null, SFItems.LuckyIngot, SFItems.LuckyIngot, null, SFItems.LuckyIngot}).register();
    }
    public boolean checkforTitanStone(ItemStack itemA)
    {
        if (itemA != null) {
            if (itemA.getItemMeta() != null) {
                if (itemA.getItemMeta().hasDisplayName() && itemA.getItemMeta().hasLore()) {
                    if (itemA.getItemMeta().getDisplayName().equals(SFItems.TitanStone.getItemMeta().getDisplayName())) {
                        if (FMTabList.plugin.equalsLore(itemA.getItemMeta().getLore(), SFItems.TitanStone.getItemMeta().getLore())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public boolean equalsLore(List<String> lore, List<String> lore2) {
        String string1 = "", string2 = "";
        for (String string: lore) {
            if (!string.startsWith(ChatColor.AQUA + "Titan.")) {
                if (!string.startsWith("&e&e&7")) string1 = string1 + "-NEW LINE-" + string;
            }
        }
        for (String string: lore2) {
            if (!string.startsWith(ChatColor.AQUA + "Titan.")) {
                if (!string.startsWith("&e&e&7")) string2 = string2 + "-NEW LINE-" + string;
            }
        }
        return string1.equals(string2);
    }
    public void sendMail(String sub, String message)
    {
        try {
            // Step1
            System.out.println("\n 1st ===> setup Mail Server Properties..");
            mailServerProperties = System.getProperties();
            mailServerProperties.put("mail.smtp.port", "587");
            mailServerProperties.put("mail.smtp.auth", "true");
            mailServerProperties.put("mail.smtp.starttls.enable", "true");
            System.out.println("Mail Server Properties have been setup successfully..");

            // Step2
            System.out.println("\n\n 2nd ===> get Mail Session..");
            getMailSession = Session.getDefaultInstance(mailServerProperties, null);
            generateMailMessage = new MimeMessage(getMailSession);
            generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("cgfreethemice@gmail.com"));
            //generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("test2@crunchify.com"));
            generateMailMessage.setSubject("Fires of Titan: " + sub);
            String emailBody = message +  "<br><br> Regards, <br>Fires of Titan";
            generateMailMessage.setContent(emailBody, "text/html");
            System.out.println("Mail Session has been created successfully..");

            // Step3
            System.out.println("\n\n 3rd ===> Get Session and Send mail");
            Transport transport = getMailSession.getTransport("smtp");

            // Enter your correct gmail UserID and Password
            // if you have 2FA enabled then provide App Specific Password
            transport.connect("smtp.gmail.com", "codegreenmice", "epLKl*=.gU1}");
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();

            System.out.println("\n\n 4th ===> Mail sent!");
        }
        catch (Exception e)
        {
            System.out.println("\n\n Error ===> \n" + e.toString());
        }
    }

    public void sendChatMessage(CommandSender sender, String message)
    {
        sendChatMessage(((Player)sender), message);
    }
    public void sendChatMessage(Player player, String message)
    {
        player.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " +ChatColor.GREEN + message);
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (command.getName().equalsIgnoreCase("emailout")) {
            if (args.length > 0) {
                String sendermail = "Server";
                if (sender instanceof Player)
                {
                    if (!((Player) sender).hasPermission("FMTabList.email")) {
                        sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " +ChatColor.GREEN + "You can't do that!");
                        return true;
                    }
                    sendermail = ((Player)sender).getDisplayName();
                }
                String Messageout = "";
                for (int i = 0; i < args.length; i++)
                {
                    Messageout = Messageout + args[i] + " ";
                }
                this.sendMail(sendermail, Messageout);
                sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " +ChatColor.GREEN + "email sent to server admin.");
            }
            return true;
        }
        if (command.getName().equalsIgnoreCase("setvote")) {
            if (args.length > 0) {
                if (sender instanceof Player)
                {
                    if (!((Player) sender).hasPermission("FMTabList.setvote")) {
                        sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " +ChatColor.GREEN + "You can't do that!");
                        return true;
                    }
                }
                String voteQ = "";
                for (int i = 0;i<args.length;i++)
                {
                    voteQ= voteQ + args[i] + " ";
                }
                for(String key :FMTabList.plugin.voter.getKeys())
                {
                    FMTabList.plugin.voter.setProperty(key, null);
                }
                FMTabList.plugin.voter.save();

                FMTabList.plugin.voter.setProperty("voteyes", 0);
                FMTabList.plugin.voter.setProperty("voteno", 0);
                FMTabList.plugin.voter.setProperty("votequestion", voteQ);
                FMTabList.plugin.voter.save();
                FMTabList.plugin.vtime = 1000 *60 * 22;

            }
            return true;
        }

        if (command.getName().equalsIgnoreCase("fmsf") && args.length > 0) {

            //give freethemice 214 1 0 {display:{Name:"&fTitan Stone"},ench:[{id:34,lvl:10}]}
            if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("all")) {
                List<String> lore = new ArrayList<String>();

                    if (sender instanceof Player) {
                        if (((Player) sender).hasPermission("FMTabList.fmsf")) {
                            int empty = ((Player) sender).getInventory().firstEmpty();
                            if (empty > -1) {
                                if (args[0].equalsIgnoreCase("1")) {
                                    ((Player) sender).getInventory().setItem(empty, SFItems.TitanBookSoulbound);
                                }
                                if (args[0].equalsIgnoreCase("2")) {
                                    ((Player) sender).getInventory().setItem(empty, SFItems.TitanBookUnbreakable);
                                }

                                if (args[0].equalsIgnoreCase("3")) {
                                    ((Player) sender).getInventory().setItem(empty, SFItems.TitanBookUndroppable);
                                }

                                if (args[0].equalsIgnoreCase("all")) {
                                    ((Player) sender).getInventory().setItem(empty, SFItems.TitanBookAll);
                                }

                            }
                        }
                    }
            }
            if (args[0].equalsIgnoreCase("4"))
            {
                if (sender instanceof Player) {
                    if (((Player) sender).hasPermission("FMTabList.fmsf")) {
                        int empty = ((Player) sender).getInventory().firstEmpty();
                        if (empty > -1) {
                            ((Player) sender).getInventory().setItem(empty, SFItems.TitanStone.clone());
                        }
                    }
                }

            }

        }
        if (command.getName().equalsIgnoreCase("pm")) {
            if (args.length > 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (((Player) sender).hasPermission("FMTabList.pm")) {
                        Plugin plugin = Bukkit.getPluginManager().getPlugin(args[1]);
                        if ((plugin != null) && (plugin.isEnabled())) {
                            Bukkit.getPluginManager().disablePlugin(plugin);
                            Bukkit.getPluginManager().enablePlugin(plugin);
                            sendChatMessage(sender, "Successfully Reloaded '" + args[1] + "'");
                        }
                    } else {
                        sendChatMessage(sender, "You don't have permission to do that!");
                    }
                    return true;
                }


                if (args[0].equalsIgnoreCase("load")) {
                    if (((Player) sender).hasPermission("FMTabList.pm")) {
                        File file = new File("plugins", args[1]);
                        if (file.exists()) {
                            ((Player) sender).sendMessage("§aTrying to load '" + args[1] + "'");
                            try {
                                Bukkit.getPluginManager().loadPlugin(file);
                            } catch (UnknownDependencyException e) {
                                sendChatMessage(sender, "Failed to load: 'Unknown dependency'");
                                return true;
                            } catch (InvalidPluginException e) {
                                sendChatMessage(sender, "Failed to load: 'Invalid plugin'");
                                return true;
                            } catch (InvalidDescriptionException e) {
                                sendChatMessage(sender, "Failed to load: 'Invalid plugin description'");
                                return true;
                            }
                            sendChatMessage(sender, "Successfully loaded '" + args[1] + "'");
                        } else {
                            sendChatMessage(sender, "The specified file '" + args[1] + "' doesn't exist!");
                        }
                    } else {
                        sendChatMessage(sender, "You don't have permission to do that!");
                    }
                    return true;
                }
            }
        }
        if (command.getName().equalsIgnoreCase("lbt")) {
            if (!(sender instanceof Player)) {
                this.lbtime = 1000 * 60 * 60;
            }
            else
            {
                if (((Player)sender).hasPermission("FMTabList.lbt"))
                {
                    this.lbtime = 1000 * 60 * 60;
                }

            }
            return true;
        }
        return true;
    }
}
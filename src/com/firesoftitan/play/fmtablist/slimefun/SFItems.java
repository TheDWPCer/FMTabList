package com.firesoftitan.play.fmtablist.slimefun;

import com.firesoftitan.play.fmtablist.FMTabList;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.World.CustomSkull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

/**
 * Created by Daniel on 12/28/2016.
 */
public class SFItems {
    public static ItemStack TitanStone;
    public static ItemStack TitanBookSoulbound;
    public static ItemStack TitanBookUnbreakable;
    public static ItemStack TitanBookUndroppable;
    public static ItemStack TitanBookAll;

    public static ItemStack LuckyNuggetB = new CustomItem(Material.GOLD_NUGGET, "&eLucky Nugget", new String[] { "DAMAGE_ALL-1"}, 0);
    public static ItemStack EclipseNuggetB = new CustomItem(Material.GHAST_TEAR, "&bEclipse Nugget", new String[] { "DAMAGE_ALL-3"}, 0);
    public static ItemStack TitanNuggetB = new CustomItem(Material.REDSTONE, "&4Titan Nugget", new String[] { "DAMAGE_ALL-5"}, 0);

    public static ItemStack LuckyNugget = new CustomItem(Material.GOLD_NUGGET, "&e&lLucky Nugget", new String[] { "DAMAGE_ALL-1"}, 0);
    public static ItemStack EclipseNugget = new CustomItem(Material.GHAST_TEAR, "&b&lEclipse Nugget", new String[] { "DAMAGE_ALL-3"}, 0);
    public static ItemStack TitanNugget = new CustomItem(Material.REDSTONE, "&4&lTitan Nugget", new String[] { "DAMAGE_ALL-5"}, 0);

    public static ItemStack LuckyIngot = new CustomItem(Material.GOLD_INGOT, "&e&lLucky Ingot", new String[] { "DAMAGE_ALL-2"}, 0);
    public static ItemStack EclipseIngot = new CustomItem(Material.IRON_INGOT, "&b&lEclipse Ingot", new String[] { "DAMAGE_ALL-6"}, 0);
    public static ItemStack TitanIngot = new CustomItem(Material.CLAY_BRICK, "&4&lTitan Ingot", new String[] { "DAMAGE_ALL-10"}, 0);

    public static ItemStack LuckySword = new CustomItem(Material.GOLD_SWORD, "&e&lLucky Sword", new String[] { "DAMAGE_ALL-10", "LOOT_BONUS_MOBS-10", "FIRE_ASPECT-5", "DURABILITY-10" }, 0);
    public static ItemStack LuckyPickaxe = new CustomItem(Material.GOLD_PICKAXE, "&e&lLucky Pickaxe", new String[] { "DIG_SPEED-10", "LOOT_BONUS_BLOCKS-10", "DURABILITY-10" }, 0);
    public static ItemStack LuckyAxe = new CustomItem(Material.GOLD_AXE, "&e&lLucky Axe", new String[] { "DAMAGE_ALL-10", "DIG_SPEED-10", "LOOT_BONUS_BLOCKS-10", "DURABILITY-10" }, 0);
    public static ItemStack LuckyHelmet = new CustomItem(Material.DIAMOND_HELMET, "&e&lLucky Helmet", new String[] { "PROTECTION_ENVIRONMENTAL-10", "PROTECTION_PROJECTILE-10", "PROTECTION_EXPLOSIONS-10", "THORNS-10", "DURABILITY-10" }, 0);
    public static ItemStack LuckyChestplate = new CustomItem(Material.DIAMOND_CHESTPLATE, "&e&lLucky Chestplate", new String[] { "PROTECTION_ENVIRONMENTAL-10", "PROTECTION_PROJECTILE-10", "PROTECTION_EXPLOSIONS-10", "THORNS-10", "DURABILITY-10" }, 0);
    public static ItemStack LuckyLeggings = new CustomItem(Material.DIAMOND_LEGGINGS, "&e&lLucky Leggings", new String[] { "PROTECTION_ENVIRONMENTAL-10", "PROTECTION_PROJECTILE-10", "PROTECTION_EXPLOSIONS-10", "THORNS-10", "DURABILITY-10" }, 0);
    public static ItemStack LuckyBoots = new CustomItem(Material.DIAMOND_BOOTS, "&e&lLucky Boots", new String[] { "PROTECTION_ENVIRONMENTAL-10", "PROTECTION_PROJECTILE-10", "PROTECTION_EXPLOSIONS-10", "THORNS-10", "DURABILITY-10" }, 0);
    public static ItemStack LuckyBlock;
    public static ItemStack ZeroLuckyBlock;
    public static ItemStack UnLuckyBlock;
    public static ItemStack PandorasBox;


    public static ItemStack EclipseSword = new CustomItem(Material.IRON_SWORD, "&b&lEclipse Sword", new String[] { "DAMAGE_ALL-13", "LOOT_BONUS_MOBS-13", "FIRE_ASPECT-7", "DURABILITY-13" }, 0);
    public static ItemStack EclipsePickaxe = new CustomItem(Material.IRON_PICKAXE, "&b&lEclipse Pickaxe", new String[] { "DIG_SPEED-13", "LOOT_BONUS_BLOCKS-13", "DURABILITY-13" }, 0);
    public static ItemStack EclipseAxe = new CustomItem(Material.IRON_AXE, "&b&lEclipse Axe", new String[] { "DAMAGE_ALL-13", "DIG_SPEED-13", "LOOT_BONUS_BLOCKS-13", "DURABILITY-13" }, 0);
    public static ItemStack EclipseHelmet = new CustomItem(Material.DIAMOND_HELMET, "&b&lEclipse Helmet", new String[] { "PROTECTION_ENVIRONMENTAL-13", "PROTECTION_PROJECTILE-13", "PROTECTION_EXPLOSIONS-13", "THORNS-13", "DURABILITY-13" }, 0);
    public static ItemStack EclipseChestplate = new CustomItem(Material.DIAMOND_CHESTPLATE, "&b&lEclipse Chestplate", new String[] { "PROTECTION_ENVIRONMENTAL-13", "PROTECTION_PROJECTILE-13", "PROTECTION_EXPLOSIONS-13", "THORNS-13", "DURABILITY-13" }, 0);
    public static ItemStack EclipseLeggings = new CustomItem(Material.DIAMOND_LEGGINGS, "&b&lEclipse Leggings", new String[] { "PROTECTION_ENVIRONMENTAL-13", "PROTECTION_PROJECTILE-13", "PROTECTION_EXPLOSIONS-13", "THORNS-13", "DURABILITY-13" }, 0);
    public static ItemStack EclipseBoots = new CustomItem(Material.DIAMOND_BOOTS, "&b&lEclipse Boots", new String[] { "PROTECTION_ENVIRONMENTAL-13", "PROTECTION_PROJECTILE-13", "PROTECTION_EXPLOSIONS-13", "THORNS-13", "DURABILITY-13" }, 0);

    public static ItemStack TitanSword = new CustomItem(Material.DIAMOND_SWORD, "&4&lTitan Sword", new String[] { "DAMAGE_ALL-23", "LOOT_BONUS_MOBS-23", "FIRE_ASPECT-23", "DURABILITY-23" }, 0);
    public static ItemStack TitanPickaxe = new CustomItem(Material.DIAMOND_PICKAXE, "&4&lTitan Pickaxe", new String[] { "DIG_SPEED-23", "LOOT_BONUS_BLOCKS-23", "DURABILITY-23" }, 0);
    public static ItemStack TitanAxe = new CustomItem(Material.DIAMOND_AXE, "&4&lTitan Axe", new String[] { "DAMAGE_ALL-23", "DIG_SPEED-23", "LOOT_BONUS_BLOCKS-23", "DURABILITY-23" }, 0);
    public static ItemStack TitanHelmet = new CustomItem(Material.DIAMOND_HELMET, "&4&lTitan Helmet", new String[] { "PROTECTION_ENVIRONMENTAL-23", "PROTECTION_PROJECTILE-23", "PROTECTION_EXPLOSIONS-23", "THORNS-23", "DURABILITY-23" }, 0);
    public static ItemStack TitanChestplate = new CustomItem(Material.DIAMOND_CHESTPLATE, "&4&lTitan Chestplate", new String[] { "PROTECTION_ENVIRONMENTAL-23", "PROTECTION_PROJECTILE-23", "PROTECTION_EXPLOSIONS-23", "THORNS-23", "DURABILITY-23" }, 0);
    public static ItemStack TitanLeggings = new CustomItem(Material.DIAMOND_LEGGINGS, "&4&lTitan Leggings", new String[] { "PROTECTION_ENVIRONMENTAL-23", "PROTECTION_PROJECTILE-23", "PROTECTION_EXPLOSIONS-23", "THORNS-23", "DURABILITY-23" }, 0);
    public static ItemStack TitanBoots = new CustomItem(Material.DIAMOND_BOOTS, "&4&lTitan Boots", new String[] { "PROTECTION_ENVIRONMENTAL-23", "PROTECTION_PROJECTILE-23", "PROTECTION_EXPLOSIONS-23", "THORNS-23", "DURABILITY-23" }, 0);

    public static ItemStack ANCIENT_ALTAR_CRAFTER_BLOCK = FMTabList.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWZkMDE2NzY4OTcxNWRmMWFhNTA1NWE2M2VhNmI4YmE2NTZlMmI0YjgxZmNjYWI1M2MzZTIxMDhkODBiODFjIn19fQ==");
    public static ItemStack AUTOMATED_VANILLA_CRAFTING_CHAMBER = new CustomItem(new MaterialData(Material.WORKBENCH), "&eAutomated Vanilla Crafting Chamber", "", "&6Advanced Machine", "&8\u21E8 &e\u26A1 &710 J/Item");
    public static ItemStack ANCIENT_ALTAR_CRAFTER = new CustomItem(ANCIENT_ALTAR_CRAFTER_BLOCK, "&6Ancient Altar Crafter", "", "&6Advanced Machine", "&8\u21E8 &e\u26A1 &750 J/Item");
    public static ItemStack AUTOMATED_ANCIENT_ALTAR_CRAFTER = new CustomItem(new MaterialData(Material.WORKBENCH), "&6Automated Ancient Altar Crafter", "", "&6Advanced Machine", "&8\u21E8 &e\u26A1 &750 J/Item");
    public static ItemStack THERMAL_GENERATOR;
    public static ItemStack ELECTRIC_COBBLE_TO_DUST;
    public static ItemStack ELECTRIC_COBBLE_TO_INGOT;
    public static ItemStack ELECTRIC_LUCKY_BLOCK_FACTORY;
    public static ItemStack ELECTRIC_LUCKY_BLOCK_GRINDER;

    static {
        try {
            ZeroLuckyBlock = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjNiNzEwYjA4YjUyM2JiYTdlZmJhMDdjNjI5YmEwODk1YWQ2MTEyNmQyNmM4NmJlYjM4NDU2MDNhOTc0MjZjIn19fQ=="), "&rLucky Block", new String[]{"&7Luck: &r0"});
            LuckyBlock = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjNiNzEwYjA4YjUyM2JiYTdlZmJhMDdjNjI5YmEwODk1YWQ2MTEyNmQyNmM4NmJlYjM4NDU2MDNhOTc0MjZjIn19fQ=="), "&rVery lucky Block", new String[]{"&7Luck: &a+80"});
            UnLuckyBlock = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjNiNzEwYjA4YjUyM2JiYTdlZmJhMDdjNjI5YmEwODk1YWQ2MTEyNmQyNmM4NmJlYjM4NDU2MDNhOTc0MjZjIn19fQ=="), "&rVery unlucky Block", new String[]{"&7Luck: &c-80"});
            PandorasBox = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODZjN2RkZTUxMjg3MWJkNjA3Yjc3ZTY2MzVhZDM5ZjQ0ZjJkNWI0NzI5ZTYwMjczZjFiMTRmYmE5YTg2YSJ9fX0="), "&5Pandora\'s Box", new String[]{"&7Luck: &c&oERROR"});
            ELECTRIC_COBBLE_TO_DUST = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTU0OTQ3ZGU3ZjUyNTk4MjU1ZDZhZmVlOWQ3N2JlZmFkOWI0ZjI0YzBjNDY2M2QyOGJjZGY4YTY0NTdmMzQifX19"), "&3Electric Cobble to Dust", "", "&4End-Game Machine", "&6Has a small changes of Lucky and Eclispse Nuggets", "&8\u21E8 &7Speed: 2x", "&8\u21E8 &e\u26A1 &720 J/s");
            ELECTRIC_COBBLE_TO_INGOT = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzRkYmNiN2UxZmJlY2VhOWE3MzUwNDM2Y2JiZWEyYjQ5NmY3NGMyOTcyMDRmMWJiOWFjYzM4NzhkNTQyY2NiIn19fQ=="), "&3Electric Cobble to Ingot", "", "&4End-Game Machine", "&6Has a small changes of Lucky and Eclispse Ingots", "&8\u21E8 &7Speed: 2x", "&8\u21E8 &e\u26A1 &730 J/s");
            ELECTRIC_LUCKY_BLOCK_FACTORY = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmI1ZDhiOWEzYTk0MjFkY2VkYjE3ZDcxZTNhODg0ZDk1ZWM1MDM4YzgzOGNlMTllZDZkOGU5NmM1YjIzZWQ3In19fQ=="), "&3Electric Lucky Block Factory", "", "&4End-Game Machine", "&6Will take any Gold Ingot", "&8\u21E8 &7Speed: 1x", "&8\u21E8 &e\u26A1 &725 J/s");
            ELECTRIC_LUCKY_BLOCK_GRINDER = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWFkNTQyNGQ5OTAzOTUzODQzNTI2YTdjNDE2ODY2ZTdkNzk1MDFjODhjZTdjZGFiZWVlNTI4NGVhMzlmIn19fQ=="), "&3Electric Lucky Block Grinder", "", "&4End-Game Machine", "&6Will almost anything Lucky or Not", "&8\u21E8 &7Speed: 1x", "&8\u21E8 &e\u26A1 &730 J/s");
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

}

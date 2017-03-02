package com.firesoftitan.play.fmtablist.slimefun;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.MenuItem;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SeasonCategory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Daniel on 12/28/2016.
 */
public class CustomCategories
{
    public static final Category SLIMEFUN_TITAN = new Category(new CustomItem(new ItemStack(Material.NETHER_WART_BLOCK), "&5Magic", new String[] { "", "&a >Click to open" }), 5);
    public static final Category SLIMEFUN_RESOURCES = new Category(new CustomItem(new ItemStack(Material.GOLD_INGOT), "&5Resources", new String[] { "", "&a >Click to open" }), 5);
    public static final SeasonCategory SLIMEFUN_BLANK = new SeasonCategory(18, 0, new MenuItem(Material.NETHER_STAR, "TRASH", 0, ChatColor.translateAlternateColorCodes('&', "&chelp &aSanta")));
    public static final Category SLIMEFUN_LUCKY = new Category(new CustomItem(new ItemStack(Material.ENCHANTMENT_TABLE), "&5Lucky Gear", new String[] { "", "&a >Click to open" }), 5);
    public static final Category SLIMEFUN_ECLIPSE = new Category(new CustomItem(new ItemStack(Material.ARMOR_STAND), "&bEclipse Gear", new String[] { "", "&a >Click to open" }), 5);
    public static final Category SLIMEFUN_TITAN_GEAR = new Category(new CustomItem(new ItemStack(Material.BLUE_SHULKER_BOX), "&4Titan Gear", new String[] { "", "&a >Click to open" }), 5);

}

package com.firesoftitan.play.fmtablist.timers;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.firesoftitan.play.fmtablist.FMTabList;
import com.firesoftitan.play.fmtablist.listeners.GPREListener;
import me.mrCookieSlime.CSCoreLibPlugin.general.World.CustomSkull;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

/**
 * Created by Daniel on 12/20/2016.
 */
public class mainBrain implements Runnable {
    private GPREListener tmp;
    public  mainBrain (GPREListener Listme)
    {
        tmp = Listme;

    }

    @Override
    public void run() {
        try {

            bossWorld();

            LuckBlockTime();

            EnvoyTime();

            VoteTime();

            TPAKiller();

            ReBirth();

            tabListUpdater();


        }
        catch (Exception e)
        {
            System.out.println("FMT: Error in Loop");
            e.printStackTrace();
        }
    }

    private void tabListUpdater() {

        tmp.Ticks++;
        for (Player p : Bukkit.getOnlinePlayers()) {
            tmp.setTabText(p);
        }
        if (tmp.Ticks >= 14)
        {
            tmp.Ticks = 0;
        }
    }

    private void bossWorld() {
        Random luck = new Random(System.currentTimeMillis());
        String worldtoSpawn = "bossworld";;
        for (Player p : Bukkit.getOnlinePlayers()) {;
            if (!p.hasPermission("fly.fly"))
            {
                p.setFlying(false);
                p.setAllowFlight(false);
            }
            else
            {
                if (p.hasPermission("FMTabList.email") && p.getAllowFlight() == false) {
                    p.setAllowFlight(true);
                }
            }
            if (p.getInventory().getItemInMainHand() != null)
            {

                if (p.getInventory().getItemInMainHand().getItemMeta() != null)
                {

                    if (p.getInventory().getItemInMainHand().getItemMeta().getLore() != null) {
                        ItemMeta iM = p.getInventory().getItemInMainHand().getItemMeta();
                        List<String> loreList = iM.getLore();
                        //"ºrSplash Potion of º4ºlDEATH"
                        for (int i = 0; i < loreList.size(); i++) {
                            if (loreList.get(i).equals(ChatColor.AQUA + "Titan." + ChatColor.DARK_GREEN + "Undroppable")) {
                                loreList.set(i, ChatColor.AQUA + "Titan." + ChatColor.DARK_GREEN + "Undroppable " + ChatColor.GOLD + p.getName());
                            }
                            if (loreList.get(i).startsWith(ChatColor.AQUA + "Titan:"))
                            {
                                loreList.set(i, loreList.get(i).replace("Titan:", "Titan."));
                            }
                        }
                        iM.setLore(loreList);
                        p.getInventory().getItemInMainHand().setItemMeta(iM);
                    }
                    if (p.getInventory().getItemInMainHand().getType() == Material.SKULL_ITEM)
                    {
                        SkullMeta ISCS =  (SkullMeta)p.getInventory().getItemInMainHand().getItemMeta();
                        if (ISCS != null) {
                            if (ISCS.getOwner() != null) {
                                if (ISCS.getOwner().equalsIgnoreCase("cscorelib") && ISCS.hasDisplayName() == false) {
                                    String texture = CustomSkull.getTexture(p.getInventory().getItemInMainHand());
                                    SlimefunItem SFI = SlimefunItem.getByTexture(texture);
                                    if (SFI != null) {
                                        ItemStack toReplace = SFI.getItem().clone();
                                        toReplace.setAmount(p.getInventory().getItemInMainHand().getAmount());
                                        p.getInventory().setItemInMainHand(toReplace);
                                        p.sendMessage(ChatColor.LIGHT_PURPLE + "Your Slimefun block was fixed!");
                                    }
                                }
                            }
                        }
                    }
                    if (p.getInventory().getItemInMainHand().getItemMeta().hasDisplayName())
                    {
                        if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().startsWith(ChatColor.AQUA + "" +ChatColor.BOLD + "Titan:"))
                        {
                            ItemStack tmpY =  p.getInventory().getItemInMainHand().clone();
                            ItemMeta tmpMet = tmpY.getItemMeta();
                            tmpMet.setDisplayName(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().replace(ChatColor.AQUA + "" +ChatColor.BOLD + "Titan:", ChatColor.AQUA + "" +ChatColor.BOLD + "Titan."));
                            tmpY.setItemMeta(tmpMet);
                            p.getInventory().setItemInMainHand(tmpY.clone());
                        }
                    }
                }
            }

            if (!p.hasPermission("FMTabList.email")) {
                hackerCheck(p);
            }

            Claim claim = GriefPrevention.instance.dataStore.getClaimAt(p.getLocation(), false, null);
            if (claim != null || FMTabList.plugin.protectedList.contains(p.getUniqueId())) {
                Collection<PotionEffect> tmpAPR = p.getActivePotionEffects();
                for (PotionEffect APR : tmpAPR) {
                    if (claim != null ) {
                        if (!claim.isAdminClaim() || FMTabList.plugin.protectedList.contains(p.getUniqueId())) {
                            if ((APR.getType().equals(PotionEffectType.DAMAGE_RESISTANCE) || APR.getType().equals(PotionEffectType.BLINDNESS) || APR.getType().equals(PotionEffectType.CONFUSION) || APR.getType().equals(PotionEffectType.HARM) || APR.getType().equals(PotionEffectType.POISON) || APR.getType().equals(PotionEffectType.SLOW) || APR.getType().equals(PotionEffectType.UNLUCK) || APR.getType().equals(PotionEffectType.WEAKNESS) || APR.getType().equals(PotionEffectType.WITHER))) {
                                p.removePotionEffect(APR.getType());

                            }
                            if (p.getFireTicks() > 0)
                            {
                                p.setFireTicks(0);
                            }
                        }
                    }
                    else if (FMTabList.plugin.protectedList.contains(p.getUniqueId())) {
                        if ((APR.getType().equals(PotionEffectType.DAMAGE_RESISTANCE) || APR.getType().equals(PotionEffectType.BLINDNESS) || APR.getType().equals(PotionEffectType.CONFUSION) || APR.getType().equals(PotionEffectType.HARM) || APR.getType().equals(PotionEffectType.POISON) || APR.getType().equals(PotionEffectType.SLOW) || APR.getType().equals(PotionEffectType.UNLUCK) || APR.getType().equals(PotionEffectType.WEAKNESS) || APR.getType().equals(PotionEffectType.WITHER))) {
                            p.removePotionEffect(APR.getType());;
                        }
                        if (p.getFireTicks() > 0)
                        {
                            p.setFireTicks(0);
                        }
                            p.setHealth(20);
                    }
                }
            }


            for (int u = 0; u< tmp.tpaprocter.size(); u++)
                {
                    if (p.getUniqueId().equals(tmp.tpaprocter.get(u).usernameA) || p.getUniqueId().equals(tmp.tpaprocter.get(u).usernameB))
                    {
                        Long timeleft = System.currentTimeMillis() - tmp.tpaprocter.get(u).timetp;
                        timeleft = timeleft /1000;
                        timeleft = 30 - timeleft;
                        if (tmp.tpaprocter.get(u).protect == true) {
                            if (timeleft < 0) {
                                tmp.tpaprocter.remove(u);
                                return;
                            }
                            ActionBarAPI.sendActionBar(p, ChatColor.RED + "PVP disabled for " + ChatColor.WHITE + timeleft + ChatColor.RED + " more seconds.");
                        }

                    }
                }
        }
    }

    private void hackerCheck(Player p) {
       /* if (p.getGameMode() == GameMode.SURVIVAL) {
            if (p.getActivePotionEffects() != null) {
                Collection<PotionEffect> tmpAPR = p.getActivePotionEffects();
                for (PotionEffect APR : tmpAPR) {
                    if (APR.getAmplifier() > 12) {
                        punisPlayer(p,  "POSTION\n" + "postion: " + APR.getAmplifier() + "," + APR.getType().toString()  +"\n" + "player: " + p.getName());
                    }

                }
            }
        }*/
        if (p.getInventory().getItemInMainHand() != null)
        {
            if (p.getInventory().getItemInMainHand().getItemMeta() != null)
            {
                if (p.getInventory().getItemInMainHand().getItemMeta().hasDisplayName())
                {
                    //"ºrSplash Potion of º4ºlDEATH"
                    if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(ChatColor.RESET +  "Splash Potion of " +ChatColor.DARK_RED + ChatColor.BOLD +"DEATH"))
                    {
                        punisPlayer(p, "DEATH\n" + "player: " + p.getName() + "\n" + "Item: " + p.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
                    }

                }
                if (p.getInventory().getItemInMainHand().getItemMeta().hasEnchants()) {
                    if (p.getInventory().getItemInMainHand().getItemMeta().getEnchants() != null) {
                        Map<Enchantment, Integer> tmpEnch = p.getInventory().getItemInMainHand().getItemMeta().getEnchants();
                        for (Enchantment key : tmpEnch.keySet()) {
                            if (tmpEnch.get(key) > 20) {
                                punisPlayer(p, "Enchant\n" + "player: " + p.getName() + "\n" + "Item: " + p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() + "\n" + "Enchant: " + key + "," + tmpEnch.get(key));
                                break;
                            }
                        }
                    }
                }

            }

        }
        if (p.getInventory().getItemInOffHand() != null)
        {
            if (p.getInventory().getItemInOffHand().getItemMeta() != null) {
                if (p.getInventory().getItemInOffHand().getItemMeta().hasDisplayName()) {
                    //"ºrSplash Potion of º4ºlDEATH"
                    if (p.getInventory().getItemInOffHand().getItemMeta().getDisplayName().contains(ChatColor.RESET + "Splash Potion of " + ChatColor.DARK_RED + ChatColor.BOLD + "DEATH")) {
                        punisPlayer(p,  "DEATH\n" + "Item: " + p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() +"\n" + "player: " + p.getName());
                    }

                }

                if (p.getInventory().getItemInOffHand().getItemMeta().hasEnchants()) {
                    if (p.getInventory().getItemInOffHand().getItemMeta().getEnchants() != null) {
                        Map<Enchantment, Integer> tmpEnch = p.getInventory().getItemInOffHand().getItemMeta().getEnchants();
                        for (Enchantment key : tmpEnch.keySet()) {
                            if (tmpEnch.get(key) > 50) {
                                punisPlayer(p, "Enchant\n" + "Item: " + p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() + "\n" + "Enchant: " + key + "," + tmpEnch.get(key) +"\n" + "player: " + p.getName());
                                break;
                            }
                        }
                    }
                }
            }

        }
    }

    private void punisPlayer(Player p, String message) {
        p.setLevel(0);
        p.getInventory().clear();
        Collection<PotionEffect> tmpAPR = p.getActivePotionEffects();
        for(PotionEffect APR: tmpAPR)
        {
            p.removePotionEffect(APR.getType());
        }
        p.kickPlayer("You have been KICK for using a banned item from a hacked client!!! an email has been sent to server admin.");
        FMTabList.plugin.sendMail("HACKER NOTICE", message);
    }

    private void ReBirth()
    {

        for (UUID player: FMTabList.plugin.playerListSaves.keySet()) {
            Player Dieing=  Bukkit.getPlayer(player);
            if (Dieing != null) {
                if (Dieing.isDead() == false) {
                    PlayerInventory CheckInv = Dieing.getInventory();
                    ItemStack[] tmpSave = FMTabList.plugin.playerListSaves.get(Dieing.getUniqueId());
                    boolean firstStone = false;
                    for (int i = 0; i < CheckInv.getSize(); i++) {
                        if (FMTabList.plugin.checkforTitanStone(tmpSave[i]) && firstStone == false)
                        {
                            firstStone = true;
                            if (tmpSave[i].getAmount() > 1)
                            {
                                tmpSave[i].setAmount(tmpSave[i].getAmount() - 1);
                                CheckInv.setItem(i, tmpSave[i]);
                            }
                        }
                        else {
                            CheckInv.setItem(i, tmpSave[i]);
                        }
                    }
                    FMTabList.plugin.playerListSaves.remove(Dieing.getUniqueId());
                    break;
                }
            }
        }


    }

    private void VoteTime() {
        if (System.currentTimeMillis() - FMTabList.plugin.vtime > 1000 *60 * 22)
        {
            FMTabList.plugin.vtime = System.currentTimeMillis();
            if (FMTabList.plugin.voter.getProperty("votequestion") != null) {
                if (!((String)FMTabList.plugin.voter.getProperty("votequestion")).equalsIgnoreCase("off ")) {
                    Bukkit.broadcastMessage("" + ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "It's Vote Time !!!!");
                    Bukkit.broadcastMessage("" + ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Type /Yes or /No");
                    Bukkit.broadcastMessage(ChatColor.GOLD + "");
                    Bukkit.broadcastMessage("" + ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Question" + ChatColor.GOLD + "] " + ChatColor.GREEN + (String) FMTabList.plugin.voter.getProperty("votequestion"));
                    Bukkit.broadcastMessage("" + ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Results" + ChatColor.GOLD + "] " + ChatColor.RED + ChatColor.BOLD + "Yes: " + ChatColor.WHITE + (int) FMTabList.plugin.voter.getProperty("voteyes") + ChatColor.RED + ChatColor.BOLD + " No: " + ChatColor.WHITE + (int) FMTabList.plugin.voter.getProperty("voteno"));
                }
            }
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lagg killmobs");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lagg clear");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lagg unloadchunks");
        }

    }
    private void EnvoyTime() {
        Random luck;
        long totalTime = 1000 * 60 * 60 * 3;
        if (System.currentTimeMillis() - FMTabList.plugin.evtime > totalTime - 5 * 1000 && FMTabList.plugin.evcount == 2) {
            FMTabList.plugin.evcount++;
            Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.RED + ChatColor.BOLD  + "Envoy in " + ChatColor.WHITE + "5 Seconds");
        }
        if (System.currentTimeMillis() - FMTabList.plugin.evtime > totalTime - 10 * 1000 && FMTabList.plugin.evcount == 1) {
            FMTabList.plugin.evcount++;
            Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.RED + ChatColor.BOLD  + "Envoy in " + ChatColor.WHITE + "10 Seconds");
        }
        if (System.currentTimeMillis() - FMTabList.plugin.evtime > totalTime - 30 * 1000 && FMTabList.plugin.evcount == 0) {
            FMTabList.plugin.evcount++;
            Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.RED + ChatColor.BOLD  + "Envoy in" + ChatColor.WHITE + "30 Seconds");
        }

        if (System.currentTimeMillis() - FMTabList.plugin.evtime > totalTime) {
            FMTabList.plugin.evtime = System.currentTimeMillis();
            FMTabList.plugin.evcount = 0;
            Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.RED + ChatColor.BOLD  + "Envoy is NOW!!!!");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "envoy start");
        }
    }
    private void LuckBlockTime() {
        Random luck;
        if (System.currentTimeMillis() - FMTabList.plugin.lbtime > 1000 *60 *60)
        {
            FMTabList.plugin.lbtime = System.currentTimeMillis();
            FMTabList.plugin.ranonce = 6;
            Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.RED + ChatColor.BOLD  + "It's Lucky Block Time !!!!");
            Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " +ChatColor.LIGHT_PURPLE + ChatColor.BOLD  + "Clear 4 Slots Now !!!");
            Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " +ChatColor.DARK_PURPLE + ChatColor.BOLD  + "Leave Parkour Now !!!!");
            if (Bukkit.getOnlinePlayers().size() >= 23)
            {
                Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " +ChatColor.GREEN + "Double Items has been unlocked, You have a 100% chance of getting this!");
            }
            else
            {
                Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " +ChatColor.GOLD + "Double Items locked, 23 players needed!");
            }
            if (Bukkit.getOnlinePlayers().size() >= 17)
            {
                Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " +ChatColor.GREEN + "Titan Books has been unlocked, You have a 5% chance of getting this!");
            }
            else
            {
                Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " +ChatColor.GOLD + "Titan Books locked, 17 players needed!");
            }
            if (Bukkit.getOnlinePlayers().size() >= 13)
            {
                Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " +ChatColor.GREEN + "Pandoras Box has been unlocked, You have a 20% chance of getting this!");
            }
            else
            {
                Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " +ChatColor.GOLD + "Pandoras Box locked, 13 players needed!");
            }
            if (Bukkit.getOnlinePlayers().size() >= 10)
            {
                Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " +ChatColor.GREEN + "Money Keys has been unlocked, You have a 25% chance of getting this!");
            }
            else
            {
                Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " +ChatColor.GOLD + "Money Keys locked, 10 players needed!");
            }
        }
        if (System.currentTimeMillis() - FMTabList.plugin.lbtime > 10000 && FMTabList.plugin.ranonce > 1 &&Bukkit.getOnlinePlayers().size() < 7)
        {
            Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " +ChatColor.GOLD + "Lucky Blocks Canceled, not enough players, 7 or more needed!");
            FMTabList.plugin.ranonce = 1;
        }

        if (System.currentTimeMillis() - FMTabList.plugin.lbtime > 10000 && FMTabList.plugin.ranonce > 1 && Bukkit.getOnlinePlayers().size() >= 7)
        {
            FMTabList.plugin.ranonce--;
            Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " +ChatColor.GREEN + "Lucky Blocks In " + FMTabList.plugin.ranonce);
            if (FMTabList.plugin.ranonce == 1) {
                luck = new Random(System.currentTimeMillis());

                for (Player p : Bukkit.getOnlinePlayers()) {
                    int i =0;
                    int amount = luck.nextInt(5);

                    if (amount < 1)
                    {
                        amount = 1;
                    }
                    if (luck.nextInt(100) > 80 && Bukkit.getOnlinePlayers().size() >= 13) {
                        i++;
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sf give " + p.getName() + " PANDORAS_BOX");
                        if (Bukkit.getOnlinePlayers().size() >= 23)
                        {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sf give " + p.getName() + " PANDORAS_BOX");
                        }
                    }
                    if (luck.nextInt(100) > 85) {
                        i++;
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sf give " + p.getName() + " LUCKY_BLOCK_UNLUCKY");
                        if (Bukkit.getOnlinePlayers().size() >= 23)
                        {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sf give " + p.getName() + " LUCKY_BLOCK_UNLUCKY");
                        }
                    }
                    if (luck.nextInt(100) > 75 && Bukkit.getOnlinePlayers().size() >= 10) {
                        i++;

                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "crate GiveKey " +  p.getName() + " MoneyCrate " + amount);
                        if (Bukkit.getOnlinePlayers().size() >= 23)
                        {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "crate GiveKey " +  p.getName() + " MoneyCrate " + amount);
                        }
                        Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " +ChatColor.GOLD + p.getDisplayName() + " got Money Key(s)");
                    }
                    if (luck.nextInt(100) > 95 && Bukkit.getOnlinePlayers().size() >= 17)
                    {
                        i++;
                        int yy = luck.nextInt(3);
                        List<String> lore = new ArrayList<String>();
                        if (yy == 1 || yy == 0) {
                            lore.add(ChatColor.RED + "Soulbound");
                        }
                        if (yy == 2 || yy == 0) {
                            lore.add(ChatColor.DARK_PURPLE + "Unbreakable");
                        }
                        if (yy == 3 || yy == 0) {
                            lore.add(ChatColor.DARK_GREEN + "Undroppable");
                        }
                        ItemStack Soulbound = new ItemStack(Material.ENCHANTED_BOOK);
                        ItemMeta Soulboundmeta =  Soulbound.getItemMeta();
                        Soulboundmeta.setDisplayName(ChatColor.AQUA + "Titan's Enchanted Book");
                        Soulboundmeta.setLore(lore);
                        Soulbound.setItemMeta(Soulboundmeta);
                        if (Bukkit.getOnlinePlayers().size() >= 23)
                        {
                            Soulbound.setAmount(2);
                        }
                        int empty = p.getInventory().firstEmpty();
                        if (empty > -1)
                        {
                            p.getInventory().setItem(empty, Soulbound);
                        }
                        Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " +ChatColor.GOLD + p.getDisplayName() + " got Titan Book(s)");
                    }

                    if (luck.nextInt(100) > 30 || i == 0) {
                        i++;
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sf give " + p.getName() + " LUCKY_BLOCK_LUCKY");
                        if (Bukkit.getOnlinePlayers().size() >= 17)
                        {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sf give " + p.getName() + " LUCKY_BLOCK_LUCKY");
                        }
                    }

                }

            }
        }
    }

    private void TPAKiller() {
        for (int u = 0; u< tmp.tpaprocter.size(); u++)
        {
            if (System.currentTimeMillis() - tmp.tpaprocter.get(u).timetp > 30*1000 && tmp.tpaprocter.get(u).protect == true)
            {
                tmp.tpaprocter.remove(u);
                break;
            }
            if (System.currentTimeMillis() - tmp.tpaprocter.get(u).timetp > 300*1000 && tmp.tpaprocter.get(u).protect == false)
            {
                tmp.tpaprocter.remove(u);
                break;
            }
        }
    }


}

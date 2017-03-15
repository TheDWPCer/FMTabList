package com.firesoftitan.play.fmtablist.listeners;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.firesoftitan.play.fmtablist.FMTabList;
import com.firesoftitan.play.fmtablist.mystuff.teleportpro;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import me.BadBones69.envoy.api.Envoy;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GPREListener implements Listener {
    
    private FMTabList plugin;
	private long elptims = 0;
	private Essentials ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
	public long Ticks = 0;
	public List<teleportpro> tpaprocter= new ArrayList<teleportpro>();
	NBTTagCompound replacesMent = null;

	private ProtocolManager protocolManager;




    public GPREListener(FMTabList plugin){
        this.plugin = plugin;
		this.protocolManager = ProtocolLibrary.getProtocolManager();
    }

    public void registerEvents(){
        PluginManager pm = this.plugin.getServer().getPluginManager();
        pm.registerEvents(this, this.plugin);
    }
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event)
	{

		if (FMTabList.WhosFirst == true)
		{
			if (event.getMessage().toLowerCase().contains("welcome"))
			{

				FMTabList.WhosFirst = false;
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "broadcast &d>>>>>>>>>>>>>>>>>>>>>&6Reward&d<<<<<<<<<<<<<<<<<<<<<");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "broadcast &6" + event.getPlayer().getDisplayName() + "&d was the first one to welcome the new player and get's 1 inv key!");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "broadcast &d>>>>>>>>>>>>>>>>>>>>>&6Reward&d<<<<<<<<<<<<<<<<<<<<<");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "crate givekey " + event.getPlayer().getName() + " InventoryCrate 1");
			}
		}
	}
	@EventHandler
	public void onPlayerItemDamageEvent(PlayerItemDamageEvent event) {
		if (event.getItem().getItemMeta().getLore() != null) {
			List<String> loreList = event.getItem().getItemMeta().getLore();
			for (int i = 0; i < loreList.size(); i++) {
				if (loreList.get(i).equals(ChatColor.AQUA + "Titan." + ChatColor.DARK_PURPLE + "Unbreakable")) {
					event.setCancelled(true);
					Material mat = event.getItem().getType();
					short max = mat.getMaxDurability();
					event.getItem().setDurability((short)0);
				}
			}
		}
	}

	@EventHandler
	public void oneServerCommand(ServerCommandEvent event){
		plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {

				for(Player p : Bukkit.getOnlinePlayers()) {
					setTabText(p);
				}
			}
		},2L);
	}




	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (FMTabList.plugin.checkforTitanStone(event.getItemInHand()))
		{
			event.setCancelled(true);
			if (event.getPlayer() != null) {
				event.getPlayer().sendMessage(ChatColor.RED + "[WARNING]: " + ChatColor.GOLD + "The Titan Stone Can't Be Placed On The Ground It Would Truely Kill Us All!!!");
			}
		}

	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

		if (!event.isCancelled()) {
			if (event.getDamager() != null) {
				if (event.getDamager() instanceof Player) {
					boolean shoulCancel = checkProtection(((Player) event.getDamager()));
					event.setCancelled(shoulCancel);
				}
			}
			if (event.getEntity() != null) {
				if (event.getEntity() instanceof Player) {
					boolean shoulCancel = checkProtection((Player) event.getEntity());
					event.setCancelled(shoulCancel);
				}
			}
		}
	}

	private boolean checkProtection(Player A ) {
		for(int i = 0; i<tpaprocter.size();i++) {
            teleportpro tmp = tpaprocter.get(i);
            if (System.currentTimeMillis() - tmp.timetp < 30*1000  && tmp.protect)
            {
                if ( tmp.usernameA.equals(A.getUniqueId()) || tmp.usernameB.equals(A.getUniqueId()))
                {
                    if (A != null) {

                        Long timeleft = System.currentTimeMillis() - tmp.timetp;
                        timeleft = timeleft /1000;
						timeleft = 30 - timeleft;
                        A.sendMessage(ChatColor.GREEN +"If someone is trying to kill you type "  + ChatColor.WHITE + "/spawn" + ChatColor.RED +  " There is " + ChatColor.UNDERLINE + "NO TPA Killing rule.");
                        return true;
                    }
                }
            }
        }
		return false;
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity()  != null) {
			if (event.getEntity() instanceof Player) {
				Player Dieing = (Player) event.getEntity();
				PlayerInventory CheckInv = Dieing.getInventory();
				Boolean saving = false;;
				if (Dieing.getGameMode().equals(GameMode.CREATIVE))
				{;
					Dieing.setLevel(0);
					Dieing.getInventory().clear();

					Dieing.kickPlayer("What happened???? You've been reported!");
					FMTabList.plugin.sendMail("HACKER NOTICE", "Death-player: " + Dieing.getName());
					return;
				}
				for (int i = 0; i < CheckInv.getSize(); i++) {
					if (FMTabList.plugin.checkforTitanStone(CheckInv.getItem(i))) {
						event.getDrops().clear();
						saving = true;
						break;
					}
				}
				if (saving == true) {
					ItemStack[] tmpSave = new ItemStack[CheckInv.getSize()];
					for (int i = 0; i < CheckInv.getSize(); i++) {
						if (CheckInv.getItem(i) != null) {
							tmpSave[i] = CheckInv.getItem(i).clone();
						}
					}
					FMTabList.plugin.playerListSaves.put(Dieing.getUniqueId(), tmpSave);
				}


			}
		}
	}
		@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event){


		if (event.getPlayer() != null) {





			if (event.getPlayer().getGameMode() != null && event.getPlayer().hasPermission("FMTabList.email")) {
				if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
					if (event.getMessage().startsWith("/guide") || event.getMessage().startsWith("/sf guide")) {
						event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "Server" + ChatColor.GOLD + "] " + ChatColor.RED + "Can't use Slimefun in creative!");
						event.setCancelled(true);
					}
				}
			}
			if (event.getMessage().startsWith("/helper") && event.getPlayer().hasPermission("FMTabList.email"))
			{

					if (FMTabList.plugin.protectedList.contains(((Player) event.getPlayer()).getUniqueId()))
					{
						FMTabList.plugin.protectedList.remove(((Player) event.getPlayer()).getUniqueId());
						event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " +ChatColor.GREEN + "You are no logger protected!");
					}
					else
					{
						FMTabList.plugin.protectedList.add(((Player) event.getPlayer()).getUniqueId());
						event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " +ChatColor.GREEN + "You are protected!");
					}
				event.setCancelled(true);

			}
			if (event.getMessage().startsWith("/t") || event.getMessage().startsWith("/w") || event.getMessage().startsWith("/msg") || event.getMessage().startsWith("/m") || event.getMessage().startsWith("/tell") || event.getMessage().startsWith("/whisper") || event.getMessage().startsWith("/msg"))
			{
				if (!event.getPlayer().hasPermission("FMTabList.email")) {
					String[] args = event.getMessage().split(" ");
					if (args.length > 1) {
						Player player = Bukkit.getPlayer(args[1]);
						if (player != null) {
							if (player.hasPermission("FMTabList.email"))
							{
								event.setCancelled(true);
							event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "Server" + ChatColor.GOLD + "]:" + ChatColor.RED + " You can't whisper mods, staff, admins, or owner. Please use /mail send name or use /r to reply to them." ); //Please use " +ChatColor.GOLD + "/ts ticket"
								player.sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "Server" + ChatColor.GOLD + "]: " + ChatColor.GRAY +  event.getPlayer().getName() + ", tried to whisper you and was blocked! He was told to open a ticket. He can use /r to reply to you." );
								player.sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "Server" + ChatColor.GOLD + "]msg: " + ChatColor.GRAY + event.getMessage());
							}
						}

					}
				}
			}
			if (event.getMessage().equalsIgnoreCase("/yes") && FMTabList.plugin.voter.getProperty(event.getPlayer().getUniqueId().toString()) == null)
			{
				if (FMTabList.plugin.voter.getProperty("voteyes") == null)
				{
					FMTabList.plugin.voter.setProperty("voteyes", 0);
					FMTabList.plugin.voter.save();
				}
				int count = (int)FMTabList.plugin.voter.getProperty("voteyes");
				count++;
				FMTabList.plugin.voter.setProperty("voteyes", count);
				FMTabList.plugin.voter.setProperty(event.getPlayer().getUniqueId().toString(), true);
				FMTabList.plugin.voter.save();
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + " thank you for your vote!");
			}
			else
			{
				if (event.getMessage().equalsIgnoreCase("/yes"))
				{
					event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + " You can only vote once for this question!");
					event.setCancelled(true);
				}
			}
			if (event.getMessage().equalsIgnoreCase("/no") && FMTabList.plugin.voter.getProperty(event.getPlayer().getUniqueId().toString()) == null)
			{
				if (FMTabList.plugin.voter.getProperty("voteno") == null)
				{
					FMTabList.plugin.voter.setProperty("voteno", 0);
					FMTabList.plugin.voter.save();
				}
				int count = (int)FMTabList.plugin.voter.getProperty("voteno");
				count++;
				FMTabList.plugin.voter.setProperty("voteno", count);
				FMTabList.plugin.voter.setProperty(event.getPlayer().getUniqueId().toString(), true);
				FMTabList.plugin.voter.save();
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + " thank you for your vote!");
			}
			else
			{
				if (event.getMessage().equalsIgnoreCase("/no"))
				{
					event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + " You can only vote once for this question!");
					event.setCancelled(true);
				}
			}



			if (event.getPlayer().getGameMode() != GameMode.SURVIVAL) {
				if (event.getMessage().startsWith("/echest") || event.getMessage().startsWith("/ec")) {
					event.setCancelled(true);
					event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + "Can only use echest in survival!");
				}
			}
			if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
				if (event.getMessage().startsWith("/tpa") || event.getMessage().startsWith("/tpask") || event.getMessage().startsWith("/tpc") || event.getMessage().startsWith("/tpahere") || event.getMessage().startsWith("/tpchere"))
				{

					String[] args = event.getMessage().split(" ");
					if (args.length > 1) {
						if (Bukkit.getPlayer(args[1]) != null) {
							teleportpro tmp = new teleportpro(event.getPlayer().getUniqueId(), Bukkit.getPlayer(args[1]).getUniqueId(), System.currentTimeMillis());
							tpaprocter.add(tmp);
						}
					}
				}
				if (event.getMessage().equalsIgnoreCase("/tpaccept") || event.getMessage().startsWith("/tpaccept") || event.getMessage().startsWith("/tpyes") || event.getMessage().equalsIgnoreCase("/tpyes"))
				{
					for(int i = 0; i<tpaprocter.size();i++)
					{
						teleportpro tmp = tpaprocter.get(i);
						if (tmp.usernameB.equals(event.getPlayer().getUniqueId()))
						{
							tmp.protect = true;
							tmp.timetp = System.currentTimeMillis();
							tpaprocter.remove(i);
							tpaprocter.add(tmp);
							Player A = Bukkit.getPlayer(tmp.usernameA);
							Player B = Bukkit.getPlayer(tmp.usernameB);
							if (A != null)
							{
								A.sendMessage(ChatColor.RED + "You are protected from damage for 30 seconds.");
							}
							if (B != null)
							{
								B.sendMessage(ChatColor.RED + "You are protected from damage for 30 seconds.");
							}
							break;
						}
					}
				}
				if (event.getMessage().equalsIgnoreCase("/tpdeny") || event.getMessage().startsWith("/tpdeny") || event.getMessage().startsWith("/tpno") || event.getMessage().equalsIgnoreCase("/tpno"))
				{
					for(int i = 0; i<tpaprocter.size();i++) {
						teleportpro tmp = tpaprocter.get(i);
						if (tmp.usernameB.equals(event.getPlayer().getUniqueId())) {
							tpaprocter.remove(i);
							break;
						}
					}
				}
			}
		}


		plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					setTabText(p);
				}
			}
		},2L);


	}
	@EventHandler
	public void onJoin(PlayerJoinEvent event){

		Player p = event.getPlayer();

		User user= ess.getUser(p);
		user.setAfk(false);
		setTabText(p);

		PacketContainer pc = this.protocolManager.createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);

		pc.getChatComponents().write(0, WrappedChatComponent.fromText(replaceColors("&c&lFires &a&lOf &b&lTitan")))
				.write(1, WrappedChatComponent.fromText(replaceColors("&2Owner: &6Freethemice" )));
		try
		{
			this.protocolManager.sendServerPacket(event.getPlayer(), pc);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if(!event.getPlayer().hasPlayedBefore()) {
			FMTabList.WhosFirst = true;
		}

	}
	private String replaceColors(String input)
	{
		ChatColor[] allcolors = ChatColor.values();
		for(int i = 0; i < allcolors.length;i++)
		{
			String Colorcode =allcolors[i].toString().substring(1);

			input = input.replace("&" + Colorcode, "" + ChatColor.getByChar(Colorcode));
		}
		return input;
	}

	public void setTabText(Player p) {


		User user = ess.getUser(p);
		String prefix = ess.getPermissionsHandler().getPrefix(p);
		String suffix = ess.getPermissionsHandler().getSuffix(p);
		prefix = replaceColors(prefix);
		prefix = prefix.replace("[", "");
		prefix = prefix.replace("]", "");
		prefix = prefix.replace(" ", "");
		String nick = user.getNickname();

		String tabName ="";
		tabName = tabName + ChatColor.GOLD + "[" + ChatColor.RESET + returnAlias(p.getWorld()) + ChatColor.GOLD + "]";
		tabName = tabName + ChatColor.GOLD + "[" + ChatColor.WHITE + prefix + ChatColor.GOLD + "]";


		if (user.getMuted() || GriefPrevention.instance.dataStore.isSoftMuted(p.getUniqueId())) {
			tabName = ChatColor.GOLD + "[" + ChatColor.GRAY + "MUTED" + ChatColor.GOLD + "]" + tabName;
			tabName = tabName + ChatColor.GRAY + ChatColor.STRIKETHROUGH + p.getName();
		} else if (user.isJailed()) {
			tabName = ChatColor.GOLD + "[" + ChatColor.GRAY + "JAILED" + ChatColor.GOLD + "]" + tabName;
			tabName = tabName + ChatColor.GRAY + ChatColor.STRIKETHROUGH + p.getName();
		} else if (user.isAfk()) {
			tabName = ChatColor.GOLD + "[" + ChatColor.GRAY + "AFK" + ChatColor.GOLD + "]" + tabName;
			tabName = tabName + ChatColor.GRAY + p.getName();
		} else if (user.isVanished() && !p.hasPermission("FMTabList.email")) {
			tabName = ChatColor.GOLD + "[" + ChatColor.BLUE + "VANISHED" + ChatColor.GOLD + "]" + tabName;
			tabName = tabName + ChatColor.BLUE + p.getName();
		} else if (user.isHidden() && !p.hasPermission("FMTabList.email")) {
			tabName = ChatColor.GOLD + "[" + ChatColor.RED + "HIDDEN" + ChatColor.GOLD + "]" + tabName;
			tabName = tabName + ChatColor.RED + p.getName();
		} else {
			tabName = tabName + ChatColor.WHITE + p.getName();
		}
		if (!suffix.equals(""))
		{
			tabName = tabName + ChatColor.translateAlternateColorCodes('&', suffix);
		}
		if (nick != null && !nick.equals("")) {
			tabName = tabName + ChatColor.GOLD + " AKA:" + ChatColor.WHITE + user.getNickname();
		}

/*
		if (Ticks < 10) {
			Help = ChatColor.GOLD + "<" + ChatColor.AQUA + "Party" + ChatColor.GOLD + ">";
			if (Parties.getInstance() != null) {
				if (Parties.getInstance().getPlayerHandler() != null) {
					if (Parties.getInstance().getPlayerHandler().getPartyFromPlayer(p) != null) {
						Party myParty = Parties.getInstance().getPlayerHandler().getPartyFromPlayer(p);
						tabName = tabName + ChatColor.GOLD + "<" + ChatColor.AQUA + myParty.getName() + ChatColor.GOLD + ">";
					}
				}
			}

		} else if (Ticks < 15) {
			Help = ChatColor.GOLD + "[" + ChatColor.WHITE + "Where" + ChatColor.GOLD + "]";
			tabName = tabName + ChatColor.GOLD + "[" + ChatColor.RED + returnAlias(p.getWorld().getName()) + ChatColor.GOLD + "]";
		}*/

		DateFormat df = new SimpleDateFormat("hh:mm:ss aa");
		Calendar calobj = Calendar.getInstance();
		String ServerTime = ChatColor.GREEN  + "Server: " + ChatColor.WHITE + df.format(calobj.getTime()).replace(" ", " " + ChatColor.GOLD);


		p.setPlayerListName(tabName);


		long TimeToBlocks = 1000 *60 *60 - (System.currentTimeMillis() - plugin.lbtime) ;
		int seconds = (int) (TimeToBlocks / 1000) % 60 ;
		int minutes = (int) ((TimeToBlocks / (1000*60)) % 60);
		int hours   = (int) ((TimeToBlocks / (1000*60*60)) % 24);


		long TimeToEnvoy = 1000 * 60 * 60 * 3 - (System.currentTimeMillis() - plugin.evtime) ;
		int Eseconds = (int) (TimeToEnvoy / 1000) % 60 ;
		int Eminutes = (int) ((TimeToEnvoy / (1000*60)) % 60);
		int Ehours   = (int) ((TimeToEnvoy / (1000*60*60)) % 24);


		String EnvoyTime = Ehours + ":" + Eminutes + ":" + Eseconds;
		if (Envoy.isEnvoyActive())
		{
			EnvoyTime = "Going Now!!!";
		}
		//EnvoyTime = EnvoyTime.replace("h, ", ":").replace("m, ", ":").replace("s", "");
		EnvoyTime = ChatColor.YELLOW + "Envoy Time: " + ChatColor.WHITE + EnvoyTime;
		PacketContainer pc = this.protocolManager.createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
		if (FMTabList.plugin.voter.getProperty("voteno") == null)
		{
			FMTabList.plugin.voter.setProperty("voteno", 0);
		}
		if (FMTabList.plugin.voter.getProperty("voteyes") == null)
		{
			FMTabList.plugin.voter.setProperty("voteyes", 0);
		}

		if (((String) FMTabList.plugin.voter.getProperty("votequestion")).equalsIgnoreCase("off "))
		{
			pc.getChatComponents().write(0, WrappedChatComponent.fromText(replaceColors("&c&lFires &a&lOf &b&lTitan") + "\n" + ChatColor.WHITE + Bukkit.getOnlinePlayers().size() + "/500" + "\n" + ServerTime + "\n")).write(1, WrappedChatComponent.fromText("\n" + ChatColor.RED + ChatColor.BOLD + "No vote at this time" + ChatColor.GREEN + "\nLucky Block Time: " + ChatColor.WHITE + hours + ":" + minutes + ":" + seconds + "\n" + EnvoyTime + replaceColors("\n&2Owner: &6Freethemice")));
		}
		else {
			pc.getChatComponents().write(0, WrappedChatComponent.fromText(replaceColors("&c&lFires &a&lOf &b&lTitan") + "\n" + ChatColor.WHITE + Bukkit.getOnlinePlayers().size() + "/500" + "\n" + ServerTime + "\n")).write(1, WrappedChatComponent.fromText("\n" + ChatColor.RED + ChatColor.BOLD + "Yes: " + ChatColor.WHITE + (int) FMTabList.plugin.voter.getProperty("voteyes") + ChatColor.RED + ChatColor.BOLD + " No: " + ChatColor.WHITE + (int) FMTabList.plugin.voter.getProperty("voteno") + ChatColor.GREEN + "\nLucky Block Time: " + ChatColor.WHITE + hours + ":" + minutes + ":" + seconds + "\n" + EnvoyTime + replaceColors("\n&2Owner: &6Freethemice")));

		}

		try
		{
			this.protocolManager.sendServerPacket(p, pc);
		} catch (Exception ex) {
			ex.printStackTrace();
		}


	}
	public String returnAlias(World w) {
		Plugin mv = Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
		if (mv == null) return w.getName();
		MultiverseCore mvc = (MultiverseCore) mv;
		MultiverseWorld mvw = mvc.getMVWorldManager().getMVWorld(w);
		if (mvw == null)
		{
			return ChatColor.GRAY + "Parkour";

		}
		try {
			return mvw.getColoredWorldString();
		}
		catch (Exception e)
		{
			return ChatColor.GRAY + "Parkour";
		}
	}
    
}

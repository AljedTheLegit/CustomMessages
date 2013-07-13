package org.thelegitsociety.cm;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
  public final Logger logger = Logger.getLogger("Minecraft");

	@Override
	public void onEnable() {
		PluginDescriptionFile desc = this.getDescription();
		PluginManager pm = this.getServer().getPluginManager();
		this.logger.info(desc.getName() + " Version: " + desc.getVersion()
				+ ", has been enabled!");
		pm.registerEvents(this, this);
		if (!getConfig().contains("CustomMessages.JoinMessage"))
			getConfig().set("CustomMessages.JoinMessage",
					"&f&lWelcome, &4&l%player% &f&lto the server!");
		if (!getConfig().contains("CustomMessages.LeaveMessage"))
			getConfig().set("CustomMessages.LeaveMessage",
					"&4&l%player% &f&lhas left the server!");
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile desc = this.getDescription();
		this.logger.info(desc.getName() + " Version: " + desc.getVersion()
				+ ", has been disabled!");
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		event.setJoinMessage(this.getConfig()
				.getString("CustomMessages.JoinMessage")
				.replaceAll("(&([a-z0-9]))", "\u00A7$2")
				.replace("%player%", player.getName()));
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.setQuitMessage(this.getConfig()
				.getString("CustomMessages.LeaveMessage")
				.replaceAll("(&([a-z0-9]))", "\u00A7$2")
				.replace("%player%", player.getName()));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("custommessages") || cmd.getName().equalsIgnoreCase("cm")) {
			if (args.length == 0) {
				PluginDescriptionFile desc = this.getDescription();
				sender.sendMessage("§6§l" + desc.getName() + " - AljedTheLegit" + " §eVersion: " + desc.getVersion());
				sender.sendMessage("§6Type /custommessages help or /cm help for help!");
			}
		}
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("help")) {
				if (sender.hasPermission("custommessages.help")) {
					sender.sendMessage("§6/CM help §4- §eDisplay's the help page!");
					sender.sendMessage("§6/CM reload §4- §eReload's the config.yml!");
					sender.sendMessage("§6/CM info §4- §eShow's info about the plugin!");
				} else {
					sender.sendMessage("§cNo Permission.");
				}
			}
			if (args[0].equalsIgnoreCase("info")) {
				sender.sendMessage("§3----------=[ §4CustomMessages Info §3]=----------");
				sender.sendMessage("§ePlugin Made By: §4AljedTheLegit!");
				sender.sendMessage("§eBukkit Website: §4http://dev.bukkit.org/bukkit-plugins/CustomMessages");
				sender.sendMessage("§eAljedTheLegit's Website: §4http://www.tls-sg.org");
			}
			if (args[0].equalsIgnoreCase("reload")) {
				if (sender.hasPermission("custommessages.reload")) {
					this.reloadConfig();
					sender.sendMessage("§aSuccesfully reloaded the config.yml!");
				} else {
					sender.sendMessage("§cNo Permission.");
				}
			}
		}
		else {
			sender.sendMessage("§cUnkown command! Type /cm help for help!");
		}
		return true;
	}
}

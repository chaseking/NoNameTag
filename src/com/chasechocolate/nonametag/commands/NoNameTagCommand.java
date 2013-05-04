package com.chasechocolate.nonametag.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kitteh.tag.TagAPI;

import com.chasechocolate.nonametag.NoNameTag;

public class NoNameTagCommand implements CommandExecutor {
	private NoNameTag plugin;
	
	public NoNameTagCommand(NoNameTag plugin){
		this.plugin = plugin;
	}
	
	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("nonametag") || cmd.getName().equalsIgnoreCase("nnt")){
			if(!(sender instanceof Player)){
				sender.sendMessage("Player only!");
				return true;
			} else {
				Player player = (Player) sender;
				
				if(args.length == 0){
					player.sendMessage(plugin.chatTitle + ChatColor.AQUA + "NoNameTag: Last updated " + plugin.getDescription().getVersion() + " | Made by chasechocolate");
					player.sendMessage(plugin.chatTitle + ChatColor.AQUA + "Download: http://dev.bukkit.org/server-mods/nonametag");
					player.sendMessage(plugin.chatTitle + ChatColor.AQUA + "Type /nnt help for plugin help.");
					
					return true;
				} else if(args.length == 1){
					if(args[0].equalsIgnoreCase("toggle")){
						if(player.hasPermission("nonametag.use") || player.isOp()){
							if(plugin.invisibleTags.contains(player.getName())){
								player.sendMessage(plugin.chatTitle + ChatColor.GOLD + "Your nametag was already invisible! Making it visible...");
								plugin.invisibleTags.remove(player.getName());
								
								TagAPI.refreshPlayer(player);
								
								return true;
							} else {
								player.sendMessage(plugin.chatTitle + ChatColor.GOLD + "Your nametag was visible! Making it invisible...");
								plugin.invisibleTags.add(player.getName());
								
								TagAPI.refreshPlayer(player);
								
								return true;
							}
						} else {
							player.sendMessage(plugin.chatTitle + ChatColor.GOLD + "You do not have access to that command!");
							return true;
						}
					} else if(args[0].equalsIgnoreCase("help")){
						player.sendMessage(plugin.chatTitle + ChatColor.GREEN + "Plugin Help:");
						player.sendMessage(plugin.chatTitle + ChatColor.DARK_AQUA + "-/nnt" + ChatColor.RED + " | " + ChatColor.AQUA + "Shows plugin info.");
						player.sendMessage(plugin.chatTitle + ChatColor.DARK_AQUA + "-/nnt help" + ChatColor.RED + " | " + ChatColor.AQUA + "Shows this page (plugin help).");
						player.sendMessage(plugin.chatTitle + ChatColor.DARK_AQUA + "-/nnt toggle" + ChatColor.RED + " | " + ChatColor.AQUA + "Toggles the visibility of your nametag.");
						player.sendMessage(plugin.chatTitle + ChatColor.DARK_AQUA + "-/nnt toggle <player>" + ChatColor.RED + " | " + ChatColor.AQUA + "Toggles the visibility of another player's nametag.");
						
						return true;
					} else {
						player.sendMessage(plugin.chatTitle + ChatColor.GOLD + "Invalid sub-command!");
						return true;
					}
				} else if(args.length == 2){
					if(args[0].equalsIgnoreCase("toggle")){
						if(player.hasPermission("nonametag.use.others") || player.isOp()){
							Player target = Bukkit.getPlayer(args[1]);
							
							if(player != null){
								if(plugin.invisibleTags.contains(target.getName())){
									player.sendMessage(plugin.chatTitle + ChatColor.GREEN + "You toggled " + target.getName() + "'s nametag! It is now visible!");
									target.sendMessage(plugin.chatTitle + ChatColor.GREEN + "Your nametag has been toggled by " + player.getName() + "! It is now visible!");
									
									plugin.invisibleTags.remove(target.getName());
									TagAPI.refreshPlayer(target);
									
									return true;
								} else {
									player.sendMessage(plugin.chatTitle + ChatColor.GREEN + "You toggled " + target.getName() + "'s nametag! It is now invisible!");
									target.sendMessage(plugin.chatTitle + ChatColor.GREEN + "Your nametag has been toggled by " + player.getName() + "! It is now invisible!");
									
									plugin.invisibleTags.add(target.getName());
									TagAPI.refreshPlayer(target);
									
									return true;
								}
							} else {
								player.sendMessage(plugin.chatTitle + ChatColor.GOLD + "Could not find the player: '" + target.getName() + "'!");
								return true;
							}
						} else {
							player.sendMessage(plugin.chatTitle + ChatColor.GOLD + "You do not have access to that command!");
							return true;
						}
					} else {
						player.sendMessage(plugin.chatTitle + ChatColor.GOLD + "Invalid sub-command!");
						return true;
					}
				} else {
					player.sendMessage(plugin.chatTitle + ChatColor.GOLD + "Invalid command!");
					return true;
				}
			}
		}
		return false;
	}
}
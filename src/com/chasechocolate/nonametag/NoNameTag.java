package com.chasechocolate.nonametag;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.chasechocolate.nonametag.commands.NoNameTagCommand;
import com.chasechocolate.nonametag.listeners.TagListener;

public class NoNameTag extends JavaPlugin {
	public File configFile = new File(this.getDataFolder(), "config.yml");
	
	public List<String> invisibleTags = new ArrayList<String>();
	
	public final String chatTitle = ChatColor.GRAY + "[" + ChatColor.RED + "NoNameTag" + ChatColor.GRAY + "]" + ChatColor.RESET + " ";
	
	public void log(String msg){
		this.getLogger().info(msg);
	}
	
	public void onEnable(){
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new TagListener(this), this);
		
		if(pm.getPlugin("TagAPI") != null){
			log("Successfully hooked into TagAPI!");
		} else {
			this.getLogger().severe("Could not find TagAPI!");
			this.getLogger().severe("Download from: dev.bukkit.org/server-mods/tag");
			pm.disablePlugin(this);
			return;
		}
		
		if(!(configFile.exists())){
            log("Found no config.yml! Creating one for you...");
            this.saveDefaultConfig();
            log("Config successfully created!");
        }
		
		this.getCommand("nonametag").setExecutor(new NoNameTagCommand(this));
		
		log("Enabled!");
	}
	
	public void onDisable(){
		log("Disabled!");
	}
}
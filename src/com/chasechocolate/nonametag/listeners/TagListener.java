package com.chasechocolate.nonametag.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

import com.chasechocolate.nonametag.NoNameTag;

public class TagListener implements Listener {
	private NoNameTag plugin;
	
	public TagListener(NoNameTag plugin){
	    this.plugin = plugin;
	}
	
	@EventHandler
	public void onTagChange(PlayerReceiveNameTagEvent event){
		Player player = event.getNamedPlayer();
		
		if((plugin.getConfig().getBoolean("opdefault") && player.isOp())){
			event.setTag("§§§§");
		} else if(plugin.invisibleTags.contains(player.getName())){
			event.setTag("§§§§");
		}
	}
}
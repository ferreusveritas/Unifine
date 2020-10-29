package com.ferreusveritas.unifine;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldLoadEventHandler {
	
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		if(event.getWorld().isRemote) {
			net.optifine.CustomColors.update();
			System.out.println("Update Custom Colors");
		}
	}
	
}

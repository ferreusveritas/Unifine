package com.ferreusveritas.unifine;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) { }
	
	public OptifineHandlerBase initOptifineHandler() {
		return new OptifineHandlerBase();
	}
	
	public void invalidateAllChunkMeshes() { }
	
}

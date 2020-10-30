package com.ferreusveritas.unifine;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class OptifineHandlerBase {
	
	public void preInit(FMLPreInitializationEvent event) { }
	
	public ShaderState determineShaderState() {
		return ShaderState.NULL;
	}
	
	public String getCurrentShaderName() {
		return "";
	}
	
	public void updateCustomColors() {
		
	}
	
}

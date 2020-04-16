package com.ferreusveritas.unifine;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.optifine.shaders.IShaderPack;
import net.optifine.shaders.ShaderPackDefault;
import net.optifine.shaders.ShaderPackNone;
import net.optifine.shaders.Shaders;

public class OptifineHandlerActive extends OptifineHandlerBase {

	ThermalDynamicsBase thermalDynamicsBase;
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		boolean hasThermalDynamics = Loader.isModLoaded(ModConstants.THERMALDYNAMICS);
		thermalDynamicsBase = initThermalDynamicsProxy(hasThermalDynamics);
		thermalDynamicsBase.preInit(event);
	}
	
	private ThermalDynamicsBase initThermalDynamicsProxy(boolean hasThermalDynamics) {
		try {
			Class<?> clazz = Class.forName("com.ferreusveritas.unifine.ThermalDynamics" + ( hasThermalDynamics ? "Active" : "Base" ));
			return (ThermalDynamicsBase)clazz.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public ShaderState determineShaderState() {
		IShaderPack pack = Shaders.getShaderPack();
		
		if(pack == null || pack instanceof ShaderPackNone) {
			return ShaderState.OFF;
		}
		
		if(pack instanceof ShaderPackDefault) {
			return ShaderState.DEFAULT;
		}
		
		return ShaderState.SHADER;
	}
	
	@Override
	public String getCurrentShaderName() {
		return net.optifine.shaders.Shaders.currentShaderName;
	}
	
}

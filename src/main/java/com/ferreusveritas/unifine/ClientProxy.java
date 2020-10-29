package com.ferreusveritas.unifine;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new WorldLoadEventHandler());
	}
	
	public OptifineHandlerBase initOptifineHandler() {
		DummyModContainer optifineContainer = (DummyModContainer)ReflectionHelper.getPrivateValue(FMLClientHandler.class, FMLClientHandler.instance(), "optifineContainer");
		boolean hasOptifine = optifineContainer != null;
		return initOptifineHandlerProxy(hasOptifine);
	}
	
	private OptifineHandlerBase initOptifineHandlerProxy(boolean hasOptifine) {
		try {
			Class<?> clazz = Class.forName("com.ferreusveritas.unifine.OptifineHandler" + ( hasOptifine ? "Active" : "Base" ));
			return (OptifineHandlerBase)clazz.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public void invalidateAllChunkMeshes() {
		Minecraft.getMinecraft().renderGlobal.loadRenderers();
	}
	
}

package com.ferreusveritas.unifine;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

import com.ferreusveritas.unifine.proxy.CommonProxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCMessage;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.optifine.shaders.IShaderPack;
import net.optifine.shaders.ShaderPackNone;
import net.optifine.shaders.Shaders;

/**
* <p><pre><tt><b>
*  ╭─────────────────╮
*  │                 │
*  │                 │
*  │                 │
*  │                 │
*  │                 │
*  │                 │
*  │                 │
*  │                 │
*  │                 │
*  │                 │
*  │                 │
*  ╞═════════════════╡
*  │     UNIFINE     │
*  ╰─────────────────╯
* </b></tt></pre></p>
* 
* <p>
* 2019 Ferreusveritas
* </p>
*
*/
@Mod(modid = ModConstants.MODID, name = ModConstants.NAME, version=ModConstants.VERSION, dependencies=ModConstants.DEPENDENCIES)
public class Unifine {
	
	@Mod.Instance(ModConstants.MODID)
	public static Unifine instance;
	
	@SidedProxy(clientSide = "com.ferreusveritas.unifine.proxy.ClientProxy", serverSide = "com.ferreusveritas.unifine.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static Boolean isShaderActive = false;
	public static ArrayList<Function<Boolean, Void>> callbacks = new ArrayList<>();
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
		
		MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
	}
	
	@Mod.EventHandler
	public void onMessageRecieved(IMCEvent event) {
		for(IMCMessage mess : event.getMessages()) {
			if("shadertest".equals(mess.key) && mess.isFunctionMessage()) {
				Optional<Function<Boolean, Void>> function = mess.getFunctionValue(Boolean.class, Void.class);
				if(function.isPresent()) {
					callbacks.add(function.get());
				}
			}
		}
	}
	
	public boolean isShaderOn() {
		IShaderPack pack = Shaders.getShaderPack();
		return !(pack instanceof ShaderPackNone);
	}
	
	public void shaderChanged() {
		callbacks.forEach(a -> a.apply(isShaderActive));
	}
	
	public class CommonEventHandler {
		@SubscribeEvent
		public void onWorldTick(ClientTickEvent event) {
			boolean testShader = isShaderOn();
			if(testShader != isShaderActive) {
				isShaderActive = testShader;
				shaderChanged();
			}
		}
	}
	
}
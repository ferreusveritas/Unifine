package com.ferreusveritas.unifine;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCMessage;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

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
 * 2019-2020 Ferreusveritas
 * </p>
 *
 */
@Mod(modid = ModConstants.MODID, name = ModConstants.NAME, version=ModConstants.VERSION, dependencies=ModConstants.DEPENDENCIES)
public class Unifine {

	@Mod.Instance(ModConstants.MODID)
	public static Unifine instance;

	@SidedProxy(clientSide = "com.ferreusveritas.unifine.ClientProxy", serverSide = "com.ferreusveritas.unifine.CommonProxy")
	public static CommonProxy proxy;
	
	private static ArrayList<Function<Boolean, Void>> callbacks = new ArrayList<>();
	private static ShaderState currentShaderState = ShaderState.NULL;
	private static String cachedShaderName = "";
	
	private static OptifineHandlerBase optifineHandler;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		proxy.preInit(event);
		
		optifineHandler = proxy.initOptifineHandler();
		currentShaderState = determineShaderState();
		optifineHandler.preInit(event);
		
		if(isOptifineInstalled()) {
			MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
		}
		
		System.out.println("Unifine detected Optifine shader state: " + currentShaderState);
		
	}

	@Mod.EventHandler
	public void onMessageRecieved(IMCEvent event) {
		for(IMCMessage mess : event.getMessages()) {
			if("shadertest".equalsIgnoreCase(mess.key)) {
				Optional<Function<Boolean, Void>> function = mess.getFunctionValue(Boolean.class, Void.class);
				if(function.isPresent()) {
					AddCallback(function.get());
				}
			}
		}
	}

	public static void AddCallback(Function<Boolean, Void> function) {
		callbacks.add(function);
	}
	
	public static boolean isShaderActive() {
		return currentShaderState.DoesShading();
	}
	
	private ShaderState determineShaderState() {
		return optifineHandler.determineShaderState();
	}
	
	public static String getCurrentShaderName() {
		return optifineHandler.getCurrentShaderName();
	}
	
	public static ShaderState getCurrentShaderState() {
		return currentShaderState;
	}
	
	public static boolean isOptifineInstalled() {
		return currentShaderState != ShaderState.NULL;
	}
	
	private void shaderStateChanged() {
		System.out.println("Unifine detected an Optifine shader state change: " + currentShaderState + " Name:" + getCurrentShaderName());
		boolean isShaderActive = isShaderActive();
		callbacks.forEach(a -> a.apply(isShaderActive));
		
		proxy.invalidateAllChunkMeshes();
	}

	public CommonEventHandler CreateEventHandler() {
		return new CommonEventHandler();
	}
	
	public class CommonEventHandler {
		@SubscribeEvent
		public void onWorldTick(ClientTickEvent event) {
			if(event.phase == Phase.START) {
				String shaderName = getCurrentShaderName();
				if(!cachedShaderName.equals(shaderName)) {
					cachedShaderName = shaderName;
					currentShaderState = determineShaderState();
					shaderStateChanged();
				}
			}
		}
		
		@SubscribeEvent
		public void onWorldLoad(WorldEvent.Load event) {
			if(event.getWorld().isRemote && UnifineConfig.reloadCustomColors) {
				optifineHandler.updateCustomColors();
			}
		}
		
	}

	@Config(modid="longerdays")
	public static class UnifineConfig{
		
		@Config.Name("Reload Custom Colors")
		@Config.Comment("If enabled the Optifine custom color settings will be reloaded each time the client joins a world")
		public static boolean reloadCustomColors = true;
		
	}
	
}
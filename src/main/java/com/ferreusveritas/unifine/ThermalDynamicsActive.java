package com.ferreusveritas.unifine;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import cofh.thermaldynamics.duct.attachments.cover.CoverRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ThermalDynamicsActive extends ThermalDynamicsBase {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		hackThermalDynamicsCovers();
	}

	private void hackThermalDynamicsCovers() {
		ThreadLocal<VertexLighterFlatSpecial> lighterFlat = ThreadLocal.withInitial(() -> new VertexLighterFlatSpecial(Minecraft.getMinecraft().getBlockColors()));
		ThreadLocal<VertexLighterFlatSpecial> lighterSmooth = ThreadLocal.withInitial(() -> new VertexLighterSmoothAoSpecial(Minecraft.getMinecraft().getBlockColors()));
		
		setVertexLighter("lighterFlat", lighterFlat);
		setVertexLighter("lighterSmooth", lighterSmooth);
	}
	
	//Look.. I know this is a dumpster fire, okay?
	private void setVertexLighter(String lighterName, ThreadLocal<VertexLighterFlatSpecial> lighterThread) {
		
		Field field = ReflectionHelper.findField(CoverRenderer.class, lighterName);
		field.setAccessible(true);

		Field modifiersField;
		try {
			modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			field.set(null, lighterThread);

		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

	}

}

package com.ferreusveritas.unifine;

public enum ShaderState {
	NULL(false),//No Optifine installed
	OFF(false),
	DEFAULT(false),
	SHADER(true);
	
	private boolean doesShading;
	
	private ShaderState(boolean doesShading) {
		this.doesShading = doesShading;
	}
	
	public boolean DoesShading() {
		return doesShading;
	}
}
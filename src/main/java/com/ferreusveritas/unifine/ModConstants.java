package com.ferreusveritas.unifine;


public class ModConstants {
	
	public static final String MODID = "unifine";
	public static final String NAME = "Unifine";
	public static final String VERSION = "1.12.2-9999.9999.9999z";//Maxed out version to satisfy dependencies during dev, Assigned from gradle during build, do not change
	
	public static final String OPTAFTER = "after:";
	public static final String OPTBEFORE = "before:";
	public static final String REQAFTER = "required-after:";
	public static final String REQBEFORE = "required-before:";
	public static final String NEXT = ";";
	public static final String AT = "@[";
	public static final String GREATERTHAN = "@(";
	public static final String ORGREATER = ",)";
	
	//Other mods can use this string to depend on the latest version of Dynamic Trees
	public static final String UNIFINE_LATEST = MODID + AT + VERSION + ORGREATER;
	
	//Forge
	private static final String FORGE = "forge";
	public static final String FORGE_VER = FORGE + AT + "14.23.5.2768" + ORGREATER;
	
	public static final String DEPENDENCIES
		= REQAFTER + FORGE_VER;
	
}

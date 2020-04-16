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

	public static final String THERMALDYNAMICS = "thermaldynamics";

	public static final String DEPENDENCIES
	= OPTAFTER + THERMALDYNAMICS
	;
	
}

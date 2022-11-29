package core;

import maths.MathF;
import maths.Vector3f;

public class Constants
{
	//PROGRAM
	public static final String WND_TITLE 					= "Gravity Project";
	public static final int WND_W 							= 640;
	public static final int WND_H 							= 400;
	public static final Vector3f BACKGROUND_COLOR 			= new Vector3f(0.02f, 0.02f, 0.02f);
	public static final float MOUSE_SENSITIVITY 			= 0.15f;
	public static final float CAMERA_SPEED 					= 0.1f;
	
	//UNIT: METERS!!!
	public static final float SPACE_RADIUS					= 100;
	public static final float UA 							= 1.496f * MathF.pow(10, 11);
	public static final Vector3f SPACE_CENTER		 		= new Vector3f(0, 0, 0);
	public static final Vector3f START_POINT 				= new Vector3f(0, 0, 10);
	public static final float GRAVITATIONAL_CONSTANT 		= 6.67408f * MathF.pow(10, -11);
	public static final float TIME_SCALE_FACTOR 			= MathF.pow(10, 1);
	
	//DEFAULTS
	public static final float 	ENTITY_DEFAULT_MASS 		= MathF.pow(10, 6);
	public static final float 	ENTITY_DEFAULT_DIAMETER 	= 10;
	public static final float	VECTOR_SCALE_FACTOR 		= MathF.pow(5, -2);
	
	//OTHER
	public final static float VISIBILITY_RANGE 	 			= 10 * UA;
	public final static float UA_PER_PIXEL					= MathF.pow(10, -2);
	public final static int ICOSPHERE_SUBDIVISION			= 3;
	public final static int NUMBER_OF_SECTORS				= 72;
	public final static int NUMBER_OF_STACKS				= 24;

	//SWITCHES
	public final static boolean USING_LAPTOP				= true;
	public final static String SHADER_USED					= (USING_LAPTOP ? "portatile" : "shader");
}
package core;

import input.Input;
import maths.MathF;
import maths.Vector3f;
import org.lwjgl.glfw.GLFW;
import parts.*;

import java.util.*;

import drawing.*;

public class Scenario3D extends Scenario
{
	//DRAWABLE OBJECTS
	private Camera camera;
	private Background background;
	private List<Entity> entities;
	
	//VARIABLES
	private boolean paused;
	
	//CONSTRUCTOR
	public Scenario3D(Renderer renderer)
	{
		super(renderer);

		paused = false;
		camera = new Camera(Constants.START_POINT.clone(), new Vector3f(0, 0, 0));
		background = new Background(Constants.SPACE_CENTER.clone(), Constants.SPACE_RADIUS);
		background.initMesh();
		entities = new ArrayList<>();
		initializeEntities();
	}
	
	@Override
	public void render()
	{
		renderer.renderBackground(background, camera);
		entities.forEach(e -> renderer.renderEntity(e, camera));
	}

	@Override
	public void processCommands()
	{
		if (Input.consumeKeyTrigger(GLFW.GLFW_KEY_P)) paused = !paused;
	}

	@Override
	public void update()
	{
		//CAMERA
		camera.update();
		//BACKGROUND
		background.update();
		//ENTITIES
		if (!paused)
		{
			calculateInteractions();
			entities.forEach(Entity::update);
		}
	}
	@Override
	public void destroy()
	{
		background.getMesh().destroy();
		entities.forEach(e -> e.getMesh().destroy());
	}

	//METHODS
	private void initializeEntities()
	{
		entities = generateStartingEntities();
		entities.forEach(Entity::initMesh);
	}
	private List<Entity> generateStartingEntities()
	{
		Entity temp;
		List<Entity> entities;

		entities = new ArrayList<>();
		temp = new Entity(30000000f, 50, new Vector3f(5, 0, 0), new Vector3f(), new Vector3f(0, -1, 0), EntityType.SUN);
		entities.add(temp);
		temp = new Entity(1000000, 50, new Vector3f(-5, 0, 0), new Vector3f(0, -0.02f, 0.05f), new Vector3f(0, 2, 0), EntityType.EARTH_DAY);
		entities.add(temp);
		temp = new Entity(1000, 50, new Vector3f(-7, 0, 0), new Vector3f(0.005f, -0.02f, 0.03f), new Vector3f(0, -1, 0), EntityType.MOON);
		entities.add(temp);

		return entities;
	}
	private void calculateInteractions()
	{
		int i, j;
		Object[] tmp;
		Entity entA, entB;
		float modAB, modBA, th, ph, r;
		Vector3f vecAB, vecBA, posA, posB;

		tmp = entities.toArray();
		for (i = 0; i < tmp.length; i++)
			for (j = 0; j < i; j++)
			{
				entA = (Entity)tmp[i];
				entB = (Entity)tmp[j];
				posA = entA.getLocation();
				posB = entB.getLocation();
				r = entA.getDistanceFromCOM(entB);
				modAB = Constants.GRAVITATIONAL_CONSTANT * entA.getMass() / MathF.pow(r, 2);
				modBA = Constants.GRAVITATIONAL_CONSTANT * entB.getMass() / MathF.pow(r, 2);
				th = MathF.atan((posB.x - posA.x) / (posB.z - posA.z)) +
						((posB.z - posA.z) < 0 ? MathF.toRadians(180) : 0);
				ph = MathF.acos((posB.y - posA.y) / r);
				vecAB = new Vector3f(-modAB * MathF.sin(th) * MathF.sin(ph),
						-modAB * MathF.cos(ph),
						-modAB * MathF.cos(th) * MathF.sin(ph));
				vecBA = new Vector3f(modBA * MathF.sin(th) * MathF.sin(ph),
						modBA * MathF.cos(ph),
						modBA * MathF.cos(th) * MathF.sin(ph));
				vecAB.scale(Constants.TIME_SCALE_FACTOR);
				vecBA.scale(Constants.TIME_SCALE_FACTOR);
				entB.applyForce(vecAB);
				entA.applyForce(vecBA);
			}
	}
}
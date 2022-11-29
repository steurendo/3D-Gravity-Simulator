package parts;

import drawing.MeshMaker;
import drawing.TextureQuality;
import maths.Vector3f;

public class Entity extends DrawableObject
{
	private static final TextureQuality QUALITY_DEFAULT = TextureQuality.STANDARD;

	private float mass;
	private float radius;
	private Vector3f currentVector;
	private Vector3f nextVector;
	private Vector3f momento;
	
	public Entity(float mass, float radius, Vector3f location, Vector3f startingVector, Vector3f momento, EntityType entityType)
	{
		super(location, new Vector3f(), new Vector3f(1, 1, 1), MeshMaker.generateMesh(entityType, QUALITY_DEFAULT));

		this.mass = mass;
		this.radius = radius;
		currentVector = startingVector;
		nextVector = new Vector3f();
		this.momento = momento;
	}
	public Entity(float mass, float radius, Vector3f location, Vector3f startingVector, EntityType entityTipe) { this(mass, radius, location, startingVector, new Vector3f(), entityTipe); }
	public Entity(float mass, float radius, Vector3f location, EntityType entityTipe) { this(mass, radius, location, new Vector3f(), new Vector3f(), entityTipe); }

	public float getMass() { return mass; }
	public float getRadius() { return radius; }
	public Vector3f getLocation() { return location; }
	public Vector3f getCurrentVector() { return currentVector; }
	public Vector3f getNextVector() {	return nextVector; }
	public Vector3f getMomento() { return momento; }
	public void setMass(float mass) { this.mass = mass; }
	public void setRadius(float radius) { this.radius = radius; }
	public void setLocation(Vector3f location) { this.location = location;	}
	public void setCurrentVector(Vector3f currentVector) { this.currentVector = currentVector; }
	public void setNextVector(Vector3f nextVector) { this.nextVector = nextVector; }
	public void setMomento(Vector3f momento) { this.momento = momento; }

	//DISTANCE FROM BOUNDS
	public float getDistanceFromEntity(Entity entity)
	{
		return (location.distanceFrom(entity.location) - radius - entity.radius);
	}
	//DISTANCE FROM CENTER OF MASS
	public float getDistanceFromCOM(Entity entity) { return location.distanceFrom(entity.location); }
	//THIS INSTANCE WILL BE THE ONLY ONE
	public void collide(Entity entity)
	{
		float mass;
		
		mass = this.mass + entity.mass;
		currentVector.x = (this.mass * currentVector.x + entity.mass * entity.currentVector.x) / mass;
		currentVector.y = (this.mass * currentVector.y + entity.mass * entity.currentVector.y) / mass;
		this.mass = mass;
		if (radius < entity.radius)
			location = entity.location;
		else if (radius == entity.radius)
		{
			location.x = (location.x + entity.location.x) / 2;
			location.y = (location.y + entity.location.y) / 2;
		}
	}
	public void applyForce(Vector3f force) { nextVector.add(force); }
	public void update()
	{
		currentVector.add(nextVector);
		location.add(currentVector);
		rotation.x = (rotation.x + momento.x) % 360;
		rotation.y = (rotation.y + momento.y) % 360;
		rotation.z = (rotation.z + momento.z) % 360;
		nextVector.scale(0);
	}
}
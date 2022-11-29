package maths;

public class Dimension3f
{
	public float x;
	public float y;
	public float z;
	
	public Dimension3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Dimension3f() { this(0, 0, 0); }

	public float getX() { return x; }
	public float getY() { return y; }
	public float getZ() { return z; }

	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	public void setZ(float z) { this.z = z; }
	
	public void scaleByFactor(float factor)
	{
		x *= factor;
		y *= factor;
		z *= factor;
	}
	public Dimension3f clone() { return new Dimension3f(x, y, z); }
}
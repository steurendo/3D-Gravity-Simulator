package maths;

public class Vector4f
{
	public float x;
	public float y;
	public float z;
	public float w;
	private float[] vec;

	public Vector4f(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		vec = new float[] { x, y, z, w };
	}
	public Vector4f() { this(0, 0, 0, 0); }

	public float getX() { return x; }
	public float getY() { return y; }
	public float getZ() { return z; }
	public float getW() { return w; }
	public float[] getAll()
	{
		updateVec();

		return vec;
	}

	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	public void setZ(float z) { this.z = z; }
	public void setW(float w) { this.w = w; }
	public void set(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	private void updateVec()
	{
		vec[0] = x;
		vec[1] = y;
		vec[2] = z;
		vec[3] = w;
	}
	public float mod() { return MathF.sqrt(MathF.pow(x, 2) + MathF.pow(y, 2) + MathF.pow(z, 2) + MathF.pow(w, 2)); }
	public float distanceFrom(Vector4f vector) { return MathF.sqrt(
				 MathF.pow(x - vector.x, 2) +
					MathF.pow(y - vector.y, 2) +
				    MathF.pow(z - vector.z, 2) +
				    MathF.pow(w - vector.w, 2)
	); }
	public void add(Vector4f vector)
	{
		x += vector.x;
		y += vector.y;
		z += vector.z;
		w += vector.w;
	}
	public void subtract(Vector4f vector)
	{
		x -= vector.x;
		y -= vector.y;
		z -= vector.z;
		w -= vector.w;
	}
	public void multiply(Vector4f vector)
	{
		x *= vector.x;
		y *= vector.y;
		z *= vector.z;
		w *= vector.w;
	}
	public void divide(Vector4f vector)
	{
		x /= vector.x;
		y /= vector.y;
		z /= vector.z;
		w /= vector.w;
	}
	public void scale(float value)
	{
		x *= value;
		y *= value;
		z *= value;
		w *= value;
	}
	public void normalize() { scale(1f / mod()); }
	public float dot(Vector4f vector) { return x * vector.x + y * vector.y + z * vector.z + w * vector.w; }
	public Vector4f clone() { return new Vector4f(x, y, z, w); }
	public String toString()
	{
		return " [" + x + "; " + y + "; " + z + "; " + w + "]";
	}

	//STATICS
	public static float mod(Vector4f vector)
	{ return MathF.sqrt(MathF.pow(vector.x, 2) + MathF.pow(vector.y, 2) + MathF.pow(vector.z, 2)); }
	public static Vector4f add(Vector4f v1, Vector4f v2)
	{ return new Vector4f(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z, v1.w + v2.w); }
	public static Vector4f subtract(Vector4f v1, Vector4f v2)
	{ return new Vector4f(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z, v1.w - v2.w); }
	public static Vector4f multiply(Vector4f v1, Vector4f v2)
	{ return new Vector4f(v1.x * v2.x, v1.y * v2.y, v1.z * v2.z, v1.w * v2.w); }
	public static Vector4f divide(Vector4f v1, Vector4f v2)
	{ return new Vector4f(v1.x / v2.x, v1.y / v2.y, v1.z / v2.z, v1.w / v2.w); }
	public static Vector4f scale(Vector4f vector, float value)
	{ return new Vector4f(vector.x * value, vector.y * value, vector.z * value, vector.w * value); }
	public Vector4f normalize(Vector4f vector)
	{ return scale(vector, 1f / vector.mod()); }
	public float dot(Vector4f v1, Vector4f v2)
	{ return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z + v1.w * v2.w; }
}
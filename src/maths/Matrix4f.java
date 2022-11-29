package maths;

public class Matrix4f
{
    public static final int SIZE = 4;
    private float[] vals = new float[SIZE * SIZE];

    public float get(int x, int y) { return vals[y * SIZE + x]; }
    public float[] getAll() { return vals; }
    public void set(int x, int y, float val) { vals [y * SIZE + x] = val; }

    public static Matrix4f identity()
    {
        Matrix4f result;

        result = new Matrix4f();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                result.set(i, j, (i == j ? 1 : 0));

        return result;
    }
    public static Matrix4f translate(Vector3f vector)
    {
        Matrix4f result;

        result = Matrix4f.identity();
        result.set(3, 0, vector.x);
        result.set(3, 1, vector.y);
        result.set(3, 2, vector.z);

        return result;
    }
    public static Matrix4f rotate(float th, Vector3f axis)
    {
        float cos, sin, C;
        Matrix4f result;

        cos = MathF.cos(MathF.toRadians(th));
        sin = MathF.sin(MathF.toRadians(th));
        C = 1 - cos;

        result = Matrix4f.identity();
        //X = 0
        result.set(0, 0, axis.x * axis.x * C + cos);
        result.set(0, 1, axis.x * axis.y * C - axis.z * sin);
        result.set(0, 2, axis.x * axis.z * C + axis.y * sin);
        //X = 1
        result.set(1, 0, axis.y * axis.x * C + axis.z * sin);
        result.set(1, 1, axis.y * axis.y * C + cos);
        result.set(1, 2, axis.y * axis.z * C - axis.x * sin);
        //X = 2
        result.set(2, 0, axis.z * axis.x * C - axis.y * sin);
        result.set(2, 1, axis.z * axis.y * C + axis.x * sin);
        result.set(2, 2, axis.z * axis.z * C + cos);

        return result;
    }
    public static Matrix4f scale(Vector3f vector)
    {
        Matrix4f result;

        result = Matrix4f.identity();
        result.set(0, 0, vector.x);
        result.set(1, 1, vector.y);
        result.set(2, 2, vector.z);

        return result;
    }
    public static Matrix4f multiply(Matrix4f m1, Matrix4f m2)
    {
        int x, y, i;
        float val;
        Matrix4f result;

        result = Matrix4f.identity();
        for (y = 0; y < SIZE; y++)
            for (x = 0; x < SIZE; x++)
            {
                val = 0;
                for (i = 0; i < SIZE; i++)
                    val += m2.get(i, y) * m1.get(x, i);
                result.set(x, y, val);
            }

        return result;
    }
    public static Matrix4f transform(Vector3f position, Vector3f rotation, Vector3f scale)
    {
        Matrix4f result, rotXMatrix, rotYMatrix, rotZMatrix, scaleMatrix, rotationMatrix, translationMatrix;

        translationMatrix = Matrix4f.translate(position);
        rotXMatrix = Matrix4f.rotate(rotation.x, new Vector3f(1, 0, 0));
        rotYMatrix = Matrix4f.rotate(rotation.y, new Vector3f(0, 1, 0));
        rotZMatrix = Matrix4f.rotate(rotation.z, new Vector3f(0, 0, 1));
        scaleMatrix = Matrix4f.scale(scale);
        rotationMatrix = Matrix4f.multiply(rotXMatrix, Matrix4f.multiply(rotYMatrix, rotZMatrix));
        result = Matrix4f.multiply(Matrix4f.multiply(rotationMatrix, scaleMatrix), translationMatrix);

        return result;
    }
    public static Matrix4f projection(float fov, float aspect, float near, float far)
    {
        Matrix4f result;
        float tanFOV, range;

        result = Matrix4f.identity();
        tanFOV = MathF.tan(MathF.toRadians(fov / 2));
        range = far - near;
        result.set(0, 0, 1.0f / (aspect * tanFOV));
        result.set(1, 1, 1.0f / tanFOV);
        result.set(2, 2, - ((far + near) / range));
        result.set(2, 3, -1.0f);
        result.set(3, 2, -((2 * far * near) / range));
        result.set(3, 3, 0.0f);

        return result;
    }
    public static Matrix4f view(Vector3f position, Vector3f rotation)
    {
        Vector3f negative;
        Matrix4f result, rotXMatrix, rotYMatrix, rotZMatrix, rotationMatrix, translationMatrix;

        negative = new Vector3f(-position.x, -position.y, -position.z);
        translationMatrix = Matrix4f.translate(negative);
        rotXMatrix = Matrix4f.rotate(rotation.x, new Vector3f(1, 0, 0));
        rotYMatrix = Matrix4f.rotate(rotation.y, new Vector3f(0, 1, 0));
        rotZMatrix = Matrix4f.rotate(rotation.z, new Vector3f(0, 0, 1));
        rotationMatrix = Matrix4f.multiply(rotZMatrix, Matrix4f.multiply(rotYMatrix, rotXMatrix));
        result = Matrix4f.multiply(translationMatrix, rotationMatrix);

        return result;
    }
}

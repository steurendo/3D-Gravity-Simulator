package parts.solids;

import drawing.Vertex;
import maths.MathF;
import maths.Vector2f;
import maths.Vector3f;

public class UVSphere extends Solid
{
    private int numberOfSectors;
    private int numberOfStacks;

    public UVSphere(int numberOfSectors, int numberOfStacks)
    {
        this.numberOfSectors = numberOfSectors;
        this.numberOfStacks = numberOfStacks;
    }

    private Vector3f FUV(float ix, float iy)
    {
        float x, y, z, r, th, ph;

        r = 1f;
        th = 2f * MathF.PI * ix / numberOfSectors;
        ph = MathF.PI * iy / numberOfStacks;
        x = r * MathF.cos(th) * MathF.sin(ph);
        z = r * MathF.sin(th) * MathF.sin(ph);
        y = r * MathF.cos(ph);

        return new Vector3f(x, y, z);
    }
    private Vector2f textureCoordUV(float ix, float iy)
    {
        float x, y;

        x = -ix / numberOfSectors;
        y = iy / numberOfStacks;

        return new Vector2f(x, y);
    }

    @Override
    public void create()
    {
        int x, y, offset;
        Vertex[] tmpVertices;
        int[] tmpIndices;

        vertices = new Vertex[numberOfSectors * numberOfStacks * 4];
        indices = new int[numberOfSectors * numberOfStacks * 6];
        for (y = 0; y < numberOfStacks; y++)
        {
            for (x = 0; x < numberOfSectors; x++)
            {
                tmpVertices = new Vertex[]{
                        new Vertex(FUV(x, y), textureCoordUV(x, y)),
                        new Vertex(FUV(x, y + 1), textureCoordUV(x, y + 1)),
                        new Vertex(FUV(x + 1, y + 1), textureCoordUV(x + 1, y + 1)),
                        new Vertex(FUV(x + 1, y), textureCoordUV(x + 1, y))
                };
                offset = (y * numberOfSectors + x) * 4;
                tmpIndices = new int[]{
                        offset, offset + 1, offset + 3,
                        offset + 3, offset + 1, offset + 2
                };
                System.arraycopy(tmpVertices, 0, vertices, tmpVertices.length * (y * numberOfSectors + x), tmpVertices.length);
                System.arraycopy(tmpIndices, 0, indices, tmpIndices.length * (y * numberOfSectors + x), tmpIndices.length);
            }
        }
    }
}

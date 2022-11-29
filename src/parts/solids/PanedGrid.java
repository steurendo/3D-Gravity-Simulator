package parts.solids;

import drawing.Vertex;
import maths.Vector2f;
import maths.Vector3f;

public class PanedGrid extends Solid
{
    private int numberOfSectors;
    private int numberOfStacks;

    public PanedGrid(int numberOfSectors, int numberOfStacks)
    {
        this.numberOfSectors = numberOfSectors;
        this.numberOfStacks = numberOfStacks;
    }

    public Vector3f FUV(int ix, int iy)
    {
        float x, z;

        x = (float)ix / numberOfSectors - 0.5f;
        z = (float)iy / numberOfStacks - 0.5f;

        return new Vector3f(x / numberOfSectors, 0, z / numberOfSectors);
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
                        new Vertex(FUV(x, y), new Vector2f(0, 0)),
                        new Vertex(FUV(x, y + 1), new Vector2f(0, 1)),
                        new Vertex(FUV(x + 1, y + 1), new Vector2f(1, 1)),
                        new Vertex(FUV(x + 1, y), new Vector2f(1, 0))
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

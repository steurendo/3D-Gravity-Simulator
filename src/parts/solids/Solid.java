package parts.solids;

import drawing.Material;
import drawing.Mesh;
import drawing.Vertex;

public abstract class Solid
{
    protected Vertex[] vertices;
    protected int[] indices;

    public Mesh generateMesh(Material material) { return new Mesh(vertices, indices, material); }
    public abstract void create();
}

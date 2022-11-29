package parts;

import drawing.Mesh;
import maths.Vector3f;

public class DrawableObject
{
    protected Vector3f location;
    protected Vector3f rotation;
    protected Vector3f scale;
    protected Mesh mesh;

    public DrawableObject(Vector3f location, Vector3f rotation, Vector3f scale, Mesh mesh)
    {
        this.location = location;
        this.rotation = rotation;
        this.scale = scale;
        this.mesh = mesh;
    }

    public Vector3f getLocation() { return location; }
    public Vector3f getRotation() { return rotation; }
    public Vector3f getScale() { return scale; }
    public Mesh getMesh() { return mesh; }
    public void initMesh() { mesh.create(); }
}

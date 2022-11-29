package parts;

import drawing.MeshMaker;
import drawing.TextureQuality;
import maths.Vector3f;

public class Background extends DrawableObject
{
    public static final BackgroundTexture TEXTURE_DEFAULT = BackgroundTexture.MILKYWAY;
    public static final TextureQuality QUALITY_DEFAULT = TextureQuality.STANDARD;

    private Vector3f momento;

    public Background(Vector3f location, Vector3f rotation, Vector3f scale)
    {
        super(location, rotation, scale, MeshMaker.generateBackgroundMesh(TEXTURE_DEFAULT, 4, QUALITY_DEFAULT));

        momento = rotation.clone();
    }
    public Background(Vector3f location, float dim) { this(location, new Vector3f(), new Vector3f(dim, dim, dim)); }

    public void update()
    {
        rotation.x = (rotation.x + momento.x) % 360;
        rotation.y = (rotation.y + momento.y) % 360;
        rotation.z = (rotation.z + momento.z) % 360;
    }
}

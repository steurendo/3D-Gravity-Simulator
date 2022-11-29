package parts;

import core.Constants;
import drawing.MeshMaker;
import drawing.Vertex;
import maths.MathF;
import maths.Vector3f;

import java.util.ArrayList;

public class Grid extends DrawableObject
{
    private static final float SCALE = MathF.pow(Constants.SPACE_RADIUS, 2);

    public Grid(float height)
    {
        super(Constants.SPACE_CENTER.clone(),
                new Vector3f(),
                new Vector3f(SCALE, SCALE, SCALE),
                MeshMaker.generateMeshGrid((int)Constants.SPACE_RADIUS, new Vector3f(0, 1, 0)));

        location.y = -height;
    }
}

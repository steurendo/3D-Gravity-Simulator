package drawing;

import maths.Vector2f;
import maths.Vector3f;

import java.awt.*;

public class ReferenceFrame
{
    public static final Vector2f DEFAULT_CENTER_COORD = new Vector2f(20, 20);
    public static final int DEFAULT_ARROWS_LENGTH = 10;
    public static final Color DEFAULT_X_COLOR = Color.RED;
    public static final Color DEFAULT_Y_COLOR = Color.BLUE;
    public static final Color DEFAULT_Z_COLOR = Color.GREEN;
    private Vector2f position;
    private Vector3f rotation;

    private Vector2f centerCoord;
    private int arrowsLength;
    private Color xColor;
    private Color yColor;
    private Color zColor;

    public ReferenceFrame()
    {
        centerCoord = DEFAULT_CENTER_COORD;
        arrowsLength = DEFAULT_ARROWS_LENGTH;
        xColor = DEFAULT_X_COLOR;
        yColor = DEFAULT_Y_COLOR;
        zColor = DEFAULT_Z_COLOR;
    }
    public void update()
    {
    }
    public Vector2f getPosition() { return position; }
    public Vector3f getRotation() { return rotation; }
}
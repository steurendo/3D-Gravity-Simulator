package parts;

import core.Canvas;
import core.Constants;
import input.Input;
import maths.MathF;
import maths.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera
{
	private Vector3f position;
	private Vector3f rotation;
	private float moveSpeed;
	private float mouseSensitivity;
	private double oldMouseX, oldMouseY;

	public Camera(Vector3f position, Vector3f rotation)
	{
		this.position = position;
		this.rotation = rotation;
		moveSpeed = Constants.CAMERA_SPEED;
		mouseSensitivity = Constants.MOUSE_SENSITIVITY;
		oldMouseX = 0;
		oldMouseY = 0;
	}

	public void update()
	{
		double newMouseX, newMouseY;
		float dx, dy, x, z;

		newMouseX = Input.getCursorX() - (float)Canvas.w / 2;
		newMouseY = Input.getCursorY() - (float)Canvas.h / 2;
		dx = (float)(newMouseX - oldMouseX);
		dy = (float)(newMouseY - oldMouseY);
		if (rotation.x -dy * mouseSensitivity < 90 && rotation.x -dy * mouseSensitivity > -90)
			rotation.x += -dy * mouseSensitivity;
		rotation.y += -dx * mouseSensitivity;

		x = MathF.cos(MathF.toRadians(rotation.y)) * moveSpeed;
		z = MathF.sin(MathF.toRadians(rotation.y)) * moveSpeed;
		if (Input.isKeyDown(GLFW.GLFW_KEY_W)) position.add(new Vector3f(-z, 0, -x));
		if (Input.isKeyDown(GLFW.GLFW_KEY_S)) position.add(new Vector3f(z, 0, x));
		if (Input.isKeyDown(GLFW.GLFW_KEY_D)) position.add(new Vector3f(x, 0, -z));
		if (Input.isKeyDown(GLFW.GLFW_KEY_A)) position.add(new Vector3f(-x, 0, z));
		if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) position.add(new Vector3f(0, moveSpeed, 0));
		if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) position.add(new Vector3f(0, -moveSpeed, 0));

		oldMouseX = newMouseX;
		oldMouseY = newMouseY;
	}
	public Vector3f getPosition() { return position; }
	public Vector3f getRotation() { return rotation; }
}
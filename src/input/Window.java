package input;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;
import static org.lwjgl.system.MemoryStack.*;

import java.nio.*;

import core.Canvas;
import core.Constants;
import maths.Matrix4f;
import maths.Vector3f;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

public class Window
{
	private long id;
	private String title;
	private Vector3f bgColor;
	private long time;
	private long frames;
	private Matrix4f projection;
	private Input input;
	private boolean mouseLocked;
	
	public Window(int width, int height, String title)
	{
		core.Canvas.w = width;
		core.Canvas.h = height;
		this.title = title;
		projection = Matrix4f.projection(70.0f, (float)width / height, 0.1f, 1000.0f);
	}
	
	public void create() throws Exception
	{
		//GLFW INITIALIZATION
		if (!glfwInit())
			throw new Exception("Unable to start GLFW");

		//WINDOW INITIALIZATION
		core.Canvas.w = Constants.WND_W;
		core.Canvas.h = Constants.WND_H;
		id = GLFW.glfwCreateWindow(core.Canvas.w, core.Canvas.h, "Gravity Simulation", 0, 0);
		if (id == 0)
			throw new Exception("Unable to create window");
		bgColor = Constants.BACKGROUND_COLOR;
		//COMMANDS
		input = new Input();
		glfwSetKeyCallback(id, input.getKeyboardCallback());
		glfwSetMouseButtonCallback(id, input.getMouseButtonsCallback());
		glfwSetScrollCallback(id, input.getScrollCallback());
		glfwSetCursorEnterCallback(id, input.getCursorEnterCallback());
		glfwSetCursorPosCallback(id, input.getCursorPosCallback());
		//RESIZE CALLBACK
		glfwSetFramebufferSizeCallback(id, (window, width, height) ->
		{
			core.Canvas.w = width;
			Canvas.h = height;
			glViewport(0, 0, width, height);
		});
		//CENTERED WINDOW
		try (MemoryStack stack = stackPush())
		{
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			glfwGetWindowSize(id, pWidth, pHeight);
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(
				id,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		}
		glfwMakeContextCurrent(id);
		GL.createCapabilities(); //OPENGL-GLFW INTERACTION
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_DEPTH_TEST);
		glfwSwapInterval(1); //V-SYNC
		glfwShowWindow(id);
		time = System.currentTimeMillis();
		frames = 0;
	}
	public void update()
	{
		glClearColor(bgColor.x, bgColor.y, bgColor.z, 1);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glfwPollEvents();
		frames++;
		if (System.currentTimeMillis() > time + 1000)
		{
			glfwSetWindowTitle(id, title + " | FPS: " + frames);
			time = System.currentTimeMillis();
			frames = 0;
		}
	}
	public void toggleMouseLocked()
	{
		mouseLocked = !mouseLocked;
		GLFW.glfwSetInputMode(id, GLFW.GLFW_CURSOR, mouseLocked ? GLFW.GLFW_CURSOR_DISABLED : GLFW.GLFW_CURSOR_NORMAL);
	}
	public void setBackgroundColor(Vector3f backgroundColor) { this.bgColor = backgroundColor; }
	public void swapBuffers() { glfwSwapBuffers(id); }
	public boolean shouldClose() { return glfwWindowShouldClose(id); }
	public void destroy()
	{
		input.destroy();
		glfwDestroyWindow(id);
		glfwTerminate();
	}
	public Matrix4f getProjection() { return projection; }
}
package core;

import drawing.*;
import input.Input;
import input.Window;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;

public class Main implements Runnable
{
	private Thread application;
	private Window window;
	private ShaderCollection shaders;
	private Renderer renderer;
	private boolean active;
	private int switchScenario = 1;

	private Scenario3D scenario3D;
	private Scenario2D scenario2D;
	private Scenario[] scenarioGroup;

	public void start()
	{
		application = new Thread(this, "Application");
		application.start();
	}
	@Override
	public void run()
	{
		init();
		loop();
		close();
	}
	private void init()
	{
		active = true;
		//OPENGL
		window = new Window(Constants.WND_W, Constants.WND_H, Constants.WND_TITLE);
		shaders = new ShaderCollection();
		renderer = new Renderer(window, shaders);
		try { window.create(); } catch (Exception e) { e.printStackTrace(); }
		shaders.createAll();

		//OTHER
		scenario3D = new Scenario3D(renderer);
		scenario2D = new Scenario2D(renderer);
		scenarioGroup = new Scenario[] { scenario3D, scenario2D };
	}
	private void loop()
	{
		while (active)
		{
			window.update();
			scenarioGroup[switchScenario].processCommands();
			scenarioGroup[switchScenario].update();
			scenarioGroup[switchScenario].render();
			window.swapBuffers();
			if (Input.consumeKeyTrigger(GLFW.GLFW_KEY_F1)) window.toggleMouseLocked();
			if ( switchScenario + 1 < scenarioGroup.length &&
					Input.consumeKeyTrigger(GLFW.GLFW_KEY_RIGHT) &&
					Input.consumeKeyTrigger(GLFW.GLFW_KEY_LEFT_CONTROL) &&
					Input.consumeKeyTrigger(GLFW.GLFW_KEY_LEFT_SHIFT))
				switchScenario++;
			if ( switchScenario > 0 &&
					Input.consumeKeyTrigger(GLFW.GLFW_KEY_LEFT) &&
					Input.consumeKeyTrigger(GLFW.GLFW_KEY_LEFT_CONTROL) &&
					Input.consumeKeyTrigger(GLFW.GLFW_KEY_LEFT_SHIFT))
				switchScenario--;
			if (window.shouldClose() || Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE))
				active = false;
		}
	}
	private void close()
	{
		window.destroy();
		Arrays.asList(scenarioGroup).forEach(Scenario::destroy);
		shaders.destroyAll();
	}
	
	//MAIN
	public static void main(String[] args) { new Main().start(); }
}
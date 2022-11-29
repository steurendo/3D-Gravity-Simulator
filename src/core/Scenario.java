package core;

import drawing.Renderer;
import parts.*;

public abstract class Scenario
{
	protected Renderer renderer;
	
	public Scenario(Renderer renderer) { this.renderer = renderer; }
	
	public abstract void render();
	public abstract void processCommands();
	public abstract void update();
	public abstract void destroy();
}
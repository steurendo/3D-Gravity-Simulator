package core;

import drawing.Renderer;
import input.Input;
import maths.MathF;
import maths.Vector3f;
import maths.Vector4f;
import org.lwjgl.glfw.GLFW;
import parts.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Scenario2D extends Scenario
{
    //DRAWABLE OBJECTS
    private Camera camera;
    private Background background;
    private Grid grid;
    private List<Entity> entities;

    //VARIABLES
    private boolean paused;

    public Scenario2D(Renderer renderer)
    {
        super(renderer);
        background = new Background(Constants.SPACE_CENTER.clone(), Constants.SPACE_RADIUS);
        background.initMesh();
        grid = new Grid(4);
        grid.initMesh();
        initializeEntities();
        camera = new Camera(new Vector3f(), new Vector3f());
    }

    @Override
    public void render()
    {
        renderer.renderBackground(background, camera);
        renderer.renderGrid(grid, camera, getEntitiesData());
        entities.forEach(e -> renderer.renderEntity(e, camera));
    }
    @Override
    public void processCommands()
    {
        if (Input.consumeKeyTrigger(GLFW.GLFW_KEY_P)) paused = !paused;
    }
    @Override
    public void update()
    {
        camera.update();
        background.update();
        if (!paused)
        {
            calculateInteractions();
            entities.forEach(Entity::update);
        }
    }
    @Override
    public void destroy()
    {
        background.getMesh().destroy();
        grid.getMesh().destroy();
        entities.forEach(e -> e.getMesh().destroy());
    }

    public Vector4f[] getEntitiesData()
    {
        return entities.stream()
                .map(entity -> new Vector4f(entity.getLocation().x, entity.getLocation().z, entity.getMass(), entity.getRadius()))
                .toArray(Vector4f[]::new);
    }
    private void initializeEntities()
    {
        entities = generateStartingEntities();
        entities.forEach(Entity::initMesh);
    }
    private List<Entity> generateStartingEntities()
    {
        ArrayList<Entity> entities;
        Entity temp;

        entities = new ArrayList<>();
        temp = new Entity(30000000f, 50, new Vector3f(5, 0, 0), new Vector3f(), new Vector3f(0, -0.1f, 0), EntityType.SUN);
        entities.add(temp);
        temp = new Entity(1000000, 50, new Vector3f(-5, 0, 0), new Vector3f(0, 0, 0.05f), new Vector3f(0, 2, 0), EntityType.EARTH_DAY);
        entities.add(temp);
        temp = new Entity(1000, 50, new Vector3f(-7, 0, 0), new Vector3f(0.005f, 0, 0.03f), new Vector3f(0, -1, 0), EntityType.MOON);
        entities.add(temp);

        return entities;
    }
    private void calculateInteractions()
    {
        int i, j;
        Object[] tmp;
        Entity entA, entB;
        float modAB, modBA, th, r;
        Vector3f vecAB, vecBA, posA, posB;

        tmp = entities.toArray();
        for (i = 0; i < tmp.length; i++)
            for (j = 0; j < i; j++)
            {
                entA = (Entity)tmp[i];
                entB = (Entity)tmp[j];
                posA = entA.getLocation();
                posB = entB.getLocation();
                r = entA.getDistanceFromCOM(entB);
                modAB = Constants.GRAVITATIONAL_CONSTANT * entA.getMass() / MathF.pow(r, 2);
                modBA = Constants.GRAVITATIONAL_CONSTANT * entB.getMass() / MathF.pow(r, 2);
                th = MathF.atan((posB.x - posA.x) / (posB.z - posA.z)) +
                        ((posB.z - posA.z) < 0 ? MathF.toRadians(180) : 0);
                vecAB = new Vector3f(-modAB * MathF.sin(th),
                        0,
                        -modAB * MathF.cos(th));
                vecBA = new Vector3f(modBA * MathF.sin(th),
                        0,
                        modBA * MathF.cos(th));
                vecAB.scale(Constants.TIME_SCALE_FACTOR);
                vecBA.scale(Constants.TIME_SCALE_FACTOR);
                entB.applyForce(vecAB);
                entA.applyForce(vecBA);
            }
    }
}

package drawing;

import core.Constants;
import maths.Vector2f;
import maths.Vector3f;
import parts.Background;
import parts.BackgroundTexture;
import parts.EntityType;
import parts.solids.PanedGrid;
import parts.solids.Solid;
import parts.solids.UVSphere;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MeshMaker
{
    private static boolean isQualityAvailable(EntityType entityType, TextureQuality quality)
    {
        return (MeshMaker.class.getResource("/textures/planets/" + quality.getQuality() + "/" + entityType.getTexture()) != null);
    }
    private static boolean isQualityAvailable(BackgroundTexture backgroundTexture, TextureQuality quality)
    {
        return (MeshMaker.class.getResource("/textures/backgrounds/" + quality.getQuality() + "/" + backgroundTexture.getTexture()) != null);
    }

    public static Mesh generateBackgroundMesh(BackgroundTexture backgroundTexture, int scaleFactor, TextureQuality quality)
    {
        Solid solid;
        Material material;
        String texturePath;

        if (isQualityAvailable(backgroundTexture, quality))
            texturePath = "/textures/backgrounds/" + quality.getQuality() + "/" + backgroundTexture.getTexture();
        else
            texturePath = "/textures/backgrounds/" + backgroundTexture.getTexture();
        material = new Material(texturePath);
        solid = new UVSphere(scaleFactor * Constants.NUMBER_OF_SECTORS, scaleFactor * Constants.NUMBER_OF_STACKS);
        solid.create();

        return solid.generateMesh(material);
    }
    public static Mesh generateMesh(EntityType entityType, TextureQuality quality)
    {
        Mesh result;

        switch (entityType)
        {
            case DIAMOND:
                result = generateMeshSquare(entityType, quality);
                break;
            default:
                result = generateMeshPlanet(entityType, quality);
        }

        return result;
    }
    public static Mesh generateMeshGrid(int squaresCount, Vector3f color)
    {
        PanedGrid grid;
        Material material;
        BufferedImage imageGrid;

        imageGrid = new BufferedImage(32, 32, BufferedImage.TYPE_4BYTE_ABGR);
        {
            Graphics2D g;

            g = imageGrid.createGraphics();
            g.setColor(new Color(color.x, color.y, color.z));
            g.drawRect(0, 0, 32, 32);
            g.setColor(new Color(0, 0, 0, 0));
            g.fillRect(1, 1, 30, 30);
        }
        material = new Material(imageGrid);
        grid = new PanedGrid(squaresCount, squaresCount);
        grid.create();

        return grid.generateMesh(material);
    }

    private static Mesh generateMeshSquare(EntityType entityType, TextureQuality quality)
    {
        Vertex[] vertices;
        int[] indices;
        Material material;
        String texturePath;

        if (isQualityAvailable(entityType, quality))
            texturePath = "/textures/planets/" + quality.getQuality() + "/" + entityType.getTexture();
        else
            texturePath = "/textures/planets/" + entityType.getTexture();
        material = new Material(texturePath);

        vertices = new Vertex[] {
                //Back face
                new Vertex(new Vector3f(-0.5f, 0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
                new Vertex(new Vector3f(0.5f, 0.5f, -0.5f), new Vector2f(1.0f, 0.0f)),

                //Front face
                new Vertex(new Vector3f(-0.5f, 0.5f, 0.5f), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, 0.5f), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(0.5f, -0.5f, 0.5f), new Vector2f(1.0f, 1.0f)),
                new Vertex(new Vector3f(0.5f, 0.5f, 0.5f), new Vector2f(1.0f, 0.0f)),

                //Right face
                new Vertex(new Vector3f(0.5f, 0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(0.5f, -0.5f, 0.5f), new Vector2f(1.0f, 1.0f)),
                new Vertex(new Vector3f(0.5f, 0.5f, 0.5f), new Vector2f(1.0f, 0.0f)),

                //Left face
                new Vertex(new Vector3f(-0.5f, 0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, 0.5f), new Vector2f(1.0f, 1.0f)),
                new Vertex(new Vector3f(-0.5f, 0.5f, 0.5f), new Vector2f(1.0f, 0.0f)),

                //Top face
                new Vertex(new Vector3f(-0.5f, 0.5f, 0.5f), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-0.5f, 0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(0.5f, 0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
                new Vertex(new Vector3f(0.5f, 0.5f, 0.5f), new Vector2f(1.0f, 0.0f)),

                //Bottom face
                new Vertex(new Vector3f(-0.5f, -0.5f, 0.5f), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
                new Vertex(new Vector3f(0.5f, -0.5f, 0.5f), new Vector2f(1.0f, 0.0f))
        };
        indices = new int[] {
                //Back face
                0, 1, 3,
                3, 1, 2,

                //Front face
                4, 5, 7,
                7, 5, 6,

                //Right face
                8, 9, 11,
                11, 9, 10,

                //Left face
                12, 13, 15,
                15, 13, 14,

                //Top face
                16, 17, 19,
                19, 17, 18,

                //Bottom face
                20, 21, 23,
                23, 21, 22
        };

        return new Mesh(vertices, indices, material);
    }
    private static Mesh generateMeshPlanet(EntityType entityType, TextureQuality quality)
    {
        Solid solid;
        Material material;
        String texturePath;

        if (isQualityAvailable(entityType, quality))
            texturePath = "/textures/planets/" + quality.getQuality() + "/" + entityType.getTexture();
        else
            texturePath = "/textures/planets/" + entityType.getTexture();
        material = new Material(texturePath);
        solid = new UVSphere(Constants.NUMBER_OF_SECTORS, Constants.NUMBER_OF_STACKS);
        solid.create();

        return solid.generateMesh(material);
    }
}

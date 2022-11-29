package drawing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Material
{
	private String path;
	private BufferedImage image;
	private Texture texture;
	private float width, height;
	private int textureID;
	private MaterialLoadMode loadMode;

	public Material(String path)
	{
		this.path = path;
		loadMode = MaterialLoadMode.PATH;
	}
	public Material(BufferedImage image)
	{
		this.image = image;
		loadMode = MaterialLoadMode.OBJECT;
	}

	public void create()
	{
		try
		{
			switch (loadMode)
			{
				case OBJECT:
					ByteArrayOutputStream os;

					os = new ByteArrayOutputStream();
					ImageIO.write(image, "PNG", os);
					texture = TextureLoader.getTexture("PNG", new ByteArrayInputStream(os.toByteArray()));
					break;
				case PATH:
					texture = TextureLoader.getTexture(path.split("[.]")[1], Material.class.getResourceAsStream(path), GL11.GL_NEAREST);
					break;
				default:
					break;
			}
		} catch (Exception e) { e.printStackTrace(); }
		width = texture.getWidth();
		height = texture.getHeight();
		textureID = texture.getTextureID();
	}

	public void destroy() { GL13.glDeleteTextures(textureID); }
	public float getWidth() { return width; }
	public float getHeight() { return height; }
	public int getTextureID() { return textureID; }
}
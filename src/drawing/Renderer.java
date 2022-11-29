package drawing;

import input.Window;
import maths.Matrix4f;
import maths.Vector4f;
import org.lwjgl.opengl.*;
import parts.*;

public class Renderer
{
	private Window window;
	private ShaderCollection shaders;

	public Renderer(Window window, ShaderCollection shaders)
	{
		this.window = window;
		this.shaders = shaders;
	}

	public void renderEntity(DrawableObject object, Camera camera)
	{
		Shader shader;

		GL30.glBindVertexArray(object.getMesh().getVAO());
		GL30.glEnableVertexAttribArray(0);
		GL30.glEnableVertexAttribArray(1);
		GL30.glEnableVertexAttribArray(2);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL13.glBindTexture(GL11.GL_TEXTURE_2D, object.getMesh().getMaterial().getTextureID());
		shader = shaders.getShaderObjects();
		shader.bind();
		shader.setUniform("model", Matrix4f.transform(object.getLocation(), object.getRotation(), object.getScale()));
		shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
		shader.setUniform("projection", window.getProjection());
		GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
		shader.unbind();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glDisableVertexAttribArray(0);
		GL30.glDisableVertexAttribArray(1);
		GL30.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	public void renderBackground(Background object, Camera camera)
	{
		Shader shader;

		GL30.glBindVertexArray(object.getMesh().getVAO());
		GL30.glEnableVertexAttribArray(0);
		GL30.glEnableVertexAttribArray(1);
		GL30.glEnableVertexAttribArray(2);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL13.glBindTexture(GL11.GL_TEXTURE_2D, object.getMesh().getMaterial().getTextureID());
		shader = shaders.getShaderObjects();
		shader.bind();
		shader.setUniform("model", Matrix4f.transform(object.getLocation(), object.getRotation(), object.getScale()));
		shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
		shader.setUniform("projection", window.getProjection());
		GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
		shader.unbind();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glDisableVertexAttribArray(0);
		GL30.glDisableVertexAttribArray(1);
		GL30.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	public void renderGrid(Grid grid, Camera camera, Vector4f[] entitiesData)
	{
		Shader shader;

		GL30.glBindVertexArray(grid.getMesh().getVAO());
		GL30.glEnableVertexAttribArray(0);
		GL30.glEnableVertexAttribArray(2);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, grid.getMesh().getIBO());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL13.glBindTexture(GL11.GL_TEXTURE_2D, grid.getMesh().getMaterial().getTextureID());
		shader = shaders.getShaderGrid();
		shader.bind();
		shader.setUniform("model", Matrix4f.transform(grid.getLocation(), grid.getRotation(), grid.getScale()));
		shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
		shader.setUniform("projection", window.getProjection());
		shader.setUniform("entities", entitiesData);
		shader.setUniform("entCount", entitiesData.length);
		GL11.glDrawElements(GL11.GL_TRIANGLES, grid.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
		shader.unbind();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glDisableVertexAttribArray(0);
		GL30.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
}
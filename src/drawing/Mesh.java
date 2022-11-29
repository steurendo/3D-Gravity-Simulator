package drawing;

import java.nio.*;

import core.Constants;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

public class Mesh
{
	protected Vertex[] vertices;
	protected int[] indices;
	protected int vao, pbo, ibo, tbo;
	protected Material material;
	
	public Mesh(Vertex[] vertices, int[] indices, Material material)
	{
		this.vertices = vertices;
		this.indices = indices;
		this.material = material;
	}
	
	public void create()
	{
		IntBuffer indicesBuffer;
		FloatBuffer positionBuffer, textureBuffer;
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);

		positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
		float[] positionData = new float[vertices.length * 3];
		for (int i = 0; i < vertices.length; i++) {
			positionData[i * 3] = vertices[i].getLocation().x;
			positionData[i * 3 + 1] = vertices[i].getLocation().y;
			positionData[i * 3 + 2] = vertices[i].getLocation().z;
		}
		positionBuffer.put(positionData).flip();
		pbo = storeData(positionBuffer, 0, 3);

		material.create();
		textureBuffer = MemoryUtil.memAllocFloat(vertices.length * 2);
		float[] textureData = new float[vertices.length * 2];
		for (int i = 0; i < vertices.length; i++)
		{
			textureData[i * 2] = vertices[i].getTextureCoord().getX();
			textureData[i * 2 + 1] = vertices[i].getTextureCoord().getY();
		}
		textureBuffer.put(textureData).flip();
		tbo = storeData(textureBuffer, 2, 2);

		indicesBuffer = MemoryUtil.memAllocInt(indices.length);
		indicesBuffer.put(indices).flip();

		ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	public int storeData(FloatBuffer buffer, int index, int size)
	{
		int bufferID;

		bufferID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		return bufferID;
	}
	public void destroy()
	{
		GL15.glDeleteBuffers(pbo);
		//GL15.glDeleteBuffers(cbo);
		GL15.glDeleteBuffers(ibo);
		GL15.glDeleteBuffers(tbo);
		material.destroy();

		GL30.glDeleteVertexArrays(vao);
	}
	public Vertex[] getVertices() { return vertices; }
	public int[] getIndices() { return indices; }
	public int getVAO() { return vao; }
	public int getPBO() { return pbo; }
	public int getIBO() { return ibo; }
	public Material getMaterial() { return material; }
}
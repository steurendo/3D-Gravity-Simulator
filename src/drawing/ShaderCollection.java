package drawing;

import core.Constants;

public class ShaderCollection
{
    private Shader shaderObjects;
    private Shader shaderGrid;

    public ShaderCollection()
    {
        shaderObjects = new Shader(Constants.SHADER_USED);
        shaderGrid = new Shader("shader_grid");
    }

    public Shader getShaderObjects() { return shaderObjects; }
    public Shader getShaderGrid() { return shaderGrid; }
    public void createAll()
    {
        shaderObjects.create();
        shaderGrid.create();
    }
    public void destroyAll()
    {
        shaderObjects.destroy();
        shaderGrid.destroy();
    }
}

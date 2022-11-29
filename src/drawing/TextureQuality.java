package drawing;

public enum TextureQuality
{
    STANDARD(""),
    HQ4K("4k");

    private String texture;

    TextureQuality(String texture)
    {
        this.texture = texture;
    }

    public String getQuality() { return texture; }
}

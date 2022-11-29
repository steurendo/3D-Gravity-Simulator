package parts;

public enum BackgroundTexture
{
    MILKYWAY("milkyway.jpg"),
    MILKYWAY2("milkyway2.jpg"),
    MILKYWAY3("milkyway3.jpg"),
    STARS("stars.jpg");

    private String texture;

    BackgroundTexture(String texture)
    {
        this.texture = texture;
    }

    public String getTexture() { return texture; }
}

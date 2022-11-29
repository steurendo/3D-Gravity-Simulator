package parts;

public enum EntityType
{
    DIAMOND("test.png"),
    CERES("ceres.jpg"),
    EARTH_DAY("earth_day.jpg"),
    EARTH_NIGHT("earth_night.jpg"),
    ERIS("eris.jpg"),
    HAUMEA("haumea.jpg"),
    JUPITER("jupiter.jpg"),
    MAKEMAKE("makemake.jpg"),
    MARS("mars.jpg"),
    MERCURY("mercury.jpg"),
    MOON("moon.jpg"),
    NEPTUNE("neptune.jpg"),
    SATURN("saturn.jpg"),
    SATURN_RINGS("saturn_rings.jpg"),
    SUN("sun.jpg"),
    URANUS("uranus.jpg"),
    VENUS_ATMOSPHERE("venus_atmosphere.jpg"),
    VENUS_SURFACE("venus_surface.jpg");

    private String texture;

    EntityType(String texture)
    {
        this.texture = texture;
    }

    public String getTexture() { return texture; }
}
#version 330 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 color;
layout(location = 2) in vec2 textureCoord;

out vec3 passColor;
out vec2 passTextureCoord;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;
uniform vec4 entities[100];
uniform int entCount;

#define G 0.0000001

float getDistance(float x1, float y1, float x2, float y2)
{
    return pow(x1 - x2, 2) + pow(y1 - y2, 2);
}
float deformation(float distance, float mass, float radius)
{
    // return (-mass * pow(2.72, -(distance + radius) / 2.2));
    return -G * mass / pow(distance + radius, 2);
}
vec4 vertexResult(vec3 pos)
{
    vec4 wPos;
    float x, y, z, distance, mass, radius;
    int i;

    wPos = model * vec4(pos, 1.0);
    y = pos.y;
    for (i = 0; i < entCount; i++)
    {
        x = entities[i].x;
        z = entities[i].y;
        mass = entities[i].z;
        radius = entities[i].w;
        distance = getDistance(x, z, wPos.x, wPos.z);
        y -= deformation(distance, mass, radius) / wPos.y;
    }

    return vec4(pos.x, y, pos.z, 1.0);
}

void main()
{
    vec4 pos;

    pos = vertexResult(position);
	gl_Position = projection * view * model * pos;
	passTextureCoord = textureCoord;
}
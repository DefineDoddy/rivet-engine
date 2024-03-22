#version 330 core

layout (location = 0) in vec3 in_position;

uniform vec3 colour;

out vec3 frag_colour;

void main() {
    gl_Position = vec4(in_position, 1.0);
    frag_colour = colour;
}

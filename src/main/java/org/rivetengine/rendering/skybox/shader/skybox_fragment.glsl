#version 330 core

in vec3 pass_tex_coords;

uniform samplerCube cube_map;

out vec4 frag_colour;

void main() {
    frag_colour = texture(cube_map, pass_tex_coords);
}
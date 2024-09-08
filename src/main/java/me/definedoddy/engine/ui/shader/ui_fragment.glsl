#version 330 core

in vec2 frag_tex_coords;

out vec4 frag_colour;

// Uniforms
uniform vec2 ui_dimensions;

void main() {
    vec2 tex_coords = frag_tex_coords / ui_dimensions;
    frag_colour = vec4(1.0, 1.0, 1.0, 1.0);
}
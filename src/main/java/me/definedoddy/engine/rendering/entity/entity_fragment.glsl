#version 330 core

in vec2 pass_tex_coords;

uniform vec3 tint_colour;
uniform sampler2D tex;

out vec4 frag_colour;

void main() {
    frag_colour = texture(tex, pass_tex_coords) * vec4(tint_colour, 1.0);
}
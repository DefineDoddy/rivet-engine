#version 330 core

in vec2 pass_tex_coords;
in vec3 pass_surface_normal;
in vec3 pass_light_direction;

uniform vec3 tint_colour;
uniform sampler2D tex;

uniform vec3 light_colour;

out vec4 frag_colour;

void main() {
    vec3 unit_normal = normalize(pass_surface_normal);
    vec3 unit_light_direction = normalize(pass_light_direction);

    float brightness = max(dot(unit_normal, unit_light_direction), 0.0);
    vec3 diffuse = brightness * light_colour;

    frag_colour = vec4(diffuse, 1.0) * texture(tex, pass_tex_coords) * vec4(tint_colour, 1.0);
}
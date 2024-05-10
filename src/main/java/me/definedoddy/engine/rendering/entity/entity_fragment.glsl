#version 330 core

in vec2 pass_tex_coords;
in vec3 pass_surface_normal;
in vec3 pass_light_directions[4];

uniform vec3 tint_colour;
uniform sampler2D tex;
uniform bool use_texture;

uniform vec3 light_colours[4];
uniform float light_inner_radii[4];
uniform float light_outer_radii[4];

out vec4 frag_colour;

float calc_attenuation(float distance, float inner_radius, float outer_radius);

void main() {
    vec3 unit_normal = normalize(pass_surface_normal);

    vec3 total_diffuse = vec3(0.0);

    for (int i = 0; i < 4; i++) {
        float distance = length(pass_light_directions[i]);
        float attenuation = calc_attenuation(distance, light_inner_radii[i], light_outer_radii[i]);

        vec3 unit_light_direction = normalize(pass_light_directions[i]);

        float brightness = max(dot(unit_normal, unit_light_direction), 0.0);
        total_diffuse += brightness * light_colours[i] * attenuation;
    }

    total_diffuse = max(total_diffuse, 0.2); // Ambient light

    if (use_texture) {
        frag_colour = vec4(total_diffuse, 1.0) * texture(tex, pass_tex_coords) * vec4(tint_colour, 1.0);
    } else {
        frag_colour = vec4(total_diffuse, 1.0) * vec4(tint_colour, 1.0);
    }
}

float calc_attenuation(float distance, float inner_radius, float outer_radius) {
    return clamp((inner_radius - distance) / (outer_radius - inner_radius), 0.0, 1.0);
}
#version 330 core

in vec2 pass_tex_coords;
in vec3 pass_surface_normal;
in vec3 pass_light_directions[4];

uniform vec3 tint_colour;
uniform sampler2D tex;
uniform bool use_texture;

uniform vec3 light_colours[4];
uniform float light_attenuations[4];

out vec4 frag_colour;

void main() {
    vec3 unit_normal = normalize(pass_surface_normal);

    vec3 total_diffuse = vec3(0.0);

    for (int i = 0; i < 4; i++) {
        float distance = length(pass_light_directions[i]);
        float attenuation = clamp(light_attenuations[i] / distance, 0.0, 1.0);

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
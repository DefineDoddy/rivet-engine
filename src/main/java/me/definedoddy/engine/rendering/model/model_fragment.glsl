#version 330 core

// Input (texture)
in vec2 pass_tex_coords;
in vec3 pass_surface_normal;

// Input (lighting)
in vec3 pass_light_directions[_MAX_LIGHTS_];
in vec3 pass_camera_direction;

// Uniform (texture)
uniform vec3 tint_colour;
uniform sampler2D tex;
uniform bool use_texture;

// Uniform (lighting)
uniform int num_lights;
uniform vec3 light_colours[_MAX_LIGHTS_];
uniform float reflectivity;

// Output
out vec4 frag_colour;

void main() {
    vec3 unit_normal = normalize(pass_surface_normal);
    vec3 unit_camera_direction = normalize(pass_camera_direction);

    vec3 total_diffuse = vec3(0.0);
    vec3 total_specular = vec3(0.0);

    for (int i = 0; i < num_lights; i++) {
        float distance = length(pass_light_directions[i]);
        vec3 unit_light_direction = normalize(pass_light_directions[i]);

        // Specular lighting
        float shine_damper = 10.0;
        vec3 unit_reflection = reflect(-unit_light_direction, unit_normal);
        float specular_factor = max(dot(unit_reflection, unit_camera_direction), 0.0);
        total_specular += pow(specular_factor, shine_damper) * reflectivity * light_colours[i];

        float brightness = max(dot(unit_normal, unit_light_direction), 0.0);
        total_diffuse += brightness * light_colours[i];
    }

    total_diffuse = max(total_diffuse, 0.2); // Ambient light

    // Texture colour
    vec3 texture_colour = vec3(1.0);
    if (use_texture) total_diffuse *= texture(tex, pass_tex_coords).rgb;

    vec3 output_colour = total_diffuse + total_specular;
    frag_colour = vec4(output_colour * tint_colour, 1.0);
}

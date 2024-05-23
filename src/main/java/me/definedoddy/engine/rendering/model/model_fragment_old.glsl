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
uniform vec3 light_attenuations[_MAX_LIGHTS_];
uniform float reflectivity;
uniform float ambient_light;

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

        // Diffuse lighting
        float brightness = max(dot(unit_normal, unit_light_direction), 0.0);
        total_diffuse += brightness * light_colours[i];

        // Specular lighting
        float shine_damper = 10.0;
        vec3 unit_reflection = reflect(-unit_light_direction, unit_normal);
        float specular_factor = max(dot(unit_reflection, unit_camera_direction), 0.0);
        total_specular += pow(specular_factor, shine_damper) * reflectivity * light_colours[i];

        // Attenuation
        float attenuation = light_attenuations[i].x + light_attenuations[i].y * distance + light_attenuations[i].z * distance * distance;
        total_diffuse /= attenuation;
        total_specular /= attenuation;
    }

    // Ambient light
    total_diffuse = max(total_diffuse, ambient_light);

    // Texture
    vec4 texture_colour = vec4(1.0);
    if (use_texture) texture_colour = texture(tex, pass_tex_coords);
    texture_colour.rgb *= tint_colour;

    // Transparency
    if (texture_colour.a < 0.5) discard;

    frag_colour = vec4(total_diffuse, 1.0) * texture_colour + vec4(total_specular, 1.0);
}

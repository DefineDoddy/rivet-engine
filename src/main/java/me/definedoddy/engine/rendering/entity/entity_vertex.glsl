#version 330 core

// Input (vertex)
layout (location = 0) in vec3 position;
layout (location = 1) in vec2 tex_coords;
layout (location = 2) in vec3 normal;

// Uniform (transform)
uniform mat4 transform_matrix;
uniform mat4 projection_matrix;
uniform mat4 view_matrix;

// Uniform (lighting)
uniform vec3 light_positions[4];

// Output (texture)
out vec2 pass_tex_coords;
out vec3 pass_surface_normal;

// Output (lighting)
out vec3 pass_light_directions[4];
out vec3 pass_camera_direction;

void main() {
    vec4 world_pos = transform_matrix * vec4(position, 1.0);

    gl_Position = projection_matrix * view_matrix * world_pos;

    pass_tex_coords = tex_coords;
    pass_surface_normal = (transform_matrix * vec4(normal, 0.0)).xyz;

    for (int i = 0; i < 4; i++) {
        pass_light_directions[i] = light_positions[i] - world_pos.xyz;
    }

    pass_camera_direction = (inverse(view_matrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - world_pos.xyz;
}

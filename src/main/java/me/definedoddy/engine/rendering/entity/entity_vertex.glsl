#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 tex_coords;
layout (location = 2) in vec3 normal;

uniform mat4 transform_matrix;
uniform mat4 projection_matrix;
uniform mat4 view_matrix;

uniform vec3 light_position;

out vec2 pass_tex_coords;
out vec3 pass_surface_normal;
out vec3 pass_light_direction;

void main() {
    vec4 world_pos = transform_matrix * vec4(position, 1.0);

    gl_Position = projection_matrix * view_matrix * world_pos;

    pass_tex_coords = tex_coords;
    pass_surface_normal = (transform_matrix * vec4(normal, 0.0)).xyz;
    pass_light_direction = light_position - world_pos.xyz;
}

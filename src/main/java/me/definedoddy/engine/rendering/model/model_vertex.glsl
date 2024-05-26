#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 tex_coords;
layout (location = 2) in vec3 normal;

out vec3 frag_position;
out vec2 frag_tex_coords;
out vec3 frag_normal;
out vec3 frag_view_dir;

// Uniforms
uniform mat4 transform_matrix;
uniform mat4 projection_matrix;
uniform mat4 view_matrix;

void main()
{
    vec4 world_pos = transform_matrix * vec4(position, 1.0);

    frag_position = vec3(world_pos);
    frag_tex_coords = tex_coords;
    frag_normal = (transform_matrix * vec4(normal, 0.0)).xyz;
    frag_view_dir = (inverse(view_matrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - world_pos.xyz;

    gl_Position = projection_matrix * view_matrix * world_pos;
}

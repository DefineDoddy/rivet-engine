#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 tex_coords;
layout (location = 2) in vec3 normal;

out vec2 frag_tex_coords;
out vec3 frag_normal;
out vec3 frag_world_position;

// Uniforms
uniform mat4 transform_matrix;
uniform mat4 projection_matrix;
uniform mat4 view_matrix;

void main()
{
    vec4 world_position = transform_matrix * vec4(position, 1.0);
    frag_world_position = world_position.xyz;
    frag_tex_coords = tex_coords;
    frag_normal = mat3(transpose(inverse(transform_matrix))) * normal;
    gl_Position = projection_matrix * view_matrix * world_position;
}

#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 tex_coords;

uniform mat4 transform_matrix;
uniform mat4 projection_matrix;
uniform mat4 view_matrix;

out vec2 pass_tex_coords;

void main() {
    gl_Position = projection_matrix * view_matrix * transform_matrix * vec4(position, 1.0);
    pass_tex_coords = tex_coords;
}

#version 330 core

in vec3 tex_coords;

uniform mat4 projection_matrix;
uniform mat4 view_matrix;
uniform float rotation;

out vec3 pass_tex_coords;

void main() {
    mat4 rotated_view_matrix = view_matrix * mat4(
        cos(rotation), 0.0, sin(rotation), 0.0,
        0.0, 1.0, 0.0, 0.0,
        -sin(rotation), 0.0, cos(rotation), 0.0,
        0.0, 0.0, 0.0, 1.0
    );

    gl_Position = (projection_matrix * rotated_view_matrix * vec4(tex_coords, 1.0)).xyww;
    pass_tex_coords = tex_coords;
}

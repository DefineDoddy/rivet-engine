#version 330 core

in vec3 tex_coords;

uniform mat4 projection_matrix;
uniform mat4 view_matrix;
uniform float rotation;

out vec3 pass_tex_coords;

void main() {
    gl_Position = projection_matrix * view_matrix * vec4(tex_coords, 1.0);

    // Rotate the texture coordinates
    pass_tex_coords = vec3(
        tex_coords.x * cos(rotation) - tex_coords.z * sin(rotation),
        tex_coords.y,
        tex_coords.x * sin(rotation) + tex_coords.z * cos(rotation)
    );
}

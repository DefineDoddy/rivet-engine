#version 330 core

in vec2 frag_texCoords;

uniform vec3 colour;
uniform sampler2D texSampler;

out vec4 fragColour;

void main() {
    fragColour = texture(texSampler, frag_texCoords) * vec4(colour, 1.0);
}

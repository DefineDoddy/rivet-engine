#version 400 core

in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D colourTexture;
uniform float intensity;

void main(void){
    vec2 dist = textureCoords - 0.5;
    vec2 offset = dist * intensity * 0.05;
    
    float r = texture(colourTexture, textureCoords - offset).r;
    float g = texture(colourTexture, textureCoords).g;
    float b = texture(colourTexture, textureCoords + offset).b;
    
    out_Color = vec4(r, g, b, 1.0);
}

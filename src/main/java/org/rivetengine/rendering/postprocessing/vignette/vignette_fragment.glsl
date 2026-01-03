#version 400 core

in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D colourTexture;
uniform float intensity;
uniform float smoothness;

void main(void){
    vec4 color = texture(colourTexture, textureCoords);
    
    vec2 d = textureCoords - 0.5;
    float len = length(d);
    float vignette = smoothstep(1.0 - intensity, 1.0 - intensity - smoothness, len);
    color.rgb *= vignette;

    out_Color = color;
}

#version 400 core

in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D colourTexture;
uniform float exposure;
uniform float contrast;
uniform float saturation;

void main(void){
    vec4 color = texture(colourTexture, textureCoords);

    // Exposure
    color.rgb *= exposure;
    
    // Contrast
    color.rgb = (color.rgb - 0.5) * contrast + 0.5;
    
    // Saturation
    float gray = dot(color.rgb, vec3(0.299, 0.587, 0.114));
    color.rgb = mix(vec3(gray), color.rgb, saturation);

    out_Color = color;
}

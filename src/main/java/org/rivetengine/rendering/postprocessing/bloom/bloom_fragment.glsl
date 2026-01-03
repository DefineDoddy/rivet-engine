#version 400 core

in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D colourTexture;
uniform float threshold;
uniform float intensity;

void main(void){
	vec3 baseColor = texture(colourTexture, textureCoords).rgb;
	vec2 texelSize = 1.0 / vec2(textureSize(colourTexture, 0));

	const vec2 offsets[9] = vec2[](
		vec2(-1.0, -1.0), vec2(0.0, -1.0), vec2(1.0, -1.0),
		vec2(-1.0,  0.0), vec2(0.0,  0.0), vec2(1.0,  0.0),
		vec2(-1.0,  1.0), vec2(0.0,  1.0), vec2(1.0,  1.0)
	);

	const float weights[9] = float[](1.0, 2.0, 1.0,
									 2.0, 4.0, 2.0,
									 1.0, 2.0, 1.0);

	vec3 bloom = vec3(0.0);

	for(int i = 0; i < 9; i++){
		vec3 sampleColor = texture(colourTexture, textureCoords + offsets[i] * texelSize).rgb;
		float luminance = dot(sampleColor, vec3(0.2126, 0.7152, 0.0722));
		float mask = max(luminance - threshold, 0.0);
		bloom += sampleColor * mask * weights[i];
	}

	bloom /= 16.0; // Sum of weights

	vec3 finalColor = baseColor + bloom * intensity;
	out_Color = vec4(clamp(finalColor, 0.0, 1.0), 1.0);
}

#version 330 core

in vec2 frag_tex_coords;
in vec3 frag_normal;
in vec3 frag_world_position;

out vec4 frag_colour;

struct Material {
    sampler2D diffuse;
    bool has_diffuse;

    sampler2D normal;
    bool has_normal;

    sampler2D specular;
    bool has_specular;

    float shininess;
};

struct Light {
    vec3 position; // Position for point and spot lights
    vec3 direction; // Direction for directional lights

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;

    float constant;
    float linear;
    float quadratic;

    float inner_radius; // For spot light
    float outer_radius; // For spot light

    int type; // 0 for directional, 1 for point, 2 for spot
};

uniform Material material;
uniform Light lights[_MAX_LIGHTS_];
uniform int light_count;

vec3 calc_light(Light light, vec3 normal, vec3 fragPos, vec3 viewDir);

void main() {
    vec4 diffuse_colour = vec4(1.0);
    if (material.has_diffuse) diffuse_colour = texture(material.diffuse, frag_tex_coords);
    if (diffuse_colour.a < 0.1) discard; // Discard fully transparent pixels

    vec3 normal = normalize(frag_normal);
    vec3 viewDir = normalize(-frag_world_position);

    vec3 result = vec3(0.0);

    for (int i = 0; i < light_count; ++i) {
        result += calc_light(lights[i], normal, frag_world_position, viewDir);
    }

    frag_colour = vec4(result, 1.0) * diffuse_colour;
}

vec3 calc_light(Light light, vec3 normal, vec3 fragPos, vec3 viewDir) {
    vec4 diffuse_colour = texture(material.diffuse, frag_tex_coords);
    vec4 specular_colour = texture(material.specular, frag_tex_coords);

    vec3 lightDir = normalize(light.position - fragPos);

    vec3 ambient = light.ambient * vec3(diffuse_colour);

    float diff = max(dot(normal, lightDir), 0.0);
    vec3 diffuse = light.diffuse * diff * vec3(diffuse_colour);

    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    vec3 specular = light.specular * spec * vec3(specular_colour);

    float distance = length(light.position - fragPos);
    float attenuation = 1.0 / (light.constant + light.linear * distance + light.quadratic * (distance * distance));

    if (light.type == 2) { // Spot light
        float theta = dot(lightDir, normalize(-light.direction));
        float epsilon = light.inner_radius - light.outer_radius;
        float intensity = clamp((theta - light.outer_radius) / epsilon, 0.0, 1.0);
        attenuation *= intensity;
    }

    return (ambient + diffuse + specular) * attenuation;
}

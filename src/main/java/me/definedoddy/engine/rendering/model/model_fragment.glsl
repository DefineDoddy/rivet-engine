#version 330 core

#define SHINE_DAMPER 10.0

in vec3 frag_position;
in vec2 frag_tex_coords;
in vec3 frag_normal;
in vec3 frag_view_dir;

out vec4 frag_colour;

struct Material {
    sampler2D diffuse;
    sampler2D normal;
    sampler2D specular;

    bool has_diffuse;
    bool has_normal;
    bool has_specular;

    vec3 tint_colour;
    float shininess;
    bool transparent;
};

struct Light {
    vec3 position; // Position for point and spot lights
    vec3 direction; // Direction for directional lights

    vec3 colour;
    float intensity;

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

vec3 calc_dir_light(Light light, vec3 normal, vec3 view_dir);
vec3 calc_point_light(Light light, vec3 normal, vec3 view_dir);
vec3 calc_spot_light(Light light, vec3 normal, vec3 view_dir);

vec3 calc_specular(Light light, vec3 normal, vec3 view_dir);

void main() {
    if (material.transparent) {
        vec4 diffuse = texture(material.diffuse, frag_tex_coords);
        if (diffuse.a < 0.1) discard;
    }

    vec3 normal = normalize(frag_normal);
    vec3 view_dir = normalize(frag_view_dir);

    vec3 result = vec3(0.0);
    for (int i = 0; i < light_count; i++) {
        Light light = lights[i];
        if (light.type == 0) {
            result += calc_dir_light(light, normal, view_dir);
        } else if (light.type == 1) {
            result += calc_point_light(light, normal, view_dir);
        } else if (light.type == 2) {
            result += calc_spot_light(light, normal, view_dir);
        }
    }

    frag_colour = vec4(result, 1.0);
}

vec3 calc_dir_light(Light light, vec3 normal, vec3 view_dir) {
    vec3 light_dir = normalize(-light.direction);

    float brightness = max(dot(normal, light_dir), 0.0) * light.intensity;
    vec3 diffuse = brightness * light.colour * material.tint_colour;

    vec3 specular = calc_specular(light, normal, view_dir);

    if (material.has_diffuse) {
        diffuse *= texture(material.diffuse, frag_tex_coords).rgb;
    }

    if (material.has_specular) {
        specular *= texture(material.specular, frag_tex_coords).r;
    }

    return diffuse + specular;
}

vec3 calc_point_light(Light light, vec3 normal, vec3 view_dir) {
    vec3 light_dir = normalize(light.position - frag_position);
    float distance = length(light.position - frag_position);
    float attenuation = 1.0 / (light.constant + light.linear * distance + light.quadratic * (distance * distance));

    float brightness = max(dot(normal, light_dir), 0.0) * light.intensity;
    vec3 diffuse = brightness * light.colour * material.tint_colour * attenuation;

    vec3 specular = calc_specular(light, normal, view_dir) * attenuation;

    if (material.has_diffuse) {
        diffuse *= texture(material.diffuse, frag_tex_coords).rgb;
    }

    if (material.has_specular) {
        specular *= texture(material.specular, frag_tex_coords).r;
    }

    return diffuse + specular;
}

vec3 calc_spot_light(Light light, vec3 normal, vec3 view_dir) {
    vec3 light_dir = normalize(light.position - frag_position);
    float distance = length(light.position - frag_position);
    float attenuation = 1.0 / (light.constant + light.linear * distance + light.quadratic * (distance * distance));

    float spot_factor = dot(normalize(light.direction), -light_dir);
    float inner_cutoff = cos(radians(light.inner_radius));
    float outer_cutoff = cos(radians(light.outer_radius));
    float spot_brightness = clamp((spot_factor - outer_cutoff) / (inner_cutoff - outer_cutoff), 0.0, 1.0);

    float brightness = max(dot(normal, light_dir), 0.0) * light.intensity;
    vec3 diffuse = brightness * light.colour * material.tint_colour * attenuation * spot_brightness;

    vec3 specular = calc_specular(light, normal, view_dir) * attenuation * spot_brightness;

    if (material.has_diffuse) {
        diffuse *= texture(material.diffuse, frag_tex_coords).rgb;
    }

    if (material.has_specular) {
        specular *= texture(material.specular, frag_tex_coords).r;
    }

    return diffuse + specular;
}

vec3 calc_specular(Light light, vec3 normal, vec3 view_dir) {
    vec3 light_dir = normalize(light.position - frag_position);
    vec3 unit_reflection = reflect(-light_dir, normal);
    float specular_factor = max(dot(unit_reflection, view_dir), 0.0);
    return pow(specular_factor, SHINE_DAMPER) * material.shininess * light.colour;
}
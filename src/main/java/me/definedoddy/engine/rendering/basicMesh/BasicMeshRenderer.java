package me.definedoddy.engine.rendering.basicMesh;

import me.definedoddy.engine.rendering.object.mesh.Mesh;

import java.util.ArrayList;
import java.util.List;

public class BasicMeshRenderer {
    private final BasicMeshShader shader;
    private final List<Mesh> meshes = new ArrayList<>();

    public BasicMeshRenderer(BasicMeshShader shader) {
        this.shader = shader;
    }

    public void render() {
        shader.bind();
        for (Mesh mesh : meshes) {
            shader.getColour().loadColour(mesh.getColour());
            mesh.render();
        }
        shader.unbind();
    }

    public void addMeshToRender(Mesh mesh) {
        meshes.add(mesh);
    }

    public BasicMeshShader getShader() {
        return shader;
    }
}

package me.definedoddy.engine.rendering.basicMesh;

import me.definedoddy.engine.rendering.object.Mesh;
import me.definedoddy.toolkit.debug.DebugLog;

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
        DebugLog.info("Rendering " + meshes.size() + " meshes");
        for (Mesh mesh : meshes) {
            shader.getColour().loadColour(mesh.getColor());
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

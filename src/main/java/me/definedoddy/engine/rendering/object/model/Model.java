package me.definedoddy.engine.rendering.object.model;

import me.definedoddy.engine.manager.GameManager;
import me.definedoddy.engine.rendering.object.mesh.Mesh;
import me.definedoddy.engine.rendering.shader.model.ModelShader;
import me.definedoddy.engine.rendering.texture.Material;
import me.definedoddy.engine.utils.maths.MathsUtils;
import me.definedoddy.toolkit.debug.Debug;
import me.definedoddy.toolkit.debug.DebugHandler;
import me.definedoddy.toolkit.debug.draw.Line;
import me.definedoddy.toolkit.memory.Disposable;
import me.definedoddy.toolkit.model.obj.Vertex;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.util.Map;

public class Model implements Disposable {
    private final Map<Mesh, Material> meshData;
    private final Material material;

    private DebugHandler debugHandler;
    private boolean renderNormals;
    private boolean wireframe;

    public Model(Mesh mesh) {
        this(mesh, new Material());
    }

    public Model(Mesh mesh, Material material) {
        this(Map.of(mesh, material), material);
    }

    public Model(@NotNull Map<Mesh, Material> meshData, @NotNull Material material) {
        this.meshData = meshData;
        this.material = material;
        initDebugHandler();
    }

    private void initDebugHandler() {
        debugHandler = new DebugHandler();
        debugHandler.createRenderNormalsCallback(visible -> renderNormals = visible);
        debugHandler.createWireframeCallback(visible -> wireframe = visible);
    }

    public void render(Vector3f position, Vector3f rotation, Vector3f scale) {
        ModelShader modelShader = GameManager.getRenderEngine().getModelRenderer().getShader();

        Matrix4f transformMat = MathsUtils.createTransformationMatrix(position, rotation, scale);
        modelShader.getTransformMatrix().loadMatrix(transformMat);
        modelShader.getModelMaterial().loadMaterial(material);

        if (wireframe) GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);

        for (Map.Entry<Mesh, Material> entry : meshData.entrySet()) {
            Mesh mesh = entry.getKey();
            Material material = entry.getValue();

            modelShader.getMeshMaterial().loadMaterial(material);
            material.bind();

            mesh.bind();
            mesh.render();
            mesh.unbind();

            material.unbind();
        }

        if (wireframe) GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);

        if (renderNormals) renderNormals(position, rotation, scale);
    }

    private void renderNormals(Vector3f position, Vector3f rotation, Vector3f scale) {
        for (Mesh mesh : meshData.keySet()) {
            for (Vertex vertex : mesh.getVertices()) {
                Vector3f normal = vertex.getNormal();
                Vector3f positionOffset = new Vector3f(normal).mul(0.1f);
                Vector3f vertexPosition = new Vector3f(vertex.getPosition()).mul(scale)
                        .rotateX(rotation.x).rotateY(rotation.y).rotateZ(rotation.z).add(position);
                Vector3f normalPosition = new Vector3f(vertexPosition).add(positionOffset);
                Debug.drawLine(new Line(vertexPosition, normalPosition));
            }
        }
    }

    public void setRenderNormals(boolean renderNormals) {
        this.renderNormals = renderNormals;
    }

    public void setWireframe(boolean wireframe) {
        this.wireframe = wireframe;
    }

    public Map<Mesh, Material> getMeshData() {
        return meshData;
    }

    public Material getMaterial() {
        return material;
    }

    @Override
    public void dispose() {
        for (Mesh mesh : meshData.keySet()) {
            mesh.dispose();
        }
        material.dispose();
        debugHandler.dispose();
    }
}

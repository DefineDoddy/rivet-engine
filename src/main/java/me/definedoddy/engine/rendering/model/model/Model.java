package me.definedoddy.engine.rendering.model.model;

import me.definedoddy.engine.manager.GameManager;
import me.definedoddy.engine.physics.collision.BoundingBox;
import me.definedoddy.engine.rendering.model.mesh.Mesh;
import me.definedoddy.engine.rendering.model.shader.ModelShader;
import me.definedoddy.engine.rendering.texture.Material;
import me.definedoddy.engine.utils.maths.MathsUtils;
import me.definedoddy.engine.debug.Debug;
import me.definedoddy.engine.debug.DebugHandler;
import me.definedoddy.engine.debug.draw.Line;
import me.definedoddy.toolkit.memory.Disposable;
import me.definedoddy.toolkit.model.obj.Vertex;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.util.Map;

public class Model implements Disposable {
    private final MeshMap meshMap;
    private final Material material;

    private DebugHandler debugHandler;
    private boolean renderNormals;
    private boolean wireframe;

    private BoundingBox boundingBox;
    private Vector3f origin;
    private Vector3f normal;

    public Model(Mesh mesh) {
        this(mesh, new Material());
    }

    public Model(Mesh mesh, Material material) {
        this(new MeshMap(mesh), material);
    }

    public Model(@NotNull MeshMap meshMap, @NotNull Material material) {
        this.meshMap = meshMap;
        this.material = material;
        initDebugHandler();
    }

    private void initDebugHandler() {
        debugHandler = new DebugHandler();
        debugHandler.createRenderNormalsCallback(visible -> renderNormals = visible);
        debugHandler.createWireframeCallback(visible -> wireframe = visible);
    }

    public void renderMesh(String meshId, Vector3f position, Vector3f rotation, Vector3f scale) {
        ModelShader modelShader = GameManager.getRenderEngine().getModelRenderer().getShader();

        Matrix4f transformMat = MathsUtils.createTransformationMatrix(position, rotation, scale);
        modelShader.getTransformMatrix().loadMatrix(transformMat);
        modelShader.getModelMaterial().loadMaterial(material);

        if (wireframe) GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);

        // Get mesh and material from map and render
        Map.Entry<Mesh, Material> entry = meshMap.get(meshId);
        Mesh mesh = entry.getKey();
        Material material = entry.getValue();

        modelShader.getMeshMaterial().loadMaterial(material);
        material.bind();

        mesh.render();

        material.unbind();

        if (wireframe) GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
        if (renderNormals) renderNormals(position, rotation, scale);
    }

    private void renderNormals(Vector3f position, Vector3f rotation, Vector3f scale) {
        for (Mesh mesh : meshMap.getData().keySet()) {
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

    public MeshMap getMeshMap() {
        return meshMap;
    }

    public Material getMaterial() {
        return material;
    }

    public BoundingBox getBoundingBox() {
        if (boundingBox != null) return boundingBox;

        Vector3f min = new Vector3f(Float.MAX_VALUE);
        Vector3f max = new Vector3f(Float.MIN_VALUE);

        for (Mesh mesh : meshMap.getData().keySet()) {
            for (Vertex vertex : mesh.getVertices()) {
                Vector3f position = new Vector3f(vertex.getPosition());
                min.min(position);
                max.max(position);
            }
        }

        return boundingBox = new BoundingBox(min, max);
    }

    public Vector3f getOrigin() {
        if (origin != null) return origin;

        Vector3f min = new Vector3f(Float.MAX_VALUE);
        Vector3f max = new Vector3f(Float.MIN_VALUE);

        for (Mesh mesh : meshMap.getData().keySet()) {
            for (Vertex vertex : mesh.getVertices()) {
                Vector3f position = new Vector3f(vertex.getPosition());
                min.min(position);
                max.max(position);
            }
        }

        return origin = new Vector3f(min).add(new Vector3f(max).sub(min));
    }

    public Vector3f getNormal() {
        if (normal != null) return normal;

        Vector3f normal = new Vector3f();
        for (Mesh mesh : meshMap.getData().keySet()) {
            for (Vertex vertex : mesh.getVertices()) {
                normal.add(vertex.getNormal());
            }
        }
        return this.normal = normal.normalize();
    }

    @Override
    public void dispose() {
        for (Mesh mesh : meshMap.getData().keySet()) {
            mesh.dispose();
        }
        material.dispose();
        debugHandler.dispose();
    }

    @Override
    public Model clone() {
        return new Model(meshMap.clone(), material.clone());
    }
}

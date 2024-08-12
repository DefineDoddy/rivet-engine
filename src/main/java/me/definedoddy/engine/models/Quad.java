package me.definedoddy.engine.models;

import me.definedoddy.engine.rendering.object.mesh.OldMesh;
import me.definedoddy.engine.rendering.object.model.Model;
import me.definedoddy.engine.rendering.texture.Material;
import org.joml.Vector3f;

public class Quad extends Model {
    private final Vector3f size;

    public Quad(Vector3f size, Material material) {
        super(null, material);
        this.size = size;
    }

    private static OldMesh createMesh(Vector3f size) {
        OldMesh mesh = new OldMesh();
        mesh.setVertexPositions(genVertexPositions(size));
        mesh.setNormals(NORMALS);
        mesh.setTextureCoords(TEXTURE_COORDS);
        mesh.setIndices(INDICES);
        return mesh;
    }

    public Vector3f getSize() {
        return size;
    }

    private static float[] genVertexPositions(Vector3f size) {
        return new float[] {
                -size.x / 2, size.y / 2, 0,
                -size.x / 2, -size.y / 2, 0,
                size.x / 2, -size.y / 2, 0,
                size.x / 2, size.y / 2, 0
        };
    }

    private static final float[] NORMALS = new float[] {
            0, 0, 1,
            0, 0, 1,
            0, 0, 1,
            0, 0, 1
    };

    private static final float[] TEXTURE_COORDS = new float[] {
            0, 0,
            0, 1,
            1, 1,
            1, 0
    };

    private static final int[] INDICES = new int[] {
            0, 1, 3,
            3, 1, 2
    };
}

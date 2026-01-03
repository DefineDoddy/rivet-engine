package org.rivetengine.utils;

import org.rivetengine.core.Assets;
import org.rivetengine.entity.Entity;
import org.rivetengine.entity.components.rendering.Mesh3d;
import org.rivetengine.physics.collision.BoundingBox;
import org.rivetengine.rendering.mesh.Mesh;
import org.rivetengine.entity.components.Transform;
import org.joml.Vector3f;
import org.joml.Matrix4f;

public class EntityUtils {
    public static BoundingBox getTotalBounds(Entity entity) {
        Vector3f min = new Vector3f(Float.MAX_VALUE);
        Vector3f max = new Vector3f(-Float.MAX_VALUE);
        boolean found = calculateTotalBounds(entity, new Matrix4f(), min, max);

        if (!found)
            return null;
        return BoundingBox.fromMinMax(min, max);
    }

    private static boolean calculateTotalBounds(Entity entity, Matrix4f parentMatrix, Vector3f min, Vector3f max) {
        boolean found = false;
        Transform transform = entity.getComponent(Transform.class);
        Matrix4f currentMatrix = new Matrix4f(parentMatrix);
        if (transform != null) {
            currentMatrix.mul(transform.getLocalMatrix());
        }

        Mesh3d mesh3d = entity.getComponent(Mesh3d.class);
        if (mesh3d != null) {
            Mesh mesh = Assets.get(mesh3d.mesh);
            if (mesh != null && mesh.getBounds() != null) {
                BoundingBox bounds = mesh.getBounds();

                // Transform 8 corners of the bounding box
                Vector3f[] corners = {
                        new Vector3f(bounds.getMin()),
                        new Vector3f(bounds.getMax().x, bounds.getMin().y, bounds.getMin().z),
                        new Vector3f(bounds.getMin().x, bounds.getMax().y, bounds.getMin().z),
                        new Vector3f(bounds.getMin().x, bounds.getMin().y, bounds.getMax().z),
                        new Vector3f(bounds.getMin().x, bounds.getMax().y, bounds.getMax().z),
                        new Vector3f(bounds.getMax().x, bounds.getMin().y, bounds.getMax().z),
                        new Vector3f(bounds.getMax().x, bounds.getMax().y, bounds.getMin().z),
                        new Vector3f(bounds.getMax())
                };

                for (Vector3f corner : corners) {
                    corner.mulPosition(currentMatrix);
                    min.min(corner);
                    max.max(corner);
                }
                found = true;
            }
        }

        for (Entity child : entity.getChildren()) {
            if (calculateTotalBounds(child, currentMatrix, min, max)) {
                found = true;
            }
        }
        return found;
    }
}

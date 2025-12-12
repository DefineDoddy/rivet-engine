package org.rivetengine.physics.collision;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class SATCollision {
    private static Vector3f collisionNormal;
    private static float penetrationDepth = Float.MAX_VALUE;

    public static boolean arePolygonsColliding(Vector3f[] poly1, Vector3f[] poly2) {
        Vector3f[] axes = getSeparatingAxes(poly1, poly2);

        collisionNormal = null;
        penetrationDepth = Float.MAX_VALUE; // Reset the depth for each collision check

        // Check projections on all potential separating axes
        for (Vector3f axis : axes) {
            // Normalize the axis to get a unit vector (this is crucial for correct depth calculation)
            axis.normalize();

            if (!isProjectionOverlapping(poly1, poly2, axis)) {
                return false; // No overlap found, polygons do not collide
            }
        }

        return true; // All projections overlap, polygons collide
    }

    private static Vector3f[] getSeparatingAxes(Vector3f[] poly1, Vector3f[] poly2) {
        List<Vector3f> axes = new ArrayList<>();

        // Add face normals from both polygons as potential separating axes
        axes.addAll(getFaceNormals(poly1));
        axes.addAll(getFaceNormals(poly2));

        // Add cross products of edges from both polygons as potential separating axes
        axes.addAll(getEdgeCrossProducts(poly1, poly2));

        return axes.toArray(new Vector3f[0]);
    }

    private static List<Vector3f> getFaceNormals(Vector3f[] vertices) {
        List<Vector3f> normals = new ArrayList<>();
        int vertexCount = vertices.length;

        // Assuming the polygon is composed of triangles, we calculate the normals of each triangle
        for (int i = 0; i < vertexCount; i += 3) {
            Vector3f v1 = vertices[i];
            Vector3f v2 = vertices[(i + 1) % vertexCount];
            Vector3f v3 = vertices[(i + 2) % vertexCount];

            // Compute the normal for this face
            Vector3f edge1 = v2.sub(v1);
            Vector3f edge2 = v3.sub(v1);
            Vector3f normal = edge1.cross(edge2);

            if (!isZeroVector(normal)) {
                normals.add(normal);
            }
        }

        return normals;
    }

    private static List<Vector3f> getEdgeCrossProducts(Vector3f[] vertices1, Vector3f[] vertices2) {
        List<Vector3f> axes = new ArrayList<>();

        // Loop over all edges in poly1 and poly2
        for (int i = 0; i < vertices1.length; i++) {
            Vector3f edge1 = new Vector3f(vertices1[(i + 1) % vertices1.length]).sub(vertices1[i]);

            for (int j = 0; j < vertices2.length; j++) {
                Vector3f edge2 = new Vector3f(vertices2[(j + 1) % vertices2.length]).sub(vertices2[j]);

                // The cross product of two edges provides a potential separating axis
                Vector3f axis = edge1.cross(edge2);
                if (!isZeroVector(axis)) {
                    axes.add(axis);
                }
            }
        }

        return axes;
    }

    private static boolean isZeroVector(Vector3f v) {
        return v.x == 0 && v.y == 0 && v.z == 0;
    }

    private static boolean isProjectionOverlapping(Vector3f[] poly1, Vector3f[] poly2, Vector3f axis) {
        // Project vertices of both polygons on the axis
        Projection proj1 = projectPolygonOnAxis(poly1, axis);
        Projection proj2 = projectPolygonOnAxis(poly2, axis);

        // Check if the projections overlap
        if (proj1.max < proj2.min || proj2.max < proj1.min) {
            return false; // No overlap on this axis, polygons are not colliding
        }

        // Calculate the overlap (penetration depth) along the axis
        float overlap = Math.min(proj1.max - proj2.min, proj2.max - proj1.min);

        // If the overlap is smaller than the current penetration depth, update it
        if (overlap < penetrationDepth) {
            penetrationDepth = overlap;
            collisionNormal = axis; // Set the axis as the collision normal
        }

        return true;
    }

    private static Projection projectPolygonOnAxis(Vector3f[] vertices, Vector3f axis) {
        float min = axis.dot(vertices[0]);
        float max = min;

        for (int i = 1; i < vertices.length; i++) {
            float projection = axis.dot(vertices[i]);

            if (projection < min) {
                min = projection;
            }
            if (projection > max) {
                max = projection;
            }
        }

        return new Projection(min, max);
    }

    public static Vector3f getCollisionNormal() {
        return collisionNormal;
    }

    // Get the penetration depth after detecting a collision
    public static float getPenetrationDepth() {
        return penetrationDepth;
    }

    private static class Projection {
        public float min, max;

        public Projection(float min, float max) {
            this.min = min;
            this.max = max;
        }
    }
}

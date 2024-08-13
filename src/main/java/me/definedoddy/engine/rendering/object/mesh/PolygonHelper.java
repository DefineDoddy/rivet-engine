package me.definedoddy.engine.rendering.object.mesh;

import me.definedoddy.toolkit.debug.Debug;
import me.definedoddy.toolkit.model.obj.Vertex;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class PolygonHelper {

    private static Vector3f computeNormal(List<Vertex> vertices) {
        // Assume the polygon is not degenerate and has at least three vertices.
        Vector3f v0 = vertices.get(0).getPosition();
        Vector3f v1 = vertices.get(1).getPosition();
        Vector3f v2 = vertices.get(2).getPosition();

        Vector3f edge1 = v1.sub(v0, new Vector3f());
        Vector3f edge2 = v2.sub(v0, new Vector3f());
        return edge1.cross(edge2).normalize();
    }

    private static List<Vertex> projectVerticesTo2D(List<Vertex> vertices, Vector3f normal) {
        Vector3f axis1 = new Vector3f(1, 0, 0);
        if (Math.abs(normal.dot(axis1)) > 0.9) {
            axis1 = new Vector3f(0, 1, 0);
        }
        Vector3f axis2 = normal.cross(axis1, new Vector3f()).normalize();
        axis1 = axis2.cross(normal, new Vector3f()).normalize();

        List<Vertex> projectedVertices = new ArrayList<>();
        for (Vertex vertex : vertices) {
            Vector3f position = vertex.getPosition();
            float x = position.dot(axis1);
            float y = position.dot(axis2);
            //projectedVertices.add(new Vertex(new Vector3f(x, y, 0), vertex.getTextureCoord(), vertex.getNormal()));
        }
        return projectedVertices;
    }

    private static float findPolygonArea(List<Vertex> vertices) {
        float area = 0;
        for (int i = 0; i < vertices.size(); i++) {
            Vertex vertex = vertices.get(i);
            Vertex nextVertex = vertices.get((i + 1) % vertices.size());
            area += vertex.getPosition().x * nextVertex.getPosition().y - nextVertex.getPosition().x * vertex.getPosition().y;
        }
        return area / 2;
    }

    public static List<Triangle> triangulate(List<Vertex> vertices) {
        List<Triangle> triangles = new ArrayList<>();

        if (vertices.size() < 3) return triangles;
        //if (!isSimplePolygon(vertices)) return triangles;

        Vector3f normal = computeNormal(vertices);
        //List<Vertex> projectedVertices = projectVerticesTo2D(vertices, normal);

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            indices.add(i);
        }

        int totalTriangleCount = vertices.size() - 2;
        int totalTriangleIndexCount = totalTriangleCount * 3;

        triangles = new ArrayList<>(totalTriangleIndexCount);

        while (indices.size() > 3) {
            for (int i = 0; i < indices.size(); i++) {
                int a = getIndex(indices, i - 1);
                int b = getIndex(indices, i);
                int c = getIndex(indices, i + 1);

                Vertex vertA = vertices.get(a);
                Vertex vertB = vertices.get(b);
                Vertex vertC = vertices.get(c);

                Triangle triangle = new Triangle(vertA, vertB, vertC);

                Vector3f vAToVb = vertB.getPosition().sub(vertA.getPosition(), new Vector3f());
                Vector3f vAToVc = vertC.getPosition().sub(vertA.getPosition(), new Vector3f());

                if (vAToVb.cross(vAToVc, new Vector3f()).dot(normal) < 0) continue;

                boolean isEar = true;
                Debug.log("Checking ear: " + a + ", " + b + ", " + c);

                for (int j = 0; j < vertices.size(); j++) {
                    if (j == a || j == b || j == c) continue;

                    if (triangle.containsVertex(vertices.get(j))) {
                        isEar = false;
                        break;
                    }
                }

                Debug.log("Is ear: " + isEar);

                if (isEar) {
                    triangles.add(triangle);
                    indices.remove(i);
                    break;
                }
            }
        }

        if (indices.size() == 3) {
            triangles.add(new Triangle(vertices.get(indices.get(0)), vertices.get(indices.get(1)), vertices.get(indices.get(2))));
        }

        return triangles;
    }

    public static boolean isSimplePolygon(List<Vertex> vertices) {
        // Implementation remains the same
        for (int i = 0; i < vertices.size(); i++) {
            Vertex vertex = vertices.get(i);
            Vertex nextVertex = vertices.get((i + 1) % vertices.size());
            for (int j = 0; j < vertices.size(); j++) {
                if (j == i || j == (i + 1) % vertices.size()) {
                    continue;
                }
                Vertex vertex2 = vertices.get(j);
                Vertex nextVertex2 = vertices.get((j + 1) % vertices.size());
                if (vertex.getPosition().equals(nextVertex2.getPosition()) && nextVertex.getPosition().equals(vertex2.getPosition())) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean containsColinearEdges(List<Vertex> vertices) {
        // Implementation remains the same
        for (int i = 0; i < vertices.size(); i++) {
            Vertex vertex = vertices.get(i);
            Vertex nextVertex = vertices.get((i + 1) % vertices.size());
            for (int j = 0; j < vertices.size(); j++) {
                if (j == i || j == (i + 1) % vertices.size()) {
                    continue;
                }
                Vertex vertex2 = vertices.get(j);
                Vertex nextVertex2 = vertices.get((j + 1) % vertices.size());
                if (vertex.getPosition().equals(nextVertex2.getPosition()) || nextVertex.getPosition().equals(vertex2.getPosition())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static float computePolygonArea(List<Vertex> vertices) {
        Vector3f normal = computeNormal(vertices);
        List<Vertex> projectedVertices = projectVerticesTo2D(vertices, normal);
        return Math.abs(findPolygonArea(projectedVertices));
    }

    public static int getIndex(List<Integer> indices, int i) {
        if (i >= indices.size()) return indices.get(i % indices.size());
        else if (i < 0) return indices.get(i % indices.size() + indices.size());
        return indices.get(i);
    }
}

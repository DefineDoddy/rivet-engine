package me.definedoddy.engine.rendering.model.mesh;

import me.definedoddy.toolkit.model.obj.Vertex;
import org.joml.Vector3f;

public class Triangle {
    private final Vertex p0, p1, p2;

    public Triangle(Vertex p0, Vertex p1, Vertex p2) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
    }

    public Vertex getP0() {
        return p0;
    }

    public Vertex getP1() {
        return p1;
    }

    public Vertex getP2() {
        return p2;
    }

    public boolean containsVertex(Vertex vertex) {
        Vector3f ab = p1.getPosition().sub(p0.getPosition(), new Vector3f());
        Vector3f bc = p2.getPosition().sub(p1.getPosition(), new Vector3f());
        Vector3f ca = p0.getPosition().sub(p2.getPosition(), new Vector3f());

        Vector3f ap = vertex.getPosition().sub(p0.getPosition(), new Vector3f());
        Vector3f bp = vertex.getPosition().sub(p1.getPosition(), new Vector3f());
        Vector3f cp = vertex.getPosition().sub(p2.getPosition(), new Vector3f());

        Vector3f abCrossAp = ab.cross(ap);
        Vector3f bcCrossBp = bc.cross(bp);
        Vector3f caCrossCp = ca.cross(cp);

        return !(abCrossAp.z > 0f) && !(bcCrossBp.z > 0f) && !(caCrossCp.z > 0f);
    }
}

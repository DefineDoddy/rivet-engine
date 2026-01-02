package org.rivetengine.entity.components.camera;

import org.rivetengine.entity.component.Component;

public class Camera implements Component {
    public float fieldOfView = 70f;
    public float nearPlane = 0.1f;
    public float farPlane = 1000f;
    public boolean active = true;
}

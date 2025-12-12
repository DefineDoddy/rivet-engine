package org.rivetengine.debug;

import org.rivetengine.toolkit.memory.Disposable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DebugHandler implements Disposable {
    public DebugHandler() {
        Debug.registerHandler(this);
    }

    @Override
    public void dispose() {
        Debug.unregisterHandler(this);
    }

    private final List<Consumer<Boolean>> normalsVisibleConsumers = new ArrayList<>();
    public void createRenderNormalsCallback(Consumer<Boolean> consumer) {
        normalsVisibleConsumers.add(consumer);
    }

    public void setRenderNormals(boolean visible) {
        normalsVisibleConsumers.forEach(consumer -> consumer.accept(visible));
    }

    private final List<Consumer<Boolean>> wireframeConsumers = new ArrayList<>();
    public void createWireframeCallback(Consumer<Boolean> consumer) {
        wireframeConsumers.add(consumer);
    }

    public void setWireframe(boolean visible) {
        wireframeConsumers.forEach(consumer -> consumer.accept(visible));
    }

    private final List<Consumer<Boolean>> boundingBoxesVisibleConsumers = new ArrayList<>();
    public void createRenderBoundingBoxesCallback(Consumer<Boolean> consumer) {
        boundingBoxesVisibleConsumers.add(consumer);
    }

    public void setRenderBoundingBoxes(boolean visible) {
        boundingBoxesVisibleConsumers.forEach(consumer -> consumer.accept(visible));
    }
}

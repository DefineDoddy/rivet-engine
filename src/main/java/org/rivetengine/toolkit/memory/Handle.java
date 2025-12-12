package org.rivetengine.toolkit.memory;

public class Handle<T> {
    public final String id;

    public Handle(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Handle<?> handle = (Handle<?>) other;
        return id.equals(handle.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

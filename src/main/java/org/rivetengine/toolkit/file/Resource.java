package org.rivetengine.toolkit.file;

import java.net.URL;

public class Resource extends File {
    private final String resourcePath;

    public Resource(String path) {
        super(getAbsolutePath(path));
        this.resourcePath = path;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    private static String getAbsolutePath(String path) {
        URL url = Resource.class.getClassLoader().getResource(path);
        if (url == null) {
            throw new RuntimeException("Failed to find resource from path: " + path);
        }
        return url.getPath();
    }
}

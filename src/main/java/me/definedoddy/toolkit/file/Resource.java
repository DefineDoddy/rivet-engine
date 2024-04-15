package me.definedoddy.toolkit.file;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Resource {
    private final String path;

    public Resource(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getAbsolutePath() {
        URL url = Resource.class.getClassLoader().getResource(path);
        if (url == null) {
            throw new RuntimeException("Failed to find resource from path: " + path);
        }
        return url.getPath();
    }

    public InputStream getStream() {
        InputStream stream = Resource.class.getClassLoader().getResourceAsStream(path);
        if (stream == null) {
            throw new RuntimeException("Failed to find resource from path: " + path);
        }
        return stream;
    }

    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getStream()));
    }

    public boolean exists() {
        return Resource.class.getClassLoader().getResource(path) != null;
    }
}

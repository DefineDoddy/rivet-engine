package org.rivetengine.toolkit.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class File {
    protected final Path path;

    public File(String path) {
        this.path = Paths.get(normalisePath(path));
    }

    public File(File directory, String... subPaths) {
        this.path = Paths.get(normalisePath(directory.getPath()), subPaths);
    }

    private static String normalisePath(String path) {
        if (path.matches("^/[A-Za-z]:.*")) {
            return path.substring(1);
        }
        return path;
    }

    public String getPath() {
        return path.toString().replace("\\", "/");
    }

    public boolean exists() {
        return path.toFile().exists();
    }

    @Override
    public String toString() {
        return getPath();
    }

    public String getFileName() {
        return path.getFileName().toString();
    }

    public String getExtension() {
        String fileName = getFileName();
        int index = fileName.lastIndexOf(".");
        if (index < 0) return null;
        return fileName.substring(index + 1);
    }

    public String getDirectory() {
        return path.getParent().toString();
    }

    public File getParent() {
        return new File(getDirectory());
    }

    public InputStream getStream() {
        try {
            return new java.io.FileInputStream(this.getPath());
        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + this.getPath(), e);
        }
    }

    public BufferedReader getReader() {
        try {
            return new BufferedReader(new FileReader(this.getPath()));
        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + this.getPath(), e);
        }
    }

    public String read() {
        StringBuilder fileContent = new StringBuilder();
        try {
            BufferedReader reader = this.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + this.getPath(), e);
        }
        return fileContent.toString();
    }
}

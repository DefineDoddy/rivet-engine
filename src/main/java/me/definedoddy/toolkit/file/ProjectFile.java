package me.definedoddy.toolkit.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.FileSystems;

public class ProjectFile {
    public static final String PROJECT_ROOT = "src/main/java/me/definedoddy/";
    public static final String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();

    private final String path;

    public ProjectFile(String path) {
        this.path = PROJECT_ROOT + path;
    }

    public ProjectFile(ProjectFile directory, String... subPaths) {
        StringBuilder pathBuilder = new StringBuilder(directory.getPath());
        for (String subPath : subPaths) {
            pathBuilder.append(FILE_SEPARATOR).append(subPath);
        }
        this.path = pathBuilder.toString();
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return getPath();
    }

    public String getFileName() {
        return path.substring(path.lastIndexOf(FILE_SEPARATOR) + 1);
    }

    public String getExtension() {
        return path.substring(path.lastIndexOf(".") + 1);
    }

    public String getDirectory() {
        return path.substring(0, path.lastIndexOf(FILE_SEPARATOR));
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

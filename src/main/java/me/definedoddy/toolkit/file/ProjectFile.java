package me.definedoddy.toolkit.file;

public class ProjectFile extends File {
    public static final String PROJECT_ROOT = "src/main/java/me/definedoddy/";

    public ProjectFile(String path) {
        super(PROJECT_ROOT + path);
    }

    public ProjectFile(File directory, String... subPaths) {
        super(directory, subPaths);
    }
}

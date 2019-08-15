package org.vfspoc;

public class PathName {

    private final String name;
    private final String path;

    public PathName(String name, String path) {
        ValidationUtils.checkNotEmpty(name,"Name is empty");
        ValidationUtils.checkNotEmpty(path,"Path is empty");
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}

package org.vfspoc.core;

import org.vfspoc.util.ValidationUtils;

public class PathName {

    private final String name;
    private final String path;

    public PathName(String name, String path) {
        ValidationUtils.checkNotEmpty(name,"Name is empty");
        ValidationUtils.checkParameter(isValideName(name),"Name is invalide");
        ValidationUtils.checkNotNull(path,"Path is null");
        this.name = name;
        this.path = path;
    }

    private boolean isValideName(String name) {
        if(name==null||name.trim().isEmpty()){
            return false;
        }
        return name.matches("^[a-z][a-z0-9]*$");
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}

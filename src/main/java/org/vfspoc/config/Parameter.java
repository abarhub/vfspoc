package org.vfspoc.config;

import java.nio.file.Path;

public class Parameter {

    private final Path path;
    private final boolean readonly;

    public Parameter(Path path, boolean readonly) {
        this.path = path;
        this.readonly = readonly;
    }

    public Path getPath() {
        return path;
    }

    public boolean isReadonly() {
        return readonly;
    }
}

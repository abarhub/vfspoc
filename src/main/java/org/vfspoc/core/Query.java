package org.vfspoc.core;

import org.vfspoc.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Query {

    private final FileManager fileManager;

    public Query(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public boolean exists(PathName file) throws IOException {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        return Files.exists(p);
    }

    private Path getRealFile(PathName file){
        return fileManager.getRealFile(file);
    }
}

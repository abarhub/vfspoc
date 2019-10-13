package org.vfspoc.core;

import org.vfspoc.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Query extends AbstractOperation {

    public Query(FileManager fileManager) {
        super(fileManager);
    }

    public boolean exists(PathName file) throws IOException {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        return Files.exists(p);
    }

}

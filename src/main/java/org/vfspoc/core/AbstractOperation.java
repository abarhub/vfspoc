package org.vfspoc.core;

import java.nio.file.Path;

public abstract class AbstractOperation {

    private final FileManager fileManager;

    public AbstractOperation(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    protected Path getRealFile(PathName file){
        return fileManager.getRealFile(file);
    }

    public FileManager getFileManager() {
        return fileManager;
    }
}

package org.vfspoc.core;

import org.vfspoc.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Command {

     private final FileManager fileManager;

    public Command(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void createFile(PathName file) throws IOException {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        Files.createFile(p);
    }

    public void createDirectory(PathName file) throws IOException {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        Files.createDirectory(p);
    }

    public void createDirectories(PathName file) throws IOException {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        Files.createDirectories(p);
    }

    public void delete(PathName file) throws IOException {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        Files.delete(p);
    }

    public void deleteIfExists(PathName file) throws IOException {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        Files.deleteIfExists(p);
    }

    private Path getRealFile(PathName file){
        return fileManager.getRealFile(file);
    }
}

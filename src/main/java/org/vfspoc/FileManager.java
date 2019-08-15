package org.vfspoc;

import org.vfspoc.config.Parameter;
import org.vfspoc.config.VFSConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    private VFSConfig vfsConfig;

    public FileManager() {
        vfsConfig=new VFSConfig();
    }

    public void addPath(String nom, Path path){
        vfsConfig.addPath(nom,path,false);
    }

    public Parameter getPath(String nom){
        return vfsConfig.getPath(nom);
    }

    public void createFile(PathName file) throws IOException {
        Path p=getRealFile(file);
        Files.createFile(p);
    }

    private Path getRealFile(PathName file) {
        Parameter p=getPath(file.getName());
        ValidationUtils.checkNotNull(p,"PathName doesn't exist");
        Path path;
        if(file.getPath()==null||file.getPath().isEmpty()){
            path=p.getPath();
        } else {
            path=p.getPath().resolve(file.getPath());
        }
        return path;
    }
}

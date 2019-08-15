package org.vfspoc.core;

import org.vfspoc.config.Parameter;
import org.vfspoc.config.VFSConfig;
import org.vfspoc.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {

    private VFSConfig vfsConfig;

    public FileManager() {
        vfsConfig=new VFSConfig();
    }

    public void addPath(String name, Path path){
        ValidationUtils.checkNotEmpty(name,"Name is empty");
        ValidationUtils.checkNotNull(path,"Path is null");
        vfsConfig.addPath(name,path,false);
    }

    public void addPathRealOnly(String name, Path path){
        ValidationUtils.checkNotEmpty(name,"Name is empty");
        ValidationUtils.checkNotNull(path,"Path is null");
        vfsConfig.addPath(name,path,true);
    }

    public Parameter getPath(String name){
        ValidationUtils.checkNotEmpty(name,"Name is empty");
        return vfsConfig.getPath(name);
    }

    public void createFile(PathName file) throws IOException {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        Files.createFile(p);
    }

    private Path getRealFile(PathName file) {
        ValidationUtils.checkNotNull(file,"Path is null");
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

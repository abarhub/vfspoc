package org.vfspoc.core;

import org.vfspoc.config.Parameter;
import org.vfspoc.config.VFSConfig;
import org.vfspoc.util.ConvertFile;
import org.vfspoc.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {

    private VFSConfig vfsConfig;
    private Command command;
    private ConvertFile convertFile;

    public FileManager() {
        vfsConfig=new VFSConfig();
        command=new Command(this);
        convertFile=new ConvertFile(vfsConfig);
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

    protected Path getRealFile(PathName file) {
        ValidationUtils.checkNotNull(file,"Path is null");
        return convertFile.getRealFile(file);
    }

    public Command getCommand() {
        return command;
    }
}

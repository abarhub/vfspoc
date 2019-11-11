package org.vfspoc.core;

import org.vfspoc.config.Parameter;
import org.vfspoc.config.VFSConfig;
import org.vfspoc.util.ConvertFile;
import org.vfspoc.util.ValidationUtils;

import java.nio.file.Path;
import java.util.Optional;

public class FileManager {

    private VFSConfig vfsConfig;
    private Command command;
    private Query query;
    private Open open;
    private ConvertFile convertFile;

    public FileManager() {
        vfsConfig=new VFSConfig();
        command=new Command(this);
        convertFile=new ConvertFile(vfsConfig);
        query=new Query(this);
        open=new Open(this);
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

    public Optional<PathName> convertFromRealPath(Path file) {
        ValidationUtils.checkNotNull(file,"Path is null");
        return convertFile.convertFromRealPath(file);
    }

    public Query getQuery() {
        return query;
    }

    public Open getOpen() {
        return open;
    }
}

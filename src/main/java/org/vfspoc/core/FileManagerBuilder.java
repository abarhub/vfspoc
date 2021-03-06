package org.vfspoc.core;

import org.vfspoc.config.Parameter;
import org.vfspoc.config.VFSConfig;
import org.vfspoc.util.ValidationUtils;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FileManagerBuilder {

    private final Map<String, Parameter> listeConfig;

    public FileManagerBuilder() {
        listeConfig=new HashMap<>();
    }

    public FileManagerBuilder addPath(String name, Path path, boolean readonly){
        ValidationUtils.checkNotEmpty(name,"Name is empty");
        ValidationUtils.checkNotNull(path,"Path is null");
        listeConfig.put(name,new Parameter(path,readonly));
        return this;
    }

    public VFSConfig build(){
        return new VFSConfig(listeConfig);
    }


}

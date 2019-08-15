package org.vfspoc.config;

import org.vfspoc.util.ValidationUtils;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class VFSConfig {

    private final Map<String,Parameter> listeConfig;

    public VFSConfig() {
        listeConfig=new HashMap<>();
    }

    public void addPath(String name, Path path,boolean readonly){
        ValidationUtils.checkNotEmpty(name,"Name is empty");
        ValidationUtils.checkNotNull(path,"Path is null");
        listeConfig.put(name,new Parameter(path,readonly));
    }

    public Parameter getPath(String name){
        ValidationUtils.checkNotEmpty(name,"Name is empty");
        return listeConfig.get(name);
    }
}

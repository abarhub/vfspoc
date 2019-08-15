package org.vfspoc.config;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class VFSConfig {

    private final Map<String,Parameter> listeConfig;

    public VFSConfig() {
        listeConfig=new HashMap<>();
    }

    public void addPath(String nom, Path path,boolean readonly){
        listeConfig.put(nom,new Parameter(path,readonly));
    }

    public Parameter getPath(String nom){
        return listeConfig.get(nom);
    }
}

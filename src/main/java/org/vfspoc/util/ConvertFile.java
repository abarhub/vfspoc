package org.vfspoc.util;

import org.vfspoc.config.Parameter;
import org.vfspoc.config.VFSConfig;
import org.vfspoc.core.PathName;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ConvertFile {

    private final VFSConfig vfsConfig;

    public ConvertFile(VFSConfig vfsConfig) {
        ValidationUtils.checkNotNull(vfsConfig,"vfsConfig is null");
        this.vfsConfig = vfsConfig;
    }

    public Path getRealFile(PathName file){
        ValidationUtils.checkNotNull(file,"Path is null");
        Parameter p=vfsConfig.getPath(file.getName());
        ValidationUtils.checkNotNull(p,"PathName doesn't exist");
        Path path;
        if(file.getPath()==null||file.getPath().isEmpty()){
            path=p.getPath();
        } else {
            Path p2=Paths.get(file.getPath()).normalize();
            p2 = removeReferenceParentInBegin(p2);
            path=p.getPath().resolve(p2);
        }
        return path;
    }

    /**
     * Supprime les .. au debut du path
     * @param path
     * @return
     */
    private Path removeReferenceParentInBegin(Path path) {
        boolean aucunTraitement;
        do {
            aucunTraitement=true;
            String name=path.getName(0).toString();
            if (name!=null&&name.equals("..")) {
                path = path.subpath(1, path.getNameCount());
                aucunTraitement=false;
            }
        } while(!aucunTraitement);
        return path;
    }
}

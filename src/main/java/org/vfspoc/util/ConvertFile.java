package org.vfspoc.util;

import org.vfspoc.config.Parameter;
import org.vfspoc.config.VFSConfig;
import org.vfspoc.core.PathName;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

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
        ValidationUtils.checkNotNull(path,"Path is null");
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

    public Optional<PathName> convertFromRealPath(Path file) {
        ValidationUtils.checkNotNull(file,"Path is null");
        List<String> nameList=vfsConfig.getNames();
        if(nameList!=null&&!nameList.isEmpty()){
            Path trouve=null;
            PathName pathNameTrouve=null;
            Path fileNormalized=file.normalize();
            for(String name:nameList){
                Parameter parameter=vfsConfig.getPath(name);
                Path path = parameter.getPath();
                if(fileNormalized.startsWith(path)){
                    if(trouve==null){
                        trouve=path;
                        pathNameTrouve = createPathName(fileNormalized, name, path);
                    } else {
                        if(trouve.getNameCount()<path.getNameCount()){
                            trouve=path;
                            pathNameTrouve = createPathName(fileNormalized, name, path);
                        }
                    }
                }
            }
            return Optional.ofNullable(pathNameTrouve);
        } else {
            return Optional.empty();
        }
    }

    private PathName createPathName(Path fileNormalized, String name, Path path) {
        PathName pathNameTrouve;
        Path p=path.relativize(fileNormalized);
        pathNameTrouve=new PathName(name, p.toString());
        return pathNameTrouve;
    }

    private Path getNormalizedPath(String path){
        Path p=Paths.get(path).normalize();
        p = removeReferenceParentInBegin(p);
        return p;
    }
}

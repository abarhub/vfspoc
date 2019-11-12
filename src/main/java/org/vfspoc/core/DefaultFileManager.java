package org.vfspoc.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vfspoc.exception.VFSException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultFileManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultFileManager.class);

    private static FileManager fileManager= createFileManager();

    private static FileManager createFileManager() {
        FileManagerBuilder fileManagerBuilder=new FileManagerBuilder();
        Properties properties=null;

        // lecture du fichier de configuration dans le classpath
        try(InputStream is = DefaultFileManager.class.getResourceAsStream("/vfs.properties")) {
            properties = new Properties();
            properties.load(is);
        } catch (FileNotFoundException e) {
            properties=null;
            LOGGER.info("File vfs.properties not found in classpath");
        } catch (IOException e) {
            throw new VFSException("Error in reading file vfs.properties in classpath", e);
        }

        if(properties==null) {
            // lecture du fichier de configuration dans la propriété VFS_CONFIG
            String vfsConfigPath = System.getProperty("VFS_CONFIG");
            if (vfsConfigPath != null && !vfsConfigPath.trim().isEmpty()) {
                Path path = Path.of(vfsConfigPath);
                if (Files.exists(path)) {
                    try (InputStream in = Files.newInputStream(path)) {
                        properties = new Properties();
                        properties.load(in);
                    } catch (IOException e) {
                        throw new VFSException("Error for reading file '" + vfsConfigPath + "'", e);
                    }
                } else {
                    throw new VFSException("File '" + vfsConfigPath + "' not exists");
                }
            }
        }

        if(properties!=null&&!properties.isEmpty()){
            // construction de la map des propriétés (on enlève ce qui n'est pas de type string)
            Set<Object> keys=properties.keySet();
            Map<String, String> map=new HashMap<>();
            for(Object o:keys){
                if(o !=null&&o instanceof String){
                    Object o2=properties.get(o);
                    if(o2!=null&&o2 instanceof String){
                        String key= (String) o;
                        String value= (String) o2;
                        map.put(key, value);
                    }
                }
            }
            // détermination des noms
            final String prefix = "vfs.";
            final String suffix = ".path";
            List<String> liste=map.keySet()
                    .stream()
                    .filter(x -> x.startsWith(prefix))
                    .map(x -> x.substring(prefix.length()))
                    .filter(x -> x.endsWith(suffix))
                    .map(x -> x.substring(0, x.indexOf(suffix)))
                    .filter(x -> !x.trim().isEmpty())
                    .filter(x -> x.matches("[a-zA-Z]+"))
                    .distinct()
                    .collect(Collectors.toList());

            // ajout des paths dans fileManagerBuilder
            for(String nom:liste){
                String key=prefix+nom+suffix;
                if(map.containsKey(key)){
                    String value=map.get(key);
                    if(value==null||value.trim().isEmpty()){
                        throw new VFSException("Path for '"+key+"' is empty");
                    } else {
                        Path p= Paths.get(value);
                        fileManagerBuilder.addPath(nom, p, false);
                    }
                }
            }
        }

        return new FileManager(fileManagerBuilder);
    }

    public static FileManager get(){
        return fileManager;
    }
}

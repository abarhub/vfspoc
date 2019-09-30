package org.vfspoc.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vfspoc.config.VFSConfig;
import org.vfspoc.core.PathName;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ConvertFileTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConvertFileTest.class);

    @Test
    @DisplayName("getRealFile sur un fichier")
    void getRealFile_test1() {
        LOGGER.info("getRealFile_test1");
        VFSConfig vfsConfig;
        vfsConfig=new VFSConfig();
        vfsConfig.addPath("nom", Paths.get("/tmp/test"),false);
        ConvertFile convertFile=new ConvertFile(vfsConfig);

        // methode testée
        Path path=convertFile.getRealFile(new PathName("nom","fichier.txt"));

        // vérifications
        assertNotNull(path);
        assertEquals(Paths.get("/tmp/test/fichier.txt"), path);
    }

    @Test
    @DisplayName("getRealFile sur un fichier dans un répertoire")
    void getRealFile_test2() {
        LOGGER.info("getRealFile_test2");
        VFSConfig vfsConfig;
        vfsConfig=new VFSConfig();
        vfsConfig.addPath("nom", Paths.get("/tmp/test"),false);
        ConvertFile convertFile=new ConvertFile(vfsConfig);

        // methode testée
        Path path=convertFile.getRealFile(new PathName("nom","doc/fichier2.txt"));

        // vérifications
        assertNotNull(path);
        assertEquals(Paths.get("/tmp/test/doc/fichier2.txt"), path);
    }

    private static Stream<Arguments> getRealFile_test3Parameter() {
        return Stream.of(
                Arguments.of("nom", "/tmp/test", "fichier.txt", "/tmp/test/fichier.txt"),
                Arguments.of("nom2","/tmp/test", "temp/fichier.txt", "/tmp/test/temp/fichier.txt"),
                Arguments.of("nom2","/tmp/test2", "temp/monfichier.txt", "/tmp/test2/temp/monfichier.txt"),
                Arguments.of("nom3","/tmp/test3", "temp/../monfichier3.txt", "/tmp/test3/monfichier3.txt"),
                Arguments.of("nom4","/tmp", "../monfichier4.txt", "/tmp/monfichier4.txt")
        );
    }

    @ParameterizedTest
    @MethodSource("getRealFile_test3Parameter")
    @DisplayName("getRealFile sur un fichier dans un répertoire")
    void getRealFile_test3(final String nameRef,final String pathRoot,final String path,final String pathRef) {
        LOGGER.info("getRealFile_test3({},{},{},{})",nameRef, pathRoot, path, pathRef);
        VFSConfig vfsConfig;
        vfsConfig=new VFSConfig();
        vfsConfig.addPath(nameRef, Paths.get(pathRoot),false);
        ConvertFile convertFile=new ConvertFile(vfsConfig);

        // methode testée
        Path pathRes=convertFile.getRealFile(new PathName(nameRef,path));

        // vérifications
        LOGGER.info("pathRes={}",pathRes);
        assertNotNull(pathRes);
        assertEquals(Paths.get(pathRef), pathRes);
    }
}
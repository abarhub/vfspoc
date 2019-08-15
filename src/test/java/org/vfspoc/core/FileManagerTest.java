package org.vfspoc.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileManagerTest.class);

    @Test
    void createFile_test1(@TempDir Path tempDir) throws IOException {

        LOGGER.info("createFile_test1");

        assertTrue(Files.exists(tempDir));
        Path temp=tempDir.resolve("temp");
        assertFalse(Files.exists(temp));
        Files.createDirectory(temp);
        assertTrue(Files.exists(temp));
        FileManager fileManager=new FileManager();
        fileManager.addPath("path1",temp);

        final String pathRef = "test.txt";
        Path p=temp.resolve(pathRef);

        assertFalse(Files.exists(p));

        LOGGER.info("Le fichier {} n'existe pas",p);

        // methode testée
        fileManager.createFile(new PathName("path1", pathRef));

        // vérifications
        assertTrue(Files.exists(p));

        LOGGER.info("Le fichier {} existe",p);
    }
}
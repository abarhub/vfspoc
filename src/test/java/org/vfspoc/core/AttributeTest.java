package org.vfspoc.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class AttributeTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttributeTest.class);
    public static final String PATH1 = "path1";

    private FileManager fileManager;

    private Path directory;

    private Attribute attribute;

    @BeforeEach
    void setUp(@TempDir Path tempDir) throws IOException {
        fileManager=new FileManager();

        assertTrue(Files.exists(tempDir));
        Path temp=tempDir.resolve("temp");
        assertFalse(Files.exists(temp));
        Files.createDirectory(temp);
        assertTrue(Files.exists(temp));
        assertTrue(Files.isDirectory(temp));
        directory=temp;
        fileManager=new FileManager();
        fileManager.addPath(PATH1,temp);

        attribute=new Attribute(fileManager);
    }

    @Test
    void setAttribute() throws IOException {
        String filename="fichier1.txt";
        final Path file=directory.resolve(filename);
        assertFalse(Files.exists(file));
        Files.createFile(file);
        final FileTime fileTime = FileTime.from(Instant.EPOCH);

        // methode testée
        PathName res = attribute.setAttribute(getPathName(filename), "lastModifiedTime", fileTime);

        // vérifications
        assertNotNull(res);
        FileTime time= (FileTime) Files.getAttribute(file,"lastModifiedTime");
        assertNotNull(time);
        assertEquals(fileTime, time);
    }

    // methodes utilitaires

    public PathName getPathName(String filename){
        return new PathName(PATH1, filename);
    }

}
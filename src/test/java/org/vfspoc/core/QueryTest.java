package org.vfspoc.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class QueryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryTest.class);
    public static final String PATH1 = "path1";

    private FileManager fileManager;

    private Path directory;

    private Query query;

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

        query=new Query(fileManager);
    }

    @Test
    void existsTrue() throws IOException {

        final String filename = "fichier.txt";
        final Path file=directory.resolve(filename);
        Files.createFile(file);
        assertTrue(Files.exists(file));

        // methode testée
        boolean res=query.exists(getPathName(filename));

        // vérifications
        assertTrue(res);
    }

    @Test
    void existsNot() throws IOException {

        final String filename = "fichier.txt";
        final Path file=directory.resolve(filename);
        assertFalse(Files.exists(file));

        // methode testée
        boolean res=query.exists(getPathName(filename));

        // vérifications
        assertFalse(res);
    }

    // methodes utilitaires

    public PathName getPathName(String filename){
        return new PathName(PATH1, filename);
    }
}
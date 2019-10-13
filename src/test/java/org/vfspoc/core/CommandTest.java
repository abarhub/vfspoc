package org.vfspoc.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandTest.class);
    public static final String PATH1 = "path1";

    private FileManager fileManager;

    private Path directory;

    private Command command;

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

        command=new Command(fileManager);
    }

    @Test
    void createFile() throws IOException {
        final String filename = "fichier.txt";
        final Path file=directory.resolve(filename);
        assertFalse(Files.exists(file));

        // methode testée
        command.createFile(getPathName(filename));

        // vérifications
        assertTrue(Files.exists(file));
        assertTrue(Files.isRegularFile(file));
        assertFalse(Files.isDirectory(file));
    }

    @Test
    void createDirectory() throws IOException {
        final String filename = "mytemp";
        final Path file=directory.resolve(filename);
        assertFalse(Files.exists(file));

        // methode testée
        command.createDirectory(getPathName(filename));

        // vérifications
        assertTrue(Files.exists(file));
        assertFalse(Files.isRegularFile(file));
        assertTrue(Files.isDirectory(file));
    }

    @Test
    void createDirectories() throws IOException {
        final String filename = "mytemp/mydir";
        final Path file=directory.resolve(filename);
        assertFalse(Files.exists(file));
        assertFalse(Files.exists(file.getParent()));
        assertTrue(Files.exists(file.getParent().getParent()));

        // methode testée
        command.createDirectories(getPathName(filename));

        // vérifications
        assertTrue(Files.exists(file));
        assertTrue(Files.exists(file.getParent()));
        assertFalse(Files.isRegularFile(file));
        assertTrue(Files.isDirectory(file));
    }

    @Test
    void deleteExists() throws IOException {
        final String filename = "fichier2";
        final Path file=directory.resolve(filename);
        assertFalse(Files.exists(file));
        Files.createFile(file);
        assertTrue(Files.exists(file));

        // methode testée
        command.delete(getPathName(filename));

        // vérifications
        assertFalse(Files.exists(file));
    }

    @Test
    void deleteNotExists() throws IOException {
        final String filename = "fichier3";
        final Path file=directory.resolve(filename);
        assertFalse(Files.exists(file));

        try {
            // methode testée
            command.delete(getPathName(filename));
            fail("Error");
        }catch(NoSuchFileException e){
            assertEquals(e.getMessage(),file.toString());
        }

        // vérifications
        assertFalse(Files.exists(file));
    }

    @Test
    void deleteIfExistsExists() throws IOException {
        final String filename = "fichier4";
        final Path file=directory.resolve(filename);
        assertFalse(Files.exists(file));
        Files.createFile(file);
        assertTrue(Files.exists(file));

        // methode testée
        command.deleteIfExists(getPathName(filename));

        // vérifications
        assertFalse(Files.exists(file));
    }

    @Test
    void deleteIfExistsNotExists() throws IOException {
        final String filename = "fichier5";
        final Path file=directory.resolve(filename);
        assertFalse(Files.exists(file));

        // methode testée
        command.deleteIfExists(getPathName(filename));

        // vérifications
        assertFalse(Files.exists(file));
    }

    // methodes utilitaires

    public PathName getPathName(String filename){
        return new PathName(PATH1, filename);
    }
}
package org.vfspoc.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        boolean res = command.deleteIfExists(getPathName(filename));

        // vérifications
        assertFalse(Files.exists(file));
        assertTrue(res);
    }

    @Test
    void deleteIfExistsNotExists() throws IOException {
        final String filename = "fichier5";
        final Path file=directory.resolve(filename);
        assertFalse(Files.exists(file));

        // methode testée
        boolean res = command.deleteIfExists(getPathName(filename));

        // vérifications
        assertFalse(Files.exists(file));
        assertFalse(res);
    }

    @Test
    void createLink() throws IOException {
        final String link = "filelink";
        final Path linkPath=directory.resolve(link);
        final String target = "dirtarget";
        final Path targetPath=directory.resolve(target);
        final String contenuFichier = "abcaaa123";
        Files.createFile(targetPath);
        Files.write(targetPath, contenuFichier.getBytes(StandardCharsets.UTF_8));
        assertFalse(Files.exists(linkPath));
        assertTrue(Files.exists(targetPath));
        assertTrue(Files.isRegularFile(targetPath));
        assertArrayEquals(contenuFichier.getBytes(StandardCharsets.UTF_8),Files.readAllBytes(targetPath));

        // methode testée
        PathName res = command.createLink(getPathName(link), getPathName(target));

        // vérifications
        assertTrue(Files.exists(linkPath));
        assertTrue(Files.exists(targetPath));
        assertTrue(Files.isRegularFile(linkPath));
        assertTrue(Files.isRegularFile(targetPath));
        assertArrayEquals(contenuFichier.getBytes(StandardCharsets.UTF_8),Files.readAllBytes(linkPath));
        assertArrayEquals(contenuFichier.getBytes(StandardCharsets.UTF_8),Files.readAllBytes(targetPath));
        assertEquals(getPathName(link),res);
    }

    @Test
    @Disabled("Les liens symboliques ne fonctionnent que si on est superadmin => test désactivé")
    void createSymbolicLink() throws IOException {
        final String link = "dirlink";
        final Path linkPath=directory.resolve(link);
        final String target = "dirtarget";
        final Path targetPath=directory.resolve(target);
        Files.createDirectory(targetPath);
        assertFalse(Files.exists(linkPath));
        assertTrue(Files.exists(targetPath));
        assertTrue(Files.isDirectory(targetPath));

        // methode testée
        PathName res = command.createSymbolicLink(getPathName(link), getPathName(target));

        // vérifications
        assertTrue(Files.exists(linkPath));
        assertTrue(Files.exists(targetPath));
        assertTrue(Files.isSymbolicLink(linkPath));
        assertTrue(Files.isDirectory(targetPath));
        assertEquals(getPathName(link),res);
    }

    @Test
    void copyInput() throws IOException {
        final String contenu = "abc";
        ByteArrayInputStream input=new ByteArrayInputStream(contenu.getBytes(StandardCharsets.UTF_8));
        final String filename = "file1.txt";
        PathName outputFile = getPathName(filename);

        // methode testée
        long res = command.copy(input,outputFile);

        // vérifications
        byte[] buf = Files.readAllBytes(directory.resolve(filename));
        assertNotNull(buf);
        assertArrayEquals(contenu.getBytes(StandardCharsets.UTF_8), buf);
    }

    @Test
    void copyOutput() throws IOException {
        final String contenu = "abc2";
        final String filename = "file2.txt";
        PathName inputFile = getPathName(filename);
        Path input=directory.resolve(filename);
        Files.write(input,contenu.getBytes(StandardCharsets.UTF_8));
        assertTrue(Files.exists(input));
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();

        // methode testée
        long res = command.copy(inputFile, outputStream);

        // vérifications
        byte[] buf = outputStream.toByteArray();
        assertNotNull(buf);
        assertArrayEquals(contenu.getBytes(StandardCharsets.UTF_8), buf);
    }

    @Test
    void copyFile() throws IOException {
        final String contenu = "abc3";
        final String filenameInput = "fileinput.txt";
        final String filenameOutput = "fileoutput.txt";
        PathName inputFile = getPathName(filenameInput);
        Path input=directory.resolve(filenameInput);
        Files.write(input,contenu.getBytes(StandardCharsets.UTF_8));
        assertTrue(Files.exists(input));
        PathName outputFile = getPathName(filenameOutput);
        Path output=directory.resolve(filenameOutput);
        assertFalse(Files.exists(output));

        // methode testée
        PathName res = command.copy(inputFile, outputFile);

        // vérifications
        assertTrue(Files.exists(input));
        assertTrue(Files.exists(output));
        byte[] buf = Files.readAllBytes(output);
        assertNotNull(buf);
        assertArrayEquals(contenu.getBytes(StandardCharsets.UTF_8), buf);
        assertEquals(outputFile, res);
    }

    @Test
    void move() throws IOException {
        final String contenu = "abc4";
        final String filenameInput = "fileinput2.txt";
        final String filenameOutput = "fileoutput2.txt";
        PathName inputFile = getPathName(filenameInput);
        Path input=directory.resolve(filenameInput);
        Files.write(input,contenu.getBytes(StandardCharsets.UTF_8));
        assertTrue(Files.exists(input));
        PathName outputFile = getPathName(filenameOutput);
        Path output=directory.resolve(filenameOutput);
        assertFalse(Files.exists(output));

        // methode testée
        PathName res = command.move(inputFile, outputFile);

        // vérifications
        assertFalse(Files.exists(input));
        assertTrue(Files.exists(output));
        byte[] buf = Files.readAllBytes(output);
        assertNotNull(buf);
        assertArrayEquals(contenu.getBytes(StandardCharsets.UTF_8), buf);
        assertEquals(outputFile, res);
    }

    // methodes utilitaires

    public PathName getPathName(String filename){
        return new PathName(PATH1, filename);
    }
}
package org.vfspoc.core;

import org.vfspoc.exception.VFSException;
import org.vfspoc.util.ValidationUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

public class Query extends AbstractOperation {

    public Query(FileManager fileManager) {
        super(fileManager);
    }

    public boolean exists(PathName file, LinkOption... options) {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        return Files.exists(p, options);
    }

    public boolean isDirectory(PathName file, LinkOption... options) {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        return Files.isDirectory(p, options);
    }

    public boolean isRegularFile(PathName file, LinkOption... options) {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        return Files.isRegularFile(p, options);
    }

    public boolean isSameFile(PathName file, PathName file2) throws IOException {
        ValidationUtils.checkNotNull(file,"Path is null");
        ValidationUtils.checkNotNull(file2,"Path2 is null");
        Path p=getRealFile(file);
        Path p2=getRealFile(file2);
        return Files.isSameFile(p, p2);
    }

    public boolean isSymbolicLink(PathName file) {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        return Files.isSymbolicLink(p);
    }

    public boolean isWritable(PathName file) {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        return Files.isWritable(p);
    }

    public Stream<String> lines(PathName file) throws IOException {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        return Files.lines(p);
    }

    public Stream<String> lines(PathName file, Charset charsets) throws IOException {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        return Files.lines(p, charsets);
    }

    public boolean notExists(PathName file, LinkOption... options) {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        return Files.notExists(p, options);
    }

    public byte[] readAllBytes(PathName file) throws IOException {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        return Files.readAllBytes(p);
    }

    public List<String> readAllLines(PathName file) throws IOException {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        return Files.readAllLines(p);
    }

    public List<String> readAllLines(PathName file, Charset charset) throws IOException {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        return Files.readAllLines(p, charset);
    }

    public long size(PathName file) throws IOException {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        return Files.size(p);
    }

    public Stream<PathName> list(PathName file) throws IOException {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        return Files.list(p)
                .map(x -> getFileManager()
                        .convertFromRealPath(x)
                        .orElseThrow(() ->new VFSException("Invalid Path")));
    }

    public Stream<PathName> walk(PathName file, int maxDepth, FileVisitOption... options) throws IOException {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        return Files.walk(p, maxDepth, options)
                .map(x -> getFileManager()
                        .convertFromRealPath(x)
                        .orElseThrow(() ->new VFSException("Invalid Path")));
    }

    public Stream<PathName> walk(PathName file, FileVisitOption... options) throws IOException {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        return Files.walk(p, options)
                .map(x -> getFileManager()
                        .convertFromRealPath(x)
                        .orElseThrow(() ->new VFSException("Invalid Path")));
    }

    public Stream<PathName> find(PathName file, int maxDepth,
                                 BiPredicate<PathName, BasicFileAttributes> matcher, FileVisitOption... options) throws IOException {
        ValidationUtils.checkNotNull(file,"Path is null");
        Path p=getRealFile(file);
        BiPredicate<Path, BasicFileAttributes> matcher2=(path, attr)->{
            Optional<PathName> p2 = getFileManager().convertFromRealPath(path);
            if(p2.isPresent()){
                return matcher.test(p2.get(),attr);
            } else {
                throw new VFSException("Invalide Path");
            }
        };
        return Files.find(p, maxDepth,matcher2, options)
                .map(x -> getFileManager()
                        .convertFromRealPath(x)
                        .orElseThrow(() ->new VFSException("Invalid Path")));
    }
}

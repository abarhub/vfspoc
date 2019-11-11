package org.vfspoc.util;

import org.vfspoc.core.FileManager;
import org.vfspoc.core.PathName;
import org.vfspoc.exception.VFSException;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.Optional;

public class IteratorPathName implements Iterator<PathName> {

    private final Iterator<Path> pathIterator;
    private final FileManager fileManager;

    public IteratorPathName(Iterator<Path> pathIterator, FileManager fileManager) {
        this.pathIterator = pathIterator;
        this.fileManager = fileManager;
    }

    @Override
    public boolean hasNext() {
        return pathIterator.hasNext();
    }

    @Override
    public PathName next() {
        Path path = pathIterator.next();
        Optional<PathName> pathNameOpt = fileManager.convertFromRealPath(path);
        return pathNameOpt.orElseThrow(() -> new VFSException("Invalid path"));
    }
}

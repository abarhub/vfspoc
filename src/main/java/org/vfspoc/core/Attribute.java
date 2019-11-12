package org.vfspoc.core;

import org.vfspoc.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.NoSuchElementException;

public class Attribute extends AbstractOperation {

    public Attribute(FileManager fileManager) {
        super(fileManager);
    }

    public PathName setAttribute(PathName path, String attribute, Object value, LinkOption... options) throws IOException {
        ValidationUtils.checkNotNull(path,"Path is null");
        Path p=getRealFile(path);
        Path pathRes= Files.setAttribute(p, attribute,value, options);
        return convertFromRealPath(pathRes).orElseThrow(NoSuchElementException::new);
    }

}

package org.vfspoc.exception;

public class VFSInvalideParameterException extends VFSException {
    public VFSInvalideParameterException(String message) {
        super(message);
    }

    public VFSInvalideParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}

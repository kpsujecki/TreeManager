package org.sujecki.Exception;

public class ParentIdRequiredException extends RuntimeException {
    public ParentIdRequiredException(String errorMessage) {
        super(errorMessage);
    }
}

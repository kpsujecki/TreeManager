package org.sujecki.Exception;

public class ParentTreeNodeNotFoundException extends RuntimeException {
    public ParentTreeNodeNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}

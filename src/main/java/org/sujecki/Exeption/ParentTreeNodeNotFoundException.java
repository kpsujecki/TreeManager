package org.sujecki.Exeption;

public class ParentTreeNodeNotFoundException extends RuntimeException {
    public ParentTreeNodeNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}

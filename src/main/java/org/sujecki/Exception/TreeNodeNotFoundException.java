package org.sujecki.Exception;

public class TreeNodeNotFoundException extends RuntimeException {
    public TreeNodeNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}

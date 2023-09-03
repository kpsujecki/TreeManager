package org.sujecki.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NodeDTO {

    private Long node_id;
    private String value;
    private Long parent_id;
    @JsonProperty
    private Boolean isNewTreeRoot = false;

    public NodeDTO() {
    }

    public Long getNode_id() {
        return node_id;
    }

    public void setNode_id(Long node_id) {
        this.node_id = node_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public Boolean isNewTreeRoot() {
        return isNewTreeRoot;
    }

    public void setNewTreeRoot(Boolean newTreeRoot) {
        isNewTreeRoot = newTreeRoot;
    }
}

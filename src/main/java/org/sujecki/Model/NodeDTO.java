package org.sujecki.Model;

public class NodeDTO {

    private String value;
    private Long parent_id;

    public NodeDTO() {
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
}

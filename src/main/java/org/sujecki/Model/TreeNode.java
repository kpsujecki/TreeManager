package org.sujecki.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;

import java.util.LinkedList;
import java.util.List;


@Entity
@Table(name = "treeNode")
public class TreeNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "node_id", nullable = false)
    private Long node_id;
    private String value;
    private int depth;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private TreeNode parent;
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @BatchSize(size = 100)
    private List<TreeNode> childNodes;

    public TreeNode(String value) {
        this.value = value;
        this.childNodes = new LinkedList<>();
    }

    public TreeNode() {

    }

    public void addChild(TreeNode childNode) {
        this.childNodes.add(childNode);
    }

    public void showTreeNodes() {
        //BreathFirstSearchPrintTreeNodes.printNodes(this);
    }

    public String getValue() {
        return value;
    }

    public List<TreeNode> getChildNodes() {
        return childNodes;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Long getNode_id() {
        return node_id;
    }

    public void setNode_id(Long node_id) {
        this.node_id = node_id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public TreeNode getParent() {
        return parent;
    }
}

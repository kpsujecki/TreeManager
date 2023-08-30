package org.sujecki.Model;

import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
public class TreeNode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String value;
    @OneToMany
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

}

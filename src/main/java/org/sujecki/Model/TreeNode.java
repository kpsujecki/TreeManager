package org.sujecki.Model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "treeNode")
@Getter
@Setter
public class TreeNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "node_id", nullable = false)
    private Long nodeId;
    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private TreeNode parent;
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 100)
    private List<TreeNode> childNodes = new ArrayList<>();

    public void addChild(TreeNode childNode) {
        this.childNodes.add(childNode);
    }

    public void removeChild(TreeNode childNode) {
        this.childNodes.remove(childNode);
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return childNodes.isEmpty();
    }

    //This getter finding totals from all nodes on the way to the root

    public int getDepth() {
        int depth = 0;
        if (this.getParent() != null) {
            depth = this.getParent().getDepth() + 1;
        }
        return depth;
    }

    public Long getParentId() {
        if (isRoot()) {
            return null;
        } else {
            return this.parent.nodeId;
        }
    }

    public TreeNode() {
    }
}

package org.sujecki.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.BatchSize;

import java.util.List;


@Entity
@Table(name = "treeNode")
@Data
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
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @BatchSize(size = 100)
    private List<TreeNode> childNodes;

    public void addChild(TreeNode childNode) {
        this.childNodes.add(childNode);
    }

    public boolean isRoot(){
        return parent == null;
    }

    public boolean isLeaf(){
        return childNodes.isEmpty();
    }

    public int getDepth() {
        if(isRoot()){
            return 0;
        }else{
            return this.parent.getDepth() + 1;
        }
    }
}

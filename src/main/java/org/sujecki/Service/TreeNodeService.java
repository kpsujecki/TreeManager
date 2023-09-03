package org.sujecki.Service;

import org.springframework.stereotype.Service;
import org.sujecki.Exception.ParentTreeNodeNotFoundException;
import org.sujecki.Exception.TreeNodeNotFoundException;
import org.sujecki.Model.NodeDTO;
import org.sujecki.Model.TreeNode;
import org.sujecki.Reposiotry.TreeNodeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TreeNodeService {

    private TreeNodeRepository treeNodeRepository;

    public TreeNodeService(TreeNodeRepository treeNodeRepository){
        this.treeNodeRepository = treeNodeRepository;
    }


    public List<TreeNode> getAllNode(){
        return treeNodeRepository.findAll();
    }
    public Optional<TreeNode> getNodeById(Long id){
        Optional<TreeNode> treeNode = treeNodeRepository.findById(id);

        return treeNode;
    }

    public TreeNode addNode(NodeDTO treeNode){
        TreeNode newTreeNode = new TreeNode();
        Optional<TreeNode> parentNode = treeNodeRepository.findById(treeNode.getParent_id());

        newTreeNode.setValue(treeNode.getValue());
        newTreeNode.setParent(parentNode.orElseThrow(()
                -> new ParentTreeNodeNotFoundException(String.format("Parent with ID: %s is not found", treeNode.getParent_id()))));

        treeNodeRepository.save(newTreeNode);

        return newTreeNode;
    }

    public TreeNode editNode(NodeDTO treeNode){
        TreeNode newTreeNode = treeNodeRepository.findById(treeNode.getNode_id()).orElseThrow(()
                -> new TreeNodeNotFoundException(String.format("TreeNode with ID: %s is not found", treeNode.getNode_id())));

        if(treeNode.getParent_id() != null) {
            newTreeNode.setParent(treeNodeRepository.findById(treeNode.getParent_id()).orElseThrow(()
                    -> new ParentTreeNodeNotFoundException(String.format("Parent with ID: %s is not found", treeNode.getParent_id()))));
        }

        newTreeNode.setValue(treeNode.getValue());

        treeNodeRepository.save(newTreeNode);
        return newTreeNode;
    }

    public void removeNode(Long id){
        TreeNode treeNodeToRemove = treeNodeRepository.findById(id).orElseThrow(()
                -> new TreeNodeNotFoundException(String.format("TreeNode with ID: %s is not found", id)));

        treeNodeRepository.delete(treeNodeToRemove);
    }

}

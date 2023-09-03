package org.sujecki.Service;

import org.springframework.stereotype.Service;
import org.sujecki.Exeption.ParentTreeNodeNotFoundException;
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

    public Optional<TreeNode> addNode(NodeDTO treeNode){
        TreeNode newTreeNode = new TreeNode();
        Optional<TreeNode> parentNode = treeNodeRepository.findById(treeNode.getParent_id());

        newTreeNode.setValue(treeNode.getValue());
        newTreeNode.setParent(parentNode.orElseThrow(() -> new ParentTreeNodeNotFoundException("Parent is not found")));

        treeNodeRepository.save(newTreeNode);

        return Optional.of(newTreeNode);
    }

    public TreeNode editNode(TreeNode treeNode){
        TreeNode newTreeNode = treeNodeRepository.save(treeNode);

        return newTreeNode;
    }

    public TreeNode deleteNode(TreeNode treeNode){
        TreeNode newTreeNode = treeNodeRepository.save(treeNode);

        return newTreeNode;
    }

}

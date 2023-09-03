package org.sujecki.Service;

import org.springframework.stereotype.Service;
import org.sujecki.Exception.ParentIdRequiredException;
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

    public Optional<TreeNode> getTree(){

        return findTreeRoot();
    }
    public List<TreeNode> getAllNode(){
        return treeNodeRepository.findAll();
    }
    public TreeNode getNodeById(Long id){
        TreeNode treeNode = treeNodeRepository.findById(id).orElseThrow(()
                -> new TreeNodeNotFoundException(String.format("TreeNode with ID: %s is not found", id)));

        return treeNode;
    }

    public TreeNode addNode(NodeDTO treeNode){
        TreeNode newTreeNode = new TreeNode();

        if(treeNode.isNewTreeRoot()){
            Optional<TreeNode> actualTreeNodeRoot = findTreeRoot();
            newTreeNode.setValue(treeNode.getValue());
            if(actualTreeNodeRoot.isPresent()) {
                actualTreeNodeRoot.get().setParent(newTreeNode);
            }
            treeNodeRepository.save(newTreeNode);
        }else {
            if (treeNode.getParent_id() == null && findTreeRoot().isPresent())
                throw new ParentIdRequiredException("Tree has a root, ParentId is required!");

            Optional<TreeNode> parentNode = treeNodeRepository.findById(treeNode.getParent_id());

            newTreeNode.setValue(treeNode.getValue());
            newTreeNode.setParent(parentNode.orElseThrow(()
                    -> new ParentTreeNodeNotFoundException(String.format("Parent with ID: %s is not found", treeNode.getParent_id()))));
            treeNodeRepository.save(newTreeNode);
        }

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

    private Optional<TreeNode> findTreeRoot(){
        Optional<TreeNode> root = treeNodeRepository.findAll().stream()
                .filter(TreeNode::isRoot).findAny();

        return root;
    }
}
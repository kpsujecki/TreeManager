package org.sujecki.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.sujecki.Exception.ParentIdRequiredException;
import org.sujecki.Exception.ParentTreeNodeNotFoundException;
import org.sujecki.Exception.TreeNodeNotFoundException;
import org.sujecki.Model.NodeDTO;
import org.sujecki.Model.TreeNode;
import org.sujecki.Repository.TreeNodeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TreeNodeService {

    private static Logger logger = LogManager.getLogger(TreeNodeService.class);
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

        logger.info("Getting TreeNode by ID:{}, {}", id, treeNode);
        return treeNode;
    }

    public TreeNode addNode(NodeDTO treeNode){
        TreeNode newTreeNode = new TreeNode();
        logger.info("Adding new Node from NodeDTO {}", treeNode);

        if(treeNode.getIsNewTreeRoot()){
            logger.info("New Node is a new TreeRoot");
            replaceTreeRoot(treeNode, newTreeNode);
            treeNodeRepository.save(newTreeNode);
        }else {
            if (treeNode.getParentId() == null && findTreeRoot().isPresent())
                throw new ParentIdRequiredException("Tree has a root, ParentId is required!");

            Optional<TreeNode> parentNode = treeNodeRepository.findById(treeNode.getParentId());

            newTreeNode.setValue(treeNode.getValue());
            newTreeNode.setParent(parentNode.orElseThrow(()
                    -> new ParentTreeNodeNotFoundException(String.format("Parent with ID: %s is not found", treeNode.getParentId()))));
            treeNodeRepository.save(newTreeNode);
        }
        logger.info("Added new Node {} from NodeDTO {}", newTreeNode, treeNode);
        return newTreeNode;
    }

    public TreeNode editNode(NodeDTO treeNode){
        TreeNode newTreeNode = treeNodeRepository.findById(treeNode.getNodeId()).orElseThrow(()
                -> new TreeNodeNotFoundException(String.format("TreeNode with ID: %s is not found", treeNode.getNodeId())));

        logger.info("Node with ID: {} is editing", treeNode.getNodeId());

        if(treeNode.getParentId() != null) {
            newTreeNode.setParent(treeNodeRepository.findById(treeNode.getParentId()).orElseThrow(()
                    -> new ParentTreeNodeNotFoundException(String.format("Parent with ID: %s is not found", treeNode.getParentId()))));
        }
        if(treeNode.getParentId() == null && treeNode.getIsNewTreeRoot() && !newTreeNode.isRoot()) replaceTreeRoot(treeNode, newTreeNode);

        newTreeNode.setValue(treeNode.getValue());
        treeNodeRepository.save(newTreeNode);

        logger.info("The node has been successfully edited {} ", newTreeNode);
        return newTreeNode;
    }

    public void removeNode(Long id){
        TreeNode treeNodeToRemove = treeNodeRepository.findById(id).orElseThrow(()
                -> new TreeNodeNotFoundException(String.format("TreeNode with ID: %s is not found", id)));

        treeNodeRepository.delete(treeNodeToRemove);
        logger.info("The node has been successfully removed {} ", treeNodeToRemove);
    }

    private Optional<TreeNode> findTreeRoot(){
        Optional<TreeNode> root = treeNodeRepository.findAll().stream()
                .filter(TreeNode::isRoot).findAny();

        return root;
    }

    private void replaceTreeRoot(NodeDTO treeNode, TreeNode newTreeNode){
        Optional<TreeNode> actualTreeNodeRoot = findTreeRoot();
        newTreeNode.setValue(treeNode.getValue());
        logger.info("Replace TreeNodeRoot from {} to {}", actualTreeNodeRoot, newTreeNode);

        if(actualTreeNodeRoot.isPresent()) {
            actualTreeNodeRoot.get().setParent(newTreeNode);
        }
    }
}
package org.sujecki.Controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sujecki.Exception.ParentIdRequiredException;
import org.sujecki.Exception.ParentTreeNodeNotFoundException;
import org.sujecki.Exception.TreeNodeNotFoundException;
import org.sujecki.Model.NodeDTO;
import org.sujecki.Model.TreeNode;
import org.sujecki.Service.TreeNodeService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tree")
public class TreeNodeController {

    private static Logger logger = LogManager.getLogger(TreeNodeController.class);
    private final TreeNodeService treeNodeService;

    public TreeNodeController(TreeNodeService treeNodeService){
        this.treeNodeService = treeNodeService;
    }

    //This endpoint returns us the tree in the hierarchy from the root
    @GetMapping("/")
    public ResponseEntity<Optional<TreeNode>> getTree() {
        try {
            Optional<TreeNode> tree = treeNodeService.getTree();
            if (tree.isPresent()) {
                return new ResponseEntity<>(tree, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(tree, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //This endpoint returns us the all node in tree
    @GetMapping("/all")
    public ResponseEntity<List<TreeNode>> getAll() {
        try {
            List<TreeNode> treeNodeList = treeNodeService.getAllNode();
            if (!treeNodeList.isEmpty()) {
                return new ResponseEntity<>(treeNodeList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //This endpoint returns us node by id together with his descendants
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        try {
            TreeNode user = treeNodeService.getNodeById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch(TreeNodeNotFoundException treeNodeNotFoundException){
            return new ResponseEntity<>(treeNodeNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //This endpoint allow us to create a new node
    @PostMapping(path = "/")
    public ResponseEntity<?> createNode(@RequestBody NodeDTO newTreeNode) {
        try{
            TreeNode treeNode = treeNodeService.addNode(newTreeNode);

            return new ResponseEntity<>(treeNode, HttpStatus.CREATED);
        } catch(ParentTreeNodeNotFoundException parentTreeNodeNotFoundException){
            return new ResponseEntity<>(parentTreeNodeNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ParentIdRequiredException parentIdRequiredException){
            return new ResponseEntity<>(parentIdRequiredException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //This endpoint allow us to edit existing node
    @PatchMapping(path = "/")
    public ResponseEntity<?> updateNode(@RequestBody NodeDTO treeNode) {
        try{
            TreeNode updatedTreeNode = treeNodeService.editNode(treeNode);
            return new ResponseEntity<>(updatedTreeNode, HttpStatus.OK);
        } catch(TreeNodeNotFoundException treeNodeNotFoundException){
            return new ResponseEntity<>(treeNodeNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        } catch(ParentTreeNodeNotFoundException parentTreeNodeNotFoundException){
            return new ResponseEntity<>(parentTreeNodeNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //This endpoint allow us to remove existing node
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> removeNode(@PathVariable long id) {
        try{
            treeNodeService.removeNode(id);
            return new ResponseEntity<>(String.format("Node with ID: %s has been removed", id), HttpStatus.OK);
        } catch(TreeNodeNotFoundException treeNodeNotFoundException){
            return new ResponseEntity<>(treeNodeNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

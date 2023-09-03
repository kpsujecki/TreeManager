package org.sujecki.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sujecki.Model.NodeDTO;
import org.sujecki.Model.TreeNode;
import org.sujecki.Service.TreeNodeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tree")
public class TreeNodeController {

    private TreeNodeService treeNodeService;

    public TreeNodeController(TreeNodeService treeNodeService){
        this.treeNodeService = treeNodeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TreeNode>> getAll() {
        List<TreeNode> treeNodeList = treeNodeService.getAllNode();

        if (!treeNodeList.isEmpty()) {
            return new ResponseEntity<>(treeNodeList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(treeNodeList, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreeNode> getById(@PathVariable long id) {

        Optional<TreeNode> user = treeNodeService.getNodeById(id);

        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(user.get(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/")
    public ResponseEntity<Optional<TreeNode>> createNode(@RequestBody NodeDTO newTreeNode) {
        Optional<TreeNode> treeNode = treeNodeService.addNode(newTreeNode);

        if (treeNode.isPresent()) {
            return new ResponseEntity<>(treeNode, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.valueOf("NOT_ADDED"));
        }
    }
}

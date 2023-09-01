package org.sujecki.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        List<TreeNode> treeNodeList = treeNodeService.getAll();

        return new ResponseEntity<>(treeNodeList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreeNode> getById(@PathVariable long id) {

        Optional<TreeNode> user = treeNodeService.getById(id);

        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(user.get(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TreeNode> createNode(@RequestBody TreeNode newTreeNode) {

        TreeNode treeNode = treeNodeService.addTreeNode(newTreeNode);
        if (treeNode == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(treeNode, HttpStatus.CREATED);
        }
    }
}

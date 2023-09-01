package org.sujecki.Service;

import org.springframework.stereotype.Service;
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


    public List<TreeNode> getAll(){
        return treeNodeRepository.findAll();
    }
    public Optional<TreeNode> getById(Long id){
        Optional<TreeNode> treeNode = treeNodeRepository.findById(id);

        return treeNode;
    }

    public TreeNode addTreeNode(TreeNode treeNode){
        TreeNode newTreeNode = treeNodeRepository.save(treeNode);

        return newTreeNode;
    }
}

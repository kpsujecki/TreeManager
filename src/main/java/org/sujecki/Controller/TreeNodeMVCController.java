package org.sujecki.Controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.sujecki.Model.TreeNode;
import org.sujecki.Service.TreeNodeService;


import java.util.Optional;

@Controller
public class TreeNodeMVCController {
    private final TreeNodeService treeNodeService;
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    public TreeNodeMVCController(TreeNodeService treeNodeService){
        this.treeNodeService = treeNodeService;
    }

    @GetMapping({"/viewTree"})
    public String indexPage(Model model) throws JsonProcessingException {
        Optional<TreeNode> tree = treeNodeService.getTree();
        String json = ow.writeValueAsString(tree.get());
        model.addAttribute("nodes", json);
        return "view-tree";
    }
}

package org.sujecki.Controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.sujecki.Exception.ParentIdRequiredException;
import org.sujecki.Exception.ParentTreeNodeNotFoundException;
import org.sujecki.Exception.TreeNodeNotFoundException;
import org.sujecki.Model.NodeDTO;
import org.sujecki.Model.TreeNode;
import org.sujecki.Service.TreeNodeService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class TreeManagerController {

    private static Logger logger = LogManager.getLogger(TreeManagerController.class);

    @RequestMapping("/add")
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add.html");
        return modelAndView;
    }
}

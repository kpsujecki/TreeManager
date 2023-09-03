package org.sujecki.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.sujecki.Exception.ParentTreeNodeNotFoundException;
import org.sujecki.Exception.TreeNodeNotFoundException;
import org.sujecki.Model.NodeDTO;
import org.sujecki.Model.TreeNode;
import org.sujecki.Reposiotry.TreeNodeRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TreeNodeServiceTest {

    @Mock
    TreeNodeRepository treeNodeRepository;

    @InjectMocks
    TreeNodeService treeNodeService;

    private TreeNode parentTreeNode;

    private TreeNode childTreeNode;
    @BeforeEach
    void init(){
        treeNodeRepository = Mockito.mock(TreeNodeRepository.class);
        treeNodeService = new TreeNodeService(treeNodeRepository);

        parentTreeNode = new TreeNode();
        parentTreeNode.setValue("PARENT");
        parentTreeNode.setParent(null);
        parentTreeNode.setNode_id(1L);

        childTreeNode = new TreeNode();
        childTreeNode.setValue("CHILD");
        childTreeNode.setParent(parentTreeNode);
        childTreeNode.setNode_id(2L);
    }

    @Test
    void nodeIsAddedCorrectly() {
        //given
        NodeDTO nodeDTO = new NodeDTO();
        nodeDTO.setParent_id(parentTreeNode.getNode_id());
        nodeDTO.setValue("TEST");
        given(treeNodeRepository.findById(parentTreeNode.getNode_id())).willReturn(Optional.ofNullable(parentTreeNode));

        //when
        TreeNode savedTreeNode = treeNodeService.addNode(nodeDTO);

        //then
        assertThat(savedTreeNode).isNotNull();
        assertTrue(savedTreeNode.getParent().getNode_id()== parentTreeNode.getNode_id());
    }

    @Test
    void nodeIsNotAddedWhenParentIsWrong() {
        //given
        NodeDTO nodeDTO = new NodeDTO();
        nodeDTO.setParent_id(3L);
        nodeDTO.setValue("TEST");
        given(treeNodeRepository.findById(parentTreeNode.getNode_id())).willReturn(Optional.ofNullable(parentTreeNode));

        // when -  action or the behaviour that we are going test
        assertThrows(ParentTreeNodeNotFoundException.class, () -> {
            treeNodeService.addNode(nodeDTO);
        });

        // then
        verify(treeNodeRepository, never()).save(any(TreeNode.class));
    }

    @Test
    void nodeIsEditedCorrectly() {
        //given
        NodeDTO newTreeNode = new NodeDTO();
        newTreeNode.setValue("EDITED PARENT");
        newTreeNode.setParent_id(null);
        newTreeNode.setNode_id(1L);

        given(treeNodeRepository.findById(parentTreeNode.getNode_id())).willReturn(Optional.ofNullable(parentTreeNode));

        //when
        TreeNode editedTreeNode = treeNodeService.editNode(newTreeNode);

        //then
        assertThat(editedTreeNode).isNotNull();
        assertTrue(editedTreeNode.getNode_id() == parentTreeNode.getNode_id());
        assertTrue(editedTreeNode.getChildNodes() == parentTreeNode.getChildNodes());
        assertFalse(editedTreeNode.getValue() != parentTreeNode.getValue());
    }

    @Test
    void nodeIsNotEditedWhenNodeIdIsWrong() {
        //given
        NodeDTO newTreeNode = new NodeDTO();
        newTreeNode.setValue("EDITED PARENT");
        newTreeNode.setParent_id(null);
        newTreeNode.setNode_id(3L);

        given(treeNodeRepository.findById(parentTreeNode.getNode_id())).willReturn(null);

        // when -  action or the behaviour that we are going test
        assertThrows(TreeNodeNotFoundException.class, () -> {
            treeNodeService.editNode(newTreeNode);
        });

        // then
        verify(treeNodeRepository, never()).save(any(TreeNode.class));
    }
}
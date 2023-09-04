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
import org.sujecki.Repository.TreeNodeRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
        parentTreeNode.setNodeId(1L);

        childTreeNode = new TreeNode();
        childTreeNode.setValue("CHILD");
        childTreeNode.setParent(parentTreeNode);
        childTreeNode.setNodeId(2L);
    }

    @Test
    void nodeIsAddedCorrectly() {
        //given
        NodeDTO nodeDTO = new NodeDTO();
        nodeDTO.setParentId(parentTreeNode.getNodeId());
        nodeDTO.setValue("TEST");
        given(treeNodeRepository.findById(parentTreeNode.getNodeId())).willReturn(Optional.ofNullable(parentTreeNode));

        //when
        TreeNode savedTreeNode = treeNodeService.addNode(nodeDTO);

        //then
        assertThat(savedTreeNode).isNotNull();
        assertTrue(savedTreeNode.getParent().getNodeId()== parentTreeNode.getNodeId());
    }

    @Test
    void nodeIsNotAddedWhenParentIsWrong() {
        //given
        NodeDTO nodeDTO = new NodeDTO();
        nodeDTO.setParentId(3L);
        nodeDTO.setValue("TEST");
        given(treeNodeRepository.findById(parentTreeNode.getNodeId())).willReturn(Optional.ofNullable(parentTreeNode));

        // when -  action or the behaviour that we are going test
        assertThrows(ParentTreeNodeNotFoundException.class, () -> {
            treeNodeService.addNode(nodeDTO);
        });

        // then
        verify(treeNodeRepository, never()).save(any(TreeNode.class));
    }
    @Test
    void whenNodeHasFieldIsNewTreeRootShouldBeNewTreeRoot() {
        //given
        NodeDTO nodeDTO = new NodeDTO();
        nodeDTO.setParentId(null);
        nodeDTO.setValue("TEST");
        nodeDTO.setIsNewTreeRoot(true);

        given(treeNodeRepository.findById(parentTreeNode.getNodeId())).willReturn(Optional.ofNullable(parentTreeNode));

        // when
        TreeNode savedTreeNode = treeNodeService.addNode(nodeDTO);

        // then
        assertThat(savedTreeNode).isNotNull();
        assertTrue(savedTreeNode.isRoot());
    }

    @Test
    void nodeIsEditedCorrectly() {
        //given
        NodeDTO newTreeNode = new NodeDTO();
        newTreeNode.setValue("EDITED PARENT");
        newTreeNode.setParentId(null);
        newTreeNode.setNodeId(1L);

        given(treeNodeRepository.findById(parentTreeNode.getNodeId())).willReturn(Optional.ofNullable(parentTreeNode));

        //when
        TreeNode editedTreeNode = treeNodeService.editNode(newTreeNode);

        //then
        assertThat(editedTreeNode).isNotNull();
        assertTrue(editedTreeNode.getNodeId() == parentTreeNode.getNodeId());
        assertTrue(editedTreeNode.getChildNodes() == parentTreeNode.getChildNodes());
        assertFalse(editedTreeNode.getValue() != parentTreeNode.getValue());
    }

    @Test
    void nodeIsNotEditedWhenNodeIdIsWrong() {
        //given
        NodeDTO newTreeNode = new NodeDTO();
        newTreeNode.setValue("EDITED PARENT");
        newTreeNode.setParentId(null);
        newTreeNode.setNodeId(3L);

        given(treeNodeRepository.findById(parentTreeNode.getNodeId())).willReturn(null);

        // when -  action or the behaviour that we are going test
        assertThrows(TreeNodeNotFoundException.class, () -> {
            treeNodeService.editNode(newTreeNode);
        });

        // then
        verify(treeNodeRepository, never()).save(any(TreeNode.class));
    }
}
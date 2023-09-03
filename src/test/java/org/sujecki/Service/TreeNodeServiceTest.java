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
import org.sujecki.Exeption.ParentTreeNodeNotFoundException;
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

    private TreeNode treeNode;

    @BeforeEach
    void init(){
        treeNodeRepository = Mockito.mock(TreeNodeRepository.class);
        treeNodeService = new TreeNodeService(treeNodeRepository);

        treeNode = new TreeNode();
        treeNode.setValue("PARENT");
        treeNode.setParent(null);
        treeNode.setNode_id(1L);

    }

    @Test
    void nodeIsAddedCorrectly() {
        //given
        NodeDTO nodeDTO = new NodeDTO();
        nodeDTO.setParent_id(treeNode.getNode_id());
        nodeDTO.setValue("TEST");
        given(treeNodeRepository.findById(treeNode.getNode_id())).willReturn(Optional.ofNullable(treeNode));

        //when
        Optional<TreeNode> savedTreeNode = treeNodeService.addNode(nodeDTO);

        //then
        assertThat(savedTreeNode).isNotNull();
        assertTrue(savedTreeNode.get().getParent().getNode_id()==treeNode.getNode_id());

    }

    @Test
    void nodeIsNotAddedWhenParentIsWrong() {
        //given
        NodeDTO nodeDTO = new NodeDTO();
        nodeDTO.setParent_id(3L);
        nodeDTO.setValue("TEST");
        given(treeNodeRepository.findById(treeNode.getNode_id())).willReturn(Optional.ofNullable(treeNode));

        // when -  action or the behaviour that we are going test
        assertThrows(ParentTreeNodeNotFoundException.class, () -> {
            treeNodeService.addNode(nodeDTO);
        });

        // then
        verify(treeNodeRepository, never()).save(any(TreeNode.class));

    }
}
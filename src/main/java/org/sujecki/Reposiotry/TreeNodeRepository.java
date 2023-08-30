package org.sujecki.Reposiotry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.sujecki.Model.TreeNode;

@Repository
public interface TreeNodeRepository extends JpaRepository<TreeNode, Long> {

}

INSERT INTO tree_node (node_id, parent_id, depth, value) VALUES (1, null, 1, 'first node');
INSERT INTO tree_node (node_id, parent_id, depth, value) VALUES (2, 1, 1, 'second node first leaf');
INSERT INTO tree_node (node_id, parent_id, depth, value) VALUES (3, 1, 1, 'second node second leaf');
INSERT INTO tree_node (node_id, parent_id, depth, value) VALUES (4, 3, 1, 'third node');
INSERT INTO tree_node (node_id, parent_id, depth, value) VALUES (5, 4, 1, 'fourth node');
INSERT INTO tree_node (node_id, parent_id, depth, value) VALUES (6, 5, 1, 'fifth node');
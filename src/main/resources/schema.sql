CREATE TABLE tree_node (
                          node_id BIGINT IDENTITY PRIMARY KEY,
                          parent_id BIGINT NULL REFERENCES tree_node (node_id),
                          value VARCHAR(255)
);
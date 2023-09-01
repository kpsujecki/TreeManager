CREATE TABLE tree_node (
                          node_id BIGINT PRIMARY KEY,
                          parent_id BIGINT NULL REFERENCES tree_node (node_id),
                          depth INT NOT NULL,
                          value VARCHAR(255)
);
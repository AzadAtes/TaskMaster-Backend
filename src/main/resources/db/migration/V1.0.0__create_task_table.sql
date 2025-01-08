CREATE TABLE tasks (
                       id VARCHAR(36) PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       description TEXT,
                       parent_id VARCHAR(36),
                       CONSTRAINT fk_parent_task FOREIGN KEY (parent_id) REFERENCES tasks(id) ON DELETE CASCADE
);

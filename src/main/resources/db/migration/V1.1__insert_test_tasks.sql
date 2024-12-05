-- V1.1__insert_test_tasks.sql

-- Insert main tasks
INSERT INTO tasks (id, name, description) VALUES ('1', 'Main Task 1', 'Description for Main Task 1');
INSERT INTO tasks (id, name, description) VALUES ('2', 'Main Task 2', 'Description for Main Task 2');
INSERT INTO tasks (id, name, description) VALUES ('3', 'Main Task 3', 'Description for Main Task 3');
INSERT INTO tasks (id, name, description) VALUES ('4', 'Main Task 4', 'Description for Main Task 4');
INSERT INTO tasks (id, name, description) VALUES ('5', 'Main Task 5', 'Description for Main Task 5');

-- Insert subtasks for Main Task 1
INSERT INTO tasks (id, name, description, parent_id) VALUES ('1-1', 'Subtask 1-1', 'Description for Subtask 1-1', '1');

-- Insert sub-subtasks for Subtask 1-1
INSERT INTO tasks (id, name, description, parent_id) VALUES ('1-1-1', 'Sub-subtask 1-1-1', 'Description for Sub-subtask 1-1-1', '1-1');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('1-1-2', 'Sub-subtask 1-1-2', 'Description for Sub-subtask 1-1-2', '1-1');

-- Insert sub-sub-subtasks for Sub-subtask 1-1-1
INSERT INTO tasks (id, name, description, parent_id) VALUES ('1-1-1-1', 'Sub-sub-subtask 1-1-1-1', 'Description for Sub-sub-subtask 1-1-1-1', '1-1-1');

-- Insert subtasks for Main Task 2
INSERT INTO tasks (id, name, description, parent_id) VALUES ('2-1', 'Subtask 2-1', 'Description for Subtask 2-1', '2');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('2-2', 'Subtask 2-2', 'Description for Subtask 2-2', '2');

-- Insert sub-subtasks for Subtask 2-1
INSERT INTO tasks (id, name, description, parent_id) VALUES ('2-1-1', 'Sub-subtask 2-1-1', 'Description for Sub-subtask 2-1-1', '2-1');

-- Insert subtasks for Main Task 3
INSERT INTO tasks (id, name, description, parent_id) VALUES ('3-1', 'Subtask 3-1', 'Description for Subtask 3-1', '3');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('3-2', 'Subtask 3-2', 'Description for Subtask 3-2', '3');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('3-3', 'Subtask 3-3', 'Description for Subtask 3-3', '3');

-- Insert sub-subtasks for Subtask 3-1
INSERT INTO tasks (id, name, description, parent_id) VALUES ('3-1-1', 'Sub-subtask 3-1-1', 'Description for Sub-subtask 3-1-1', '3-1');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('3-1-2', 'Sub-subtask 3-1-2', 'Description for Sub-subtask 3-1-2', '3-1');

-- Insert sub-sub-subtasks for Sub-subtask 3-1-1
INSERT INTO tasks (id, name, description, parent_id) VALUES ('3-1-1-1', 'Sub-sub-subtask 3-1-1-1', 'Description for Sub-sub-subtask 3-1-1-1', '3-1-1');

-- Insert subtasks for Main Task 4
INSERT INTO tasks (id, name, description, parent_id) VALUES ('4-1', 'Subtask 4-1', 'Description for Subtask 4-1', '4');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('4-2', 'Subtask 4-2', 'Description for Subtask 4-2', '4');

-- Insert sub-subtasks for Subtask 4-1
INSERT INTO tasks (id, name, description, parent_id) VALUES ('4-1-1', 'Sub-subtask 4-1-1', 'Description for Sub-subtask 4-1-1', '4-1');

-- Insert subtasks for Main Task 5
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-1', 'Subtask 5-1', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-2', 'Subtask 5-2', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-3', 'Subtask 5-3', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-4', 'Subtask 5-4', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-5', 'Subtask 5-5', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-6', 'Subtask 5-6', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-7', 'Subtask 5-7', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-8', 'Subtask 5-8', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-9', 'Subtask 5-9', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-10', 'Subtask 5-10', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-11', 'Subtask 5-11', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-12', 'Subtask 5-12', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-13', 'Subtask 5-13', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-14', 'Subtask 5-14', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-15', 'Subtask 5-15', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-16', 'Subtask 5-16', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-17', 'Subtask 5-17', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-18', 'Subtask 5-18', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-19', 'Subtask 5-19', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-20', 'Subtask 5-20', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-21', 'Subtask 5-21', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-22', 'Subtask 5-22', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-23', 'Subtask 5-23', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-24', 'Subtask 5-24', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-25', 'Subtask 5-25', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-26', 'Subtask 5-26', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-27', 'Subtask 5-27', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-28', 'Subtask 5-28', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-29', 'Subtask 5-29', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-30', 'Subtask 5-30', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-31', 'Subtask 5-31', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-32', 'Subtask 5-32', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-33', 'Subtask 5-33', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-34', 'Subtask 5-34', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-35', 'Subtask 5-35', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-36', 'Subtask 5-36', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-37', 'Subtask 5-37', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-38', 'Subtask 5-38', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-39', 'Subtask 5-39', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-40', 'Subtask 5-40', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-41', 'Subtask 5-41', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-42', 'Subtask 5-42', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-43', 'Subtask 5-43', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-44', 'Subtask 5-44', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-45', 'Subtask 5-45', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-46', 'Subtask 5-46', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-47', 'Subtask 5-47', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-48', 'Subtask 5-48', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-49', 'Subtask 5-49', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-50', 'Subtask 5-50', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-51', 'Subtask 5-51', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-52', 'Subtask 5-52', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-53', 'Subtask 5-53', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-54', 'Subtask 5-54', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-55', 'Subtask 5-55', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-56', 'Subtask 5-56', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-57', 'Subtask 5-57', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-58', 'Subtask 5-58', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-59', 'Subtask 5-59', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-60', 'Subtask 5-60', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-61', 'Subtask 5-61', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-62', 'Subtask 5-62', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-63', 'Subtask 5-63', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-64', 'Subtask 5-64', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-65', 'Subtask 5-65', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-66', 'Subtask 5-66', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-67', 'Subtask 5-67', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-68', 'Subtask 5-68', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-69', 'Subtask 5-69', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-70', 'Subtask 5-70', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-71', 'Subtask 5-71', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-72', 'Subtask 5-72', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-73', 'Subtask 5-73', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-74', 'Subtask 5-74', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-75', 'Subtask 5-75', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-76', 'Subtask 5-76', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-77', 'Subtask 5-77', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-78', 'Subtask 5-78', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-79', 'Subtask 5-79', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-80', 'Subtask 5-80', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-81', 'Subtask 5-81', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-82', 'Subtask 5-82', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-83', 'Subtask 5-83', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-84', 'Subtask 5-84', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-85', 'Subtask 5-85', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-86', 'Subtask 5-86', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-87', 'Subtask 5-87', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-88', 'Subtask 5-88', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-89', 'Subtask 5-89', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-90', 'Subtask 5-90', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-91', 'Subtask 5-91', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-92', 'Subtask 5-92', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-93', 'Subtask 5-93', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-94', 'Subtask 5-94', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-95', 'Subtask 5-95', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-96', 'Subtask 5-96', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-97', 'Subtask 5-97', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-98', 'Subtask 5-98', '', '5');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-99', 'Subtask 5-99', '', '5');

-- Insert sub-subtasks for Subtask 5-1
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-1-1', 'Sub-subtask 5-1-1', 'Description for Sub-subtask 5-1-1', '5-1');
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-1-2', 'Sub-subtask 5-1-2', 'Description for Sub-subtask 5-1-2', '5-1');

-- Insert sub-sub-subtasks for Sub-subtask 5-1-1
INSERT INTO tasks (id, name, description, parent_id) VALUES ('5-1-1-1', 'Sub-sub-subtask 5-1-1-1', 'Description for Sub-sub-subtask 5-1-1-1', '5-1-1');
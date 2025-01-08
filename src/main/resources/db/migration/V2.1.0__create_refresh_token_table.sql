CREATE TABLE `refresh_token` (
                                 `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 `token` VARCHAR(255) NOT NULL UNIQUE,
                                 `expiration_date` DATETIME NOT NULL,
                                 `revoked` BOOLEAN NOT NULL DEFAULT FALSE,
                                 `user_id` BIGINT,
                                 CONSTRAINT `fk_refresh_token_user` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
);

-- Indexes for performance
CREATE INDEX `idx_refresh_token_user_id` ON `refresh_token` (`user_id`);

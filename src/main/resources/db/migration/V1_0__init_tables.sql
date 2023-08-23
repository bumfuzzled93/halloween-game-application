CREATE TABLE IF NOT EXISTS `redemption`
(
    `id`                 int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id`            int,
    `staff_id`           int,
    `photo_path`         varchar(255),
    `amount`             int,
    `redemption_status`  ENUM ('VALID', 'INVALID', 'REDEEMED'),
    `create_timestamp`   timestamp DEFAULT CURRENT_TIMESTAMP,
    `modified_timestamp` timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `staff`
(
    `id`               int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `username`         varchar(255) NOT NULL,
    `password`         varchar(255) NOT NULL,
    `create_timestamp` timestamp DEFAULT CURRENT_TIMESTAMP,
    `update_timestamp` timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `player`
(
    `id`   int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(255)
);

CREATE TABLE IF NOT EXISTS `game`
(
    `id`                   int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `game_length_seconds`  int,
    `start_time_seconds`   int,
    `current_time_seconds` int,
    `ghost_killed`         int,
    `score`                DECIMAL(4, 2)
);
DROP DATABASE `halloween-app`;
CREATE DATABASE IF NOT EXISTS `halloween-app`;

USE `halloween-app`;

-- `halloween-app`.game definition

CREATE TABLE `game`
(
    `id`                  int          NOT NULL AUTO_INCREMENT,
    `photon_id`           varchar(255) NOT NULL,
    `game_length_seconds` int                                DEFAULT NULL,
    `start_time_seconds`  int                                DEFAULT NULL,
    `ghost_killed`        int                                DEFAULT '0',
    `score`               decimal(4, 0)                      DEFAULT '0',
    `status`              enum ('CREATED','STARTED','ENDED') DEFAULT 'CREATED',
    PRIMARY KEY (`id`),
    UNIQUE KEY `photon_id` (`photon_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- `halloween-app`.player definition

CREATE TABLE `player`
(
    `id`                   int          NOT NULL AUTO_INCREMENT,
    `username`             varchar(255) NOT NULL,
    `password`             varchar(40) DEFAULT NULL,
    `mobile_number`        varchar(8)   NOT NULL,
    `email`                varchar(255) NOT NULL,
    `credits`              int         DEFAULT '0',
    `score`                varchar(40) DEFAULT '0',
    `powerup_ammo_box`     int         DEFAULT '0',
    `powerup_drone`        int         DEFAULT '0',
    `powerup_bonus`        int         DEFAULT '0',
    `starter_pack_claimed` tinyint(1)  DEFAULT '0',
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`),
    UNIQUE KEY `mobile_number` (`mobile_number`),
    UNIQUE KEY `email` (`email`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- `halloween-app`.staff definition

CREATE TABLE `staff`
(
    `id`               int       NOT NULL AUTO_INCREMENT,
    `username`         varchar(255) NOT NULL,
    `password`         varchar(255) NOT NULL,
    `create_timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `update_timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- `halloween-app`.weapon definition

CREATE TABLE `weapon`
(
    `id`   int NOT NULL AUTO_INCREMENT,
    `upgrade`     int          NOT NULL,
    `weapon_name` varchar(255) NOT NULL,
    `cost` int NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- `halloween-app`.game_player definition

CREATE TABLE `game_player`
(
    `id`        int NOT NULL AUTO_INCREMENT,
    `game_id`   int NOT NULL,
    `player_id` int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `game_id` (`game_id`),
    KEY `player_id` (`player_id`),
    CONSTRAINT `game_player_ibfk_1` FOREIGN KEY (`game_id`) REFERENCES `game` (`id`),
    CONSTRAINT `game_player_ibfk_2` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- `halloween-app`.player_weapon definition

CREATE TABLE `player_weapon`
(
    `id`       int NOT NULL AUTO_INCREMENT,
    `player_id` int NOT NULL,
    `weapon_id` int NOT NULL,
    `equipped` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`id`),
    KEY `player_id` (`player_id`),
    KEY `weapon_id` (`weapon_id`),
    CONSTRAINT `player_weapon_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
    CONSTRAINT `player_weapon_ibfk_2` FOREIGN KEY (`weapon_id`) REFERENCES `weapon` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- `halloween-app`.redemption definition

CREATE TABLE `redemption`
(
    `id`                 int       NOT NULL AUTO_INCREMENT,
    `player_id`          int                                 DEFAULT NULL,
    `staff_id`           int                                 DEFAULT NULL,
    `photo_path`         varchar(255)                        DEFAULT NULL,
    `credits_issued`     int                                 DEFAULT NULL,
    `redemption_status`  enum ('VALID','INVALID','REDEEMED') DEFAULT NULL,
    `image_file_uuid` varchar(255),
    `create_timestamp`   timestamp NULL                      DEFAULT CURRENT_TIMESTAMP,
    `modified_timestamp` timestamp NULL                      DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `staff_id` (`staff_id`),
    CONSTRAINT `redemption_ibfk_1` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


-- `halloween-app`.voucher definition

CREATE TABLE `voucher`
(
    `id`                 int         NOT NULL AUTO_INCREMENT,
    `voucher_uuid`       varchar(36) NOT NULL,
    `player_id`          int         NOT NULL,
    `amount`             int         NOT NULL,
    `comments`           text,
    `status`             enum ('AWARDED','USED','DISABLED') DEFAULT 'AWARDED',
    `type`               enum ('FIRST_TIME','NORMAL')       DEFAULT NULL,
    `create_timestamp`   timestamp   NULL                   DEFAULT CURRENT_TIMESTAMP,
    `modified_timestamp` timestamp   NULL                   DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `player_id` (`player_id`),
    CONSTRAINT `voucher_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `file`
(
    `id`                 int           NOT NULL AUTO_INCREMENT,
    `uuid`               varchar(255)  NOT NULL,
    `original_file_name` varchar(1024) NOT NULL,
    `file_size`          int           NOT NULL,
    `file_type`          varchar(255)  NOT NULL,
    `data` MEDIUMBLOB NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

insert into staff (username, password)
values ('admin', '1234');

insert into weapon (upgrade, weapon_name)
values (1, 'Basic Lethal Gun'),
       (1, 'Hyper Gun'),
       (1, 'Chain lightning  Gun'),
       (1, 'Basic Stun Gun'),
       (1, 'Electric explosion gun'),
       (1, 'Tornado Gun'),
       (1, 'Basic Lethal Garlic'),
       (1, 'Explosive garlic'),
       (1, 'Fire zone garlic'),
       (1, 'Basic Stun Garlic'),
       (1, 'Time slow Garlic'),
       (1, 'Black hole Garlic'),
       (2, 'Basic Lethal Gun'),
       (2, 'Hyper Gun'),
       (2, 'Chain lightning  Gun'),
       (2, 'Basic Stun Gun'),
       (2, 'Electric explosion gun'),
       (2, 'Tornado Gun'),
       (2, 'Basic Lethal Gun'),
       (2, 'Hyper Gun'),
       (2, 'Chain lightning  Gun'),
       (2, 'Basic Stun Gun'),
       (2, 'Electric explosion gun'),
       (2, 'Tornado Gun'),
       (2, 'Basic Lethal Garlic'),
       (2, 'Explosive garlic'),
       (2, 'Fire zone garlic'),
       (2, 'Basic Stun Garlic'),
       (2, 'Time slow Garlic'),
       (2, 'Black hole Garlic'),
       (2, 'Basic Lethal Gun'),
       (2, 'Hyper Gun'),
       (2, 'Chain lightning  Gun'),
       (2, 'Basic Stun Gun'),
       (2, 'Electric explosion gun'),
       (2, 'Tornado Gun'),
       (3, 'Basic Lethal Garlic'),
       (3, 'Explosive garlic'),
       (3, 'Fire zone garlic'),
       (3, 'Basic Stun Garlic'),
       (3, 'Time slow Garlic'),
       (3, 'Black hole Garlic'),
       (3, 'Basic Lethal Gun'),
       (3, 'Hyper Gun'),
       (3, 'Chain lightning  Gun'),
       (3, 'Basic Stun Gun'),
       (3, 'Electric explosion gun'),
       (3, 'Tornado Gun'),
       (4, 'Basic Lethal Garlic'),
       (4, 'Explosive garlic'),
       (4, 'Fire zone garlic'),
       (4, 'Basic Stun Garlic'),
       (4, 'Time slow Garlic'),
       (4, 'Black hole Garlic'),
       (4, 'Basic Lethal Gun'),
       (4, 'Hyper Gun'),
       (4, 'Chain lightning  Gun'),
       (4, 'Basic Stun Gun'),
       (4, 'Electric explosion gun'),
       (4, 'Tornado Gun'),
       (5, 'Basic Lethal Garlic'),
       (5, 'Explosive garlic'),
       (5, 'Fire zone garlic'),
       (5, 'Basic Stun Garlic'),
       (5, 'Time slow Garlic'),
       (5, 'Black hole Garlic'),
       (5, 'Basic Lethal Gun'),
       (5, 'Hyper Gun'),
       (5, 'Chain lightning  Gun'),
       (5, 'Basic Stun Gun'),
       (5, 'Electric explosion gun'),
       (5, 'Tornado Gun');
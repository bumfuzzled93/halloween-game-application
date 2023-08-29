DROP DATABASE `halloween-app`;
CREATE DATABASE IF NOT EXISTS `halloween-app`;

USE `halloween-app`;

CREATE TABLE IF NOT EXISTS `staff`
(
    `id`               int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `username`         varchar(255) NOT NULL,
    `password`         varchar(255) NOT NULL,
    `create_timestamp` timestamp DEFAULT CURRENT_TIMESTAMP,
    `update_timestamp` timestamp DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS `redemption`
(
    `id`                 int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id`            int,
    `staff_id`           int,
    `photo_path`         varchar(255),
    `amount`             int,
    `redemption_status`  ENUM ('VALID', 'INVALID', 'REDEEMED'),
    `create_timestamp`   timestamp DEFAULT CURRENT_TIMESTAMP,
    `modified_timestamp` timestamp DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`staff_id`) REFERENCES staff (`id`)
);


CREATE TABLE IF NOT EXISTS `game`
(
    `id`           int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `photon_id`    varchar(255) NOT NULL unique,
    `game_length_seconds` int,
    `start_time_seconds`  int,
    `ghost_killed` int                                  default 0,
    `score`        DECIMAL(4)                           default 0,
    `status`       ENUM ('CREATED', 'STARTED', 'ENDED') default 'CREATED'
);

CREATE TABLE IF NOT EXISTS `player`
(
    `id`                   int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `username`             varchar(255) NOT NULL UNIQUE,
    `mobile_number`        varchar(8)   NOT NULL UNIQUE,
    `email`                varchar(255) NOT NULL UNIQUE,
    `credits`              int     default 0,
    `powerup_ammo_box`     int     default 0,
    `powerup_drone`        int     default 0,
    `powerup_bonus` int default 0,
    `starter_pack_claimed` boolean default false
);

CREATE TABLE IF NOT EXISTS `weapon`
(
    `id`          int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `upgrade`     int          NOT NULL,
    `weapon_name` varchar(255) NOT NULL,
    `cost`        int          NOT NULL default 0
);

CREATE TABLE IF NOT EXISTS `player_weapon`
(
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `player_id` int NOT NULL,
    `weapon_id` int NOT NULL,
    `equipped`  boolean default false,

    FOREIGN KEY (`player_id`) REFERENCES player (id),
    FOREIGN KEY (`weapon_id`) REFERENCES weapon (id)
);

CREATE TABLE IF NOT EXISTS `game_player`
(
    `id`        int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `game_id`   int NOT NULL,
    `player_id` int NOT NULL,

    FOREIGN KEY (`game_id`) REFERENCES game (id),
    FOREIGN KEY (`player_id`) REFERENCES player (id)
);


insert into weapon (upgrade, weapon_name)
values (1, 'Black hole Garlic'),
       (1, 'Basic Lethal Gun'),
       (1, 'Hyper Gun'),
       (1, 'Chain lightning  Gun'),
       (1, 'Basic Stun Gun'),
       (1, 'Electric explosion gun'),
       (1, 'Tornado Gun'),
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



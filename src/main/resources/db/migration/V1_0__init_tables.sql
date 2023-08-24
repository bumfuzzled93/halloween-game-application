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
    `id`                   int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `game_length_seconds`  int,
    `start_time_seconds`   int,
    `current_time_seconds` int,
    `ghost_killed`         int,
    `score`                DECIMAL(4, 2)
);

CREATE TABLE IF NOT EXISTS `player`
(
    `id`            int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `player_name`   varchar(255),
    `mobile_number` varchar(8)
);

CREATE TABLE IF NOT EXISTS `weapon`
(
    `id`           int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `upgrade`      int          not null,
    `weapon_name`  varchar(255) not null,
    `kill_chance`  decimal(2, 2),
    `ammo`         int,
    `reload_sec`   int,
    `cooldown_sec` int
);

CREATE TABLE IF NOT EXISTS `player_weapon`
(
    `player_id` int NOT NULL,
    `weapon_id` int NOT NULL,
    `loaded`    boolean default false,

    FOREIGN KEY (`player_id`) REFERENCES player (id),
    FOREIGN KEY (`weapon_id`) REFERENCES weapon (id)
);

CREATE TABLE IF NOT EXISTS `game_player`
(
    `game_id`   int NOT NULL,
    `player_id` int NOT NULL,

    FOREIGN KEY (`game_id`) REFERENCES game (id),
    FOREIGN KEY (`player_id`) REFERENCES player (id)
);


insert into weapon (upgrade, weapon_name, kill_chance, ammo, reload_sec, cooldown_sec)
values (1, 'Black hole Garlic', 0, 5, 20, 2),
       (1, 'Basic Lethal Gun', 0.1, 5, 20, 2),
       (1, 'Hyper Gun', 0.2, 3, 28, 4),
       (1, 'Chain lightning  Gun', 0.2, 0, 20, 0),
       (1, 'Basic Stun Gun', 0, 5, 20, 2),
       (1, 'Electric explosion gun', 0, 3, 20, 3),
       (1, 'Tornado Gun', 0, 2, 8, 3),
       (2, 'Basic Lethal Garlic', 0, 0, 0, 0),
       (2, 'Explosive garlic', 0, 0, 0, 0),
       (2, 'Fire zone garlic', 0, 0, 0, 0),
       (2, 'Basic Stun Garlic', 0, 0, 0, 0),
       (2, 'Time slow Garlic', 0, 0, 0, 0),
       (2, 'Black hole Garlic', 0, 0, 0, 0),
       (2, 'Basic Lethal Gun', 0, 0, 0, 0),
       (2, 'Hyper Gun', 0, 0, 0, 0),
       (2, 'Chain lightning  Gun', 0, 0, 0, 0),
       (2, 'Basic Stun Gun', 0, 0, 0, 0),
       (2, 'Electric explosion gun', 0, 0, 0, 0),
       (2, 'Tornado Gun', 0, 0, 0, 0),
       (3, 'Basic Lethal Garlic', 0, 0, 0, 0),
       (3, 'Explosive garlic', 0, 0, 0, 0),
       (3, 'Fire zone garlic', 0, 0, 0, 0),
       (3, 'Basic Stun Garlic', 0, 0, 0, 0),
       (3, 'Time slow Garlic', 0, 0, 0, 0),
       (3, 'Black hole Garlic', 0, 0, 0, 0),
       (3, 'Basic Lethal Gun', 0, 0, 0, 0),
       (3, 'Hyper Gun', 0, 0, 0, 0),
       (3, 'Chain lightning  Gun', 0, 0, 0, 0),
       (3, 'Basic Stun Gun', 0, 0, 0, 0),
       (3, 'Electric explosion gun', 0, 0, 0, 0),
       (3, 'Tornado Gun', 0, 0, 0, 0),
       (4, 'Basic Lethal Garlic', 0, 0, 0, 0),
       (4, 'Explosive garlic', 0, 0, 0, 0),
       (4, 'Fire zone garlic', 0, 0, 0, 0),
       (4, 'Basic Stun Garlic', 0, 0, 0, 0),
       (4, 'Time slow Garlic', 0, 0, 0, 0),
       (4, 'Black hole Garlic', 0, 0, 0, 0),
       (4, 'Basic Lethal Gun', 0, 0, 0, 0),
       (4, 'Hyper Gun', 0, 0, 0, 0),
       (4, 'Chain lightning  Gun', 0, 0, 0, 0),
       (4, 'Basic Stun Gun', 0, 0, 0, 0),
       (4, 'Electric explosion gun', 0, 0, 0, 0),
       (4, 'Tornado Gun', 0, 0, 0, 0),
       (5, 'Basic Lethal Garlic', 0, 0, 0, 0),
       (5, 'Explosive garlic', 0, 0, 0, 0),
       (5, 'Fire zone garlic', 0, 0, 0, 0),
       (5, 'Basic Stun Garlic', 0, 0, 0, 0),
       (5, 'Time slow Garlic', 0, 0, 0, 0),
       (5, 'Black hole Garlic', 0, 0, 0, 0),
       (5, 'Basic Lethal Gun', 0, 0, 0, 0),
       (5, 'Hyper Gun', 0, 0, 0, 0),
       (5, 'Chain lightning  Gun', 0, 0, 0, 0),
       (5, 'Basic Stun Gun', 0, 0, 0, 0),
       (5, 'Electric explosion gun', 0, 0, 0, 0),
       (5, 'Tornado Gun', 0, 0, 0, 0);



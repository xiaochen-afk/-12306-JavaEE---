-- 12306 demo schema and synthetic data.
-- Import into an EMPTY MySQL 8.0+ database. This file contains no records
-- exported from the original ticket database and never drops existing tables.
SET NAMES utf8mb4;

CREATE TABLE `all_seat_tpe` (
  `id` int NOT NULL AUTO_INCREMENT,
  `seat_type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`, `seat_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` varchar(255) NOT NULL,
  `seat_type` varchar(255) DEFAULT NULL,
  `seat_id` varchar(255) NOT NULL,
  `train_id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`, `order_id`, `seat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `train_id` varchar(20) NOT NULL,
  `start_station` varchar(20) NOT NULL,
  `end_station` varchar(20) NOT NULL,
  `start_time` varchar(225) NOT NULL,
  `seat_type` varchar(10) NOT NULL,
  `passenger_name` varchar(20) NOT NULL,
  `id_card` varchar(20) NOT NULL,
  `status` varchar(10) DEFAULT '待支付',
  `telephone` varchar(255) NOT NULL,
  `seat_id` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`, `telephone`, `id_card`, `seat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `passenger` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `telephone` varchar(255) NOT NULL,
  `id_card` varchar(255) NOT NULL,
  PRIMARY KEY (`id`, `telephone`, `id_card`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `seat` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `train_id` varchar(20) NOT NULL,
  `seat_no` varchar(10) NOT NULL,
  `seat_type` varchar(10) NOT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_train_seat` (`train_id`, `seat_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `seat_count` (
  `id` int NOT NULL AUTO_INCREMENT,
  `train_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `bussiness_seat` int DEFAULT NULL,
  `first_seat` int DEFAULT NULL,
  `second_seat` int DEFAULT NULL,
  `no_seat` int DEFAULT NULL,
  PRIMARY KEY (`id`, `train_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `train` (
  `id` int NOT NULL AUTO_INCREMENT,
  `train_id` varchar(255) NOT NULL,
  `train_type` varchar(255) DEFAULT NULL,
  `start_station` varchar(255) DEFAULT NULL,
  `end_station` varchar(255) DEFAULT NULL,
  `start_time` date DEFAULT NULL,
  `end_time` date DEFAULT NULL,
  `run_data` varchar(255) DEFAULT NULL,
  `is_running` int DEFAULT NULL,
  `other` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`, `train_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- All values below are made up for this repository. IDs and phone numbers
-- deliberately use non-real placeholders; the current app does not validate them.
INSERT INTO `all_seat_tpe` (`seat_type`) VALUES
  ('商务座'), ('一等座'), ('二等座'), ('无座');

INSERT INTO `user` (`username`, `password`, `email`, `telephone`) VALUES
  ('demo_user', 'demo123', 'demo@example.invalid', '00000000000');

INSERT INTO `passenger` (`name`, `telephone`, `id_card`) VALUES
  ('示例乘客甲', '00000000001', 'DEMO-ID-001'),
  ('示例乘客乙', '00000000002', 'DEMO-ID-002');

INSERT INTO `train` (`train_id`, `train_type`, `start_station`, `end_station`,
                     `start_time`, `end_time`, `run_data`, `is_running`, `other`) VALUES
  ('G9001', '高铁城际', '示例东站', '示例西站',
   DATE_ADD(CURDATE(), INTERVAL 7 DAY), DATE_ADD(CURDATE(), INTERVAL 7 DAY), '每日', 1, '虚构演示车次'),
  ('D9002', '动车', '演示北站', '演示南站',
   DATE_ADD(CURDATE(), INTERVAL 8 DAY), DATE_ADD(CURDATE(), INTERVAL 8 DAY), '每日', 1, '虚构演示车次'),
  ('G9003', '复兴号', '样例一站', '样例二站',
   DATE_ADD(CURDATE(), INTERVAL 9 DAY), DATE_ADD(CURDATE(), INTERVAL 9 DAY), '每日', 1, '虚构演示车次');

-- Counts match the AVAILABLE seat rows below. Standing tickets have no seat row.
INSERT INTO `seat_count` (`train_id`, `bussiness_seat`, `first_seat`, `second_seat`, `no_seat`) VALUES
  ('G9001', 2, 2, 3, 5),
  ('D9002', 0, 1, 3, 3),
  ('G9003', 1, 2, 2, 2);

INSERT INTO `seat` (`train_id`, `seat_no`, `seat_type`, `status`) VALUES
  ('G9001', 'G1-B01', '商务座', 'AVAILABLE'),
  ('G9001', 'G1-B02', '商务座', 'AVAILABLE'),
  ('G9001', 'G1-F01', '一等座', 'AVAILABLE'),
  ('G9001', 'G1-F02', '一等座', 'AVAILABLE'),
  ('G9001', 'G1-S01', '二等座', 'OCCUPIED'),
  ('G9001', 'G1-S02', '二等座', 'AVAILABLE'),
  ('G9001', 'G1-S03', '二等座', 'AVAILABLE'),
  ('G9001', 'G1-S04', '二等座', 'AVAILABLE'),
  ('D9002', 'D2-F01', '一等座', 'AVAILABLE'),
  ('D9002', 'D2-S01', '二等座', 'AVAILABLE'),
  ('D9002', 'D2-S02', '二等座', 'AVAILABLE'),
  ('D9002', 'D2-S03', '二等座', 'AVAILABLE'),
  ('G9003', 'G3-B01', '商务座', 'AVAILABLE'),
  ('G9003', 'G3-F01', '一等座', 'AVAILABLE'),
  ('G9003', 'G3-F02', '一等座', 'AVAILABLE'),
  ('G9003', 'G3-S01', '二等座', 'AVAILABLE'),
  ('G9003', 'G3-S02', '二等座', 'AVAILABLE');

INSERT INTO `orders` (`train_id`, `start_station`, `end_station`, `start_time`,
                      `seat_type`, `passenger_name`, `id_card`, `telephone`, `seat_id`) VALUES
  ('G9001', '示例东站', '示例西站',
   DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL 7 DAY), '%Y-%m-%d'),
   '二等座', '示例乘客甲', 'DEMO-ID-001', '00000000001', 'G1-S01');

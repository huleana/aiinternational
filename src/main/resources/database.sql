CREATE DATABASE ai_hackaton CHARACTER SET utf8 COLLATE utf8_general_ci;
use ai_hackaton;
CREATE TABLE `reviews` (
  `product_id` int(10) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) DEFAULT NULL,
  `price` int(30) DEFAULT NULL,
  `description` text,
  `rating` decimal(11,2) DEFAULT NULL,
  `sentiment` varchar(30) DEFAULT NULL,
  `sentiment_score` decimal(11,2) DEFAULT NULL,
  `key_phrase` varchar(255) DEFAULT NULL,
  `key_phrase_score` decimal(11,2) DEFAULT NULL,
  `entity` varchar(255) DEFAULT NULL,
  `review` text,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
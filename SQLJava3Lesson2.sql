USE world;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--------- table#1 user 
CREATE TABLE IF NOT EXISTS  `users` (
  
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  
  `user_name` varchar(50) NOT NULL,
  
  `user_login` varchar(50) NOT NULL,
  
  `user_password` varchar(70) NOT NULL,
  
  `user_last_action` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_user`)

) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

INSERT INTO users (`user_name`, `user_login`, `user_password`, `user_last_action`) VALUES ('Bob', 'bob', '12345',null) 
INSERT INTO users (`user_name`, `user_login`, `user_password`, `user_last_action`) VALUES ('Nickolay', 'nick3', '67890',null) 

DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(25) NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,

  PRIMARY KEY (`id`),
  CONSTRAINT UK_USERNAME UNIQUE KEY (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
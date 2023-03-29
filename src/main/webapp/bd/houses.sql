/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.5.5-10.4.24-MariaDB : Database - houses
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`houses` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `houses`;

/*Table structure for table `house` */

DROP TABLE IF EXISTS `house`;

CREATE TABLE `house` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(1000) NOT NULL,
  `province` varchar(500) NOT NULL,
  `canton` varchar(500) NOT NULL,
  `address` varchar(1000) NOT NULL,
  `price` float(10,2) NOT NULL,
  `rooms` int(11) NOT NULL,
  `bathrooms` int(11) NOT NULL,
  `area` int(11) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `status` varchar(50) NOT NULL,
  `id_user` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_user` (`id_user`),
  CONSTRAINT `house_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

/*Data for the table `house` */

LOCK TABLES `house` WRITE;

insert  into `house`(`id`,`title`,`province`,`canton`,`address`,`price`,`rooms`,`bathrooms`,`area`,`description`,`status`,`id_user`) values (47,'Alquilo departamente super cÃ³modo y con excelente ubicaciÃ³n','12','1201','Colimes y la que cruza',120.00,0,0,454,'Casa con mirador','DISPONIBLE',1),(50,'Casa en alquiler con vista a la playa','12','1201','Cdla. Los Perales',150.00,0,0,100,'Se admiten mascotas\r\n\r\nCuenta con 2 garages y vigilancia las 24 horas','DISPONIBLE',7);

UNLOCK TABLES;

/*Table structure for table `images` */

DROP TABLE IF EXISTS `images`;

CREATE TABLE `images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `house_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `house_id` (`house_id`),
  CONSTRAINT `images_ibfk_1` FOREIGN KEY (`house_id`) REFERENCES `house` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;

/*Data for the table `images` */

LOCK TABLES `images` WRITE;

insert  into `images`(`id`,`name`,`house_id`) values (82,'img-47-1.jpg',47),(83,'img-47-2.jpg',47),(85,'img-50-1.jpg',50),(86,'img-50-2.jpg',50);

UNLOCK TABLES;

/*Table structure for table `rental` */

DROP TABLE IF EXISTS `rental`;

CREATE TABLE `rental` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `house_id` int(11) NOT NULL,
  `tenant_id` int(11) NOT NULL,
  `rental_time` int(11) NOT NULL,
  `payment` float(10,2) NOT NULL,
  `current_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `tenant_id` (`tenant_id`),
  KEY `FK1aosh4bo34lyqi8br15140fbo` (`house_id`),
  CONSTRAINT `FK1aosh4bo34lyqi8br15140fbo` FOREIGN KEY (`house_id`) REFERENCES `images` (`id`),
  CONSTRAINT `rental_ibfk_1` FOREIGN KEY (`house_id`) REFERENCES `house` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rental_ibfk_2` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `rental` */

LOCK TABLES `rental` WRITE;

UNLOCK TABLES;

/*Table structure for table `tenant` */

DROP TABLE IF EXISTS `tenant`;

CREATE TABLE `tenant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `lastname` varchar(500) NOT NULL,
  `telephone` varchar(500) NOT NULL,
  `dni` varchar(500) NOT NULL,
  `email` varchar(500) NOT NULL,
  `password` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `tenant` */

LOCK TABLES `tenant` WRITE;

insert  into `tenant`(`id`,`name`,`lastname`,`telephone`,`dni`,`email`,`password`) values (4,'Pedrito','Morales','2343522','14433253','pedrito@mail.com','123456');

UNLOCK TABLES;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `lastname` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `telephone` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

LOCK TABLES `user` WRITE;

insert  into `user`(`id`,`name`,`lastname`,`email`,`password`,`telephone`) values (1,'Super','Admin','admin@mail.com','12345','593982055157'),(7,'Gloria','Flores Gonzales','gloria1@mail.com','12345','593944646466'),(8,'Felipe','Maldonado','felipe@mail.com','2143446','593944646346');

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

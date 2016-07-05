
USE `filmstoretest`;

delete from country;
delete from actor;
delete from stars;
delete from allgenres;
delete from filmgenres;
delete from comments;
delete from orders;
delete from preferences;
delete from discount;
delete from rating;
delete from users;
delete from films;


/*Data for the table `actor` */

insert  into `actor`(`id`,`name_actor`) values (1,'Тиль Швайгер'),(2,'Ян Йозеф Лиферс'),(3,'Райан Рейнольдс'),(4,'Морена Баккарин');

/*Data for the table `allgenres` */

insert  into `allgenres`(`id`,`genre`) values (1,'Драма'),(2,'Комедия'),(3,'Криминал'),(4,'Фантастика'),(5,'Боевик'),(6,'Приключения');

/*Data for the table `comments` */

insert  into `comments`(`film_id`,`user_id`,`mark`,`comment`,`date_com`,`status`) values (1,2,10,'Замечательный фильм!','2016-06-15 12:18:44','checked'),(2,2,6,'Игра актеров неправдоподобна','2016-06-15 12:18:44','checked');

/*Data for the table `country` */

insert  into `country`(`id`,`country`) values (1,'Германия'),(2,'США');

/*Data for the table `discount` */

insert  into `discount`(`discount_id`,`sum_from`,`sum_to`,`discount`) values (1,'0.00','100.00','5.00'),(2,'100.00','500.00','10.00'),(3,'500.00','1000.00','15.00'),(4,'1000.00','100000.00','20.00');

/*Data for the table `filmgenres` */

insert  into `filmgenres`(`film_id`,`genre_id`) values (1,1),(1,2),(2,2),(1,3),(2,4),(2,5),(2,6);

/*Data for the table `films` */

insert  into `films`(`id`,`title`,`release_year`,`country_id`,`director`,`description`,`duration`,`quality`,`age_restriction`,`price`,`link`) values (1,'Достучаться до небес',1997,1,'Томас Ян','Быстрый автомобиль, миллион марок в багажнике, и всего одна неделя жить',87,'FullHD',16,'10.00','mov1.hdv'),(2,'Дэдпул',2016,2,'Тим Миллер','Уэйд Уилсон — наёмник. Будучи побочным продуктом программы вооружённых сил под названием «Оружие X», Уилсон приобрёл невероятную силу, проворство и способность к исцелению.',108,'HD',18,'15.00','mov2.hdv');

/*Data for the table `orders` */

insert  into `orders`(`id`,`film_id`,`user_id`,`date_sale`,`sum`,`status`) values (1,1,2,'2016-06-15 12:15:47','10.00','completed'),(2,2,2,'2016-06-15 12:15:47','15.00','completed'),(3,1,1,'2016-06-15 12:15:47','10.00','completed');

/*Data for the table `preferences` */

insert  into `preferences`(`user_id`,`genre_id`) values (1,1),(1,2),(2,2),(2,3);

/*Data for the table `rating` */

insert  into `rating`(`film_id`,`rating`) values (1,'10.0'),(2,'6.0');

/*Data for the table `stars` */

insert  into `stars`(`film_id`,`actor_id`) values (1,1),(1,2),(2,3),(2,4);

/*Data for the table `users` */

insert  into `users`(`id`,`name`,`email`,`password`,`phone`,`photo`,`date_reg`,`role`) values (1,'Andrew','andrew@gmail.com','123123','+375291112233',NULL,'2016-06-15 10:48:52','admin'),(2,'Mike','mike@mail.ru','qwerty','+375447776655','123.jpg','2016-06-15 10:48:52','user'),(3,'Kristina','kris@yahoo.com','qwe321','+375252223344','kr.jpg','2016-06-15 10:51:17','user');


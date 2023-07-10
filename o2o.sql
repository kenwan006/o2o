use o2o;
create table `tb_area`(
	`area_id` int(2) NOT NULL AUTO_INCREMENT,
	`area_name` varchar(200) NOT NULL,
	`priority` int(2) NOT NULL DEFAULT '0',
	`create_time` datetime DEFAULT NULL,
	`last_edit_time` datetime DEFAULT NULL,
	primary key(`area_id`),
	unique key `UK_AREA`(`area_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

create table `tb_person_info`(
	`user_id` int(10) NOT NULL AUTO_INCREMENT,
    `name` varchar(32) DEFAULT NULL,
    `profile_img` varchar(1024) DEFAULT NULL,
    `email` varchar(1024) DEFAULT NULL,
    `gender` varchar(2) DEFAULT NULL,
    `enable_status` int(2) NOT NULL DEFAULT '0' COMMENT '0:Not allowed to access the shop, 1: Allowed to access the shop',
    `user_type` int(2) NOT NULL DEFAULT '1' COMMENT '1:Customer, 2:Owner, 3:Admin',
    `create_time` datetime DEFAULT NULL,
    `last_edit_time` datetime DEFAULT NULL,
    primary key(`user_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

create table `tb_wechat_auth`(
	`wechat_auth_id` int(10) NOT NULL AUTO_INCREMENT,
	`user_id` int(10) NOT NULL,
	`open_id` varchar(1024) NOT NULL,
    `create_time` datetime DEFAULT NULL,
    primary key(`wechat_auth_id`),
    unique key(`open_id`),
    constraint `fk_wechatauth_profile` foreign key(`user_id`) references `tb_person_info`(`user_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

create table `tb_local_auth`(

	`local_auth_id` int(10) NOT NULL AUTO_INCREMENT,
    `user_id` int(10) NOT NULL,
    `username` varchar(128) NOT NULL,
    `password` varchar(128) NOT NULL,
    `create_time` datetime DEFAULT NULL,
    `last_edit_time` date DEFAULT NULL,
    primary key(`local_auth_id`),
    unique key `uk_local_profile`(`username`),
    constraint `fk_localauth_profile` foreign key(`user_id`) references `tb_person_info`(`user_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

create table `tb_head_line`(
	`line_id` int(100) NOT NULL AUTO_INCREMENT,
    `line_name` varchar(1000) DEFAULT NULL,
    `line_link` varchar(2000) NOT NULL,
    `line_img` varchar(2000) NOT NULL,
    `priority` int(2) DEFAULT NULL,
    `enable_status` int(2) NOT NULL DEFAULT'0',
    `create_time` datetime DEFAULT NULL,
    `last_edit_time` datetime DEFAULT NULL,
    primary key(`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

create table `tb_shop_category` (
	`shop_category_id` int(11) NOT NULL AUTO_INCREMENT,
    `shop_category_name` varchar(100) NOT NULL DEFAULT '',
    `shop_category_desc` varchar(1000) NOT NULL DEFAULT '',
    `shop_category_img` varchar(2000) DEFAULT NULL,
    `priority` int(2) NOT NULL DEFAULT '0',
    `create_time` datetime DEFAULT NULL,
    `parent_id` int(11) DEFAULT NULL,
    primary key(`shop_category_id`),
   constraint `fk_shop_category_self` foreign key(`parent_id`) references `tb_shop_category`(`shop_category_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

create table `tb_shop` (
`shop_id` int(10) NOT NULL AUTO_INCREMENT,
`owner_id` int(10) NOT NULL COMMENT 'shop owner',
`area_id` int(5) DEFAULT NULL,
`shop_category_id` int(11) DEFAULT NULL,
`shop_name` varchar(256) NOT NULL,
`shop_desc` varchar(1024) DEFAULT NULL,
`shop_addr` varchar(200) DEFAULT NULL,
`phone` varchar(128) DEFAULT NULL,
`shop_img` varchar(1024) DEFAULT NULL,
`priority` int(3) DEFAULT '0',
`create_time` datetime DEFAULT NULL,
`last_edit_time` datetime DEFAULT NULL,
`enable_statue` int(2) NOT NULL DEFAULT '0',
`advice` varchar(255) DEFAULT NULL,
primary key (`shop_id`),
constraint `fk_shop_area` foreign key(`area_id`) references `tb_area`(`area_id`),
constraint `fk_shop_profile` foreign key(`owner_id`) references `tb_person_info`(`user_id`),
constraint `fk_shop_shopcate` foreign key(`shop_category_id`) references `tb_shop_category`(`shop_category_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

create table `tb_product_category` (
	`product_category_id` int(11) NOT NULL AUTO_INCREMENT,
    `product_category_name` varchar(100) NOT NULL,
    `priority` int(2) DEFAULT '0',
    `create_time` datetime DEFAULT NULL,
	`shop_id` int(20) NOT NULL DEFAULT '0',
    primary key(`product_category_id`),
    constraint `fk_procate_shop` foreign key(`shop_id`) references `tb_shop`(`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

create table `tb_product` (
`product_id` int(100) NOT NULL AUTO_INCREMENT,
`product_name` varchar(100) NOT NULL,
`product_desc` varchar(2000) DEFAULT NULL,
`img_addr` varchar(2000) DEFAULT '',
`normal_price` varchar(100) DEFAULT NULL,
`promotion_price` varchar(100) DEFAULT NULL,
`priority` int(2) NOT NULL DEFAULT '0',
`create_time` datetime DEFAULT NULL,
`last_edit_time` datetime DEFAULT NULL,
`enable_status`int(2) NOT NULL DEFAULT '0',
`product_category_id` int(11) DEFAULT NULL,
`shop_id` int(20) NOT NULL DEFAULT '0',
primary key(`product_id`),
constraint `fk_product_procate` foreign key(`product_category_id`) references `tb_product_category` (`product_category_id`),
constraint `fk_product_shop` foreign key(`shop_id`) references `tb_shop`(`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

create table `tb_product_img` (
	`product_img_id` int(20) NOT NULL AUTO_INCREMENT,
	`img_addr` varchar(2000) NOT NULL,
    `img_desc` varchar(2000) DEFAULT NULL,
    `priority` int(2) DEFAULT '0',
	`create_time` datetime DEFAULT NULL,
    `product_id` int(20) DEFAULT NULL,
    primary key(`product_img_id`),
    constraint`fk_proming_product` foreign key(`product_id`) references `tb_product`(`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


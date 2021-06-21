create table `bank_transfer`
(
    `id`      bigint not null,
    `amount`  Decimal(10, 2) default 0.0,
    `date`    datetime(6),
    `user_id` bigint,
    primary key (`id`)
) engine = InnoDB;
create table `transaction`
(
    `id`      bigint not null,
    `amount`  Decimal(10, 2) default 0.0,
    `date`    datetime(6),
    `from_id` bigint,
    `to_id`   bigint,
    primary key (`id`)
) engine = InnoDB;
create table `hibernate_sequence`
(
    `next_val` bigint
) engine = InnoDB;
insert into `hibernate_sequence`
values (1);
create table `user`
(
    `id`         bigint not null,
    `email`      varchar(255),
    `first_name` varchar(255),
    `last_name`  varchar(255),
    `money`      Decimal(10, 2) default 0.0,
    `password`   varchar(255),
    `roles`      varchar(255),
    `version`    bigint not null,
    primary key (`id`)
) engine = InnoDB;
create table `user_connections`
(
    `user_id`        bigint not null,
    `connections_id` bigint not null,
    primary key (`user_id`, `connections_id`)
) engine = InnoDB;
alter table `bank_transfer`
    add constraint `FK82qhsd99oh3vbsoffl1bfxnka` foreign key (`user_id`) references `user` (`id`);
alter table `transaction`
    add constraint `FKiu9x08jt0iqbhmd0g4so81cht` foreign key (`from_id`) references `user` (`id`);
alter table `transaction`
    add constraint `FK4xel9cvgm7pa48epx1k014h4e` foreign key (`to_id`) references `user` (`id`);
alter table `user_connections`
    add constraint `FKi314btny333rt060rakliut0q` foreign key (`connections_id`) references `user` (`id`);
alter table `user_connections`
    add constraint `FK8kfwcuk5jm3grx45jcfk58gwe` foreign key (`user_id`) references `user` (`id`);

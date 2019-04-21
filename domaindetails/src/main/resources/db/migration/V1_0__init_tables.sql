CREATE TABLE `dnpedia_cron_entity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT current_timestamp,
  `domain_reg_date` datetime DEFAULT NULL,
--  `done` tinyint(1) DEFAULT '0',
--  `last_page_fetched` bigint(20) NOT NULL DEFAULT '0',
--  `picked` tinyint(1) DEFAULT '0',
--  `total_pages` bigint(20) NOT NULL DEFAULT '0',
  `updated_at` datetime DEFAULT current_timestamp on update current_timestamp,
  `version` bigint(20) NOT NULL DEFAULT '0',
--  `zone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

alter table dnpedia_cron_entity add
  constraint uniqueregdate unique (domain_reg_date);


--alter table dnpedia_cron_entity add
--  constraint uniquedatezone unique (domain_reg_date, zone);

CREATE TABLE `domain_entity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT current_timestamp,
  `domain_name` varchar(255) DEFAULT NULL,
  `http_protocol` varchar(255) DEFAULT "HTTP",
  `http_status_code` varchar(255) DEFAULT NULL,
  `is_alive` tinyint(1) DEFAULT '0',
  `is_parsed` tinyint(1) DEFAULT '0',
  `is_visited` tinyint(1) DEFAULT '0',
  `is_uploaded` tinyint(1) DEFAULT '0',
  `updated_at` datetime DEFAULT current_timestamp on update current_timestamp,
  `version` bigint(20) NOT NULL DEFAULT '0',
  `dnpedia_cron_entity_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK197umkmvxggd2h0krfn1pg97h` (`dnpedia_cron_entity_id`),
  CONSTRAINT `FK197umkmvxggd2h0krfn1pg97h` FOREIGN KEY (`dnpedia_cron_entity_id`) REFERENCES `dnpedia_cron_entity` (`id`)
);

alter table domain_entity add
  constraint UKjfyymtipos7s6bt7txnjvp7dc unique (domain_name);

CREATE TABLE `emails` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email_id` varchar(255) NOT NULL,
  `domain_entity_id` bigint(20) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp,
  `updated_at` datetime DEFAULT current_timestamp on update current_timestamp,
  PRIMARY KEY (`id`),
  KEY `FK47ry7bge08syr8mnwtghuokkd` (`domain_entity_id`),
  CONSTRAINT `FK47ry7bge08syr8mnwtghuokkd` FOREIGN KEY (`domain_entity_id`) REFERENCES `domain_entity` (`id`)
);

--alter table emails add constraint uniqueemail unique (email_id);
alter table domain_entity add column retry_count bigint(20) NOT NULL DEFAULT '0';

alter table domain_entity add column verification_substring varchar(255);

alter table domain_entity add column domain_score bigint(20) NOT NULL DEFAULT '0';
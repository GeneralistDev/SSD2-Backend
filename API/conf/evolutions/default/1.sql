# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table visual_model (
  id                        bigint not null,
  json_model                TEXT,
  constraint pk_visual_model primary key (id))
;

create sequence visual_model_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists visual_model;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists visual_model_seq;


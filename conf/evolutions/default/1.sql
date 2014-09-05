# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table letter (
  id                        bigint not null,
  letter                    varchar(255),
  start_frequency           float,
  end_occur_mult            float,
  constraint pk_letter primary key (id))
;

create table letter_carry (
  id                        bigint not null,
  letter_id                 bigint,
  next_id                   bigint,
  frequency                 float,
  constraint pk_letter_carry primary key (id))
;

create sequence letter_seq;

create sequence letter_carry_seq;

alter table letter_carry add constraint fk_letter_carry_letter_1 foreign key (letter_id) references letter (id) on delete restrict on update restrict;
create index ix_letter_carry_letter_1 on letter_carry (letter_id);
alter table letter_carry add constraint fk_letter_carry_next_2 foreign key (next_id) references letter (id) on delete restrict on update restrict;
create index ix_letter_carry_next_2 on letter_carry (next_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists letter;

drop table if exists letter_carry;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists letter_seq;

drop sequence if exists letter_carry_seq;


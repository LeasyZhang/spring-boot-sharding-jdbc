drop index if exists sh_item_0.sh_item_0_owner_id_created_time_idx;
drop index if exists sh_item_1.sh_item_1_owner_id_created_time_idx;
drop index if exists sh_item_2.sh_item_2_owner_id_created_time_idx;
drop index if exists sh_item_3.sh_item_3_owner_id_created_time_idx;

drop table if exists sh_item_0;
drop table if exists sh_item_1;
drop table if exists sh_item_2;
drop table if exists sh_item_3;

create table sh_item_0
(
  id           serial not null,
  created_time timestamp without time zone,
  updated_time timestamp without time zone,
  username     varchar(80),
  owner_id     bigint,
  content      varchar(512),
  primary key (id)
);

create index on sh_item_0 (owner_id, created_time);

create table sh_item_1
(
  id           serial not null,
  created_time timestamp without time zone,
  updated_time timestamp without time zone,
  username     varchar(80),
  owner_id     bigint,
  content      varchar(512),
  primary key (id)
);

create index on sh_item_1 (owner_id, created_time);

create table sh_item_2
(
  id           serial not null,
  created_time timestamp without time zone,
  updated_time timestamp without time zone,
  username     varchar(80),
  owner_id     bigint,
  content      varchar(512),
  primary key (id)
);

create index on sh_item_2 (owner_id, created_time);

create table sh_item_3
(
  id           serial not null,
  created_time timestamp without time zone,
  updated_time timestamp without time zone,
  username     varchar(80),
  owner_id     bigint,
  content      varchar(512),
  primary key (id)
);

create index on sh_item_3 (owner_id, created_time);
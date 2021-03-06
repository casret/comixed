-- create table

CREATE TABLE user_properties (
  id bigint generated by default as identity not null,
  user_id bigint,
  name varchar(128),
  value varchar(255),
  primary key (id)
);

-- add constraints

ALTER TABLE user_properties add constraint user_properties_unique unique (user_id, name);
ALTER TABLE user_properties add constraint user_properties_user_fk foreign key (user_id) references users;

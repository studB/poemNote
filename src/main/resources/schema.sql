-- pn_user
create table pn_user ( 
    id int primary key, 
    role varchar(20), 
    access_power int, 
    key varchar(10), 
    editable boolean, 
    readable boolean
);
-- pn_category_meta
create table pn_category_meta ( 
    id int primary key, 
    category varchar(100), 
    access_level int, 
    publishable boolean, 
    editable boolean, 
    title_exist boolean, 
    tag_exist boolean, 
    completed_status_exist boolean, 
    written_status_exist boolean, 
    value_status_exist boolean 
);
-- pn_log
create table pn_log ( 
    id serial primary key, 
    subject varchar(100), 
    action varchar(100), 
    log varchar(255), 
    year int, 
    month int, 
    day int, 
    hour int, 
    minute int 
);
-- pn_backup
create table pn_backup ( 
    id serial primary key, 
    category_id int, 
    text_id varchar(255), 
    publish_index int, 
    title varchar(255), 
    body text, 
    tag varchar(255), 
    completed_status varchar(255), 
    written_status varchar(255), 
    value_status varchar(255), 
    time timestamp, 
    foreign key(category_id) references pn_category_meta (id) 
);
-- pn_published
create table pn_published ( 
    id serial primary key, 
    category varchar(255), 
    text_id varchar(255), 
    publish_num int 
);
-- pn_poem
create table pn_poem ( 
    id serial primary key, 
    text_id varchar(255), 
    published boolean, 
    publish_index int, 
    publish_time timestamp, 
    title varchar(255), 
    body text, 
    tag varchar(255), 
    completed_status varchar(255), 
    written_status varchar(255), 
    value_status varchar(255), 
    created_at timestamp, 
    modified_at timestamp
);
-- pn_line
create table pn_line ( 
    id serial primary key, 
    text_id varchar(255), 
    body text, 
    created_at timestamp 
);
-- pn_opinion
create table pn_opinion ( 
    id serial primary key, 
    text_id varchar(255), 
    body text, 
    tag varchar(255), 
    created_at timestamp, 
    modified_at timestamp 
);
-- pn_theory
create table pn_theory ( 
    id serial primary key, 
    text_id varchar(255), 
    title varchar(255), 
    body text, 
    tag varchar(255), 
    created_at timestamp, 
    modified_at timestamp 
);
-- pn_review
create table pn_review ( 
    id serial primary key, 
    text_id varchar(255), 
    published boolean, 
    publish_index int, 
    publish_time timestamp, 
    title varchar(255), 
    body text, 
    tag varchar(255), 
    written_status varchar(255), 
    value_status varchar(255), 
    created_at timestamp, 
    modified_at timestamp 
);
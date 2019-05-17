create table media
(
    id              character varying(64) not null primary key,
    original_path   text,
    is_write        boolean               not null default false,
    compressed_path text,
    is_compressed   boolean               not null default false,
    created_by      character varying(100),
    created_date    timestamp             not null default now()
);

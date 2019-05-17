create table uploads
(
    id              character varying(64) not null primary key,
    original_path   text                  not null,
    is_write        boolean               not null default false,
    compressed_path text,
    is_compressed   boolean               not null default false
);

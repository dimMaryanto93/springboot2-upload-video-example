create table media
(
    id                 character varying(64) not null primary key,
    original_path      text,
    original_file_name text                  not null,
    original_file_ext  character varying(10) not null,
    is_write           boolean               not null default false,
    thumbnail_path     text,
    is_thumbnail       boolean               not null default false,
    compressed_path    text,
    is_compressed      boolean               not null default false,
    created_by         character varying(100),
    created_date       timestamp             not null default now()
);

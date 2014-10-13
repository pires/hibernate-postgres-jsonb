
    drop table if exists JSONB_ENTITIES cascade;

    create table JSONB_ENTITIES (
        id uuid not null,
        documents jsonb,
        version int8,
        primary key (id)
    );

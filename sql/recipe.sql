CREATE TABLE public.recipe
(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name text COLLATE pg_catalog."default" NOT NULL,
    content text COLLATE pg_catalog."default" NOT NULL,
    created TIMESTAMP,
    last_modified TIMESTAMP,
    CONSTRAINT recipe_pkey PRIMARY KEY (id)
);
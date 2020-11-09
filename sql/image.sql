CREATE TABLE public.image
(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name text COLLATE pg_catalog."default" NOT NULL,
    image bytea NOT NULL,
    recipe_id BIGINT NOT NULL,
    CONSTRAINT image_pkey PRIMARY KEY (id),
    CONSTRAINT fk_recipe
        FOREIGN KEY(recipe_id)
            REFERENCES recipe(id)
            ON DELETE SET NULL
);
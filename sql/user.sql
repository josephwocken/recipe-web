CREATE TABLE public.user
(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    password text COLLATE pg_catalog."default" NOT NULL
);

INSERT INTO public."user"(password)
	VALUES ('test-pwd');
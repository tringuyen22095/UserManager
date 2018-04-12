DROP TABLE IF EXISTS public.users;
CREATE TABLE public.users
(
  id serial primary key,
  user_name text,
  password text,
  email text,
  tel_no text
);

INSERT INTO users (user_name, password) VALUES
('admin', '$2a$10$NqMU9asEttojGJuo.ImjvOfVZL9A3tm2RXwmj2Npr5o0q5D6lUrrG');

DROP TABLE IF EXISTS public.log;
CREATE TABLE public.log
(
	date_modify TIMESTAMP PRIMARY KEY DEFAULT(CURRENT_TIMESTAMP),
	user_modify	TEXT,
	act TEXT,
	old_value TEXT,
	new_value TEXT
);
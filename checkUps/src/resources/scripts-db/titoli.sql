UPDATE public.titoli
SET descrizione = REGEXP_REPLACE(descrizione, '^[0-9 ]+', '');

UPDATE public.titoli
SET descrizione = REGEXP_REPLACE(descrizione, '^[\s\)]+', '')
WHERE descrizione ~ '^[\s\)]+';


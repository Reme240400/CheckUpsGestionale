UPDATE public.reparti
SET descrizione = SUBSTRING(descrizione FROM 1 FOR POSITION('T' IN descrizione) - 1)
WHERE descrizione LIKE '%T%';

UPDATE public.reparti
SET data = TO_DATE(LEFT(descrizione, 10), 'YYYY-MM-DD');
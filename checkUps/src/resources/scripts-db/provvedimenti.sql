UPDATE public.provvedimenti
SET soggetti_esposti = SUBSTRING(soggetti_esposti FROM 3)
WHERE soggetti_esposti LIKE '- %'; 
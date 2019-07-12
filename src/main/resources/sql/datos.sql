--/**
-- * Author:  josorio
-- * Created: 9/07/2019
-- * https://colombianismos.caroycuervo.gov.co/documentos/abreviaturas.pdf
-- */
insert into tipificador
(nombre, tipo)
values ('Cesar','MARCA_REGIONAL'),
('La Guajira','MARCA_REGIONAL'),
('Magdalena','MARCA_REGIONAL'),
('Atlantico','MARCA_REGIONAL'),
('Bolívar','MARCA_REGIONAL'),
('Sucre','MARCA_REGIONAL'),
('Atlantico','MARCA_REGIONAL'),
('Córdova','MARCA_REGIONAL'),
('Antioquia','MARCA_REGIONAL'),
('Chocó','MARCA_REGIONAL'),
('Caldas','MARCA_REGIONAL'),
('Risaralda','MARCA_REGIONAL'),
('Quindío','MARCA_REGIONAL'),
('Valle del Cauca','MARCA_REGIONAL'),
('Tolima','MARCA_REGIONAL'),
('Norte de Santander','MARCA_REGIONAL'),
('Santander','MARCA_REGIONAL'),
('Boyacá','MARCA_REGIONAL'),
('Cundinamarca','MARCA_REGIONAL'),
('Bogotá','MARCA_REGIONAL'),
('Huila','MARCA_REGIONAL'),
('Meta','MARCA_REGIONAL'),
('Casanare','MARCA_REGIONAL'),
('Arauca','MARCA_REGIONAL'),
('Vichada','MARCA_REGIONAL'),
('Guainía','MARCA_REGIONAL'),
('Vaupés','MARCA_REGIONAL'),
('Guaviare','MARCA_REGIONAL'),
('Caquetá','MARCA_REGIONAL'),
('Cauca','MARCA_REGIONAL'),
('Nariño','MARCA_REGIONAL'),
('Putumayo','MARCA_REGIONAL'),
('Amazonas','MARCA_REGIONAL'),
('adjetivo invariable','MARCA_GRAMATICAL'),
('adjetivo','MARCA_GRAMATICAL'),
('adjetivo/sustantivo','MARCA_GRAMATICAL'),
('adverbio','MARCA_GRAMATICAL'),
('afectivo','MARCA_GRAMATICAL'),
('femenino','MARCA_GRAMATICAL'),
('masculino','MARCA_GRAMATICAL'),
('interjección','MARCA_GRAMATICAL'),
('obsolescente','MARCA_GRAMATICAL'),
('plural','MARCA_GRAMATICAL'),
('prefijo','MARCA_GRAMATICAL'),
('preposición','MARCA_GRAMATICAL'),
('verbo pronominal','MARCA_GRAMATICAL'),
('pronombre','MARCA_GRAMATICAL'),
('sustantivo ambiguo','MARCA_GRAMATICAL'),
('sustantivo común','MARCA_GRAMATICAL'),
('sustantivo','MARCA_GRAMATICAL'),
('sustantivo/adjetivo','MARCA_GRAMATICAL'),
('sufijo','MARCA_GRAMATICAL'),
('usado más como pronominal','MARCA_GRAMATICAL'),
('usado más en plural','MARCA_GRAMATICAL'),
('usado también como plural','MARCA_GRAMATICAL'),
('usado también como pronominal','MARCA_GRAMATICAL'),
('verbo','MARCA_GRAMATICAL'),
('delincuencial','MARCA_USO'),
('deportes','MARCA_USO'),
('despectivo','MARCA_USO'),
('drogadicción','MARCA_USO'),
('estudiantil','MARCA_USO'),
('eufemismo','MARCA_USO'),
('humorístico','MARCA_USO'),
('informal','MARCA_USO'),
('informática','MARCA_USO'),
('insulto','MARCA_USO'),
('irónico','MARCA_USO'),
('juvenil','MARCA_USO'),
('narcotráfico','MARCA_USO'),
('rural','MARCA_USO'),
('tabú','MARCA_USO'),
('también','MARCA_USO'),
('urbano','MARCA_USO'),
('vulgar','MARCA_USO'),
('CEMD','FUENTE'),
('CORDE','FUENTE'),
('CORPES','FUENTE'),
('CREA','FUENTE'),
('WEB','FUENTE');

--/**
-- * Regiones de caro y cuervo
-- * amazónico, antioqueño caldense, caucano valluno, cundiboyacense, llanero, nariñense, Pacífico, santandereano, tolimense huilense
-- */

insert into lema
(texto, sufijo, genero)
values ('Reblujo', '', 'm'),
('Reguero', '', 'm'),
('rebrujo', '', 'm'),
('Abanico', '', 'm'),
('Abombado', 'da', null),
('Abombao', 'bá', null),
('Picho', 'cha', null),
('China', '', 'f'),
('Pepena', '', 'f'),
('Sopladera', '', 'f'),
('Ventiadora', '', 'f');


insert into definicion
(definicion, ejemplo)
values('Gran cantidad de cosas desordenadas y dispersas en un sitio', 'Mario, hagame el favor y arregla ese reblujo'),
('Utensilio liviano y flexible hecho con una pequeña estera de fibras vegetales, que se usa para avivar el fuego', null),
('Referido a un líquido o a un alimento, que está descompuesto', null);

--https://colombianismos.caroycuervo.gov.co/documentos/mapa_regiones_colombianismos.pdf

insert into relacion_lema_definicion
(lema_id, definicion_id, ejemplo, regiones)
values(
	(select id from lema where texto='Reblujo'),
	(select id from definicion where definicion='Gran cantidad de cosas desordenadas y dispersas en un sitio'),
	'Guardaba mi ropa doblada con celo compulsivo, animado diariamente por mi madre que me decía: "Mario, hágame el favor y arregla ese reblujo que hasta culebras debe haber ahí"',
	'["Antioquia","Caldas"]'
);

insert into relacion_lema_definicion
(lema_id, definicion_id, ejemplo, regiones)
values(
	(select id from lema where texto='rebrujo'),
	(select id from definicion where definicion='Gran cantidad de cosas desordenadas y dispersas en un sitio'),
	null,
	'["Antioquia","Caldas"]'
);

insert into relacion_lema_definicion
(lema_id, definicion_id, ejemplo, regiones)
values(
	(select id from lema where texto='Reguero'),
	(select id from definicion where definicion='Gran cantidad de cosas desordenadas y dispersas en un sitio'),
	null,
	'["Antioquia","Caldas"]'
);

insert into relacion_lema_definicion
(lema_id, definicion_id, ejemplo, regiones)
values(
	(select id from lema where texto='Abanico'),
	(select id from definicion where definicion='Utensilio liviano y flexible hecho con una pequeña estera de fibras vegetales, que se usa para avivar el fuego'),
	null,
	'["Cundinamarca","Boyacá"]'
);

insert into relacion_lema_definicion
(lema_id, definicion_id, ejemplo, regiones)
values(
	(select id from lema where texto='China'),
	(select id from definicion where definicion='Utensilio liviano y flexible hecho con una pequeña estera de fibras vegetales, que se usa para avivar el fuego'),
	null,
	null
);

insert into relacion_lema_definicion
(lema_id, definicion_id, ejemplo, regiones)
values(
	(select id from lema where texto='Pepena'),
	(select id from definicion where definicion='Utensilio liviano y flexible hecho con una pequeña estera de fibras vegetales, que se usa para avivar el fuego'),
	null,
	'["Chocó","Valle del Cauca","Cauca","Nariño"]'
);

insert into relacion_lema_definicion
(lema_id, definicion_id, ejemplo, regiones)
values(
	(select id from lema where texto='Sopladera'),
	(select id from definicion where definicion='Utensilio liviano y flexible hecho con una pequeña estera de fibras vegetales, que se usa para avivar el fuego'),
	'La sopladera es un elemento importante en el Tolima que todavía se aprecia entre las vendedoras de alimentos.',
	null
);

insert into relacion_lema_definicion
(lema_id, definicion_id, ejemplo, regiones)
values(
	(select id from lema where texto='Ventiadora'),
	(select id from definicion where definicion='Utensilio liviano y flexible hecho con una pequeña estera de fibras vegetales, que se usa para avivar el fuego'),
	null,
	'["Caquetá","Putumayo","Amazonas","Vaupés","Guaviare","Guanía"]'
);

insert into relacion_lema_definicion
(lema_id, definicion_id, ejemplo, regiones)
values(
	(select id from lema where texto='Abombado'),
	(select id from definicion where definicion='Referido a un líquido o a un alimento, que está descompuesto'),
	null,
	'["Atlántico","Bolívar","Cesar", "Córdoba","La Guajira","Magdalena","Sucre"]'
);

insert into relacion_lema_definicion
(lema_id, definicion_id, ejemplo, regiones)
values(
	(select id from lema where texto='Abombao'),
	(select id from definicion where definicion='Referido a un líquido o a un alimento, que está descompuesto'),
	null,
	null
);

insert into relacion_lema_definicion
(lema_id, definicion_id, ejemplo, regiones)
values(
	(select id from lema where texto='Picho'),
	(select id from definicion where definicion='Referido a un líquido o a un alimento, que está descompuesto'),
	'No pudimos usar los tomates para el guiso porque ya estaban pichos',
	null
);

--https://colombianismos.caroycuervo.gov.co/documentos/abreviaturas.pdf
insert into relacion_tipificador_definicion
(definicion_id, tipificador_id)
values(
    (select id from definicion where definicion='Gran cantidad de cosas desordenadas y dispersas en un sitio'),
    (select id from tipificador where nombre ='informal' )
);

insert into relacion_tipificador_definicion
(definicion_id, tipificador_id)
values(
    (select id from definicion where definicion='Gran cantidad de cosas desordenadas y dispersas en un sitio'),
    (select id from tipificador where nombre ='WEB' )
);

insert into relacion_tipificador_definicion
(definicion_id, tipificador_id)
values(
    (select id from definicion where definicion='Referido a un líquido o a un alimento, que está descompuesto'),
    (select id from tipificador where nombre ='informal' )
);

insert into relacion_tipificador_definicion
(definicion_id, tipificador_id)
values(
    (select id from definicion where definicion='Referido a un líquido o a un alimento, que está descompuesto'),
    (select id from tipificador where nombre ='adjetivo' )
);

insert into relacion_tipificador_definicion
(definicion_id, tipificador_id)
values(
    (select id from definicion where definicion='Utensilio liviano y flexible hecho con una pequeña estera de fibras vegetales, que se usa para avivar el fuego'),
    (select id from tipificador where nombre ='rural' )
);
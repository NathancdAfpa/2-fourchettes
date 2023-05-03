--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

-- Started on 2023-05-02 16:13:22

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 3373 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 224 (class 1255 OID 41017)
-- Name: update_moyenne(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_moyenne() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  UPDATE public.recette SET moyenne = (
    SELECT AVG(note) FROM public.commentaire WHERE id_recette = NEW.id_recette
  )
  WHERE id_recette = NEW.id_recette;
  RETURN NEW;
END;
$$;


ALTER FUNCTION public.update_moyenne() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 223 (class 1259 OID 33200)
-- Name: commentaire; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.commentaire (
    id_commentaire integer NOT NULL,
    texte character varying(400) NOT NULL,
    note integer,
    date date,
    id_user integer,
    id_recette integer NOT NULL
);


ALTER TABLE public.commentaire OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 33199)
-- Name: commentaire_id_commentaire_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.commentaire_id_commentaire_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.commentaire_id_commentaire_seq OWNER TO postgres;

--
-- TOC entry 3374 (class 0 OID 0)
-- Dependencies: 222
-- Name: commentaire_id_commentaire_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.commentaire_id_commentaire_seq OWNED BY public.commentaire.id_commentaire;


--
-- TOC entry 221 (class 1259 OID 33118)
-- Name: ingredient; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ingredient (
    id_ingredient integer NOT NULL,
    nom character varying(50),
    quantite integer NOT NULL,
    id_recette integer NOT NULL,
    unite character varying
);


ALTER TABLE public.ingredient OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 33117)
-- Name: ingrédient_id_ingrédient_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."ingrédient_id_ingrédient_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."ingrédient_id_ingrédient_seq" OWNER TO postgres;

--
-- TOC entry 3375 (class 0 OID 0)
-- Dependencies: 220
-- Name: ingrédient_id_ingrédient_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."ingrédient_id_ingrédient_seq" OWNED BY public.ingredient.id_ingredient;


--
-- TOC entry 217 (class 1259 OID 33092)
-- Name: recette; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.recette (
    id_recette integer NOT NULL,
    name character varying(50) NOT NULL,
    temps_preparation integer,
    nombre_couverts integer,
    type_recette character varying(50),
    vege boolean,
    id_createur integer,
    preparation character varying,
    moyenne double precision,
    id_restaurant integer NOT NULL
);


ALTER TABLE public.recette OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 33091)
-- Name: recette_id_recette_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.recette_id_recette_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.recette_id_recette_seq OWNER TO postgres;

--
-- TOC entry 3376 (class 0 OID 0)
-- Dependencies: 216
-- Name: recette_id_recette_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.recette_id_recette_seq OWNED BY public.recette.id_recette;


--
-- TOC entry 215 (class 1259 OID 33085)
-- Name: restaurant; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.restaurant (
    id_restaurant integer NOT NULL,
    name character varying(50),
    adresse character varying(50),
    ville character varying(50),
    telephone character varying(50),
    e_mail character varying(50)
);


ALTER TABLE public.restaurant OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 33084)
-- Name: restaurant_id_restaurant_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.restaurant_id_restaurant_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.restaurant_id_restaurant_seq OWNER TO postgres;

--
-- TOC entry 3377 (class 0 OID 0)
-- Dependencies: 214
-- Name: restaurant_id_restaurant_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.restaurant_id_restaurant_seq OWNED BY public.restaurant.id_restaurant;


--
-- TOC entry 219 (class 1259 OID 33104)
-- Name: utilisateur; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.utilisateur (
    id_user integer NOT NULL,
    last_name character varying(50),
    first_name character varying(50),
    resto_name character varying(50),
    id_restaurant integer NOT NULL,
    password character varying(50),
    identifiant character varying
);


ALTER TABLE public.utilisateur OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 33103)
-- Name: utilisateur_id_user_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.utilisateur_id_user_seq
    AS integer
    START WITH 8
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.utilisateur_id_user_seq OWNER TO postgres;

--
-- TOC entry 3378 (class 0 OID 0)
-- Dependencies: 218
-- Name: utilisateur_id_user_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.utilisateur_id_user_seq OWNED BY public.utilisateur.id_user;


--
-- TOC entry 3198 (class 2604 OID 33203)
-- Name: commentaire id_commentaire; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commentaire ALTER COLUMN id_commentaire SET DEFAULT nextval('public.commentaire_id_commentaire_seq'::regclass);


--
-- TOC entry 3197 (class 2604 OID 33121)
-- Name: ingredient id_ingredient; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredient ALTER COLUMN id_ingredient SET DEFAULT nextval('public."ingrédient_id_ingrédient_seq"'::regclass);


--
-- TOC entry 3195 (class 2604 OID 33095)
-- Name: recette id_recette; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recette ALTER COLUMN id_recette SET DEFAULT nextval('public.recette_id_recette_seq'::regclass);


--
-- TOC entry 3194 (class 2604 OID 33088)
-- Name: restaurant id_restaurant; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.restaurant ALTER COLUMN id_restaurant SET DEFAULT nextval('public.restaurant_id_restaurant_seq'::regclass);


--
-- TOC entry 3196 (class 2604 OID 33107)
-- Name: utilisateur id_user; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utilisateur ALTER COLUMN id_user SET DEFAULT nextval('public.utilisateur_id_user_seq'::regclass);


--
-- TOC entry 3367 (class 0 OID 33200)
-- Dependencies: 223
-- Data for Name: commentaire; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.commentaire VALUES (44, 'super', 5, '2023-05-02', 20, 202);
INSERT INTO public.commentaire VALUES (45, 'berk', 2, '2023-05-02', 20, 202);


--
-- TOC entry 3365 (class 0 OID 33118)
-- Dependencies: 221
-- Data for Name: ingredient; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3361 (class 0 OID 33092)
-- Dependencies: 217
-- Data for Name: recette; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.recette VALUES (202, 'oeufs dur', 20, 6, 'entrée', true, 1, NULL, 3.5, 1);
INSERT INTO public.recette VALUES (249, 'fondant', 56, 5, 'dessert', false, 1, 'faire de la farine
ajouter les oeuf
deguster', 3, 1);


--
-- TOC entry 3359 (class 0 OID 33085)
-- Dependencies: 215
-- Data for Name: restaurant; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.restaurant VALUES (1, 'Kabane', '1 rue de la chasse', 'Paris', '0611223344', 'kabane@paris.fr');
INSERT INTO public.restaurant VALUES (11, 'cuicui', '1 boulevard du four', 'micro', '0655449988', 'cui@four.fr');


--
-- TOC entry 3363 (class 0 OID 33104)
-- Dependencies: 219
-- Data for Name: utilisateur; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.utilisateur VALUES (1, 'Dupont', 'Jean', 'Kabane', 1, 'salut', 'jeandupont@live.fr');
INSERT INTO public.utilisateur VALUES (20, 'cestmoi', '', NULL, 11, 'oui', 'salut');


--
-- TOC entry 3379 (class 0 OID 0)
-- Dependencies: 222
-- Name: commentaire_id_commentaire_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.commentaire_id_commentaire_seq', 45, true);


--
-- TOC entry 3380 (class 0 OID 0)
-- Dependencies: 220
-- Name: ingrédient_id_ingrédient_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."ingrédient_id_ingrédient_seq"', 192, true);


--
-- TOC entry 3381 (class 0 OID 0)
-- Dependencies: 216
-- Name: recette_id_recette_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.recette_id_recette_seq', 309, true);


--
-- TOC entry 3382 (class 0 OID 0)
-- Dependencies: 214
-- Name: restaurant_id_restaurant_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.restaurant_id_restaurant_seq', 11, true);


--
-- TOC entry 3383 (class 0 OID 0)
-- Dependencies: 218
-- Name: utilisateur_id_user_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.utilisateur_id_user_seq', 20, true);


--
-- TOC entry 3208 (class 2606 OID 33205)
-- Name: commentaire commentaire_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commentaire
    ADD CONSTRAINT commentaire_pkey PRIMARY KEY (id_commentaire);


--
-- TOC entry 3206 (class 2606 OID 33123)
-- Name: ingredient ingrédient_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredient
    ADD CONSTRAINT "ingrédient_pkey" PRIMARY KEY (id_ingredient);


--
-- TOC entry 3202 (class 2606 OID 33097)
-- Name: recette recette_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recette
    ADD CONSTRAINT recette_pkey PRIMARY KEY (id_recette);


--
-- TOC entry 3200 (class 2606 OID 33090)
-- Name: restaurant restaurant_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.restaurant
    ADD CONSTRAINT restaurant_pkey PRIMARY KEY (id_restaurant);


--
-- TOC entry 3204 (class 2606 OID 33109)
-- Name: utilisateur utilisateur_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utilisateur
    ADD CONSTRAINT utilisateur_pkey PRIMARY KEY (id_user);


--
-- TOC entry 3214 (class 2620 OID 41021)
-- Name: recette moyennedynamique; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER moyennedynamique AFTER INSERT ON public.recette FOR EACH ROW EXECUTE FUNCTION public.update_moyenne();


--
-- TOC entry 3215 (class 2620 OID 41018)
-- Name: commentaire update_recette_moyenne; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_recette_moyenne AFTER INSERT ON public.commentaire FOR EACH ROW EXECUTE FUNCTION public.update_moyenne();


--
-- TOC entry 3212 (class 2606 OID 33211)
-- Name: commentaire commentaire_id_recette_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commentaire
    ADD CONSTRAINT commentaire_id_recette_fkey FOREIGN KEY (id_recette) REFERENCES public.recette(id_recette);


--
-- TOC entry 3213 (class 2606 OID 33206)
-- Name: commentaire commentaire_id_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commentaire
    ADD CONSTRAINT commentaire_id_user_fkey FOREIGN KEY (id_user) REFERENCES public.utilisateur(id_user);


--
-- TOC entry 3209 (class 2606 OID 41022)
-- Name: recette id_restaurant; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recette
    ADD CONSTRAINT id_restaurant FOREIGN KEY (id_restaurant) REFERENCES public.restaurant(id_restaurant);


--
-- TOC entry 3211 (class 2606 OID 33124)
-- Name: ingredient ingrédient_id_recette_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredient
    ADD CONSTRAINT "ingrédient_id_recette_fkey" FOREIGN KEY (id_recette) REFERENCES public.recette(id_recette);


--
-- TOC entry 3210 (class 2606 OID 33110)
-- Name: utilisateur utilisateur_id_restaurant_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utilisateur
    ADD CONSTRAINT utilisateur_id_restaurant_fkey FOREIGN KEY (id_restaurant) REFERENCES public.restaurant(id_restaurant);


-- Completed on 2023-05-02 16:13:22

--
-- PostgreSQL database dump complete
--


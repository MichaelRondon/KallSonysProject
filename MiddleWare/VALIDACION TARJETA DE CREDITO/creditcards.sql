CREATE TABLE cards (
    tipo text,
    number text,
    idcard integer NOT NULL
);


ALTER TABLE cards OWNER TO super;

--
-- TOC entry 171 (class 1259 OID 16386)
-- Name: cards_idcard_seq; Type: SEQUENCE; Schema: public; Owner: super
--

CREATE SEQUENCE cards_idcard_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cards_idcard_seq OWNER TO super;

--
-- TOC entry 1982 (class 0 OID 0)
-- Dependencies: 171
-- Name: cards_idcard_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: super
--

ALTER SEQUENCE cards_idcard_seq OWNED BY cards.idcard;


--
-- TOC entry 1862 (class 2604 OID 16391)
-- Name: idcard; Type: DEFAULT; Schema: public; Owner: super
--

ALTER TABLE ONLY cards ALTER COLUMN idcard SET DEFAULT nextval('cards_idcard_seq'::regclass);


--
-- TOC entry 1973 (class 0 OID 16388)
-- Dependencies: 172
-- Data for Name: cards; Type: TABLE DATA; Schema: public; Owner: super
--

INSERT INTO cards (tipo, number, idcard) VALUES ('VISA', '123456', 1);
INSERT INTO cards (tipo, number, idcard) VALUES ('VISA', '654321', 2);
INSERT INTO cards (tipo, number, idcard) VALUES ('VISA', '456789', 3);
INSERT INTO cards (tipo, number, idcard) VALUES ('VISA', '987654', 4);
INSERT INTO cards (tipo, number, idcard) VALUES ('VISA', '753951', 5);
INSERT INTO cards (tipo, number, idcard) VALUES ('VISA', '486426', 6);
INSERT INTO cards (tipo, number, idcard) VALUES ('VISA', '739155', 7);
INSERT INTO cards (tipo, number, idcard) VALUES ('MASTERCARD', '123456789', 8);
INSERT INTO cards (tipo, number, idcard) VALUES ('MASTERCARD', '987654321', 9);
INSERT INTO cards (tipo, number, idcard) VALUES ('MASTERCARD', '951753852', 10);
INSERT INTO cards (tipo, number, idcard) VALUES ('MASTERCARD', '654258195', 11);
INSERT INTO cards (tipo, number, idcard) VALUES ('MASTERCARD', '687354129', 12);
INSERT INTO cards (tipo, number, idcard) VALUES ('MASTERCARD', '510235487', 13);
INSERT INTO cards (tipo, number, idcard) VALUES ('MASTERCARD', '753687420', 14);


--
-- TOC entry 1983 (class 0 OID 0)
-- Dependencies: 171
-- Name: cards_idcard_seq; Type: SEQUENCE SET; Schema: public; Owner: super
--

SELECT pg_catalog.setval('cards_idcard_seq', 14, true);


--
-- TOC entry 1864 (class 2606 OID 16396)
-- Name: idcard_cards_pk; Type: CONSTRAINT; Schema: public; Owner: super
--

ALTER TABLE ONLY cards
    ADD CONSTRAINT idcard_cards_pk PRIMARY KEY (idcard);


--
-- TOC entry 1980 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2017-11-16 22:15:40

--
-- PostgreSQL database dump complete
--


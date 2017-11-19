PGDMP                     
    u            vendors    9.3.5    9.5.5     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            �           1262    16397    vendors    DATABASE     y   CREATE DATABASE vendors WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8';
    DROP DATABASE vendors;
             super    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
             postgres    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                  postgres    false    6            �           0    0    public    ACL     �   REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;
                  postgres    false    6                        3079    11789    plpgsql 	   EXTENSION     ?   CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
    DROP EXTENSION plpgsql;
                  false            �           0    0    EXTENSION plpgsql    COMMENT     @   COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';
                       false    1            �            1259    16400    configurations    TABLE     t   CREATE TABLE configurations (
    id_vendor integer NOT NULL,
    name_vendor text,
    percent double precision
);
 "   DROP TABLE public.configurations;
       public         super    false    6            �            1259    16398    configurations_idVendor_seq    SEQUENCE        CREATE SEQUENCE "configurations_idVendor_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 4   DROP SEQUENCE public."configurations_idVendor_seq";
       public       super    false    172    6            �           0    0    configurations_idVendor_seq    SEQUENCE OWNED BY     P   ALTER SEQUENCE "configurations_idVendor_seq" OWNED BY configurations.id_vendor;
            public       super    false    171            F           2604    16403 	   id_vendor    DEFAULT     w   ALTER TABLE ONLY configurations ALTER COLUMN id_vendor SET DEFAULT nextval('"configurations_idVendor_seq"'::regclass);
 G   ALTER TABLE public.configurations ALTER COLUMN id_vendor DROP DEFAULT;
       public       super    false    171    172    172            �          0    16400    configurations 
   TABLE DATA                     public       super    false    172   �       �           0    0    configurations_idVendor_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('"configurations_idVendor_seq"', 1, false);
            public       super    false    171            H           2606    16408    id_config_pk 
   CONSTRAINT     Y   ALTER TABLE ONLY configurations
    ADD CONSTRAINT id_config_pk PRIMARY KEY (id_vendor);
 E   ALTER TABLE ONLY public.configurations DROP CONSTRAINT id_config_pk;
       public         super    false    172    172            �   o   x���v
Q���WH��K�L/-J,���+V��L�/K�K�/�Q�K�M�s
R��S�J4�}B]�4uԃ��"�u4��<�a��� � O�Ya�ή �M��sq ��8     
-- ***********************************************************
-- *************       Tabla de descartados        ***********
-- ***********************************************************
DROP TABLE descartes;
CREATE TABLE descartes (
    id NUMBER PRIMARY KEY,
    timestampc TIMESTAMP,
    content VARCHAR2(255)
);

CREATE SEQUENCE descartes_seq
START WITH 1
INCREMENT BY 1;

CREATE OR REPLACE TRIGGER descartes_trigger
BEFORE INSERT ON descartes
FOR EACH ROW
BEGIN
    SELECT descartes_seq.nextval INTO :new.id FROM dual;
END;
/


-- ***********************************************************
-- *************       Tabla de canal        ***********
-- ***********************************************************
CREATE SEQUENCE canal_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE canal (
    id NUMBER PRIMARY KEY,
    nombre VARCHAR2(100),
    canal VARCHAR2(100),
    apellido VARCHAR2(100),
    timestampc TIMESTAMP,
    importe NUMBER
);

CREATE TRIGGER canal_trigger
BEFORE INSERT ON canal
FOR EACH ROW
BEGIN
    SELECT canal_seq.NEXTVAL INTO :NEW.id FROM DUAL;
END;


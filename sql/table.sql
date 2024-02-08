-- Tabla de descartados.

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

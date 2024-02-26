-- ***********************************************************
-- *************       Tabla de descartados        ***********
-- ***********************************************************
DROP TABLE descartes;
CREATE TABLE descartes (
    id NUMBER PRIMARY KEY,
    timestampc TIMESTAMP,
    content VARCHAR2(4000)
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
-- *************       Tabla de transacciones        ***********
-- ***********************************************************
DROP TABLE transacciones;
CREATE TABLE transacciones (
    txid VARCHAR2(100) PRIMARY KEY,
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    channel VARCHAR2(100),
    status VARCHAR2(50),
    type VARCHAR2(50),
    compensation_id NUMBER,
    data VARCHAR2(4000)
);


```sql
DROP TABLE transacciones;
CREATE TABLE transacciones (
    txid VARCHAR2(255) PRIMARY KEY,
    createdAt VARCHAR2(255),
    channel VARCHAR2(255),
    type VARCHAR2(255),
    data VARCHAR2(255),
    path VARCHAR2(255),
    method VARCHAR2(255),
    requestParams VARCHAR2(255),
    state VARCHAR2(255),
    tier NUMBER
);
COMMIT;
```
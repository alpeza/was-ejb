
# Servicios

## User Service

```sql

CREATE SEQUENCE user_info_seq
START WITH 1
INCREMENT BY 1;

DROP TABLE user_info;

CREATE TABLE user_info (
    id NUMBER DEFAULT user_info_seq.NEXTVAL PRIMARY KEY,
    userid VARCHAR2(100),
    username VARCHAR2(100),
    name VARCHAR2(100),
    currency VARCHAR2(3),
    amount NUMBER
);

INSERT INTO user_info (userid, username, name, currency, amount) VALUES ('user1', 'JohnDoe', 'John Doe', 'USD', 1000.00);
INSERT INTO user_info (userid, username, name, currency, amount) VALUES ('user2', 'JaneSmith', 'Jane Smith', 'EUR', 750.50);
INSERT INTO user_info (userid, username, name, currency, amount) VALUES ('user3', 'AliceJohnson', 'Alice Johnson', 'GBP', 300.25);
INSERT INTO user_info (userid, username, name, currency, amount) VALUES ('user4', 'BobWhite', 'Bob White', 'USD', 1500.75);
INSERT INTO user_info (userid, username, name, currency, amount) VALUES ('user5', 'EmilyBrown', 'Emily Brown', 'EUR', 2000.00);
INSERT INTO user_info (userid, username, name, currency, amount) VALUES ('user6', 'MichaelDavis', 'Michael Davis', 'GBP', 500.50);
INSERT INTO user_info (userid, username, name, currency, amount) VALUES ('user7', 'SarahWilson', 'Sarah Wilson', 'USD', 800.25);
INSERT INTO user_info (userid, username, name, currency, amount) VALUES ('user8', 'DavidTaylor', 'David Taylor', 'EUR', 1200.30);
INSERT INTO user_info (userid, username, name, currency, amount) VALUES ('user9', 'LauraMartinez', 'Laura Martinez', 'GBP', 950.75);
INSERT INTO user_info (userid, username, name, currency, amount) VALUES ('user10', 'ChrisGarcia', 'Chris Garcia', 'USD', 3000.00);
commit;

```

## CryptoService

```sql
DROP TABLE crypto;

CREATE TABLE crypto (
    CRYPTO VARCHAR2(50),
    AMOUNT NUMBER,
    PRICE NUMBER
);

CREATE SEQUENCE crypto_seq
START WITH 1
INCREMENT BY 1;

ALTER TABLE crypto
ADD (CRYPTO_ID NUMBER DEFAULT crypto_seq.nextval PRIMARY KEY);

INSERT INTO crypto (CRYPTO, AMOUNT, PRICE) VALUES ('BTC', 10, 45000);
INSERT INTO crypto (CRYPTO, AMOUNT, PRICE) VALUES ('ETH', 20, 2000);
INSERT INTO crypto (CRYPTO, AMOUNT, PRICE) VALUES ('XRP', 1000, 1.5);
INSERT INTO crypto (CRYPTO, AMOUNT, PRICE) VALUES ('LTC', 15, 150);
INSERT INTO crypto (CRYPTO, AMOUNT, PRICE) VALUES ('BCH', 5, 600);
INSERT INTO crypto (CRYPTO, AMOUNT, PRICE) VALUES ('ADA', 2000, 0.75);
INSERT INTO crypto (CRYPTO, AMOUNT, PRICE) VALUES ('DOT', 50, 30);
INSERT INTO crypto (CRYPTO, AMOUNT, PRICE) VALUES ('LINK', 100, 25);
INSERT INTO crypto (CRYPTO, AMOUNT, PRICE) VALUES ('XLM', 5000, 0.4);
INSERT INTO crypto (CRYPTO, AMOUNT, PRICE) VALUES ('USDT', 10000, 1);
INSERT INTO crypto (CRYPTO, AMOUNT, PRICE) VALUES ('XMR', 8, 200);
INSERT INTO crypto (CRYPTO, AMOUNT, PRICE) VALUES ('DOGE', 50000, 0.05);
INSERT INTO crypto (CRYPTO, AMOUNT, PRICE) VALUES ('ATOM', 30, 20);
INSERT INTO crypto (CRYPTO, AMOUNT, PRICE) VALUES ('XTZ', 100, 3);
INSERT INTO crypto (CRYPTO, AMOUNT, PRICE) VALUES ('UNI', 10, 25);
INSERT INTO crypto (CRYPTO, AMOUNT, PRICE) VALUES ('WBTC', 2, 45000);
INSERT INTO crypto (CRYPTO, AMOUNT, PRICE) VALUES ('AAVE', 5, 400);
INSERT INTO crypto (CRYPTO, AMOUNT, PRICE) VALUES ('SUSHI', 15, 20);
INSERT INTO crypto (CRYPTO, AMOUNT, PRICE) VALUES ('SNX', 25, 15);
INSERT INTO crypto (CRYPTO, AMOUNT, PRICE) VALUES ('MKR', 1, 2000);
commit;
```


```bash
curl -X POST -H "Content-Type: application/json" -d '{"crypto":"BTC","amount":10,"price":45000}' http://localhost:8080/crypto
```

```bash
curl http://localhost:8080/crypto | jq 
```

```bash
curl http://localhost:8080/crypto/1 | jq
```

```bash
curl -X PUT -H "Content-Type: application/json" -d '{"crypto":"BTC","amount":20,"price":46000}' http://localhost:8080/crypto/1
```

```bash
curl -X DELETE http://localhost:8080/crypto/1 | jq
```

### Dar de alta un usuario
```bash
curl -X POST -H "Content-Type: application/json" -d '{
    "username": "user1",
    "name": "John Doe",
    "currency": "USD",
    "amount": 1000.00
}' http://localhost:8080/users/
```

### Listar todos los usuarios

```bash
curl http://localhost:8080/users/ | jq
```

### Buscar un usuario por id
```bash
curl http://localhost:8080/users/{id} | jq
```

### Updatear el valor de un usuario

```bash
curl -X PUT -H "Content-Type: application/json" -d '{
    "username": "user1",
    "name": "John Doe",
    "currency": "USD",
    "amount": 1500.00
}' http://localhost:8080/users/{id} | jq
```

### Borrar un usuario

```bash
curl -X DELETE http://localhost:8080/users/{id} | jq
```

## Exchange

```bash
curl -X POST \
  http://localhost:8080/order/buy \
  -H 'Content-Type: application/json' \
  -d '{
  "fromid": "exchange",
  "toid": "user1",
  "amount": 100.0,
  "currency": "BTC"
}'
```

# Otros
```xml
		<dependency>
			<groupId>com.ibm.ws</groupId>
			<artifactId>com.ibm.ws.admin.client</artifactId>
			<version>6.1.0</version>
			<scope>system</scope>
			<systemPath>${project.basedir}/extlib/com.ibm.ws.admin.client_6.1.0.jar</systemPath>
		</dependency>
```

```agsl
		<dependency>
			<groupId>com.ibm.ws</groupId>
			<artifactId>com.ibm.ws.admin.client</artifactId>
			<version>6.1.0</version>
		</dependency>
```

```bash
set -x
cd extlib
set jarfile "$(pwd)/com.ibm.ws.admin.client_6.1.0.jar"
set jarfile "$(pwd)/com.ibm.ws.admin.client_9.0.jar"
mvn install:install-file -Dfile="$jarfile" -DgroupId="com.ibm.ws" -DartifactId="com.ibm.ws.admin.client" -Dversion="9.0.0" -Dpackaging=jar
```


Como puedo copiar el siguiente jar de un contenedor docker llamado `was`

```bash
./opt/IBM/WebSphere/AppServer/runtimes/com.ibm.ws.admin.client_9.0.jar
```
a la carpeta `/home/ubuntu23/tmpprojects/was-ejb/monitor/extlib`


docker cp was:/opt/IBM/WebSphere/AppServer/runtimes/com.ibm.ws.admin.client_9.0.jar /home/ubuntu23/tmpprojects/was-ejb/monitor/extlib

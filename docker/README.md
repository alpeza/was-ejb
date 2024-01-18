

[https:///localhost:9043/ibm/console](https:///localhost:9043/ibm/console)

* Usuario: wsadmin
* Password: `docker exec decwas cat /tmp/PASSWORD`


`docker exec decwas tail -f /opt/IBM/WebSphere/AppServer/profiles/AppSrv01/logs/ffdc/*.txt`

docker exec decwas sh -c 'tail -f /opt/IBM/WebSphere/AppServer/profiles/AppSrv01/logs/ffdc/*.txt'

# Referencias 

* **MDB:** https://github.com/ibm-cloud-architecture/refarch-mq-messaging/blob/master/docs/mdb-twas/README.md
* **MDB:** https://www.youtube.com/watch?v=YnWU9IFz-dc

* https://unic8der.medium.com/spring-boot-on-websphere-c8114da409af
* https://www.youtube.com/watch?v=ZP8Um12Z_mk&list=PLhz9baCHY7qFbO7hHskDCkPQF5buqtuop
* https://blog.tericcabrel.com/web-application-springboot-thymeleaf-tailwindcss/


DecWas

```
https://10.241.122.200:9043/ibm/console/logon.jsp
USER_WAS=decadmin
PSW_WAS=decdec
```


## Deploy


Siguiendo con el manual pero adaptado:  https://github.com/ibm-cloud-architecture/refarch-mq-messaging/blob/master/docs/mdb-twas/README.md

### Creación de la factoria

- 1.- Vamos a `Resources -> JMS -> Queue connection factories`
- 2.- Seteamos en SCOPE: `cells:DefaultCell01:nodes:DefaultNode01:servers:server1` 
- 3.- Le damos a NEW y marcamos: `WebSphere MQ messaging provider`
- **Step  1:  Configure basic attributes**
   - Name: `TestConnectionFactory` 
   - JNDIName: `jms/TestConnectionFactory` 

- **Step  2:  Select connection method**
  - `Enter all the required information into this wizard`
  - Step  2.1:  Supply queue manager details: `QM1`
  - Step  2.2:  Enter connection details: 
    - Transport: `BINDINGS_THEN_CLIENT`
    - Hostname: `mqserver`
    - Port: `1414`
    - Server Connection channel: `DEV.ADMIN.SVRCONN`

Nos dará el siguiente summary

```agsl
Creating a resource of type Queue connection factory- Name "TestConnectionFactory"
- JNDI name "jms/TestConnectionFactory"
- Queue manager or queue sharing group name "QM1"
- Server type "QMGR"
- Hostname "mqserver"
- Port "1414"
- Channel name "DEV.ADMIN.SVRCONN"
```

### Creación de la cola de peticiones

- 1.- Vamos a `Resources -> JMS -> Queues`
- 2.- Seteamos en SCOPE: `cells:DefaultCell01:nodes:DefaultNode01:servers:server1` 
- 3.- Le damos a NEW y marcamos: `WebSphere MQ messaging provider`
- Rellenamos:
  - Name: `TestRequestQueue`
  - JNDI name: `jms/TestRequestQueue`
  - Queue name: `DEV.QUEUE.1`

### Creación de la cola de respuestas

- 1.- Vamos a `Resources -> JMS -> Queues`
- 2.- Seteamos en SCOPE: `cells:DefaultCell01:nodes:DefaultNode01:servers:server1`
- 3.- Le damos a NEW y marcamos: `WebSphere MQ messaging provider`
- Rellenamos:
  - Name: `TestResponseQueue`
  - JNDI name: `jms/TestResponseQueue`
  - Queue name: `DEV.QUEUE.2`

### Creación de especificaciones de activación

- 1.- Vamos a `Resources -> JMS -> Activation specifications`
- 2.- Seteamos en SCOPE: `cells:DefaultCell01:nodes:DefaultNode01:servers:server1`
- 3.- Le damos a NEW y marcamos: `WebSphere MQ messaging provider`
- **Step  1:  Configure basic attributes**
  - Name: `TestActivationSpecification`
  - JNDIName: `jms/TestActivationSpecification`
- **Step  1.1:  Specify MDB destination data**
  - Destination JNDI name: `jms/TestRequestQueue`
  - Message selector: ` `

- **Step  2:  Select connection method**
  - `Enter all the required information into this wizard`
  - Step  2.1:  Supply queue manager details: `QM1`
  - Step  2.2:  Enter connection details:
    - Transport: `BINDINGS_THEN_CLIENT`
    - Hostname: `mqserver`
    - Port: `1414`
    - Server Connection channel: `DEV.ADMIN.SVRCONN`

Nos dará el siguiente summary

```
Creating a resource of type Activation specification
- Name "TestActivationSpecification"
- JNDI name "jms/TestActivationSpecification"
- Destination JNDI name "jms/TestRequestQueue"
- Destination type "javax.jms.Queue"
- Queue manager or queue sharing group name "QM1"
- Server type "QMGR"
- Hostname "mqserver"
- Port "1414"
- Channel name "DEV.ADMIN.SVRCONN"
```

### Deploy del EAR

- 1.- Vamos a `Applications->New Application` `-> New Enterprise Application` 
- 2.- Seleccionamos el EAR `dummyRecorder-ear-1.0.0-SNAPSHOT.ear`
- 3.- Seleccionamos `Detailed - Show all installation options and parameters.`



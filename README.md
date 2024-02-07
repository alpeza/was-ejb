# Was-Ejb
Repositorio de ejemplo de una aplicación WAS

# Deploy

* 1.- Desplegamos el docker
* 2.- Configuramos el ejb

## Servidor WAS

[https:///localhost:9043/ibm/console](https:///localhost:9043/ibm/console)

* Usuario: wsadmin
* Password: `docker exec was cat /tmp/PASSWORD`


> * Logs: `docker exec was tail -f /opt/IBM/WebSphere/AppServer/profiles/AppSrv01/logs/ffdc/*.txt`
> * Acceso a perfil: `/opt/IBM/WebSphere/AppServer/profiles/AppSrv01` 

## Servidor MQ

Accedemos

```bash
docker exec -it mqserver bash
```

```bash
runmqsc QM1
ALTER QMGR CHLAUTH(DISABLED)
EXIT
```

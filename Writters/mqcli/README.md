# Herramienta para inyectar mensajes

```bash
export MQ_PROPERTIES="/home/ubuntu23/tmpprojects/was-ejb/Writters/mqcli/cliproperties/mq.properties"
./mqcli.sh putjms --config "$MQ_PROPERTIES" --queue "DEV.QUEUE.1" --message "hola" 
```


```bash
export MQ_PROPERTIES="/home/ubuntu23/tmpprojects/was-ejb/Writters/mqcli/cliproperties/mq.properties"
./mqcli.sh putrandomimportes --config "$MQ_PROPERTIES" --queue "DEV.QUEUE.3" --channel "internet" --number 10
```


## Inyección de mensajes por canal

### Inyección en canal INTERNET

```bash
export MQ_PROPERTIES="/home/ubuntu23/tmpprojects/was-ejb/Writters/mqcli/cliproperties/mq.properties"
./mqcli.sh putrandomimportes --config "$MQ_PROPERTIES" --queue "CANAL.INTERNET" --channel "internet" --number 10
```

### Inyección en canal TIENDA


```bash
export MQ_PROPERTIES="/home/ubuntu23/tmpprojects/was-ejb/Writters/mqcli/cliproperties/mq.properties"
./mqcli.sh putrandomimportes --config "$MQ_PROPERTIES" --queue "CANAL.TIENDA" --channel "tienda" --number 10
```


### Inyección en canal TELEFONICO

```bash
export MQ_PROPERTIES="/home/ubuntu23/tmpprojects/was-ejb/Writters/mqcli/cliproperties/mq.properties"
./mqcli.sh putrandomimportes --config "$MQ_PROPERTIES" --queue "CANAL.TELEFONICO" --channel "telefonico" --number 10
```
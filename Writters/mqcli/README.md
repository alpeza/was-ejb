

```bash
export MQ_PROPERTIES="/home/ubuntu23/tmpprojects/was-ejb/Writters/mqcli/cliproperties/mq.properties"
./mqcli.sh putjms --config "$MQ_PROPERTIES" --queue "DEV.QUEUE.1" --message "hola"
```


```bash
./mqcli.sh putrandomimportes --config "$MQ_PROPERTIES" --queue "DEV.QUEUE.1" --number 10
```
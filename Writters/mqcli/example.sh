export MQ_PROPERTIES="/home/ubuntu23/tmpprojects/was-ejb/Writters/mqcli/cliproperties/mq.properties"
messages=50

./mqcli.sh putjms --config "$MQ_PROPERTIES" --queue "CANAL.INTERNET" --message "Mensaje aleatorio $(date)" &
./mqcli.sh putrandomimportes --config "$MQ_PROPERTIES" --queue "CANAL.INTERNET" --channel "internet" --number 10 &
./mqcli.sh putrandomimportes --config "$MQ_PROPERTIES" --queue "CANAL.TIENDA" --channel "tienda" --number 20 &
./mqcli.sh putrandomimportes --config "$MQ_PROPERTIES" --queue "CANAL.TELEFONICO" --channel "telefonico" --number 5

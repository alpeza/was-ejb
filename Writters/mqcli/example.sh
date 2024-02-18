export MQ_PROPERTIES="/home/ubuntu23/tmpprojects/was-ejb/Writters/mqcli/cliproperties/mq.properties"
messages=50

./mqcli.sh putjms --config "$MQ_PROPERTIES" --queue "CANAL.INTERNET" --message "Mensaje aleatorio $(date)" &
./mqcli.sh putrandomimportes --config "$MQ_PROPERTIES" --queue "CANAL.INTERNET" --channel "internet" --number $messages &
./mqcli.sh putrandomimportes --config "$MQ_PROPERTIES" --queue "CANAL.TIENDA" --channel "tienda" --number $messages &
./mqcli.sh putrandomimportes --config "$MQ_PROPERTIES" --queue "CANAL.TELEFONICO" --channel "telefonico" --number $messages

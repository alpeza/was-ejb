echo "**************************************"
echo "Construyendo artefacto"
echo "**************************************"
cd /home/ubuntu23/tmpprojects/sample.SpringBootMVC-master
set -x
mvn clean package -DskipTests
cp /home/ubuntu23/tmpprojects/sample.SpringBootMVC-master/target/*.war /home/ubuntu23/tmpprojects/sample.SpringBootMVC-master/docker/artifacts

set +x
echo "**************************************"
echo "Desplegando artefacto en el WAS"
echo "**************************************"
set -x
docker exec decwas /bin/bash -c "/home/was/scripts/artifacts/deployDecPortalWar.sh"
set +x
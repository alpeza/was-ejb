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

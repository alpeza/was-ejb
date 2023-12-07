### Generar arquetipos

```bash
./createProject.sh "dummyRecorder"
```

* En el <proyecto>:

```xml
 <!-- Se han de añadir aqui los modulos -->
  <modules>
    <module>dummyRecorder-ear</module>
    <module>dummyRecorder-ejb</module>
  </modules>
```

# Vincular proyectos

* En el proyecto `ear` lo vinculamos al `ejb` 

  * Añadimos en el ear la dependencia con el ejb:

```xml
...  
<dependencies>
    <dependency>
      <groupId>com.dummyRecorder.ejb</groupId>
      <artifactId>dummyRecorder-ejb</artifactId>
      <version>1.0.0-SNAPSHOT</version>
      <type>ejb</type>
    </dependency>
</dependencies>
...
```

* Indicamos en el plugin

```xml 
...      
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-ear-plugin</artifactId>
        <version>2.6</version>
        <configuration>
            <version>6</version>
            <defaultLibBundleDir>lib</defaultLibBundleDir>
            <!-- TODO Añadimos el siguiente fragmento -->
            <module>
              <ejbModule>
                <groupId>com.dummyRecorder</groupId>
                <artifactId>dummyRecorder-ejb</artifactId>
              </ejbModule>
            </module>
          <!-- TODO Fin -->
        </configuration>
      </plugin>
...
```

# Añadimos un MDB en el EJB

```java
import javax.annotation.PostConstruct;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "MyQueue")
})
public class EjemploMDB implements MessageListener{

    @Override
    public void onMessage(Message msg) {
        TextMessage txtMsg = (TextMessage)msg;

        try {
            System.out.println("Mensaje recibido=" + txtMsg.getText());
        }catch(Exception e) {
            System.out.println(e);
        }
    }

    @PostConstruct
    public void inicializar() {
        System.out.println("Inicializando componente");
    }

}
```

# Referencias:

* https://www.youtube.com/watch?v=x29-xqQsF7Q
* https://www.youtube.com/watch?v=0z4HKiJgW-s&t=56s
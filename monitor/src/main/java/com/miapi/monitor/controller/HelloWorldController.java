package com.miapi.monitor.controller;
import com.ibm.websphere.management.AdminClient;
import com.miapi.monitor.util.WebSphereAdmClientFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.management.ObjectName;
import java.util.Set;

@RestController
public class HelloWorldController {

    private final WebSphereAdmClientFactory factory;

    public HelloWorldController(WebSphereAdmClientFactory factory) {
        this.factory = factory;
    }

    @GetMapping("/hello")
    public String helloWorld() throws Exception {
        AdminClient adminClient = factory.createAdminClient();
        String mBeanName = "WebSphere:type=Server,*";
        // Crear un ObjectName usando el nombre del MBean
        ObjectName objectName = new ObjectName(mBeanName);

        // Obtener un conjunto de nombres de MBean que coincidan con el ObjectName proporcionado
        Set<ObjectName> mBeanSet = adminClient.queryNames(objectName, null);

        // Verificar si se encontró el MBean
        if (mBeanSet.isEmpty()) {
            return "No se encontró el MBean con el nombre: " + mBeanName;
        }

        return "Hello, World!";
    }

}

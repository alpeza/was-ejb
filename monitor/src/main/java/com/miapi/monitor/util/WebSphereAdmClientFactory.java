package com.miapi.monitor.util;


import com.ibm.websphere.management.exception.ConnectorException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


import com.ibm.websphere.management.AdminClient;
import com.ibm.websphere.management.AdminClientFactory;
import org.springframework.stereotype.Component;

import java.util.Properties;


@Component
@ConfigurationProperties(prefix = "wasadmin")
@Data
@NoArgsConstructor
public class WebSphereAdmClientFactory {


    private String host;
    private String port;
    private String configURL;
    private String user;
    private String password;


    public AdminClient createAdminClient() throws Exception {

        System.out.println(this.toString());

        AdminClient adminClient = null;
        Properties connectProps = new Properties();

        connectProps.setProperty(AdminClient.CONNECTOR_TYPE, AdminClient.CONNECTOR_TYPE_SOAP);

        connectProps.setProperty(AdminClient.CONNECTOR_HOST, host);
        connectProps.setProperty(AdminClient.CONNECTOR_PORT, port);
        connectProps.setProperty(AdminClient.CONNECTOR_SECURITY_ENABLED, "false");
        connectProps.setProperty(AdminClient.USERNAME, user);
        connectProps.setProperty(AdminClient.PASSWORD, password);
        System.out.println(connectProps.toString());
        try {
            adminClient = AdminClientFactory.createAdminClient(connectProps);

            return adminClient;
        } catch (ConnectorException e) {
            e.printStackTrace();
            System.out.println("No se ha podido crear el adminclient: " + e);
        }
        return  null;
    }

}
# Importar las clases necesarias
from javax.management import ObjectName
from java.lang import String

# Obtener el MBeanServer
#cells:DefaultCell01
server = AdminControl.queryNames("type=NodeAgent,node=*,process=server1,*")

# Nombre del servidor y del administrador de recursos
serverName = "mqseries"
resourceMgrName = "QM1"

# Nombre de la fábrica de conexiones y la cola
connFactoryName = "TestConnectionFactory"
queueName = "YourQueueName"

# Crear un nuevo recurso de fábrica de conexiones
connFactory = AdminTask.createWMQConnectionFactory(server, "[-name " + connFactoryName +
                                                   " -jndiName jms/" + connFactoryName +
                                                   " -type QUEUE -qmgr QMGR_NAME -wmqTransportType BINDINGS_THEN_CLIENT " +
                                                   "-qmgrSvrconnChannel CHANNEL_NAME -qmgrSvrconnQueueManager QMGR_NAME " +
                                                   "-qmgrSvrconnHost HOST_NAME -qmgrSvrconnPort PORT_NUMBER " +
                                                   "-qmgrSvrconnTransportType BINDINGS_THEN_CLIENT -qmgrSvrconnTransportType BINDINGS_THEN_CLIENT " +
                                                   "-jndiProperties [-jndiProperty [-name java.naming.factory.initial -value com.ibm.websphere.naming.WsnInitialContextFactory] " +
                                                   "[-name java.naming.provider.url -value corbaloc:iiop:HOST_NAME:PORT_NUMBER/NameServiceServerRoot]]]")

# Asociar la fábrica de conexiones con el servidor
AdminTask.createWMQActivationSpecification(connFactory, "[-name " + connFactoryName +
                                           " -jndiName jms/" + connFactoryName +
                                           " -destinationJndiName jms/" + queueName +
                                           " -destinationType javax.jms.Queue " +
                                           "-qmgr QMGR_NAME -wmqTransportType BINDINGS_THEN_CLIENT " +
                                           "-qmgrSvrconnChannel CHANNEL_NAME -qmgrSvrconnQueueManager QMGR_NAME " +
                                           "-qmgrSvrconnHost HOST_NAME -qmgrSvrconnPort PORT_NUMBER " +
                                           "-qmgrSvrconnTransportType BINDINGS_THEN_CLIENT -qmgrSvrconnTransportType BINDINGS_THEN_CLIENT " +
                                           "-jndiProperties [-jndiProperty [-name java.naming.factory.initial -value com.ibm.websphere.naming.WsnInitialContextFactory] " +
                                           "[-name java.naming.provider.url -value corbaloc:iiop:HOST_NAME:PORT_NUMBER/NameServiceServerRoot]]]")

# Guardar la configuración
AdminConfig.save()

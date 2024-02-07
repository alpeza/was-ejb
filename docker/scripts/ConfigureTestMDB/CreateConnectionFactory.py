# Importar módulo AdminConfig
import sys

# Nombre de la fábrica de conexiones JMS
cfName = "TestConnectionFactory3"
# JNDI name
jndiName = "jms/TestConnectionFactory3"
# Queue manager o queue sharing group name
qmName = "QM1"
# Server type
serverType = "QMGR"
# Hostname
hostname = "mqserver"
# Port
port = "1414"
# Channel name
channelName = "DEV.ADMIN.SVRCONN"

# Crear la fábrica de conexiones JMS
def createJMSConnectionFactory():
    print("Creando la fábrica de conexiones JMS...")

    # Obtener la configuración del servidor
    #  https://www.ibm.com/docs/en/was-nd/8.5.5?topic=scripting-commands-admincontrol-object-using-wsadmin
    #objNameString = AdminControl.completeObjectName('WebSphere:type=Server,*')
    #print( "Nombre del nodo actual:" + AdminControl.getAttribute(objNameString, 'nodeName'))
    #print(str(AdminConfig.list("JMSProvider")))
    # Referencias: https://www.ibm.com/docs/en/was/8.5.5?topic=cmws-configuring-new-queue-connection-factories-by-using-scripting
    resourceId = AdminConfig.getid('/Cell:DefaultCell01/Node:DefaultNode01/JMSProvider:WebSphere MQ JMS Provider/')
    print("resourceId: " + resourceId)
    print AdminConfig.required('MQQueueConnectionFactory')
    # Assuming you've already retrieved the configuration object
    configObject = AdminConfig.required('MQQueueConnectionFactory')


    jmscfAttrs = [['name', cfName],
                  ['jndiName', jndiName],
                  ['description', 'Fabrica de conexiones MQ JMS']]

    ConnectionFactory = AdminConfig.create('MQQueueConnectionFactory', resourceId, jmscfAttrs)

    AdminConfig.save()

    print("Fábrica de conexiones JMS creada exitosamente.")

# Llamar a la función para crear la fábrica de conexiones JMS
createJMSConnectionFactory()
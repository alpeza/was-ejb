import sys

# Función para crear la Connection Factory de JMS MQ con nombre de usuario y contraseña


# Función para configurar el usuario con contraseña de tipo Mapping-configuration alias
def set_user_password():
    user_id = "admin"
    user_password = "passw0rd"
    alias_name = "mquser"

    print("Configurando el usuario con contraseña tipo Mapping-configuration alias...")

    # Crear un alias para el usuario
    print("Creando el alias para el usuario...")
    AdminTask.createAuthDataEntry(
        '[-alias ' + alias_name + ' -user ' + user_id + ' -password ' + user_password + ' -description "Alias de usuario para Mapping-configuration alias"]')

    # Obtener el alias creado
    try:
        print("Obteniendo el alias creado...")
        alias_id = AdminTask.getAuthDataEntry(alias_name)
    except:
        print("No se pudo obtener el alias creado.")

    # Asociar el alias al usuario (si el usuario existe)
    try:
        print("Asociando el alias al usuario...")
        AdminTask.mapUsersToAdminAuthData(['-authDataAlias', alias_id, '-user', user_id])
    except:
        print("El usuario no existe o no se puede asociar el alias al usuario.")


    # Guardar la configuración
    print "Guardando la configuración..."
    AdminConfig.save()


def crear_connection_factory():
    connection_factory_name = 'decloc'
    jndi_name = 'jms/decloc/deccf'
    qmgr_name = 'QM1'
    transport_type = 'BINDINGS'
    qmgr_hostname = 'mqserver'
    qmgr_port = '1414'
    channel_name = 'DEV.ADMIN.SVRCONN'
    mq_username = 'admin'
    mq_password = 'passw0rd'

    # Comando para crear la Connection Factory
    command = "-name {} -jndiName {} -qmgr {} -wmqTransportType {} -qmgrHostname {} -qmgrPortNumber {} -channel {} -securityAuth true".format(
        connection_factory_name, jndi_name, qmgr_name, transport_type, qmgr_hostname, qmgr_port, channel_name)

    # Ejecutar el comando
    print("Ejecutando comando: ", command)
    AdminTask.createWMQConnectionFactory(command)
    print("Connection Factory creada con éxito.")

set_user_password()
sys.exit(0)
# Llamar a la función para crear la Connection Factory
crear_connection_factory()

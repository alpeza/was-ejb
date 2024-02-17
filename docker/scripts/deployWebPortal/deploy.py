import sys


war_file='decportal-0.1.0.war'
app_name = 'decportal-0_1_0_war'

war_file_path = '/home/was/artifact/' + war_file
host_virtual = 'default_host'
web_path='/hello'
web_path='/'

def check_and_uninstall_app(app_name):
    try:
        # Verificar si la aplicación ya está instalada
        existing_apps = AdminApp.list().splitlines()
        if app_name in existing_apps:
            # Desinstalar la aplicación si ya existe
            print("La aplicacion {} ya esta instalada. Desinstalando...".format(app_name))
            AdminApp.uninstall(app_name)
            print("La aplicacion {} ha sido desinstalada correctamente.".format(app_name))
        else:
            print("La aplicacion {} no esta instalada.".format(app_name))

    except:
        print("...........................................")
        print("Ha ocurrido un error durante la verificación/desinstalación de la aplicación:")
        print(sys.exc_info()[0])
        print(sys.exc_info()[1])

try:
    # Si la aplicación ya existe la desinstalamos y lo volvemos a instalar
    check_and_uninstall_app(app_name)
    # Desplegar la aplicación WAR
    print("Desplegando la aplicación {}...".format(app_name))
    AdminApp.install(war_file_path, ['-appname', app_name, '-contextroot', web_path, '-MapWebModToVH', [['.*', '.*', host_virtual]]])

    # Guardar la configuración
    print("Guardando la configuración...")
    AdminConfig.save()

    # Sincronizar nodos activos
    #print("Sincronizando nodos activos...")
    #AdminNodeManagement.syncActiveNodes()

    # Iniciar la aplicación después de instalarla
    print("Iniciando la aplicación {}...".format(app_name))
    appManager = AdminControl.queryNames('type=ApplicationManager,process=server1,*')
    print(appManager)
    AdminControl.invoke(appManager, 'startApplication', app_name)
    print("¡Despliegue y arranque exitosos!")


except:
    print("...........................................")
    print("Ha ocurrido un error durante el despliegue:")
    print(sys.exc_info()[0])
    print(sys.exc_info()[1])

finally:
    # Salir del script
    AdminConfig.reset()

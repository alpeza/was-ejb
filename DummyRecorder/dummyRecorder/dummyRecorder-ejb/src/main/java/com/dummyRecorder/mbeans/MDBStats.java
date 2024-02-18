package com.dummyRecorder.mbeans;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * Clase para gestionar el MBean
 */
@Data
@Slf4j
public class MDBStats {

    private static String mbeanName="com.dummyRecorder.ejb.monitor";

    private static void registerMBeanIfNeeded() {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName mBeanName = null;
        try {
            mBeanName = new ObjectName(mbeanName + ":type=MessageCounter");
            if (!mBeanServer.isRegistered(mBeanName)) {
                AppMonitorMBean mbean = new AppMonitor();
                mBeanServer.registerMBean(mbean, mBeanName);
                log.info("MBean registrado correctamente.");
            } else {
                log.debug("El MBean ya está registrado.");
            }
        } catch (Exception ex) {
            log.error("Error al registrar el MBean: " + ex.getMessage());
        }
    }

    private static void increment(String mbean){
        try {
            registerMBeanIfNeeded();
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            ObjectName mBeanName = new ObjectName(mbeanName + ":type=MessageCounter");
            mBeanServer.invoke(mBeanName, mbean, null, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(String.format("Error al incrementar el contador de mensajes: %s ex: %s", mbean, ex.getMessage()));
        }
    }

    public static void incrementaMensajes(){
        increment("incrementMessageCount");
    }

    public static void incrementaMensajesOK(){
        increment("incrementMessageOK");
    }

    public static void incrementaMensajesKO(){
        increment("incrementMessageKO");
    }
}

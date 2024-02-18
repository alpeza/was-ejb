package com.dummyRecorder.mbeans;

/**
 * Implementación del MBean
 */
public class AppMonitor implements AppMonitorMBean {
    private int messageCount = 0;
    private int messageOK = 0;
    private int messageKO = 0;

    @Override
    public int getMessageCount() {
        return messageCount;
    }

    @Override
    public void incrementMessageCount() {
        messageCount++;
    }


    @Override
    public int getMessageKO() {
        return messageKO;
    }

    @Override
    public void incrementMessageKO() {
        messageKO++;
    }


    @Override
    public int getMessageOK() {
        return messageOK;
    }

    @Override
    public void incrementMessageOK() {
        messageOK++;
    }



}

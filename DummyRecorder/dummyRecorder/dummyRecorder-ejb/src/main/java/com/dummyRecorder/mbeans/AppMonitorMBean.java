package com.dummyRecorder.mbeans;

public interface AppMonitorMBean {
    int getMessageCount();
    int getMessageKO();
    int getMessageOK();

    void incrementMessageCount();
    void incrementMessageOK();
    void incrementMessageKO();

}

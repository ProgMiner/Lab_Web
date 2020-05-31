package ru.byprogminer.Lab4_Web.querycounter;

import javax.management.NotificationEmitter;

public interface QueryCounterBeanMBean extends NotificationEmitter {

    int getAllQueriesCount();
    int getFalseResultQueriesCount();
}

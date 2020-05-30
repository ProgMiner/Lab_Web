package ru.byprogminer.Lab4_Web.querycounter;

import javax.management.NotificationEmitter;

public interface QueryCounterMBean extends NotificationEmitter {

    int getAllQueriesCount();
    int getFalseResultQueriesCount();
}

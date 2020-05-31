package ru.byprogminer.Lab4_Web.querycounter;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;
import javax.validation.constraints.NotNull;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class QueryCounterBean extends NotificationBroadcasterSupport implements QueryCounter, QueryCounterBeanMBean {

    private static final String MBEAN_NAME = "ru.byprogminer.Lab4_Web:type=QueryCounter";

    private static final String OUT_OF_BOUNDS_NOTIFICATION_TYPE = "ru.byprogminer.Lab4_Web.OutOfBounds";
    private static final String OUT_OF_BOUNDS_NOTIFICATION_MESSAGE = "Query (%s, %s, %s) is out of validation bounds";

    private final AtomicInteger allQueriesCount = new AtomicInteger();
    private final AtomicInteger falseResultQueriesCount = new AtomicInteger();
    private final AtomicInteger outOfAreaPointsCount = new AtomicInteger();

    @PostConstruct
    public void init() {
        try {
            //noinspection EjbThisExpressionInspection
            ManagementFactory.getPlatformMBeanServer().registerMBean(this, new ObjectName(MBEAN_NAME));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPointCheckingResult(boolean result) {
        allQueriesCount.incrementAndGet();

        if (!result) {
            falseResultQueriesCount.incrementAndGet();
        }
    }

    @Override
    public void sendPointCheckingValidationFailure(@NotNull BigDecimal x, @NotNull BigDecimal y, @NotNull BigDecimal r) {
        sendNotification(new Notification(OUT_OF_BOUNDS_NOTIFICATION_TYPE, this,
                outOfAreaPointsCount.getAndIncrement(), System.currentTimeMillis(),
                String.format(OUT_OF_BOUNDS_NOTIFICATION_MESSAGE,
                        x.toPlainString(),
                        y.toPlainString(),
                        r.toPlainString())));
    }

    @Override
    public int getAllQueriesCount() {
        return allQueriesCount.get();
    }

    @Override
    public int getFalseResultQueriesCount() {
        return falseResultQueriesCount.get();
    }
}

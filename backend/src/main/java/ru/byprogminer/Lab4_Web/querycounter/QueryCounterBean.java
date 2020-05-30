package ru.byprogminer.Lab4_Web.querycounter;

import javax.ejb.Singleton;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class QueryCounterBean extends NotificationBroadcasterSupport implements QueryCounter, QueryCounterMBean {

    private static final String OUT_OF_BOUNDS_NOTIFICATION_TYPE = "ru.byprogminer.Lab4_Web.management.OutOfArea";
    private static final String OUT_OF_BOUNDS_NOTIFICATION_MESSAGE = "Query (%s, %s, %s) is out of validation bounds";

    private final AtomicInteger allQueriesCount = new AtomicInteger();
    private final AtomicInteger falseResultQueriesCount = new AtomicInteger();
    private final AtomicInteger outOfAreaPointsCount = new AtomicInteger();

    @Override
    public void sendCheckPointResult(boolean result) {
        allQueriesCount.incrementAndGet();

        if (!result) {
            falseResultQueriesCount.incrementAndGet();
        }
    }

    @Override
    public void sendCheckPointValidationFailure(@NotNull BigDecimal x, @NotNull BigDecimal y, @NotNull BigDecimal r) {
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

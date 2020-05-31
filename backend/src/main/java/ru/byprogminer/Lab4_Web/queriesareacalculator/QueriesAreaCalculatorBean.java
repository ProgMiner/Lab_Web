package ru.byprogminer.Lab4_Web.queriesareacalculator;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.management.ObjectName;
import javax.validation.constraints.NotNull;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Singleton
public class QueriesAreaCalculatorBean implements QueriesAreaCalculator, QueriesAreaCalculatorBeanMBean {

    private static final String MBEAN_NAME = "ru.byprogminer.Lab4_Web:type=QueriesAreaCalculator";

    private final List<Point> points = new ArrayList<>();
    private final Queue<Point> newPoints = new ConcurrentLinkedQueue<>();

    private volatile BigDecimal area = BigDecimal.ZERO;

    private final HashMap<Point, Double> angles = new HashMap<>();
    private final HashMap<Point, Double> lengths = new HashMap<>();

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
    public void addPoint(@NotNull BigDecimal x, @NotNull BigDecimal y) {
        newPoints.add(new Point(x, y));
        area = null;
    }

    @Override
    public BigDecimal getArea() {
        final BigDecimal area = this.area;

        if (area != null) {
            return area;
        }

        final BigDecimal calculatedArea = calculateArea();
        this.area = calculatedArea;

        return calculatedArea;
    }

    private BigDecimal calculateArea() {
        synchronized (points) {
            final int pointsCount = points.size() + newPoints.size();

            if (pointsCount < 3) {
                return BigDecimal.ZERO;
            }

            if (!newPoints.isEmpty()) {
                while (!newPoints.isEmpty()) {
                    points.add(newPoints.remove());
                }

                final Point firstPoint = points.get(0);
                for (int i = angles.size() + 1; i < pointsCount; ++i) {
                    final Point currentPoint = points.get(i);

                    final BigDecimal x = currentPoint.x.subtract(firstPoint.x);
                    final BigDecimal y = currentPoint.y.subtract(firstPoint.y);
                    angles.put(currentPoint, Math.atan2(y.doubleValue(), x.doubleValue()));
                    lengths.put(currentPoint, Math.sqrt(x.multiply(x).add(y.multiply(y)).doubleValue()));
                }

                points.subList(1, pointsCount).sort(Comparator.comparingDouble(angles::get));
            }

            BigDecimal area = BigDecimal.ZERO;
            final Point secondPoint = points.get(1);
            BigDecimal previousAngle, currentAngle = BigDecimal.valueOf(angles.get(secondPoint));
            BigDecimal previousLength, currentLength = BigDecimal.valueOf(lengths.get(secondPoint));
            for (int i = 2; i < pointsCount; ++i) {
                final Point currentPoint = points.get(i);

                previousAngle = currentAngle;
                previousLength = currentLength;
                currentAngle = BigDecimal.valueOf(angles.get(currentPoint));
                currentLength = BigDecimal.valueOf(lengths.get(currentPoint));

                area = area.add(previousLength.multiply(currentLength).multiply(BigDecimal
                        .valueOf(Math.abs(Math.sin(currentAngle.subtract(previousAngle).doubleValue())))));
            }

            return area.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
        }
    }

    private static class Point {

        public final BigDecimal x, y;

        public Point(BigDecimal x, BigDecimal y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("Point{x=%s, y=%s}", x, y);
        }
    }
}

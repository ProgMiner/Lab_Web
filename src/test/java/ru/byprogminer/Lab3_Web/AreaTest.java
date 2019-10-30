package ru.byprogminer.Lab3_Web;

import ru.byprogminer.Lab3_Web.beans.QueryBean;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AreaTest {

    private static final int DEFAULT_SIDE = 1600;
    private static final Color COLOR = new Color(0xF9, 0xF9, 0xF9);

    public static void main(String[] args) throws IOException, NoSuchFieldException {
        if (args.length < 1) {
            System.err.println("Usage: <R> [side = " + DEFAULT_SIDE + "]");
            return;
        }

        final BigDecimal r = new BigDecimal(args[0]);

        final int side;
        if (args.length > 1) {
            side = Integer.parseInt(args[1]);
        } else {
            side = DEFAULT_SIDE;
        }

        final BufferedImage image = new BufferedImage(side, side, BufferedImage.TYPE_4BYTE_ABGR);

        final ExecutorService executor = Executors.newCachedThreadPool(r1 -> {
            final Thread thread = Executors.defaultThreadFactory().newThread(r1);

            thread.setDaemon(true);
            return thread;
        });

        final Field queryBeanQueryField = QueryBean.class.getDeclaredField("query");
        queryBeanQueryField.setAccessible(true);

        final BigDecimal zoom = BigDecimal.valueOf(side).multiply(BigDecimal.TEN)
                .divide(BigDecimal.valueOf(24), 5, RoundingMode.HALF_UP)
                .divide(r, RoundingMode.HALF_UP);

        final double center = (double) side / 2;
        final CompletableFuture[] futures = new CompletableFuture[side];
        for (int x = 0; x < side; ++x) {
            final BigDecimal realX = BigDecimal.valueOf(x - center)
                    .divide(zoom, 5, RoundingMode.HALF_UP);

            final int finalX = x;
            futures[x] = CompletableFuture.runAsync(() -> {
                final QueryBean queryBean = new QueryBean(realX, null, r, null);

                for (int y = 0; y < side; ++y) {
                    final BigDecimal realY = BigDecimal.valueOf(center - y)
                            .divide(zoom, 5, RoundingMode.HALF_UP);

                    queryBean.setY(realY);
                    try {
                        ((Query) queryBeanQueryField.get(queryBean)).setResult(null);

                        if (queryBean.getResult()) {
                            image.setRGB(finalX, y, COLOR.getRGB());
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }, executor);
        }

        CompletableFuture.allOf(futures).join();

        final Path path = Files.createTempFile("Lab3_Web.AreaTest.", ".png");
        ImageIO.write(image, "PNG", Files.newOutputStream(path));

        System.out.println("Result saved to " + path.toString());

        final Desktop desktop = Desktop.getDesktop();
        if (Desktop.isDesktopSupported() || desktop != null) {
            desktop.open(path.getParent().toFile());
        }
    }
}
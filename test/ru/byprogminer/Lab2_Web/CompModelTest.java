package ru.byprogminer.Lab2_Web;

import ru.byprogminer.Lab2_Web.model.CompModel;
import ru.byprogminer.Lab2_Web.model.CompModelImpl;
import ru.byprogminer.Lab2_Web.utility.AreaRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ru.byprogminer.Lab2_Web.utility.Utility.divide;

public class CompModelTest {

    private static final int DEFAULT_SIDE = 205;
    private static final String STEP = "0.001";
    private static final Color COLOR = Color.CYAN;

    private static class Calculator extends AreaRenderer.Calculator {

        public Calculator(int width, int height, BigDecimal r) {
            super(width, height, r);
        }

        public BigDecimal getZoomX() {
            return zoomX;
        }

        public BigDecimal getZoomY() {
            return zoomY;
        }
    }

    public static void main(String[] args) throws IOException {
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

        final Calculator calculator = new Calculator(side, side, r);
        final CompModel compModel = new CompModelImpl(null, null, null);
        final BufferedImage image = new BufferedImage(side, side, BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics graphics = image.getGraphics();
        graphics.setColor(COLOR);

        final List<CompletableFuture<?>> futureList = new LinkedList<>();
        final ExecutorService executor = Executors.newCachedThreadPool(r1 -> {
            final Thread thread = Executors.defaultThreadFactory().newThread(r1);

            thread.setDaemon(true);
            return thread;
        });

        final BigDecimal step = new BigDecimal(STEP);
        final BigDecimal bdSide = BigDecimal.valueOf(side);
        for (BigDecimal maxX = divide(divide(bdSide, calculator.getZoomX()), BigDecimal.valueOf(2)),
             _x = BigDecimal.ZERO.subtract(maxX); _x.compareTo(maxX) <= 0; _x = _x.add(step)) {
            final BigDecimal x = _x;

            futureList.add(CompletableFuture.runAsync(() -> {
                for (BigDecimal maxY = divide(divide(bdSide, calculator.getZoomY()), BigDecimal.valueOf(2)),
                    y = BigDecimal.ZERO.subtract(maxY); y.compareTo(maxY) <= 0;y = y.add(step)) {
                    if (compModel.getResult(x, y, r)) {
                        graphics.drawArc(
                                calculator.translateX(x)
                                        .setScale(0, RoundingMode.HALF_UP)
                                        .intValue(),
                                calculator.translateY(y)
                                        .setScale(0, RoundingMode.HALF_UP)
                                        .intValue(),
                                1,
                                1,
                                0,
                                360
                        );
                    }
                }
            }, executor));
        }

        final CompletableFuture<?>[] futures = futureList.toArray(new CompletableFuture[] {});
        futureList.clear();

        CompletableFuture.allOf(futures);

        final Path path = Files.createTempFile("Lab2_Web.CompModelTest.", ".png");
        ImageIO.write(image, "PNG", Files.newOutputStream(path));

        System.out.println("Result saved to " + path.toString());

        final Desktop desktop = Desktop.getDesktop();
        if (Desktop.isDesktopSupported() || desktop != null) {
            desktop.open(path.getParent().toFile());
        }
    }
}

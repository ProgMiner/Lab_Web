package ru.byprogminer.Lab2_Web.utility;

import ru.byprogminer.Lab2_Web.HistoryNode;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static ru.byprogminer.Lab2_Web.utility.Utility.divide;

public class AreaRenderer {

    public static class Calculator {

        private final BigDecimal centerX, centerY;
        protected final BigDecimal zoomX, zoomY;

        public Calculator(int width, int height, BigDecimal r) {
            centerX = divide(BigDecimal.valueOf(width), BigDecimal.valueOf(2))
                    .subtract(BigDecimal.ONE);
            centerY = divide(BigDecimal.valueOf(height), BigDecimal.valueOf(2))
                    .subtract(BigDecimal.ONE);

            zoomX = divide(divide(BigDecimal.valueOf(80)
                    .multiply(BigDecimal.valueOf(width)),
                    BigDecimal.valueOf(205)), r);
            zoomY = divide(divide(BigDecimal.valueOf(80)
                    .multiply(BigDecimal.valueOf(height)),
                    BigDecimal.valueOf(205)), r);
        }

        public BigDecimal translateX(BigDecimal x) {
            return centerX.add(x.multiply(zoomX));
        }

        public BigDecimal translateY(BigDecimal y) {
            return centerY.subtract(y.multiply(zoomY));
        }
    }

    public static final Color INCLUDED_COLOR = Color.GREEN;
    public static final Color NOT_INCLUDED_COLOR = Color.RED;
    public static final Color BORDER_COLOR = Color.BLACK;

    private final ServletContext context;

    public AreaRenderer(ServletContext context) {
        this.context = context;
    }

    public String renderArea(String areaPath, String areaUrl, Deque<HistoryNode> history) {
        if (Objects.requireNonNull(history).isEmpty()) {
            return areaUrl;
        }

        try {
            final BufferedImage image = ImageIO.read(context.getResourceAsStream(areaPath));

            final BufferedImage canvas = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

            final Graphics graphics = canvas.getGraphics();
            graphics.drawImage(image, 0, 0, null);

            if (graphics instanceof Graphics2D) {
                ((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }

            final Map<BigDecimal, Calculator> calcs = new HashMap<>();
            for (final HistoryNode historyNode : history) {
                final Calculator calc = calcs.computeIfAbsent(historyNode.r, r ->
                        new Calculator(image.getWidth(), image.getHeight(), r));

                final int x = calc.translateX(historyNode.x)
                        .subtract(BigDecimal.valueOf(2))
                        .setScale(0, RoundingMode.HALF_UP)
                        .intValue();

                final int y = calc.translateY(historyNode.y)
                        .subtract(BigDecimal.valueOf(2))
                        .setScale(0, RoundingMode.HALF_UP)
                        .intValue();

                graphics.setColor(BORDER_COLOR);
                graphics.fillArc(x - 1, y - 1, 7, 7, 0, 360);

                graphics.setColor(historyNode.result ? INCLUDED_COLOR : NOT_INCLUDED_COLOR);
                graphics.fillArc(x, y, 5, 5, 0, 360);
            }

            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(canvas, "PNG", os);

            return JspUtility.inlineImage(os.toByteArray(), "image/png", new LinkedList<>());
        } catch (Throwable e) {
            e.printStackTrace();
            return areaUrl;
        }
    }
}

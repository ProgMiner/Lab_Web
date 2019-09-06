package ru.byprogminer.Lab2_Web.utility;

import ru.byprogminer.Lab2_Web.HistoryNode;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class AreaRenderer {

    public static class Calculator {

        private final BigDecimal centerX, centerY;
        private final BigDecimal zoomX, zoomY;

        public Calculator(int width, int height, BigDecimal r) {
            centerX = BigDecimal.valueOf(width)
                    .divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP)
                    .subtract(BigDecimal.ONE);
            centerY = BigDecimal.valueOf(height)
                    .divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP)
                    .subtract(BigDecimal.ONE);

            zoomX = BigDecimal.valueOf(80)
                    .multiply(BigDecimal.valueOf(width))
                    .divide(BigDecimal.valueOf(205), RoundingMode.HALF_UP)
                    .divide(r, RoundingMode.HALF_UP);
            zoomY = BigDecimal.valueOf(80)
                    .multiply(BigDecimal.valueOf(height))
                    .divide(BigDecimal.valueOf(205), RoundingMode.HALF_UP)
                    .divide(r, RoundingMode.HALF_UP);
        }

        public BigDecimal translateX(BigDecimal x) {
            return centerX.add(x.multiply(zoomX));
        }

        public BigDecimal translateY(BigDecimal y) {
            return centerY.subtract(y.multiply(zoomY));
        }
    }

    private final ServletContext context;

    public AreaRenderer(ServletContext context) {
        this.context = context;
    }

    public String renderArea(String areaPath, String areaUrl, HistoryNode historyNode) {
        if (historyNode == null) {
            return areaUrl;
        }

        try {
            final BufferedImage image = ImageIO.read(context.getResourceAsStream(areaPath));

            final BufferedImage canvas = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

            final Graphics graphics = canvas.getGraphics();
            graphics.drawImage(image, 0, 0, null);
            graphics.setColor(Color.RED);

            final Map<BigDecimal, Calculator> calcs = new HashMap<>();
            while (historyNode != null) {
                final Calculator calc = calcs.computeIfAbsent(historyNode.r, r ->
                        new Calculator(image.getWidth(), image.getHeight(), r));

                graphics.fillArc(
                        calc.translateX(historyNode.x)
                                .subtract(BigDecimal.valueOf(2))
                                .setScale(0, RoundingMode.HALF_UP)
                                .intValue(),
                        calc.translateY(historyNode.y)
                                .subtract(BigDecimal.valueOf(2))
                                .setScale(0, RoundingMode.HALF_UP)
                                .intValue(),
                        5, 5, 0, 360
                );

                historyNode = historyNode.next;
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

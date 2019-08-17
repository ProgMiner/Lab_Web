package ru.byprogminer.Lab2_Web.utility;

import ru.byprogminer.Lab2_Web.model.CompModel;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.LinkedList;

public class AreaRenderer {

    public static class Calculator {

        private final int centerX, centerY;
        private final double zoomX, zoomY;

        public Calculator(int width, int height, double r) {
            centerX = (int) Math.round(width / 2D) - 1;
            centerY = (int) Math.round(height / 2D) - 1;

            zoomX = 80D * width / 205D / r;
            zoomY = 80D * height / 205D / r;
        }

        public double translateX(double x) {
            return centerX + x * zoomX;
        }

        public double translateY(double y) {
            return centerY - y * zoomY;
        }
    }

    private final ServletContext context;

    public AreaRenderer(ServletContext context) {
        this.context = context;
    }

    public String renderArea(String areaPath, String areaUrl, CompModel compModel) {
        if (!compModel.isResultAvailable()) {
            return areaUrl;
        }

        try {
            final BufferedImage image = ImageIO.read(context.getResourceAsStream(areaPath));

            final BufferedImage canvas = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

            final Graphics graphics = canvas.getGraphics();
            graphics.drawImage(image, 0, 0, null);
            graphics.setColor(Color.RED);

            final Calculator calc = new Calculator(image.getWidth(), image.getHeight(), compModel.getR().doubleValue());
            graphics.fillArc(
                    (int) Math.round(calc.translateX(compModel.getX().doubleValue())) - 2,
                    (int) Math.round(calc.translateY(compModel.getY().doubleValue())) - 2,
                    5, 5, 0, 360
            );

            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(canvas, "PNG", os);

            return JspUtility.inlineImage(os.toByteArray(), "image/png", new LinkedList<>());
        } catch (Throwable e) {
            e.printStackTrace();
            return areaUrl;
        }
    }
}

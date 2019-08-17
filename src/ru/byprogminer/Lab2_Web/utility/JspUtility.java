package ru.byprogminer.Lab2_Web.utility;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.LinkedList;

public class JspUtility {

    private final HttpServletRequest request;

    public JspUtility(HttpServletRequest request) {
        this.request = request;
    }

    public String inlineImage(String path) {
        return inlineImage(path, "PNG", "image/png");
    }

    public String inlineImage(String path, String format, String mime) {
        return inlineImage(path, format, mime, null);
    }

    public String inlineImage(String path, String format, String mime, LinkedList<String> headers) {
        if (headers == null) {
            headers = new LinkedList<>();
        }

        try {
            final BufferedImage image = ImageIO.read(request.getServletContext().getResourceAsStream(path));
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, format, os);

            return inlineImage(os.toByteArray(), mime, headers);
        } catch (Throwable e) {
            e.printStackTrace();

            return request.getContextPath() + path;
        }
    }

    public static String inlineImage(byte[] bytes, String mime, LinkedList<String> headers) {
        if (mime != null) {
            headers.addFirst(mime);
        }

        return "data:" + String.join(",", headers) + ";base64," + Base64.getEncoder().encodeToString(bytes);
    }
}

package ru.byprogminer.Lab3_Web.utilities;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.LinkedList;

public class JsfUtilities {

    public static String inlineImage(HttpServletRequest request, String path) {
        return inlineImage(request, path, "PNG", "image/png");
    }

    public static String inlineImage(HttpServletRequest request, String path, String format, String mime) {
        return inlineImage(request, path, format, mime, null);
    }

    public static String inlineImage(
            HttpServletRequest request,
            String path,
            String format,
            String mime,
            LinkedList<String> headers
    ) {
        if (headers == null) {
            headers = new LinkedList<>();
        }

        try {
            final InputStream stream = request.getServletContext().getResourceAsStream(path);
            if (stream == null) {
                throw new IllegalArgumentException("File not found: " + path);
            }

            final BufferedImage image = ImageIO.read(stream);
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

package ru.byprogminer.Lab2_Web;

import javax.servlet.http.HttpServletRequest;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.LinkedList;

public final class Utility {

    private Utility() {
        throw new UnsupportedOperationException();
    }

    public static String getBaseUrl(HttpServletRequest request) {
        final String url = request.getRequestURL().toString();

        return url.substring(0, url.length() - request.getRequestURI().length());
    }

    public static String inlineImage(String baseUrl, String path) {
        return inlineImage(baseUrl, path, null);
    }

    public static String inlineImage(String baseUrl, String path, String mime) {
        return inlineImage(baseUrl, path, mime, null);
    }

    public static String inlineImage(String baseUrl, String path, String mime, LinkedList<String> headers) {
        if (headers == null) {
            headers = new LinkedList<>();
        }

        try {
            final URLConnection conn = new URL(baseUrl + path).openConnection();
            final byte[] bytes = new byte[conn.getContentLength()];
            new DataInputStream(conn.getInputStream()).readFully(bytes);
            conn.getInputStream().close();

            if (mime == null) {
                mime = conn.getContentType();
            }

            return inlineImage(bytes, Paths.get(path).getFileName().toString(), mime, headers);
        } catch (Throwable e) {
            e.printStackTrace();
            return path;
        }
    }

    public static String inlineImage(byte[] bytes, String suffix, String mime, LinkedList<String> headers) throws IOException {
        if (mime == null) {
            final Path tmp = Files.createTempFile("inlineImage", suffix);
            Files.write(tmp, bytes);

            mime = Files.probeContentType(tmp);
        }

        return inlineImage(bytes, mime, headers);
    }

    public static String inlineImage(byte[] bytes, String mime, LinkedList<String> headers) {
        if (mime != null) {
            headers.addFirst(mime);
        }

        return "data:" + String.join(",", headers) + ";base64," + Base64.getEncoder().encodeToString(bytes);
    }

    public static Boolean parseBoolean(String str, Boolean defaultValue) {
        if (str == null) {
            return defaultValue;
        }

        switch (str) {
            case "false":
            case "off":
            case "no":
                return false;

            case "true":
            case "yes":
            case "on":
                return true;
        }

        return defaultValue;
    }
}

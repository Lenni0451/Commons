package net.lenni0451.commons.httpclient.utils;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class HttpRequestUtils {

    /**
     * Merge multiple headers into one map.
     *
     * @param maps The headers to merge
     * @return The merged headers
     */
    @SafeVarargs
    public static Map<String, List<String>> mergeHeaders(final Map<String, List<String>>... maps) {
        Map<String, List<String>> headers = new HashMap<>();
        for (Map<String, List<String>> map : maps) {
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                headers.put(entry.getKey().toLowerCase(Locale.ROOT), entry.getValue());
            }
        }
        return headers;
    }

    /**
     * Get the cookie headers for a URL.
     *
     * @param cookieManager The cookie manager to use
     * @param url           The URL to get the cookies for
     * @return The cookie headers
     * @throws IOException If an I/O error occurs
     */
    public static Map<String, List<String>> getCookieHeaders(@Nullable final CookieManager cookieManager, final URL url) throws IOException {
        try {
            if (cookieManager == null) return Collections.emptyMap();
            return cookieManager.get(url.toURI(), Collections.emptyMap());
        } catch (URISyntaxException e) {
            throw new IOException("Failed to parse URL as URI", e);
        }
    }

    /**
     * Update the cookies for a URL.
     *
     * @param cookieManager The cookie manager to use
     * @param url           The URL to update the cookies for
     * @param headers       The headers to update the cookies from
     * @throws IOException If an I/O error occurs
     */
    public static void updateCookies(@Nullable final CookieManager cookieManager, final URL url, final Map<String, List<String>> headers) throws IOException {
        if (cookieManager == null) return;
        try {
            cookieManager.put(url.toURI(), headers);
        } catch (URISyntaxException e) {
            throw new IOException("Failed to parse URL as URI", e);
        }
    }

    /**
     * Set the headers for a connection.
     *
     * @param connection The connection to set the headers for
     * @param headers    The headers to set
     */
    public static void setHeaders(final HttpURLConnection connection, final Map<String, List<String>> headers) {
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            connection.setRequestProperty(entry.getKey(), String.join("; ", entry.getValue()));
        }
    }

    /**
     * Read the body of a connection.
     *
     * @param connection The connection to read the body from
     * @return The body of the connection
     * @throws IOException If an I/O error occurs
     */
    public static byte[] readBody(final HttpURLConnection connection) throws IOException {
        InputStream is;
        if (connection.getResponseCode() >= 400) is = connection.getErrorStream();
        else is = connection.getInputStream();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = is.read(buf)) != -1) baos.write(buf, 0, len);

        return baos.toByteArray();
    }

}

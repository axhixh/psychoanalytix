
package gaul.psychoanalytix.cacofonix;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 *
 * @author ashish
 */
abstract class HttpUtil {
    static String escape(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            return value;
        }
    }
    static String get(String url) throws IOException {
        return http("GET", url, null);        
    }

    static String delete(String url) throws IOException {
        return http("DELETE", url, null);        
    }
    
    static String post(String url, Content content) throws IOException {
        return http("POST", url, content);
    }
    
    static String put(String url, Content content) throws IOException {
        return http("PUT", url, content);
    }
    
    static String http(String method, String url, Content content) throws IOException {
        if (("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)) && content == null) {
            throw new IllegalArgumentException("POST and PUT need content");
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            try {
                conn.setRequestMethod(method);
                conn.setRequestProperty("Accept", "*/*");

                if (content != null) {
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", content.getType());
                    OutputStream os = conn.getOutputStream();
                    os.write(content.getBody().getBytes());
                    os.flush();
                }
                
                if (!isOK(conn.getResponseCode())) {
                    throw new IOException("HTTP error code : " + conn.getResponseCode());
                }

                InputStream in = conn.getInputStream();
                byte[] buffer = new byte[4096];
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                int read;
                while ((read = in.read(buffer)) != -1) {
                    output.write(buffer, 0, read);
                }
                return output.toString("UTF-8");
            } finally {
                conn.disconnect();
            }
        } catch (MalformedURLException e) {

            throw new IOException("Invalid URL", e);

        }
    }
    
    private static boolean isOK(int status) {
        return status >= 200 && status < 300;
    }
    
    static class Content {
        private final String type;
        private final String body;

        public Content(String type, String body) {
            this.type = type;
            this.body = body;
        }
        
        String getType() {
            return type;
        }
        
        String getBody() {
            return body;
        }
    }
}

package org.example;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

public class ApiJsonParser {
    public static ConfigBundle configBundle = ConfigBundle.getInstance();
    public static String getJsonFromApi(String url) throws IOException {
        ConfigBundle configBundle = ConfigBundle.getInstance();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        IOException exception = null;
        int maxRetries = Integer.parseInt(configBundle.getProperty("max_request_retries"));
        int retryDelay = Integer.parseInt(configBundle.getProperty("retry_request_delay"));
        for (int i = 0; i < maxRetries; i++) {
            try {
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    break;
                } else if (response.code() == 429) {
                    try {
                        Thread.sleep(retryDelay);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new IOException("Interrupted while retrying", e);
                    }
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            } catch (IOException e) {
                exception = e;
            }
        }

        if (response == null) {
            throw Objects.requireNonNull(exception);
        }
        return response.body() != null ? response.body().string() : null;
    }
}

package api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HackerNewsApi {
    private final String url = "https://hacker-news.firebaseio.com/v0";
    private final OkHttpClient client = new OkHttpClient();
    private Long newestItemId;

    public HackerNewsApi() throws IOException {
        Request request = new Request.Builder().url(url + "/maxitem.json?print=pretty").build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            assert response.body() != null;
            String responseBody = response.body().string();
            newestItemId = Long.parseLong(responseBody.trim());
        }
    }

    public String getItem() throws IOException {
        Request request = new Request.Builder().url("https://hacker-news.firebaseio.com/v0/item/"+ --newestItemId +".json?print=pretty").build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            assert response.body() != null;
            return response.body().string();
        }
    }

    public long getNewestItemId() {
        return newestItemId;
    }
}

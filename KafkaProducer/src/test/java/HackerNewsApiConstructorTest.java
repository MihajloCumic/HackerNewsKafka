import api.HackerNewsApi;

import java.io.IOException;

public class HackerNewsApiConstructorTest {
    public static void main(String[] args) {
        try {
            HackerNewsApi hackerNewsApi = new HackerNewsApi();
            System.out.println(hackerNewsApi.getNewestItemId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

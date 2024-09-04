import api.HackerNewsApi;

import java.io.IOException;

public class GetItemTest {
    public static void main(String[] args) throws IOException {
        HackerNewsApi hackerNewsApi = new HackerNewsApi();
        System.out.println(hackerNewsApi.getItem());
    }
}

package seedu.address.network;

import static junit.framework.TestCase.assertTrue;

import org.asynchttpclient.Dsl;
import org.junit.Test;

public class HttpClientTest {
    @Test
    public void close_executedMultipleTimes_success() {
        HttpClient httpClient = new HttpClient(Dsl.asyncHttpClient());
        httpClient.close();
        httpClient.close();
        httpClient.close();
        assertTrue(true);
    }
}

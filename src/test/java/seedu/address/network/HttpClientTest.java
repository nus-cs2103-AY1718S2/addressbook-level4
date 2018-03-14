package seedu.address.network;

import org.asynchttpclient.Dsl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HttpClientTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void close_executedMultipleTimes_success() {
        HttpClient httpClient = new HttpClient(Dsl.asyncHttpClient());
        httpClient.close();
        httpClient.close();
        httpClient.close();
        // should not throw any exceptions
    }
}

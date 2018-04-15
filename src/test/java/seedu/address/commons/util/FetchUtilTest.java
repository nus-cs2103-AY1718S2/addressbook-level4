//@@author laichengyu

package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.asynchttpclient.Response;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class FetchUtilTest {
    private static final String url = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC&tsyms=USD";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * This test checks if the asynchronous fetch is performed correctly.
     * However, due to the nature of the ever changing response data, it is not possible to verify the
     * values of the returned JSON.
     * Hence, assertion is done on the structure of the returned JSON instead.
     *
     * This test also requires an online connection, so if it fails, check your internet connection or
     * disable this test temporarily.
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void asyncFetch() throws InterruptedException, ExecutionException {
        Future<Response> future = FetchUtil.asyncFetch(url);
        Response response = future.get();
        JsonObject jsonObj = FetchUtil.parseStringToJsonObj(response.getResponseBody());
        Map.Entry<String, JsonElement> soleEntry = getEntryFromJsonObj(jsonObj);

        assertEquals("BTC", soleEntry.getKey());

        Map.Entry<String, JsonElement> nestedEntry = getEntryFromJsonObj(soleEntry
                .getValue()
                .getAsJsonObject());

        assertEquals("USD", nestedEntry.getKey());
    }

    @Test
    public void parseStringToJsonObj() {

        // valid json string
        String str = "{\"USD\":494.3}";
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("USD", 494.3);

        assertEquals(jsonObj, FetchUtil.parseStringToJsonObj(str));
    }

    @Test
    public void parseStringToJsonObj_nullExceptionThrown() {

        // null string parameter
        thrown.expect(NullPointerException.class);
        FetchUtil.parseStringToJsonObj(null);
    }

    private Map.Entry<String, JsonElement> getEntryFromJsonObj(JsonObject jsonObj) {
        Set<Map.Entry<String, JsonElement>> entrySet = jsonObj.entrySet();
        return entrySet.stream().findFirst().get();
    }
}

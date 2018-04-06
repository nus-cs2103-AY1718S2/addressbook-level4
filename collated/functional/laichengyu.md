# laichengyu
###### \java\seedu\address\commons\events\ui\LoadingEvent.java
``` java

package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event to indicate that the app is loading data
 */
public class LoadingEvent extends BaseEvent {

    public final boolean isLoading;

    public LoadingEvent(boolean isLoading) {
        this.isLoading = isLoading;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\util\FetchUtil.java
``` java

package seedu.address.commons.util;

import static org.asynchttpclient.Dsl.asyncHttpClient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.asynchttpclient.AsyncHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseStatus;
import org.asynchttpclient.Response;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.netty.handler.codec.http.HttpHeaders;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.LoadingEvent;

/**
 * Retrieves data in JSON format from a specified URL
 */
public class FetchUtil {

    private static AsyncHttpClient myAsyncHttpClient = asyncHttpClient();

    /**
     * Asynchronously fetches data from the specified url and returns it as a JsonObject
     * @param url cannot be null
     * @return data received as a JsonObject
     * @throws InterruptedException when there is a thread interrupt
     * @throws ExecutionException when there is an error in data fetching
     */
    public static JsonObject asyncFetch(String url)
            throws InterruptedException, ExecutionException {
        //Send HTTP request asynchronously
        BoundRequestBuilder boundReqBuilder = myAsyncHttpClient.prepareGet(url);
        AsyncHandler<Response> asyncHandler = getResponseAsyncHandler();
        Future<Response> whenResponse = boundReqBuilder.execute(asyncHandler);

        //Set loading UI
        EventsCenter.getInstance().post(new LoadingEvent(true));
        Response response = whenResponse.get();
        //Return UI to normal
        EventsCenter.getInstance().post(new LoadingEvent(false));
        return parseStringToJsonObj(response.getResponseBody());
    }

    /**
     * Creates a new async response handler
     * @return AsyncHandler for Response objects
     */
    private static AsyncHandler<Response> getResponseAsyncHandler() {
        return new AsyncHandler<Response>() {
                private Response.ResponseBuilder builder = new Response.ResponseBuilder();
                private Integer status;
                @Override
                public State onStatusReceived(HttpResponseStatus responseStatus) throws Exception {
                    status = responseStatus.getStatusCode();
                    builder.accumulate(responseStatus);
                    if (status != 200) {
                        return State.ABORT;
                    }
                    return State.CONTINUE;
                }
                @Override
                public State onHeadersReceived(HttpHeaders headers) throws Exception {
                    return State.CONTINUE;
                }
                @Override
                public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
                    builder.accumulate(bodyPart);
                    return State.CONTINUE;
                }
                @Override
                public Response onCompleted() throws Exception {
                    return builder.build();
                }
                @Override
                public void onThrowable(Throwable t) {
                }
            };
    }

    /**
     * Parses a String into a JsonObject
     * @param str cannot be null
     * @return JsonObject converted from String
     */
    private static JsonObject parseStringToJsonObj(String str) {
        JsonObject jsonObject;

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(str);
        jsonObject = jsonElement.getAsJsonObject();

        return jsonObject;
    }
}
```
###### \java\seedu\address\commons\util\UrlBuilderUtil.java
``` java

package seedu.address.commons.util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import seedu.address.commons.core.LogsCenter;

/**
 * Builds a URL given a url and parameters
 */
public class UrlBuilderUtil {

    private static final Logger logger = LogsCenter.getLogger(UrlBuilderUtil.class);

    /**
     * Builds a URL given the url and params
     * @param url cannot be null
     * @param params are necessary
     * @return String URL concatenated with params
     */
    public static String buildUrl(String url, List<NameValuePair> params) {
        String parameterizedUrl = "";
        try {
            URIBuilder uri = new URIBuilder(url);
            uri.addParameters(params);
            parameterizedUrl = uri.build().toURL().toString();
        } catch (URISyntaxException e) {
            logger.info("Illegal characters found in url: " + url + " or params: " + params.toString());
        } catch (MalformedURLException e) {
            logger.info("Malformed URL: " + url + " provided");
        }
        return parameterizedUrl;
    }
}
```
###### \java\seedu\address\logic\commands\SyncCommand.java
``` java

package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.JsonObject;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FetchUtil;
import seedu.address.commons.util.UrlBuilderUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.coin.exceptions.CoinNotFoundException;
import seedu.address.model.coin.exceptions.DuplicateCoinException;

/**
 * Updates all coins in the coin book with latest cryptocurrency data
 */
public class SyncCommand extends Command {

    public static final String COMMAND_WORD = "sync";
    public static final String COMMAND_ALIAS = "sy";

    public static final String MESSAGE_SUCCESS = "Synced all coins with latest cryptocurrency data";

    private static final Logger logger = LogsCenter.getLogger(SyncCommand.class);

    private String cryptoCompareApiUrl = "https://min-api.cryptocompare.com/data/pricemulti";

    /**
     * Creates and returns a {@code List<NameValuePair>} with two key-value pairs, coin symbols and currency.
     *
     * @param coinList cannot be null
     * @return parameters for CryptoCompare API call
     */
    private List<NameValuePair> getParams(List<String> coinList) {
        List<NameValuePair> params = new ArrayList<>();
        String coinCodes = String.join(",", coinList);
        params.add(new BasicNameValuePair("fsyms", coinCodes));
        params.add(new BasicNameValuePair("tsyms", "USD"));
        return params;
    }

    /**
     * Adds parameters to the CryptoCompare API URL.
     *
     * @param params cannot be null
     */
    private void buildApiUrl(List<NameValuePair> params) {
        cryptoCompareApiUrl = UrlBuilderUtil.buildUrl(cryptoCompareApiUrl, params);
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            List<NameValuePair> params = getParams(model.getCodeList());
            buildApiUrl(params);
            JsonObject newData = FetchUtil.asyncFetch(cryptoCompareApiUrl);
            model.syncAll(newData);
        } catch (DuplicateCoinException dpe) {
            throw new CommandException("Unexpected code path!");
        } catch (CoinNotFoundException cnfe) {
            throw new AssertionError("The target coin cannot be missing");
        } catch (InterruptedException ie) {
            logger.warning("Thread interrupted");
        } catch (ExecutionException ee) {
            logger.warning("Data fetching error");
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\model\coin\Coin.java
``` java
    /**
     * Copy constructor with price update.
     */
    public Coin(Coin toCopy, double newPrice) {
        requireAllNonNull(toCopy);
        this.code = toCopy.code;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(toCopy.getTags());
        this.price = new Price(newPrice);
        this.totalAmountSold = new Amount(toCopy.getTotalAmountSold());
        this.totalAmountBought = new Amount(toCopy.getTotalAmountBought());
        this.totalDollarsSold = new Amount(toCopy.getTotalDollarsSold());
        this.totalDollarsBought = new Amount(toCopy.getTotalDollarsBought());
    }
```
###### \java\seedu\address\model\coin\Price.java
``` java
    /**
     * Constructs a {@code Price} with given value.
     */
    public Price(double value) {
        this.value = value;
    }
```
###### \java\seedu\address\model\CoinBook.java
``` java
    /**
     * Replaces every coin in the list that has a price change in {@code newData} with {@code updatedCoin}.
     * {@code CoinBook}'s tag list will be updated with the tags of {@code updatedCoin}.
     *
     * @throws DuplicateCoinException if updating the coin's details causes the coin to be equivalent to
     *      another existing coin in the list.
     * @throws CoinNotFoundException if {@code coin} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Coin)
     */
    public void syncAll(JsonObject newData)
            throws DuplicateCoinException, CoinNotFoundException {
        requireNonNull(newData);

        for (Coin coin : coins) {
            String code = coin.getCode().toString();
            JsonElement coinData = newData.get(code);
            if (coinData == null) {
                continue;
            }
            double newPrice = coinData.getAsJsonObject().get("USD").getAsDouble();
            Coin updatedCoin = new Coin(coin, newPrice);
            updateCoin(coin, updatedCoin);
        }
    }
```
###### \java\seedu\address\model\CoinBook.java
``` java
    @Override
    public List<String> getCodeList() {
        return Collections.unmodifiableList(coins.asObservableList().stream()
                .map(coin -> coin.getCode().toString())
                .collect(Collectors.toList()));
    }
```
###### \java\seedu\address\model\Model.java
``` java
    /** Returns an unmodifiable view of the code list */
    List<String> getCodeList();

    /**
      * Syncs all coin data
      */
    void syncAll(JsonObject newData)
            throws DuplicateCoinException, CoinNotFoundException;
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void syncAll(JsonObject newData)
            throws DuplicateCoinException, CoinNotFoundException {
        requireNonNull(newData);

        coinBook.syncAll(newData);
        indicateAddressBookChanged();
    }

    /** Returns an unmodifiable view of the code list */
    @Override
    public List<String> getCodeList() {
        return coinBook.getCodeList();
    }
```
###### \java\seedu\address\model\ReadOnlyCoinBook.java
``` java
    /**
     * Returns an unmodifiable view of the codes list.
     * This list will not contain any duplicate codes.
     */
    List<String> getCodeList();
```
###### \java\seedu\address\storage\CoinBookStorage.java
``` java
    /**
     * Saves the given {@link ReadOnlyCoinBook} to a fixed temporary location.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void backupCoinBook(ReadOnlyCoinBook addressBook) throws IOException;
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public void backupCoinBook(ReadOnlyCoinBook coinBook) throws IOException {
        coinBookStorage.backupCoinBook(coinBook);
    }
```
###### \java\seedu\address\storage\XmlCoinBookStorage.java
``` java
    @Override
    public void backupCoinBook(ReadOnlyCoinBook addressBook) throws IOException {
        saveCoinBook(addressBook, backupFilePath);
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    private void setLoadingAnimation() {
        ProgressIndicator pi = new ProgressIndicator();
        loadingAnimation = new VBox(pi);
        loadingAnimation.setAlignment(Pos.CENTER);
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Displays loading animation when isLoading is true and hides it otherwise
     * @param isLoading loading state of the application
     */
    @FXML
    private void handleLoading(boolean isLoading) {
        if (isLoading) {
            //Scene scene = new Scene(loadingAnimation, Color.TRANSPARENT);
            //primaryStage.initStyle(StageStyle.TRANSPARENT);
            //primaryStage.setScene(scene);
        } else {
            //primaryStage.setScene(new Scene(null));
        }
    }

    @Subscribe
    private void handleLoadingEvent(LoadingEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleLoading(event.isLoading);
    }
```

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

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.concurrent.Future;

import org.asynchttpclient.AsyncHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseStatus;
import org.asynchttpclient.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.netty.handler.codec.http.HttpHeaders;

/**
 * Retrieves data in JSON format from a specified URL
 */
public class FetchUtil {

    private static AsyncHttpClient myAsyncHttpClient = asyncHttpClient();

    /**
     * Returns a Future object, future from the specific url asynchronously.
     * The HTTP request Response can be retrieved using future.get().
     * All operations queued before future.get() are performed async and the application
     * will be thread-blocked at future.get() to wait for the return Response.
     * @param url cannot be null
     * @return a Future object that can retrieve a Response which contains HTTP request data
     * in its responseBody
     */
    public static Future<Response> asyncFetch(String url) {
        //Send HTTP request asynchronously
        BoundRequestBuilder boundReqBuilder = myAsyncHttpClient.prepareGet(url);
        AsyncHandler<Response> asyncHandler = getResponseAsyncHandler();
        return boundReqBuilder.execute(asyncHandler);
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
    public static JsonObject parseStringToJsonObj(String str) {
        JsonObject jsonObject;

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(str);
        jsonObject = jsonElement.getAsJsonObject();

        return jsonObject;
    }

    /**
     * Parses a file at {@code filepath} as an array of JsonObjects
     * @param fw cannot be null
     * @return JsonArray that is contained in the file at {@code filepath}
     */
    public static JsonArray parseFileToJsonObj(InputStreamReader fw) throws FileNotFoundException {
        JsonObject jsonObject;

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(fw);
        return jsonElement.getAsJsonArray();
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
###### \java\seedu\address\logic\CommandList.java
``` java

package seedu.address.logic;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BuyCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NotifyCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SellCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SyncCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewCommand;

/**
 * Stores a list of all available commands
 */
public class CommandList {
    public static final List<String> COMMAND_LIST = Arrays.asList(HelpCommand.COMMAND_WORD, AddCommand.COMMAND_WORD,
            BuyCommand.COMMAND_WORD, SellCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD,
            ClearCommand.COMMAND_WORD, TagCommand.COMMAND_WORD, ListCommand.COMMAND_WORD,
            FindCommand.COMMAND_WORD, ViewCommand.COMMAND_WORD, NotifyCommand.COMMAND_WORD,
            SortCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD,
            RedoCommand.COMMAND_WORD, SyncCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD);

}
```
###### \java\seedu\address\logic\commands\SyncCommand.java
``` java

package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.asynchttpclient.Response;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.LoadingEvent;
import seedu.address.commons.util.FetchUtil;
import seedu.address.commons.util.UrlBuilderUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.coin.Amount;
import seedu.address.model.coin.Price;
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

    private static final String historicalPriceApiUrl = "https://min-api.cryptocompare.com/data/histohour";
    private static final String cryptoCompareApiUrl = "https://min-api.cryptocompare.com/data/pricemultifull";

    private static final String HISTORICAL = "historical";
    private static final String CURRENT = "current";

    private static final String CODE_PARAM = "fsym";
    private static final String CURRENCY_PARAM = "tsym";
    private static final String PLURALIZE = "s";
    private static final String CURRENCY_TYPE = "USD";
    private static final String LIMIT_PARAM = "limit";
    private static final String HISTORICAL_DATA_HOURS_LIMIT = "168";

    @Override
    public CommandResult execute() throws CommandException {
        try {
            String commaSeparatedCodes = concatenateByComma(model.getCodeList());
            HashMap<String, Price> newPriceMetrics = createPriceObjects(getCurrentPriceRawData(commaSeparatedCodes));
            model.syncAll(newPriceMetrics);
        } catch (DuplicateCoinException dpe) {
            throw new CommandException("Unexpected code path!");
        } catch (CoinNotFoundException cnfe) {
            throw new AssertionError("The target coin cannot be missing");
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Creates and returns a {@code List<NameValuePair>} with at least two key-value pairs, coin symbols and currency.
     * Additional parameters are optional based on the {@code type}
     *
     * @param commaSeparatedCodes cannot be null
     * @param type                specifies type of data required
     * @return parameters for specified API call
     */
    private List<NameValuePair> buildParams(String commaSeparatedCodes, String type) {
        List<NameValuePair> parameters = new ArrayList<>();
        addBasicNecessaryParams(parameters, commaSeparatedCodes, type);
        addAdditionalParams(parameters, type);
        return parameters;
    }

    /**
     * Add the API parameters to the given list
     *
     * @param params              List of API parameters
     * @param commaSeparatedCodes Coin codes to use
     * @param type                API type to get from
     */
    private void addBasicNecessaryParams(List<NameValuePair> params, String commaSeparatedCodes, String type) {
        switch (type) {
        case HISTORICAL:
            params.add(new BasicNameValuePair(CODE_PARAM, commaSeparatedCodes));
            params.add(new BasicNameValuePair(CURRENCY_PARAM, CURRENCY_TYPE));
            break;
        case CURRENT:
            params.add(new BasicNameValuePair(CODE_PARAM + PLURALIZE, commaSeparatedCodes));
            params.add(new BasicNameValuePair(CURRENCY_PARAM + PLURALIZE, CURRENCY_TYPE));
            break;
        default:
            break;
        }
    }

    /**
     * Adds any additional parameters required for the API call
     *
     * @param params cannot be null
     * @param type   specifies type of data required
     */
    void addAdditionalParams(List<NameValuePair> params, String type) {
        switch (type) {
        case HISTORICAL:
            params.add(new BasicNameValuePair(LIMIT_PARAM, HISTORICAL_DATA_HOURS_LIMIT));
            break;
        default:
            //no additional parameters
        }
    }

    /**
     * Concatenates a list of strings into one with each string separated by a comma
     *
     * @param list of strings to be concatenated
     * @return comma separated string
     */
    private String concatenateByComma(List<String> list) {
        return String.join(",", list);
    }

    /**
     * Adds parameters to the CryptoCompare API URL.
     *
     * @param params cannot be null
     */
    private String buildApiUrl(String url, List<NameValuePair> params) {
        return UrlBuilderUtil.buildUrl(url, params);
    }

    /**
     * Dispatches a {@code LoadingEvent} while waiting for the Response object from the Future object
     *
     * @param promise that returns the desired data
     * @return Response object retrieved from the Future
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private Response waitForResponse(Future<Response> promise) throws InterruptedException, ExecutionException {
        //Set loading UI
        EventsCenter.getInstance().post(new LoadingEvent(true));
        Response response = promise.get();
        //Return UI to normal
        EventsCenter.getInstance().post(new LoadingEvent(false));
        return response;
    }

    /**
     * Fetches the raw data for all codes current price.
     */
    private JsonObject getCurrentPriceRawData(String commaSeparatedCodes) {
        List<NameValuePair> currentPriceParams = buildParams(commaSeparatedCodes, CURRENT);
        JsonElement currentPriceData = getJsonObject(cryptoCompareApiUrl, currentPriceParams).get("RAW");
        if (currentPriceData == null) {
            return new JsonObject();
        } else {
            return currentPriceData.getAsJsonObject();
        }
    }

    /**
     * Gets the specified object from the given web API call
     * @param url         API URL
     * @param priceParams Parameters for API
     */
    private JsonObject getJsonObject(String url, List<NameValuePair> priceParams) {
        try {
            String priceUrl = buildApiUrl(url, priceParams);

            Future<Response> promise = FetchUtil.asyncFetch(priceUrl);
            Response response = waitForResponse(promise);
            return FetchUtil.parseStringToJsonObj(response.getResponseBody());
        } catch (InterruptedException ie) {
            logger.warning("Thread interrupted");
        } catch (ExecutionException ee) {
            logger.warning("Data fetching error");
        }
        return new JsonObject();
    }

    /**
     * Creates and returns a {@code HashMap<String, Price>} of code and price metrics as key-value pairs.
     *
     * @param currentPriceData contains the latest prices of each of the user's coin
     * @return HashMap containing price metrics of each coin retrieval by its code
     */
    private HashMap<String, Price> createPriceObjects(JsonObject currentPriceData) {
        requireAllNonNull(currentPriceData);

        HashMap<String, Price> priceObjs = new HashMap<>();
        List<String> codes = model.getCodeList();

        for (String code : codes) {
            JsonElement coinCurrentPriceMetrics = currentPriceData.get(code);

            if (coinCurrentPriceMetrics == null) {
                continue;
            }

            Price newPrice = new Price();
            newPrice.setCurrent(new Amount(coinCurrentPriceMetrics
                    .getAsJsonObject()
                    .get(CURRENCY_TYPE)
                    .getAsJsonObject()
                    .get("PRICE")
                    .getAsString()));

```
###### \java\seedu\address\model\coin\Coin.java
``` java
    /**
     * Copy constructor with price update.
     * Sets previous state to copied coin.
     */
    public Coin(Coin toCopy, Price newPrice) {
        requireAllNonNull(toCopy);
        this.code = toCopy.code;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(toCopy.getTags());
        this.price = new Price(newPrice);
        this.totalAmountSold = new Amount(toCopy.getTotalAmountSold());
        this.totalAmountBought = new Amount(toCopy.getTotalAmountBought());
        this.totalDollarsSold = new Amount(toCopy.getTotalDollarsSold());
        this.totalDollarsBought = new Amount(toCopy.getTotalDollarsBought());
        prevState = toCopy;
    }
```
###### \java\seedu\address\model\coin\Price.java
``` java
    /**
     * Constructs a {@code Price} with given value.
     */
    public Price(Price toCopy) {
        this.currentPrice = toCopy.currentPrice;
        this.historicalPrices = toCopy.historicalPrices;
        this.historicalTimeStamps = toCopy.historicalTimeStamps;
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
    public void syncAll(HashMap<String, Price> newPriceMetrics)
            throws DuplicateCoinException, CoinNotFoundException {
        requireNonNull(newPriceMetrics);

        for (Coin coin : coins) {
            String code = coin.getCode().toString();
            Price newPrice = newPriceMetrics.get(code);
            if (newPrice == null) {
                continue;
            }
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
    void syncAll(HashMap<String, Price> newPriceMetrics)
            throws DuplicateCoinException, CoinNotFoundException;
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void syncAll(HashMap<String, Price> newPriceMetrics)
            throws DuplicateCoinException, CoinNotFoundException {
        requireNonNull(newPriceMetrics);

        coinBook.syncAll(newPriceMetrics);
        indicateCoinBookChanged();
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
###### \java\seedu\address\ui\CoinCard.java
``` java
    @FXML
    private ImageView icon;
```
###### \java\seedu\address\ui\CoinCard.java
``` java
        icon.setImage(IconUtil.getCoinIcon(coinCode));
```
###### \java\seedu\address\ui\CommandBox.java
``` java
        SuggestionProvider<String> suggestionProvider = SuggestionProvider.create(CommandList.COMMAND_LIST);
        TextFields.bindAutoCompletion(commandTextField, suggestionProvider);
```
###### \java\seedu\address\ui\IconUtil.java
``` java
    public static final String ICON_BASE_FILE_PATH = "/images/coin_icons/";
    public static String getCoinFilePath(String code) {
        return ICON_BASE_FILE_PATH + code + ".png";
    }

    public static Image getCoinIcon(String coinCode) {
        try {
            return AppUtil.getImage(getCoinFilePath(coinCode));
        } catch (NullPointerException e) {
            return AppUtil.getImage(getCoinFilePath("empty"));
        }
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
        toggleLoadingAnimation(isLoading);
    }

    /**
     * Adds or remove the loading animation from {@code coinListPanelPlaceholder}
     * depending on the loading status
     * @param isLoading the loading status of the application
     */
    private void toggleLoadingAnimation(boolean isLoading) {
        Platform.runLater(() -> {
            if (isLoading) {
                activateLoadingAnimation();
            } else {
                deactivateLoadingAnimation();
            }
        });
    }

    private void activateLoadingAnimation() {
        loadingAnimation.setVisible(true);
        coinListPanelPlaceholder.getChildren().add(loadingAnimation);
        setTitle("Syncing...");
    }

    private void deactivateLoadingAnimation() {
        loadingAnimation.setVisible(false);
        coinListPanelPlaceholder.getChildren().remove(loadingAnimation);
        setTitle(config.getAppTitle());
    }

    @Subscribe
    private void handleLoadingEvent(LoadingEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleLoading(event.isLoading);
    }
```
###### \resources\view\CoinListCard.fxml
``` fxml
        <ImageView fx:id="icon" fitHeight="16.0" fitWidth="16.0">
          <Image url="@/images/coin_icons/empty.png" />
        </ImageView>
        <!-- @author -->
        <Label fx:id="code" styleClass="cell_big_label" text="\$first" />
        <Region HBox.hgrow="ALWAYS" />
        <Label fx:id="amount" alignment="CENTER_RIGHT" contentDisplay="TOP" styleClass="cell_big_label" text="\$amount"/>
      </HBox>
      <FlowPane fx:id="tags" />
      <Label fx:id="price" styleClass="cell_small_label" text="\$price" />
    </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
  </GridPane>
</HBox>
```

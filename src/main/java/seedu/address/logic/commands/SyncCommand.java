//@@author laichengyu

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
        JsonObject currentPriceData = getJsonObject(cryptoCompareApiUrl, currentPriceParams);
        return currentPriceData.get("RAW").getAsJsonObject();
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
        return null;
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

            //@@author ewaldhew
            ArrayList<JsonObject> historicalPriceRawData = getHistoricalPriceRawData(code);

            List<Amount> historicalPrices =
                    historicalPriceRawData.stream()
                            .map(obj -> new Amount(obj.get("close").getAsString()))
                            .collect(Collectors.toList());
            List<String> historicalTimes =
                    historicalPriceRawData.stream()
                            .map(obj -> obj.get("time").getAsString())
                            .collect(Collectors.toList());
            newPrice.setHistorical(historicalPrices, historicalTimes);

            priceObjs.put(code, newPrice);
        }
        return priceObjs;
    }

    /**
     * Fetches the raw data for single code historical prices
     */
    private ArrayList<JsonObject> getHistoricalPriceRawData(String code) {
        List<NameValuePair> histoPriceParams = buildParams(code, HISTORICAL);
        JsonElement histoPriceArray = getJsonObject(historicalPriceApiUrl, histoPriceParams).get("Data");
        return new Gson().fromJson(histoPriceArray.toString(), new TypeToken<List<JsonObject>>(){}.getType());
    }

}

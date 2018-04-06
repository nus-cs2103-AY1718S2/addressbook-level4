# laichengyu
###### \java\seedu\address\commons\util\FetchUtil.java
``` java

package seedu.address.commons.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import seedu.address.commons.core.LogsCenter;

/**
 * Retrieves data in JSON format from a specified URL
 */
public class FetchUtil {

    private static final Logger logger = LogsCenter.getLogger(FetchUtil.class);

    /**
     * Retrieves data from the specified url
     * @param url cannot be null
     * @return File converted from the JSON obtained from the url
     */
    public static File fetch(String url) {
        URL urlObject;
        HttpURLConnection urlConnection = null;
        File serverResponse;

        try {
            urlObject = new URL(url);
            urlConnection = (HttpURLConnection) urlObject.openConnection();

            int responseCode = urlConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                serverResponse = readStream(urlConnection.getInputStream());
                logger.info("Fetch request from " + url + " is successful");
                return serverResponse;
            }
        } catch (MalformedURLException e) {
            logger.info("URL: " + url + " provided is malformed");
        } catch (IOException e) {
            logger.info("Invalid URL: " + url + " provided");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return null;
    }

    /**
     * Retrieves data from the specified url and converts it into a JsonObject
     * @param url cannot be null
     * @return JsonObject converted from the File obtained from the url
     */
    public static JsonObject fetchAsJson(String url) {
        File serverResponse = fetch(url);
        JsonObject jsonObject = new JsonObject();

        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(serverResponse));
            jsonObject = jsonElement.getAsJsonObject();
        } catch (FileNotFoundException e) {
            logger.info("File retrieved from URL not found");
        }

        return jsonObject;
    }

    /**
     * Reads the inputStream and outputs to a temporary file location
     * @param inputStream cannot be null
     * @return File read from inputStream
     */
    private static File readStream(InputStream inputStream) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        File serverData = new File("temp.json");
        //Do we want to keep the data of API calls? If yes, overwrite or make new copies?

        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            FileUtil.writeToFile(serverData, response.toString());
            return serverData;
        } catch (IOException e) {
            logger.info("There was an error reading the server data: " + e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.info("There was an error while closing connection with remote server: " + e);
                }
            }
        }

        return null;
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
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.JsonObject;

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

    private String cryptoCompareApiUrl = "https://min-api.cryptocompare.com/data/pricemulti";

    /**
     * Creates and returns a {@code List<NameValuePair>} with two key-value pairs, coin symbols and currency.
     *
     * @param coinList cannot be null
     * @return parameters for CryptoCompare API call
     */
    private List<NameValuePair> getParams(Set<String> coinList) {
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
            JsonObject newData = FetchUtil.fetchAsJson(cryptoCompareApiUrl);
            model.syncAll(newData);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (DuplicateCoinException dpe) {
            throw new CommandException("Unexpected code path!");
        } catch (CoinNotFoundException cnfe) {
            throw new AssertionError("The target coin cannot be missing");
        }
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
            double newPrice = newData.get(code).getAsJsonObject().get("USD").getAsDouble();
            Coin updatedCoin = new Coin(coin, newPrice);
            updateCoin(coin, updatedCoin);
        }
    }
```
###### \java\seedu\address\model\CoinBook.java
``` java
    @Override
    public Set<String> getCodeList() {
        return Collections.unmodifiableSet(codes);
    }
```
###### \java\seedu\address\model\Model.java
``` java
    /** Returns an unmodifiable view of the code list */
    Set<String> getCodeList();

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
    public Set<String> getCodeList() {
        return coinBook.getCodeList();
    }
```
###### \java\seedu\address\model\ReadOnlyCoinBook.java
``` java
    /**
     * Returns an unmodifiable view of the codes list.
     * This list will not contain any duplicate codes.
     */
    Set<String> getCodeList();
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

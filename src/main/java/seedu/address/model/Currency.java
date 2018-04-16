package seedu.address.model;

import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import seedu.address.commons.util.JsonUtil;
//@@author jill858
/**
 * Represent currencies and its rate
 */
public class Currency {

    private static final String API_PROVIDIER = "http://api.fixer.io/latest?base=";

    private String base;
    private String date;

    private Map<String, String> rates = new TreeMap<String, String>();
    private List<String> currencyCodes = Arrays.asList("SGD",
            "AUD", "CAD", "CHF", "CNY", "GBP",
            "HKD",  "ILS", "INR", "JPY", "MYR",
            "NZD", "PHP", "SEK", "THB", "TWD",
            "USD");

    public Currency() {
        this.date = "2018-04-13";
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, String> getRates() {
        return rates;
    }

    public void setRates(Map<String, String> rates) {
        this.rates = rates;
    }

    /**
     * Convert from source currency to the the destination currency given the value
     * Uses API:Fixer to get rates instantly
     * If API is unavailable, rates given are fixed by developer
     *
     * @param fromCurrencyCode Source currency code
     * @param toCurrencyCode Destination currency code
     * @param value Value to be converted
     * @return value in terms of the destination currency code
     */
    public double convert(String fromCurrencyCode, String toCurrencyCode, double value) {
        double convertedValue;

        Currency currencyResponse = getResponse(API_PROVIDIER + fromCurrencyCode);

        try {
            String rate = currencyResponse.getRates().get(toCurrencyCode);

            double conversionRate = Double.valueOf(rate);

            convertedValue = conversionRate * value;

            this.date = currencyResponse.getDate();

            return convertedValue;

        } catch (NullPointerException npe) {
            return convertByFixedValue(fromCurrencyCode, toCurrencyCode, value);
        }

    }

    /**
     * Convert from source currency to the destination currency given the value
     * Rates are fixed
     * @param fromCurrencyCode Source currency code
     * @param toCurrencyCode Destination currency code
     * @param value Value to be converted
     * @return
     */
    public double convertByFixedValue(String fromCurrencyCode, String toCurrencyCode, double value) {
        double convertedValue;

        if (fromCurrencyCode.equals("SGD")) {
            //rate is already in the form of base rate
            //therefore multiply to the rate to be converted
            double rate = getCurrencyRate(toCurrencyCode);

            convertedValue = value * rate;
        } else {
            //convert the value to the base rate (SGD)
            //followed by multiplying the rate to be converted
            double fromRate = getCurrencyRate(fromCurrencyCode);
            double toRate = getCurrencyRate(toCurrencyCode);

            double toSgd = value / fromRate;
            convertedValue = toSgd * toRate;
        }

        return convertedValue;
    }

    /**
     * Response from the API
     * @param strUrl API URL string
     * @return Currency object
     */
    private static Currency getResponse(String strUrl) {

        Currency currencyResponse = null;

        JsonUtil json = new JsonUtil();
        StringBuffer sb = new StringBuffer();

        if (strUrl == null || strUrl.isEmpty()) {
            return null;
        }

        URL url;
        try {
            url = new URL(strUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream stream = connection.getInputStream();

            int data = stream.read();

            while (data != -1) {

                sb.append((char) data);

                data = stream.read();
            }

            stream.close();

            currencyResponse = json.fromJsonString(sb.toString(), Currency.class);

        } catch (MalformedURLException e) {
            return null;

        } catch (IOException e) {
            return null;
        }

        return currencyResponse;
    }

    /**
     * Check the currency code given contains in the list of currency codes
     * @param currencyCode
     * @return true is a given currency code is found in the list
     */
    public boolean containsCurrencyCode(String currencyCode) {
        boolean isCurrencyCode = currencyCodes.stream().anyMatch(code -> code.equals(currencyCode));

        return isCurrencyCode;
    }

    /**
     * Retrieve the rate of the currency given the currency code
     * The default based rate is in SGD (Singapore Dollar)
     * Rate as of 13/04/2018
     *
     * @param currencyCode
     * @return rate of the currency code
     */
    public double getCurrencyRate(String currencyCode) {

        switch (currencyCode) {

        //Singapore Dollar
        case "SGD":
            return 1.000;

        //Australian dollar
        case "AUD":
            return 0.977;

        //Canadian dollar
        case "CAD":
            return 0.958;

        //Swiss franc
        case "CHF":
            return 0.733;

        //Renminbi (Chinese) yuan
        case "CNY":
            return 4.787;

        //Pound sterling
        case "GBP":
            return 0.534;

        //Hong Kong dollar
        case "HKD":
            return 5.983;

        //Israeli new shekel
        case "ILS":
            return 2.680;

        //Indian rupee
        case "INR":
            return 49.707;

        //Japanese yen
        case "JPY":
            return 82.089;

        //Malaysian ringgit
        case "MYR":
            return 2.953;

        //New Zealand dollar
        case "NZD":
            return 1.033;

        //Philippine piso
        case "PHP":
            return 39.590;

        //Swedish krona/kronor
        case "SEK":
            return 6.424;

        //Thai baht
        case "THB":
            return 23.745;

        //New Taiwan dollar
        case "TWD":
            return 22.285;

        //United States dollar
        case "USD":
            return 0.762;

        default: return 1;
        }
    }
}

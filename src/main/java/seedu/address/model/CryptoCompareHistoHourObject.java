package seedu.address.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Stores the data for a single API return object for historical data, obtained from
 * the <a href="https:/min-api.cryptocompare.com">CryptoCompare API</a>.
 */
public class CryptoCompareHistoHourObject {

    /*
    API Return format:
    {
        "Response":"Success",
        "Type":100,
        "Aggregated":false,
        "Data":[
            {
                "time":1452592800,
                "close":0.00258,
                "high":0.002629,
                "low":0.002412,
                "open":0.002591,
                "volumefrom":160178.74,
                "volumeto":403.76
            }, { ... } ...
        ],
        "TimeTo":1452679200,
        "TimeFrom":1452592800,
        "FirstValueInArray":true,
        "ConversionType": { "type":"direct", "conversionSymbol":"" }
    }
    */

    /**
     * Inner class for the data points
     */
    private static class CDataObject {
        private long time;
        private double close;
        private double high;
        private double low;
        private double open;
        private double volumefrom;
        private double volumeto;
    }
    private CDataObject[] Data;
    private long TimeTo;
    private long TimeFrom;

    /**
     * Get the timestamp data
     * @return x-Axis data
     */
    public ArrayList<Date> getTimeAxisData() {
        ArrayList<Date> result = new ArrayList<>();

        for (CDataObject dataObject : Data) {
            result.add(new Date(dataObject.time));
        }

        return result;
    }

    /**
     * Get the price history (per day open) data
     * @return y-Axis data
     */
    public ArrayList<Double> getPriceHistoryData() {
        ArrayList<Double> result = new ArrayList<>();

        for (CDataObject dataObject : Data) {
            result.add(dataObject.open);
        }

        return result;
    }
}

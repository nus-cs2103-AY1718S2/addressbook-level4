//@@author ewaldhew
package seedu.address.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CoinPanelSelectionChangedEvent;
import seedu.address.model.coin.Amount;

/**
 * The charts panel used to display graphs
 */
public class ChartsPanel extends UiPart<Region> {

    public static final String FXML = "ChartsPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(ChartsPanel.class);

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private LineChart<String, Double> priceChart;

    public ChartsPanel() {
        super(FXML);

        registerAsAnEventHandler(this);
    }

    /**
     * Adds a new plot to the graph via a coin price
     * @param xAxis
     * @param yAxis
     */
    private void addPlot(List<String> xAxis, List<Amount> yAxis) {
        ArrayList<Date> dateList = new ArrayList<>(
                xAxis.stream()
                .map(str -> new Date(parseTimeStamp(str)))
                .collect(Collectors.toList()));
        ArrayList<Double> priceList = new ArrayList<>(
                yAxis.stream()
                .map(amount -> Double.valueOf(amount.toString()))
                .collect(Collectors.toList()));

        addPlot(dateList, priceList);
    }

    /**
     * Adds a new plot to the graph
     */
    private void addPlot(ArrayList<Date> xAxis, ArrayList<Double> yAxis) {
        Series<String, Double> dataSeries = new Series<>();
        populateData(dataSeries, xAxis, yAxis);

        priceChart.getData().add(dataSeries);
        priceChart.setCreateSymbols(false);

        if (!xAxis.isEmpty()) {
            calibrateRange(Collections.min(yAxis), Collections.max(yAxis), 5);
        }
    }

    private long parseTimeStamp(String s) {
        return Long.valueOf(s + "000");
    }

    /**
     * Sets nice values for the chart axis scaling
     */
    private void calibrateRange(double min, double max, int steps) {
        this.yAxis.setLowerBound(min);
        this.yAxis.setUpperBound(max);
        this.yAxis.setTickUnit((max - min) / (double) steps);
    }

    /**
     * Adds the data from the provided lists to the data series
     * @param dataSeries
     * @param xAxis
     * @param yAxis
     */
    private void populateData(Series<String, Double> dataSeries, ArrayList<Date> xAxis, ArrayList<Double> yAxis) {
        assert (xAxis.size() == yAxis.size());
        for (int i = 0; i < xAxis.size(); i++) {
            final String date = new SimpleDateFormat("dd MMM, HHmm").format(xAxis.get(i));
            dataSeries.getData().add(new Data<>(date, yAxis.get(i)));
        }
    }

    private void clearData() {
        priceChart.getData().clear();
    }

    @Subscribe
    private void handleCoinPanelSelectionChangedEvent(CoinPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        clearData();
        addPlot(event.getNewSelection().coin.getPrice().getHistoricalTimeStamps(),
                event.getNewSelection().coin.getPrice().getHistoricalPrices());
    }
}

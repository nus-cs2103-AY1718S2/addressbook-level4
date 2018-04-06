//@@author ewaldhew
package seedu.address.ui;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.Region;

/**
 * The charts panel used to display graphs
 */
public class ChartsPanel extends UiPart<Region> {

    public static final String FXML = "ChartsPanel.fxml";

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private LineChart<String, Double> priceChart;

    private final ArrayList<Date> testDataX = new ArrayList<>(Arrays.asList(
            new Date(1452592800000L),
            new Date(1452596400000L),
            new Date(1452600000000L),
            new Date(1452603600000L),
            new Date(1452607200000L),
            new Date(1452610800000L),
            new Date(1452614400000L),
            new Date(1452618000000L),
            new Date(1452621600000L)
    ));
    private final ArrayList<Double> testDataY = new ArrayList<>(Arrays.asList(
            0.002591,
            0.002580,
            0.002617,
            0.002563,
            0.002597,
            0.002576,
            0.002555,
            0.002575,
            0.002719
    ));

    public ChartsPanel() {
        super(FXML);
        addPlot(testDataX, testDataY);
    }

    /**
     * Add a new plot to the graph
     */
    private void addPlot(ArrayList<Date> xAxis, ArrayList<Double> yAxis) {
        Series<String, Double> dataSeries = new Series<>();
        dataSeries.setName("Price History Series");
        populateData(dataSeries, xAxis, yAxis);

        priceChart.getData().add(dataSeries);
        priceChart.setCreateSymbols(false);
    }

    /**
     * Add the data from the provided lists to the data series
     * @param dataSeries
     * @param xAxis
     * @param yAxis
     */
    private void populateData(Series<String, Double> dataSeries, ArrayList<Date> xAxis, ArrayList<Double> yAxis) {
        assert (xAxis.size() == yAxis.size());
        for (int i = 0; i < xAxis.size(); i++) {
            final String date = DateFormat.getInstance().format(xAxis.get(i));
            dataSeries.getData().add(new Data<>(date, yAxis.get(i)));
        }
    }
}

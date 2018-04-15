// @@author anh2111
package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.report.Proportion;
import seedu.address.model.report.Report;

/**
 * The Report Panel of the App.
 */
public class ReportPanel extends UiPart<Region> {
    private static final String FXML = "ReportPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private static final Double CHART_BAR_GAP = 0.1;

    private Report pReport;
    private ObservableList<Report> reportHistory;

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart barChart;

    @FXML
    private ListView<ReportCard> reportListView;

    public ReportPanel(Report report, ObservableList<Report> reportHistory) {
        super(FXML);
        pReport = report;
        this.reportHistory = reportHistory;

        setupPieChart();
        setupBarChart();
        setupHistoryTable();
    }

    private void setupPieChart() {
        pieChart.setTitle("In " + pReport.getTotalTags() + " Tags");
        pieChart.setData(tabulateData());
        pieChart.setLabelsVisible(false);
    }

    private void setupBarChart() {
        String paneTitle =  "Statistics of #" + pReport.getPopulation().tagName + " candidates\n\n";
        String barChartTitle = "In " + pReport.getTotalPersons() + " candidates";
        int indentation = Math.max((int) ((paneTitle.length() - barChartTitle.length()) * 0.8), 0);
        String spacesInTitle = new String(new char[indentation]).replace('\0', ' ');
        barChart.setTitle(paneTitle + spacesInTitle + barChartTitle);
        barChart.setData(tabulateBarChartData());
        barChart.setBarGap(CHART_BAR_GAP);
    }

    private void setupHistoryTable() {
        ObservableList<ReportCard> mappedList = EasyBind.map(
                reportHistory, (p) -> new ReportCard(p));
        reportListView.setItems(mappedList);
        reportListView.setCellFactory(listView -> new ReportPanel.ReportListViewCell());
    }

    /**
     * Formats the data into PieChart.Data for display
     */
    private ObservableList<PieChart.Data> tabulateData() {
        ArrayList<PieChart.Data> data = new ArrayList<>();
        int count = 0;

        for (Proportion p : pReport.getAllProportions()) {
            count++;
            int percent = (int) ((p.value * 100.0f) / pReport.getTotalTags());
            data.add(new PieChart.Data(count + ". " + p.tagName + " " + percent + "%", p.value));
        }
        return FXCollections.observableArrayList(data);
    }

    /**
     * Formats the data into BarChart.Data for display
     */
    private ObservableList<XYChart.Series<String, Integer>> tabulateBarChartData() {
        ObservableList<XYChart.Series<String, Integer>> data = FXCollections.observableArrayList();
        XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
        series.setName("Number of Candidates Having The Tag");

        for (Proportion p : pReport.getAllProportions()) {
            series.getData().add(new XYChart.Data(p.tagName, new Integer(p.totalPersons)));
        }
        data.addAll(series);

        return data;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ReportCard}.
     */
    class ReportListViewCell extends ListCell<ReportCard> {

        @Override
        protected void updateItem(ReportCard r, boolean empty) {
            super.updateItem(r, empty);

            if (empty || r == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(r.getRoot());
            }
        }
    }
}
// @@author

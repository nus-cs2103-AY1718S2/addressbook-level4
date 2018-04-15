package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.chart.LineChart;

/**
 * A handler for the {@code ChartsPanel} of the UI.
 */
public class ChartsPanelHandle extends NodeHandle<Node> {

    public static final String CHARTS_ID = "#priceChart";

    private LineChart<String, Double> chart;

    public ChartsPanelHandle(Node chartsPanelNode) {
        super(chartsPanelNode);

        chart = getChildNode(CHARTS_ID);
    }

    public boolean isEmpty() {
        return chart.getData().get(0).getData().size() == 0;
    }
}

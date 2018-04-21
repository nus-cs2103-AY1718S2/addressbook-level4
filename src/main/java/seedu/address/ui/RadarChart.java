package seedu.address.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import seedu.address.commons.util.UiUtil;

/**
 * Customized radar chart to represent single-axis data graphically
 */
public class RadarChart extends UiPart<Region> {

    public static final double MAX_ANIMATION_TIME_MS = 600;

    private static final String FXML = "RadarChart.fxml";

    private static final double LINE_RADAR_BORDER = 1;
    private static final double LINE_RADAR_AXIS = 0.5;
    private static final double LINE_POLYGON_BORDER = 1.5;
    private static final double SIZE_LABEL = 11;
    private static final Color COLOR_RADAR_BORDER = Color.web("#BDBDBD");
    private static final Color COLOR_RADAR_AXIS = Color.web("#BDBDBD");
    private static final Color COLOR_POLYGON_BORDER = Color.web("#2196F3");
    private static final Color COLOR_POLYGON_FILL = Color.web("#2196F360");
    private static final Color COLOR_LABEL = Color.web("#757575");

    private static final int MIN_WIDTH = 220;
    private static final int MIN_HEIGHT = 150;

    // List to keep track of all started animation
    private ArrayList<Timeline> allTimeline = new ArrayList<>();
    private LinkedHashMap<String, Double> animatedData = new LinkedHashMap<>();
    private double maxValue;
    private boolean animated;

    // Canvas info
    private GraphicsContext context;
    private double width;
    private double height;
    private double centerX;
    private double centerY;

    @FXML
    private VBox radarChart;

    /**
     * Initiate a radar chart panel interface
     * @param maxValue of the data
     * @param animated If the radar chart should be animated
     */
    public RadarChart(double maxValue, boolean animated) {
        super(FXML);
        this.maxValue = maxValue;
        this.animated = animated;

        Canvas canvas = new Canvas(MIN_WIDTH, MIN_HEIGHT);
        context = canvas.getGraphicsContext2D();
        radarChart.getChildren().add(canvas);

        width = MIN_WIDTH;
        height = MIN_HEIGHT;
        centerX = width / 2.0;
        centerY = height / 2.0;
    }

    /**
     * Set the radar chart data and initiate the animation
     * @param data for the radar chart
     */
    public void setData(LinkedHashMap<String, Double> data) {
        // Stop previously started animation
        allTimeline.forEach(Timeline::pause);
        allTimeline.clear();

        if (animated) {
            for (Map.Entry<String, Double> entry : data.entrySet()) {
                double rateValue = entry.getValue();
                DoubleProperty value = new SimpleDoubleProperty(0);
                animatedData.put(entry.getKey(), 0.0);

                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.millis((rateValue / maxValue) * MAX_ANIMATION_TIME_MS),
                                new KeyValue(value, rateValue, UiUtil.EASE_OUT_CUBIC))
                );
                timeline.setAutoReverse(false);
                timeline.setCycleCount(1);
                timeline.play();

                value.addListener((observable, oldValue, newValue) -> {
                    animatedData.put(entry.getKey(), newValue.doubleValue());
                    render();
                });

                allTimeline.add(timeline);
            }
        } else {
            animatedData = data;
        }

        render();
    }

    /**
     * Render the chart with the data provided from {@link RadarChart#setData}
     */
    private void render() {
        // Default items
        long numItems = animatedData.size();
        long max = (long) Math.ceil(maxValue);
        double shortEdge = Math.min(width, height);
        double spacing = Math.ceil(shortEdge / ((double) max * 3.0));
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;
        Point2D point;
        long i;

        // Clear canvas
        context.clearRect(0, 0, width, height);
        context.setLineWidth(1);

        // Draw radar border
        context.setLineWidth(LINE_RADAR_BORDER);
        context.setStroke(COLOR_RADAR_BORDER);

        for (i = 1; i <= max; i++) {
            context.beginPath();
            context.moveTo(centerX + (spacing * (double) i), centerY);

            for (long j = 1; j <= numItems; j++) {
                point = getPoint(spacing, j, i);
                double x = point.getX();
                double y = point.getY();

                context.lineTo(x, y);
                minY = Math.min(minY, y);
                maxY = Math.max(maxY, y);
            }

            context.stroke();

            // Add axis value
            //context.fillText(String.valueOf(i), (width / 2) + (spacing * i) + 4, height / 2 - 2);
        }

        // Draw line from origin
        context.setLineWidth(LINE_RADAR_AXIS);
        context.setStroke(COLOR_RADAR_AXIS);

        for (i = 0; i < numItems; i++) {
            context.beginPath();
            context.moveTo(centerX, centerY);

            point = getPoint(spacing, i, max);
            double x = point.getX();
            double y = point.getY();

            context.lineTo(x, y);
            context.stroke();
        }

        // Draw polygon
        context.setLineWidth(LINE_POLYGON_BORDER);
        context.setStroke(COLOR_POLYGON_BORDER);
        context.setFill(COLOR_POLYGON_FILL);
        context.beginPath();

        boolean first = true;
        double startX = 0;
        double startY = 0;

        i = 0;

        for (Map.Entry<String, Double> entry : animatedData.entrySet()) {
            double value = entry.getValue();

            if (first) {
                startX = centerX + spacing * value;
                startY = centerY;
                context.moveTo(startX, startY);
                first = false;
            }

            point = getPoint(spacing, i, value);
            double x = point.getX();
            double y = point.getY();

            context.lineTo(x, y);
            i++;
        }

        context.lineTo(startX, startY);
        context.stroke();
        context.fill();

        // Draw label
        URL fontUrl = getClass().getResource("/fonts/Roboto-Regular.ttf");
        context.setFill(COLOR_LABEL);
        if (fontUrl != null) {
            context.setFont(Font.loadFont(fontUrl.toString(), SIZE_LABEL));
        } else {
            context.setFont(Font.font("Roboto", SIZE_LABEL));
        }

        i = 0;

        for (Map.Entry<String, Double> entry : animatedData.entrySet()) {
            point = getPoint(spacing, i, max);
            double textX = point.getX();
            double textY = point.getY();

            if (maxY - textY < 1e-3) {
                textY += (spacing / 3.0);
                context.setTextAlign(TextAlignment.CENTER);
                context.setTextBaseline(VPos.TOP);
            } else if (minY - textY > -1e-3) {
                textY -= (spacing / 3.0);
                context.setTextAlign(TextAlignment.CENTER);
                context.setTextBaseline(VPos.BOTTOM);
            } else if (textX < width / 2.0) {
                textX -= (spacing / 2.0);
                context.setTextAlign(TextAlignment.RIGHT);
                context.setTextBaseline(VPos.CENTER);
            } else {
                textX += (spacing / 2.0);
                context.setTextAlign(TextAlignment.LEFT);
                context.setTextBaseline(VPos.CENTER);
            }

            context.fillText(entry.getKey(), textX, textY);
            i++;
        }
    }

    /**
     * Get the correct point in the graph
     * @param spacing between each step of value
     * @param index of the value
     * @param value of the item
     * @return the {@code Point2D} representation of the point
     */
    private Point2D getPoint(double spacing, double index, double value) {
        double numItems = animatedData.size();

        double x = centerX + Math.cos((Math.PI * 2.0) * (index / numItems)) * spacing * value;
        double y = centerY + Math.sin((Math.PI * 2.0) * (index / numItems)) * spacing * value;
        return new Point2D(x, y);
    }
}

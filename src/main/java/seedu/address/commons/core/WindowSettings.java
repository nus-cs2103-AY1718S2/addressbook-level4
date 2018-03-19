package seedu.address.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the window settings.
 */
public class WindowSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_WIDTH = 800;

    private final Double windowWidth;
    private final Double windowHeight;
    private final Point windowCoordinates;

    public WindowSettings() {
        this.windowWidth = DEFAULT_WIDTH;
        this.windowHeight = DEFAULT_HEIGHT;
        this.windowCoordinates = null; // null represent no coordinates
    }

    public WindowSettings(Double windowWidth, Double windowHeight, int xPosition, int yPosition) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.windowCoordinates = new Point(xPosition, yPosition);
    }

    public Double getWindowWidth() {
        return windowWidth;
    }

    public Double getWindowHeight() {
        return windowHeight;
    }

    public Point getWindowCoordinates() {
        return windowCoordinates;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof WindowSettings)) { //this handles null as well.
            return false;
        }

        WindowSettings o = (WindowSettings) other;

        return Objects.equals(windowWidth, o.windowWidth)
                && Objects.equals(windowHeight, o.windowHeight)
                && Objects.equals(windowCoordinates, o.windowCoordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowHeight, windowCoordinates);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Width : ").append(windowWidth);
        sb.append("\nHeight : ").append(windowHeight);
        sb.append("\nPosition : ").append(windowCoordinates);
        return sb.toString();
    }
}

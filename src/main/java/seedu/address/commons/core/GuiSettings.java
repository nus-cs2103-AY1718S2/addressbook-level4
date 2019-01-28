package seedu.address.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the GUI settings.
 */
public class GuiSettings implements Serializable {

    public static final double DEFAULT_WIDTH = 1732;
    public static final double DEFAULT_HEIGHT = 1080;
    private static final String DEFAULT_THEME = "/view/LightTheme.css";

    private Double windowWidth;
    private Double windowHeight;
    private Point windowCoordinates;
    private String currentTheme;

    public GuiSettings() {
        this.windowWidth = DEFAULT_WIDTH;
        this.windowHeight = DEFAULT_HEIGHT;
        this.windowCoordinates = null; // null represent no coordinates
        currentTheme = DEFAULT_THEME;
    }

    public GuiSettings(Double windowWidth, Double windowHeight, int xPosition, int yPosition, String currentTheme) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.windowCoordinates = new Point(xPosition, yPosition);
        this.currentTheme = currentTheme;
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

    public String getCurrentTheme() {
        return currentTheme;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GuiSettings)) { //this handles null as well.
            return false;
        }

        GuiSettings o = (GuiSettings) other;

        return Objects.equals(windowWidth, o.windowWidth)
                && Objects.equals(windowHeight, o.windowHeight)
                && Objects.equals(windowCoordinates.x, o.windowCoordinates.x)
                && Objects.equals(windowCoordinates.y, o.windowCoordinates.y)
                && Objects.equals(currentTheme, o.currentTheme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowHeight, windowCoordinates, currentTheme);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Width : " + windowWidth + "\n");
        sb.append("Height : " + windowHeight + "\n");
        sb.append("Position : " + windowCoordinates);
        sb.append("Current theme : " + currentTheme);
        return sb.toString();
    }
}

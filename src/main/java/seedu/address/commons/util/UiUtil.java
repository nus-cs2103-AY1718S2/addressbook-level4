package seedu.address.commons.util;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

import javafx.scene.paint.Color;

//@@author Ang-YC
/**
 * Helper functions for handling UI information
 */
public class UiUtil {
    /**
     * Convert double into string with {@code points} amount of decimal places
     * @param decimal The double to be formatted
     * @param points Number of decimal places
     * @return the formatted string with {@code points} number of decimal places
     */
    public static String toFixed(double decimal, int points) {
        return toFixed(String.valueOf(decimal), points);
    }

    /**
     * Convert string representation of decimal into string with {@code points} amount of decimal places
     * @param decimal The string representation of decimal to be formatted
     * @param points Number of decimal places
     * @return the formatted string with {@code points} number of decimal places
     */
    public static String toFixed(String decimal, int points) {
        double value = Double.parseDouble(decimal);
        String pattern = "0";

        if (points > 0) {
            pattern += ".";
            pattern += StringUtils.repeat("0", points);
        }

        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value);
    }

    /**
     * Convert JavaFX color into web hex color
     * @param color to be converted
     * @return the web hex String representation of the color
     */
    public static String colorToHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
    }
}

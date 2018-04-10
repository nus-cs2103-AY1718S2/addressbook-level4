package seedu.address.commons.util;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.util.Duration;

//@@author Ang-YC
/**
 * Helper functions for handling UI information
 */
public class UiUtil {
    public static final Interpolator EASE_OUT_CUBIC = Interpolator.SPLINE(0.215, 0.61, 0.355, 1);
    private static final String FORMAT_DATE = "d MMM y";
    private static final String FORMAT_TIME = "hh:mm:ssa";

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

    /**
     * Fade in or fade out the node, then callback
     * @param node to be faded in or out
     * @param fadeIn If set, the fade will be fade in, otherwise it will be fade out
     * @param from The opacity to start fading from
     * @param maxDuration of the transition should be
     * @param callback after the transition is done
     * @return the {@code Animation} of the transition
     */
    public static Animation fadeNode(Node node, boolean fadeIn, double from,
                                     double maxDuration, EventHandler<ActionEvent> callback) {
        Interpolator easing = fadeIn ? Interpolator.EASE_IN : Interpolator.EASE_OUT;
        double to = fadeIn ? 1 : 0;
        double duration = Math.max(1, Math.abs(from - to) * maxDuration);

        FadeTransition fade = new FadeTransition(Duration.millis(duration), node);
        fade.setFromValue(from);
        fade.setToValue(to);
        fade.setCycleCount(1);
        fade.setAutoReverse(false);
        fade.setInterpolator(easing);
        fade.setOnFinished(event -> {
            if (Math.abs(node.getOpacity() - to) < 1e-3) {
                callback.handle(event);
            }
        });

        return fade;
    }

    /**
     * Fade in or fade out the node, then callback
     * @param node to be faded in or out
     * @param fadeIn If set, the fade will be fade in, otherwise it will be fade out
     * @param maxDuration of the transition should be
     * @param callback after the transition is done
     * @return the {@code Animation} of the transition
     */
    public static Animation fadeNode(Node node, boolean fadeIn,
                                     double maxDuration, EventHandler<ActionEvent> callback) {
        double from = node.getOpacity();
        return fadeNode(node, fadeIn, from, maxDuration, callback);
    }

    /**
     * Format date time to more readable format
     * @param dateTime to be formatted
     * @return the formatted date time
     */
    public static String formatDate(LocalDateTime dateTime) {
        String date = DateTimeFormatter.ofPattern(FORMAT_DATE, Locale.ENGLISH).format(dateTime);
        String time = DateTimeFormatter.ofPattern(FORMAT_TIME, Locale.ENGLISH).format(dateTime).toLowerCase();
        return date + " " + time;
    }
}

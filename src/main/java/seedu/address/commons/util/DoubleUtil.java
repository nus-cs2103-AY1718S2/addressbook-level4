package seedu.address.commons.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Helper functions for handling doubles.
 */
public class DoubleUtil {

    /**
     * Returns a double rounded to two decimal places.
     * Note: Whole numbers will round to one decimal place
     */
    public static double roundToTwoDecimalPlaces(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}

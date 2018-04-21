package seedu.address.commons.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author kexiaowen
public class DoubleUtilTest {

    @Test
    public void roundToTwoDecimalPlaces_inputDouble() {
        assertTrue(DoubleUtil.roundToTwoDecimalPlaces(1) == 1.0);
        assertTrue(DoubleUtil.roundToTwoDecimalPlaces(1.3424323423424) == 1.34);
        assertTrue(DoubleUtil.roundToTwoDecimalPlaces(4242.3351231231) == 4242.34);
        assertTrue(DoubleUtil.roundToTwoDecimalPlaces(
                3.345331231234444444232322325898788765767645436658689797676547587698) == 3.35);
    }

    @Test
    public void roundToTwoDecimalPlaces_inputString() {
        assertTrue(DoubleUtil.roundToTwoDecimalPlaces("1") == 1.0);
        assertTrue(DoubleUtil.roundToTwoDecimalPlaces("1.3424323423424") == 1.34);
        assertTrue(DoubleUtil.roundToTwoDecimalPlaces("4242.3351231231") == 4242.34);
        assertTrue(DoubleUtil.roundToTwoDecimalPlaces(
                "3.345331231234444444232322325898788765767645436658689797676547587698") == 3.35);
    }
}

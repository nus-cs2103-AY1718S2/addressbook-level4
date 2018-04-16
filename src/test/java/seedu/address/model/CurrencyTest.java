package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author jill858
public class CurrencyTest {

    private Currency currency = new Currency();

    @Test
    public void execute_convertByFixedValue_validOutput() {
        double baseRate = 1.00;

        //convert 1 SGD to USD
        double expectedRate = 0.762;
        double actualRate = currency.convertByFixedValue("SGD", "USD", baseRate);
        assertEquals(expectedRate, actualRate, 0);

        //convert 1 SGD to SGD
        expectedRate = 1.000;
        actualRate = currency.convertByFixedValue("SGD", "SGD", baseRate);
        assertEquals(expectedRate, actualRate, 0);

        //convert 1 SGD to AUD
        expectedRate = 0.977;
        actualRate = currency.convertByFixedValue("SGD", "AUD", baseRate);
        assertEquals(expectedRate, actualRate, 0);

        //convert 1 SGD to CAD
        expectedRate = 0.958;
        actualRate = currency.convertByFixedValue("SGD", "CAD", baseRate);
        assertEquals(expectedRate, actualRate, 0);

        //convert 1 SGD to CHF
        expectedRate = 0.733;
        actualRate = currency.convertByFixedValue("SGD", "CHF", baseRate);
        assertEquals(expectedRate, actualRate, 0);

        //convert 1 SGD to CNY
        expectedRate = 4.787;
        actualRate = currency.convertByFixedValue("SGD", "CNY", baseRate);
        assertEquals(expectedRate, actualRate, 0);

        //convert 1 SGD to GBP
        expectedRate = 0.534;
        actualRate = currency.convertByFixedValue("SGD", "GBP", baseRate);
        assertEquals(expectedRate, actualRate, 0);

        //convert 1 SGD to HKD
        expectedRate = 5.983;
        actualRate = currency.convertByFixedValue("SGD", "HKD", baseRate);
        assertEquals(expectedRate, actualRate, 0);

        //convert 1 SGD to ILS
        expectedRate = 2.680;
        actualRate = currency.convertByFixedValue("SGD", "ILS", baseRate);
        assertEquals(expectedRate, actualRate, 0);

        //convert 1 SGD to INR
        expectedRate = 49.707;
        actualRate = currency.convertByFixedValue("SGD", "INR", baseRate);
        assertEquals(expectedRate, actualRate, 0);

        //convert 1 SGD to JPY
        expectedRate = 82.089;
        actualRate = currency.convertByFixedValue("SGD", "JPY", baseRate);
        assertEquals(expectedRate, actualRate, 0);

        //convert 1 SGD to MYR
        expectedRate = 2.953;
        actualRate = currency.convertByFixedValue("SGD", "MYR", baseRate);
        assertEquals(expectedRate, actualRate, 0);

        //convert 1 SGD to NZD
        expectedRate = 1.033;
        actualRate = currency.convertByFixedValue("SGD", "NZD", baseRate);
        assertEquals(expectedRate, actualRate, 0);

        //convert 1 SGD to PHP
        expectedRate = 39.590;
        actualRate = currency.convertByFixedValue("SGD", "PHP", baseRate);
        assertEquals(expectedRate, actualRate, 0);

        //convert 1 SGD to SEK
        expectedRate = 6.424;
        actualRate = currency.convertByFixedValue("SGD", "SEK", baseRate);
        assertEquals(expectedRate, actualRate, 0);

        //convert 1 SGD to THB
        expectedRate = 23.745;
        actualRate = currency.convertByFixedValue("SGD", "THB", baseRate);
        assertEquals(expectedRate, actualRate, 0);

        //convert 1 SGD to TWD
        expectedRate = 22.285;
        actualRate = currency.convertByFixedValue("SGD", "TWD", baseRate);
        assertEquals(expectedRate, actualRate, 0);
    }

    @Test
    public void execute_containsCurrencyCode_success() {
        assertTrue(currency.containsCurrencyCode("USD"));
    }

    @Test
    public void execute_containsCurrencyCode_failure() {
        //empty argument
        assertFalse(currency.containsCurrencyCode(""));
        assertFalse(currency.containsCurrencyCode(" "));

        //invalid currency code
        assertFalse(currency.containsCurrencyCode("US"));


    }
}

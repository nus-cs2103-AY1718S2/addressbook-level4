package seedu.address.model.coin;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.CoinBuilder;

public class CoinTest {

    @Test
    public void constructor_deepCopy() {
        Coin originalCoin = new CoinBuilder().build();
        Coin copiedCoin = new Coin(originalCoin);

        assertTrue(originalCoin.equals(copiedCoin));

        // modify values to ensure deep copy
        copiedCoin.addTotalAmountBought(new Amount("12.0"));
        copiedCoin.addTotalAmountSold(new Amount("9.0"));

        assertNotSame(originalCoin.getTags(), copiedCoin.getTags());
        assertNotEquals(originalCoin.getTotalAmountBought(), copiedCoin.getTotalAmountBought());
        assertNotEquals(originalCoin.getTotalAmountSold(), copiedCoin.getTotalAmountSold());
        assertNotEquals(originalCoin.getTotalDollarsBought(), copiedCoin.getTotalDollarsBought());
        assertNotEquals(originalCoin.getTotalDollarsSold(), copiedCoin.getTotalDollarsSold());
    }
}

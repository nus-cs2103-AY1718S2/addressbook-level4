package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FAV;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HOT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.CoinBook;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.exceptions.DuplicateCoinException;

/**
 * A utility class containing a list of {@code Coin} objects to be used in tests.
 */
public class TypicalCoins {

    public static final Coin ALIS = new CoinBuilder().withName("ALIS")
            .withTags("favs").build();
    public static final Coin BTCZ = new CoinBuilder().withName("BTCZ")
            .withTags("common", "fav").build();
    public static final Coin CAS = new CoinBuilder().withName("CAS").build();
    public static final Coin DADI = new CoinBuilder().withName("DADI").build();
    public static final Coin ELIX = new CoinBuilder().withName("ELIX").build();
    public static final Coin FIRE = new CoinBuilder().withName("FIRE").build();
    public static final Coin GEO = new CoinBuilder().withName("GEO").build();

    // Manually added
    public static final Coin HORSE = new CoinBuilder().withName("HORSE").build();
    public static final Coin IDT = new CoinBuilder().withName("IDT").build();

    // Manually added - Coin's details found in {@code CommandTestUtil}
    public static final Coin AMB = new CoinBuilder().withName(VALID_NAME_AMB)
            .withTags(VALID_TAG_FAV).build();
    public static final Coin BOS = new CoinBuilder().withName(VALID_NAME_BOS)
            .withTags(VALID_TAG_HOT, VALID_TAG_FAV)
            .build();

    public static final String KEYWORD_MATCHING_BTC = "BT"; // A keyword that matches MEIER

    private TypicalCoins() {} // prevents instantiation

    /**
     * Returns an {@code CoinBook} with all the typical coins.
     */
    public static CoinBook getTypicalCoinBook() {
        CoinBook ab = new CoinBook();
        for (Coin coin : getTypicalCoins()) {
            try {
                ab.addCoin(coin);
            } catch (DuplicateCoinException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Coin> getTypicalCoins() {
        return new ArrayList<>(Arrays.asList(ALIS, BTCZ, CAS, DADI, ELIX, FIRE, GEO));
    }
}

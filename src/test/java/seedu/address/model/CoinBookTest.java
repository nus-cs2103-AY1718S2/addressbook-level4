package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalCoins.ALIS;
import static seedu.address.testutil.TypicalCoins.getTypicalCoinBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.coin.Coin;
import seedu.address.model.tag.Tag;

public class CoinBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final CoinBook coinBook = new CoinBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), coinBook.getCoinList());
        assertEquals(Collections.emptyList(), coinBook.getTagList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        coinBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyCoinBook_replacesData() {
        CoinBook newData = getTypicalCoinBook();
        coinBook.resetData(newData);
        assertEquals(newData, coinBook);
    }

    @Test
    public void resetData_withDuplicateCoins_throwsAssertionError() {
        // Repeat ALIS twice
        List<Coin> newCoins = Arrays.asList(ALIS, ALIS);
        List<Tag> newTags = new ArrayList<>(ALIS.getTags());
        CoinBookStub newData = new CoinBookStub(newCoins, newTags);

        thrown.expect(AssertionError.class);
        coinBook.resetData(newData);
    }

    @Test
    public void getCoinList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        coinBook.getCoinList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        coinBook.getTagList().remove(0);
    }

    /**
     * A stub ReadOnlyCoinBook whose coins and tags lists can violate interface constraints.
     */
    private static class CoinBookStub implements ReadOnlyCoinBook {
        private final ObservableList<Coin> coins = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        CoinBookStub(Collection<Coin> coins, Collection<? extends Tag> tags) {
            this.coins.setAll(coins);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<Coin> getCoinList() {
            return coins;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }

        //@@author laichengyu
        @Override
        public List<String> getCodeList() {
            return Collections.unmodifiableList(coins.stream()
                    .map(coin -> coin.getCode().toString())
                    .collect(Collectors.toList()));
        }
        //@@author
    }

}

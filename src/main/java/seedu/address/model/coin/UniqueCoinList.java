package seedu.address.model.coin;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.coin.exceptions.CoinNotFoundException;
import seedu.address.model.coin.exceptions.DuplicateCoinException;

/**
 * A list of coins that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Coin#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueCoinList implements Iterable<Coin> {

    private final ObservableList<Coin> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent coin as the given argument.
     */
    public boolean contains(Coin toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a coin to the list.
     *
     * @throws DuplicateCoinException if the coin to add is a duplicate of an existing coin in the list.
     */
    public void add(Coin toAdd) throws DuplicateCoinException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCoinException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the coin {@code target} in the list with {@code editedCoin}.
     *
     * @throws DuplicateCoinException if the replacement is equivalent to another existing coin in the list.
     * @throws CoinNotFoundException if {@code target} could not be found in the list.
     */
    public Index setCoin(Coin target, Coin editedCoin)
            throws DuplicateCoinException, CoinNotFoundException {
        requireNonNull(editedCoin);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CoinNotFoundException();
        }

        if (!target.equals(editedCoin) && internalList.contains(editedCoin)) {
            throw new DuplicateCoinException();
        }

        internalList.set(index, editedCoin);

        return Index.fromZeroBased(index);
    }


    /**
     * Removes the equivalent coin from the list.
     *
     * @throws CoinNotFoundException if no such coin could be found in the list.
     */
    public boolean remove(Coin toRemove) throws CoinNotFoundException {
        requireNonNull(toRemove);
        final boolean coinFoundAndDeleted = internalList.remove(toRemove);
        if (!coinFoundAndDeleted) {
            throw new CoinNotFoundException();
        }
        return coinFoundAndDeleted;
    }

    public void setCoins(UniqueCoinList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setCoins(List<Coin> coins) throws DuplicateCoinException {
        requireAllNonNull(coins);
        final UniqueCoinList replacement = new UniqueCoinList();
        for (final Coin coin : coins) {
            replacement.add(coin);
        }
        setCoins(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Coin> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    //@@author neilish3re

    /**
     * Sorts the coins using compareTo
     * @param isSort
     */
    public void sort(boolean isSort) {
        if (isSort) {
            internalList.sort((coin1, coin2) -> (coin2.getCode().fullName.compareTo(coin1.getCode().fullName)));
        } else {
            internalList.sort((coin1, coin2) -> (coin1.getCode().fullName.compareTo(coin2.getCode().fullName)));
        }
    }
    //@@author

    @Override
    public Iterator<Coin> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueCoinList // instanceof handles nulls
                        && this.internalList.equals(((UniqueCoinList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}

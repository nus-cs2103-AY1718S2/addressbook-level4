package seedu.address.model;

import java.util.Comparator;

import seedu.address.model.person.Client;

/**
 * Comparator to sort by int base on valueWeight
 */
public class RankComparator
        implements Comparator<Client> {

    @Override
    public int compare(Client o1, Client o2) {
        return o2.getRank() - o1.getRank();
    }
}

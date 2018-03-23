package seedu.address.model.coin;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Coin in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Coin {

    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;

    private final Amount currentAmountHeld;
    private final Amount totalAmountSold;
    private final Amount totalAmountBought;
    private final Price price;

    private final UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Coin(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.currentAmountHeld = new Amount();
        this.price = new Price();
        this.totalAmountSold = new Amount();
        this.totalAmountBought = new Amount();
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Amount getCurrentAmountHeld() {
        return currentAmountHeld;
    }

    public Price getPrice() {
        return price;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Coin)) {
            return false;
        }

        Coin otherCoin = (Coin) other;
        return otherCoin.getName().equals(this.getName())
                && otherCoin.getPhone().equals(this.getPhone())
                && otherCoin.getEmail().equals(this.getEmail())
                && otherCoin.getAddress().equals(this.getAddress())
                && otherCoin.getCurrentAmountHeld().equals(this.getCurrentAmountHeld())
                && otherCoin.getPrice().equals(this.getPrice());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, currentAmountHeld, price);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Amount: ")
                .append(getCurrentAmountHeld())
                .append(" Price: ")
                .append(getPrice())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    public Amount getTotalAmountSold() {
        return totalAmountSold;
    }

    public void addTotalAmountSold(Double addAmount) {
        this.totalAmountSold.addValue(addAmount);
    }

    public Amount getTotalAmountBought() {
        return totalAmountBought;
    }

    public void addTotalAmountBought(Double addAmount) {
        this.totalAmountBought.addValue(addAmount);
    }

    public Double getTotalAmountProfit() {
        return totalAmountSold.getValue() - totalAmountBought.getValue();
    }

    public Double getDollarsWorth() {
        return price.getValue() * currentAmountHeld.getValue();
    }

    public Double getProfitability() {
        return getDollarsWorth() + ((getProfitability() > 0) ? 0 : getProfitability());
    }
}

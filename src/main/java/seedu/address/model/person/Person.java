package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.SortCommand.SORT_ORDER_DESCENDING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.item.Item;
import seedu.address.model.item.UniqueItemList;
import seedu.address.model.money.Money;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;
    private Money money;

    private final UniqueTagList tags;
    private final UniqueItemList items;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Money balance, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.money = balance;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.items = new UniqueItemList(new ArrayList<>()); // initialize as empty set
    }

    //@@author chenchongsong
    /**
     * Every field must be present and not null.
     * @param items must be provided
     */
    public Person(Name name, Phone phone, Email email, Address address, Money balance,
                  Set<Tag> tags, ArrayList<Item>items) {
        requireAllNonNull(name, phone, email, address, tags, items);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.money = balance;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.items = new UniqueItemList(items);
    }
    //@@author

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

    //@@author pkuhanan
    public Money getMoney() {
        return money;
    }
    //@@author

    public void setMoney(Money money) {
        this.money = money;
    }

    //@@author chenchongsong
    /**
     * Returns the amount of money due to unknown reasons/items
     * @return
     */
    public Double getReasonUnknownAmount() {
        return money.toDouble() - items.getValueSum();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    public ArrayList<Item> getItems() {
        return items.toArrayList();
    }

    public UniqueItemList getUniqueItemList() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items.setItems(items);
    }

    public void clearItems() {
        this.items.setItems(new ArrayList<>());
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(this.getName())
                && otherPerson.getPhone().equals(this.getPhone())
                && otherPerson.getEmail().equals(this.getEmail())
                && otherPerson.getAddress().equals(this.getAddress())
                && otherPerson.getMoney().equals(this.getMoney());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, money, tags, items);
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
                .append(" Balance: ")
                .append(getMoney())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    //@@author chenchongsong
    /**
     * Create comparator for sorting person list
     * @param sortKey
     * @param sortOrder either "asc" or "desc"
     * @return comparator
     */
    public static Comparator<Person> createComparator(String sortKey, String sortOrder) {

        Comparator<Person> comparator;
        if (sortKey.equals(PREFIX_NAME.getPrefix())) {
            comparator = (person1, person2) -> +person1.getName().compareTo(person2.getName());
        } else if (sortKey.equals(PREFIX_PHONE.getPrefix())) {
            comparator = (person1, person2) -> +person1.getPhone().compareTo(person2.getPhone());
        } else if (sortKey.equals(PREFIX_EMAIL.getPrefix())) {
            comparator = (person1, person2) -> +person1.getEmail().compareTo(person2.getEmail());
        } else if (sortKey.equals(PREFIX_ADDRESS.getPrefix())) {
            comparator = (person1, person2) -> +person1.getAddress().compareTo(person2.getAddress());
        } else if (sortKey.equals(PREFIX_MONEY.getPrefix())) {
            comparator = (person1, person2) -> +person1.getMoney().compareTo(person2.getMoney());
        } else if (sortKey.equals(PREFIX_TAG.getPrefix())) {
            comparator = (person1, person2) -> +Integer.compare(person1.getTags().size(), person2.getTags().size());
        } else {
            // sort name by default
            comparator = (person1, person2) -> +person1.getName().toString()
                    .compareToIgnoreCase(person2.getName().toString());
        }

        if (sortOrder.equals(SORT_ORDER_DESCENDING)) {
            comparator = comparator.reversed();
        }
        return comparator;
    }
    //@@author

}

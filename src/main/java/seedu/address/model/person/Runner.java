package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a Runner in the address book.
 */
public class Runner extends Person {
    private final List<Customer> customers;

    public Runner() {
        super();
        this.customers = new ArrayList<>();
    }

    public Runner(List<Customer> customers) {
        super();
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return customers;
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
                .append(" Customers: ")
                .append(Optional.ofNullable(customers.toString()))
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}

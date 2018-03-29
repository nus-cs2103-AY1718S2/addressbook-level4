package seedu.address.model.person.runner;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.Person;
import seedu.address.model.person.customer.Customer;


/**
 * Represents a runner in the address book.
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
                .append(customers.toString())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}

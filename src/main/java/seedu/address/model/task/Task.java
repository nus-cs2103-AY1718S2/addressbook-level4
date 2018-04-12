package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.dish.Price;
import seedu.address.model.person.Address;
import seedu.address.model.person.Order;


/** The word "Task" is used to avoid confusion between order object in the queue and order of a person
 *
 */
public class Task {

    private ObjectProperty<Order> order;
    private ObjectProperty<Address> address;
    private ObjectProperty<Price> price;
    private ObjectProperty<Distance> distance;
    private ObjectProperty<Count> count;
    private ObjectProperty<String> description;

    public Task(Order order, Address address, Price price, Distance distance, Count count, String description) {
        requireAllNonNull(order, address, price, distance, count);
        this.order = new SimpleObjectProperty<>(order);
        this.address = new SimpleObjectProperty<>(address);
        this.price = new SimpleObjectProperty<>(price);
        this.distance = new SimpleObjectProperty<>(distance);
        this.count = new SimpleObjectProperty<>(count);
        this.description = new SimpleObjectProperty<>(description);
    }

    public Task(Task source) {
        this(source.getOrder(), source.getAddress(), source.getPrice(), source.getDistance(), source.getCount(),
                source.getDescription());
    }

    public Order getOrder() {
        return order.get();
    }

    public Address getAddress() {
        return address.get();
    }

    public Price getPrice() {
        return price.get();
    }

    public Distance getDistance() {
        return distance.get();
    }

    public Count getCount() {
        return count.get();
    }

    public String getDescription() {
        return description.get();
    }


    public ObjectProperty<Order> orderObjectProperty() {
        return order;
    }

    public ObjectProperty<Address> addressObjectProperty() {
        return address;
    }

    public ObjectProperty<Price> priceObjectProperty() {
        return price;
    }

    public ObjectProperty<Distance> distanceObjectProperty() {
        return distance;
    }

    public ObjectProperty<Count> countObjectProperty() {
        return count;
    }

    public ObjectProperty<String> descriptionObjectProperty() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Task)) {
            return false;
        }

        // state check
        Task t = (Task) other;
        return order.equals(t.order) &&
                address.equals(t.address) &&
                price.equals(t.price) &&
                distance.equals(t.distance) &&
                count.equals(t.count);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Order:")
                .append(getOrder())
                .append(" Address: ")
                .append(getAddress())
                .append(" Description: ")
                .append(getDescription());
        return builder.toString();
    }
}




//@@author ZhangYijiong
package seedu.address.testutil;

import seedu.address.model.dish.Price;
import seedu.address.model.person.Address;
import seedu.address.model.person.Order;
import seedu.address.model.task.Count;
import seedu.address.model.task.Distance;
import seedu.address.model.task.Task;

/**
 * Implementation follows {@code PersonBuilder}
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_TASK_ORDER = "Chicken Rice";
    public static final String DEFAULT_TASK_ADDRESS = "1A Kent Ridge Road";
    public static final String DEFAULT_TASK_PRICE = "100";
    public static final String DEFAULT_TASK_DISTANCE = "5";
    public static final String DEFAULT_TASK_COUNT = "2";
    public static final String DEFAULT_DESCRIPTION = "Chili Sauce Required";

    private Order defaultOrder;
    private Address defaultAddress;
    private Price defaultPrice;
    private Distance defaultDistance;
    private Count defaultCount;
    private String defaultDescription;

    public TaskBuilder()  {
        defaultOrder = new Order(DEFAULT_TASK_ORDER);
        defaultAddress = new Address(DEFAULT_TASK_ADDRESS);
        defaultPrice = new Price(DEFAULT_TASK_PRICE);
        defaultDistance = new Distance(DEFAULT_TASK_DISTANCE);
        defaultCount = new Count(DEFAULT_TASK_COUNT);
        defaultDescription = DEFAULT_DESCRIPTION;
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        defaultOrder = taskToCopy.getOrder();
        defaultAddress = taskToCopy.getAddress();
        defaultPrice = taskToCopy.getPrice();
        defaultDistance = taskToCopy.getDistance();
        defaultCount = taskToCopy.getCount();
        defaultDescription = taskToCopy.getDescription();
    }

    /**
     * Sets the {@code Order} of the {@code Task} that we are building.
     */
    public TaskBuilder withOrder(String order) {
        this.defaultOrder = new Order(order);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Task} that we are building.
     */
    public TaskBuilder withAddress(String address) {
        this.defaultAddress = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Task} that we are building.
     */
    public TaskBuilder withPrice(String price) {
        this.defaultPrice = new Price(price);
        return this;
    }

    /**
     * Sets the {@code Distance} of the {@code Task} that we are building.
     */
    public TaskBuilder withDistance(String distance) {
        this.defaultDistance = new Distance(distance);
        return this;
    }

    /**
     * Sets the {@code Count} of the {@code Task} that we are building.
     */
    public TaskBuilder withCount(String count) {
        this.defaultCount = new Count(count);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Task} that we are building.
     */
    public TaskBuilder withDescription(String description) {
        this.defaultDescription = description;
        return this;
    }

    /**
     * build task
     */
    public Task build() {
        return new Task(defaultOrder, defaultAddress, defaultPrice,
                defaultDistance, defaultCount, defaultDescription);
    }
}


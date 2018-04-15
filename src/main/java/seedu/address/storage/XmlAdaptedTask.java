package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.dish.Price;
import seedu.address.model.person.Address;
import seedu.address.model.person.Order;
import seedu.address.model.task.Count;
import seedu.address.model.task.Distance;
import seedu.address.model.task.Task;

/**
 * Implementation follows {@code XmlAdaptedPerson}
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String order;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String price;
    @XmlElement(required = true)
    private String distance;
    @XmlElement(required = true)
    private String count;
    @XmlElement(required = true)
    private String description;


    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}

    /**
     * Constructs an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedTask(String order, String address, String price,
                            String distance, String count, String description) {
        this.order = order;
        this.address = address;
        this.price = price;
        this.distance = distance;
        this.count = count;
        this.description = description;
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(Task source) {
        order = source.getOrder().toString();
        address = source.getAddress().toString();
        price = source.getPrice().value;
        distance = source.getDistance().toString();
        count = source.getCount().value;
        description = source.getDescription();
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        if (!Order.isValidOrder(order)) {
            throw new IllegalValueException(Order.MESSAGE_ORDER_CONSTRAINTS);
        }
        final Order order = new Order(this.order);
        final Address address = new Address(this.address);
        final Price price = new Price(this.price);
        final Distance distance = new Distance(this.distance);
        final Count count = new Count(this.count);
        final String description = new String(this.description);
        return new Task(order, address, price, distance, count, description);
    }
}



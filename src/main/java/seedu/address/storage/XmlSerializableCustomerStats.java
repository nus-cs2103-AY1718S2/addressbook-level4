//@@author Wuhao-ooo
package seedu.address.storage;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.CustomerStats;
import seedu.address.model.ReadOnlyCustomerStats;

/**
 * An Immutable CustomerStats that is serializable to XML format
 */
@XmlRootElement(name = "customerstats")
public class XmlSerializableCustomerStats {

    @XmlElement
    private HashMap<String, Integer> orderCount;

    /**
     * Creates an empty XmlSerializableCustomerStats.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableCustomerStats() {
        orderCount = new HashMap<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableCustomerStats(ReadOnlyCustomerStats src) {
        this();
        orderCount = src.getOrdersCount();
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson} or {@code XmlAdaptedTag}.
     */
    public CustomerStats toModelType() {
        CustomerStats customerStats = new CustomerStats();
        customerStats.setOrdersCount(orderCount);
        return customerStats;
    }
}

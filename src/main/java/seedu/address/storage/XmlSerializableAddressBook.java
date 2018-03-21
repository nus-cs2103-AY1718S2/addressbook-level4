package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook {

    @XmlElement
    private List<XmlAdaptedPerson> persons;
    @XmlElement
    private List<XmlAdaptedGroup> groups;
    @XmlElement
    private List<XmlAdaptedPreference> preferences;
    @XmlElement
    private List<XmlAdaptedOrder> orders;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        persons = new ArrayList<>();
        groups = new ArrayList<>();
        preferences = new ArrayList<>();
        orders = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        groups.addAll(src.getGroupList().stream().map(XmlAdaptedGroup::new).collect(Collectors.toList()));
        preferences.addAll(src.getPreferenceList().stream().map(XmlAdaptedPreference::new)
                .collect(Collectors.toList()));
        orders.addAll(src.getOrderList().stream().map(XmlAdaptedOrder::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson} or {@code XmlAdaptedGroup} or {@code XmlAdaptedPreference}.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (XmlAdaptedGroup g: groups) {
            addressBook.addGroup(g.toModelType());
        }

        for (XmlAdaptedPreference pref: preferences) {
            addressBook.addPreference(pref.toModelType());
        }

        for (XmlAdaptedPerson p : persons) {
            addressBook.addPerson(p.toModelType());
        }

        for (XmlAdaptedOrder o : orders) {
            addressBook.addOrderToOrderList(o.toModelType());
        }

        return addressBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableAddressBook)) {
            return false;
        }

        XmlSerializableAddressBook otherAb = (XmlSerializableAddressBook) other;
        return persons.equals(otherAb.persons)
                && groups.equals(otherAb.groups)
                && preferences.equals(otherAb.preferences)
                && orders.equals(otherAb.orders);
    }
}

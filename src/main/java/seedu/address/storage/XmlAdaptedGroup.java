package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Group;
import seedu.address.model.group.Information;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * JAXB-friendly version of the Group.
 */
public class XmlAdaptedGroup {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Group's %s field is missing!";

    @XmlElement(required = true)
    private String information;

    @XmlElement(required = true)
    private List<XmlAdaptedPerson> personList = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedGroup.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedGroup() {
    }

    /**
     * Constructs an {@code XmlAdaptedGroup} with the given group details.
     */
    public XmlAdaptedGroup(String information) {
        this.information = information;
        this.personList = new ArrayList<>();
    }

    /**
     * Converts a given Group into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedGroup
     */
    public XmlAdaptedGroup(Group source) {
        information = source.getInformation().value;
        UniquePersonList persons = source.getPersonList();
        for (Person person : persons) {
            personList.add(new XmlAdaptedPerson(person));
        }
    }

    /**
     * Converts this jaxb-friendly adapted group object into the model's Group object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted group
     */
    public Group toModelType() throws IllegalValueException {
        if (this.information == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Information.class.getSimpleName()));
        }
        if (!Information.isValidInformation(this.information)) {
            throw new IllegalValueException(Information.MESSAGE_INFORMATION_CONSTRAINTS);
        }
        final Information information = new Information(this.information);
        final UniquePersonList uniquePersonList = new UniquePersonList();
        for (XmlAdaptedPerson adaptedPerson : personList) {
            Person personToAdd = adaptedPerson.toModelType();
            uniquePersonList.add(personToAdd);
        }

        return new Group(information, uniquePersonList);

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedGroup)) {
            return false;
        }

        XmlAdaptedGroup otherGroup = (XmlAdaptedGroup) other;
        return Objects.equals(information, otherGroup.information);
    }
}

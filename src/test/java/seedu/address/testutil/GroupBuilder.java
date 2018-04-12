//@@author jas5469
package seedu.address.testutil;

import seedu.address.model.group.Group;
import seedu.address.model.group.Information;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A utility class to help with building Group objects.
 */
public class GroupBuilder {

    public static final String DEFAULT_INFORMATION = "Something to do";

    private Information information;
    private UniquePersonList personList;

    public GroupBuilder() {
        information = new Information(DEFAULT_INFORMATION);
        personList = new UniquePersonList();
    }

    /**
     * Initializes the GroupBuilder with the data of {@code groupToCopy}.
     */
    public GroupBuilder(Group groupToCopy) {
        information = groupToCopy.getInformation();
        this.personList = groupToCopy.getPersonList();
    }

    /**
     * Sets the {@code Information} of the {@code Group} that we are building.
     */
    public GroupBuilder withInformation(String information) {
        this.information = new Information(information);
        personList = new UniquePersonList();
        return this;
    }

    /**
     * Sets the {@code Information} of the {@code Group} that we are building.
     */
    public GroupBuilder withPerson(String information, Person personToAdd) {
        this.information = new Information(information);
        personList = new UniquePersonList();
        try {
            personList.add(personToAdd);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("person is expected to be unique.");
        }
        return this;
    }

    public Group build() {
        return new Group(information, personList);
    }

}

package seedu.address.testutil;

//@@author jas5469

import seedu.address.model.group.Group;
import seedu.address.model.group.Information;
import seedu.address.model.person.UniquePersonList;

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
        return this;
    }

    public Group build() {
        return new Group(information, personList);
    }

}

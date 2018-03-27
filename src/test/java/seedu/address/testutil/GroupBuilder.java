package seedu.address.testutil;

import seedu.address.model.group.Group;
import seedu.address.model.group.Information;

/**
 * A utility class to help with building Group objects.
 */
public class GroupBuilder {

    public static final String DEFAULT_INFORMATION = "Something to do";

    private Information information;

    public GroupBuilder() {
        information = new Information(DEFAULT_INFORMATION);
    }

    /**
     * Initializes the GroupBuilder with the data of {@code groupToCopy}.
     */
    public GroupBuilder(Group groupToCopy) {
        information = groupToCopy.getInformation();
    }

    /**
     * Sets the {@code Information} of the {@code Group} that we are building.
     */
    public GroupBuilder withInformation(String information) {
        this.information = new Information(information);
        return this;
    }

    public Group build() {
        return new Group(information);
    }

}

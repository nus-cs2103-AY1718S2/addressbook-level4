package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMETABLE_LINK_BOB;


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
        return this;
    }

    /**
     * Sets the {@code Information} of the {@code Group} that we are building.
     */
    public GroupBuilder withInformationAndPerson(String information) {
        this.information = new Information(information);
        Person personToAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTimeTableLink(VALID_TIMETABLE_LINK_BOB)
                .withDetail(VALID_DETAIL_BOB).withTags(VALID_TAG_FRIEND).build();
        try {
            this.personList.add(personToAdd);
        } catch (DuplicatePersonException e) {
        }
        return this;
    }
    public Group build() {
        return new Group(information,personList);
    }

}

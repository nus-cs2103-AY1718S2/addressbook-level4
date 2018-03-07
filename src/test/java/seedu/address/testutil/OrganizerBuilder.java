package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Organizer;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 * {@code Organizer ab = new OrganizerBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class OrganizerBuilder {

    private Organizer organizer;

    public OrganizerBuilder() {
        organizer = new Organizer();
    }

    public OrganizerBuilder(Organizer organizer) {
        this.organizer = organizer;
    }

    /**
     * Adds a new {@code Task} to the {@code Organizer} that we are building.
     */
    public OrganizerBuilder withPerson(Task task) {
        try {
            organizer.addPerson(task);
        } catch (DuplicateTaskException dpe) {
            throw new IllegalArgumentException("task is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code Organizer} that we are building.
     */
    public OrganizerBuilder withTag(String tagName) {
        try {
            organizer.addTag(new Tag(tagName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagName is expected to be valid.");
        }
        return this;
    }

    public Organizer build() {
        return organizer;
    }
}

package seedu.organizer.testutil;

import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.model.Organizer;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 * {@code Organizer ab = new OrganizerBuilder().withTask("John", "Doe").withTag("Friend").build();}
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
    public OrganizerBuilder withTask(Task task) {
        try {
            organizer.addTask(task);
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

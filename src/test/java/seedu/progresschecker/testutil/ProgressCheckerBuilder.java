package seedu.progresschecker.testutil;

import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.model.ProgressChecker;
import seedu.progresschecker.model.person.Person;
import seedu.progresschecker.model.person.exceptions.DuplicatePersonException;
import seedu.progresschecker.model.tag.Tag;

/**
 * A utility class to help with building ProgressChecker objects.
 * Example usage: <br>
 *     {@code ProgressChecker ab = new ProgressCheckerBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class ProgressCheckerBuilder {

    private ProgressChecker progressChecker;

    public ProgressCheckerBuilder() {
        progressChecker = new ProgressChecker();
    }

    public ProgressCheckerBuilder(ProgressChecker progressChecker) {
        this.progressChecker = progressChecker;
    }

    /**
     * Adds a new {@code Person} to the {@code ProgressChecker} that we are building.
     */
    public ProgressCheckerBuilder withPerson(Person person) {
        try {
            progressChecker.addPerson(person);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("person is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code ProgressChecker} that we are building.
     */
    public ProgressCheckerBuilder withTag(String tagName) {
        try {
            progressChecker.addTag(new Tag(tagName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagName is expected to be valid.");
        }
        return this;
    }

    public ProgressChecker build() {
        return progressChecker;
    }
}

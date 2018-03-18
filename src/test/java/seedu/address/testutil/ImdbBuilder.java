package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Imdb;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code Imdb ab = new ImdbBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class ImdbBuilder {

    private Imdb imdb;

    public ImdbBuilder() {
        imdb = new Imdb();
    }

    public ImdbBuilder(Imdb imdb) {
        this.imdb = imdb;
    }

    /**
     * Adds a new {@code Patient} to the {@code Imdb} that we are building.
     */
    public ImdbBuilder withPerson(Patient patient) {
        try {
            imdb.addPerson(patient);
        } catch (DuplicatePatientException dpe) {
            throw new IllegalArgumentException("patient is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code Imdb} that we are building.
     */
    public ImdbBuilder withTag(String tagName) {
        try {
            imdb.addTag(new Tag(tagName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagName is expected to be valid.");
        }
        return this;
    }

    public Imdb build() {
        return imdb;
    }
}

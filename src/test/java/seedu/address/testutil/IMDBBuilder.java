package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.IMDB;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code IMDB ab = new IMDBBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class IMDBBuilder {

    private IMDB IMDB;

    public IMDBBuilder() {
        IMDB = new IMDB();
    }

    public IMDBBuilder(IMDB IMDB) {
        this.IMDB = IMDB;
    }

    /**
     * Adds a new {@code Patient} to the {@code IMDB} that we are building.
     */
    public IMDBBuilder withPerson(Patient patient) {
        try {
            IMDB.addPerson(patient);
        } catch (DuplicatePatientException dpe) {
            throw new IllegalArgumentException("patient is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code IMDB} that we are building.
     */
    public IMDBBuilder withTag(String tagName) {
        try {
            IMDB.addTag(new Tag(tagName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagName is expected to be valid.");
        }
        return this;
    }

    public IMDB build() {
        return IMDB;
    }
}

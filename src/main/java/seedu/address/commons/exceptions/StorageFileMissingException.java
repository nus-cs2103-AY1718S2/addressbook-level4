package seedu.address.commons.exceptions;

//@author samuelloh
/**
 * Represents an error when using "moreInfo" command as xml data of students are not found.
 */
public class StorageFileMissingException extends Exception {

    public static final String STORAGE_FILE_MISSING = "The 'moreInfo' command requires xml data of students and"
            + " will not work with sample students data. Try clearing the sample data and adding a student.";

    public StorageFileMissingException(String message) {
        super(message);
    }

}
//@@author

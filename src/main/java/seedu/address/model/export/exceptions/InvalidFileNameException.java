package seedu.address.model.export.exceptions;

import seedu.address.commons.exceptions.PortfolioException;

//@@author daviddalmaso
/**
 * Exception class to handle invalid file names during portfolio export
 */
public class InvalidFileNameException extends PortfolioException {
    public InvalidFileNameException() {
        super("Invalid file name to use for exporting portfolio");
    }
}

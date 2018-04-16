package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.InvalidFileException;
import seedu.address.model.ReadOnlyTaskBook;

//@@author CYX28
/**
 * Represents a storage for {@link seedu.address.model.TaskBook}.
 */
public interface TaskBookStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskBookFilePath();

    /**
     * Returns TaskBook data as a {@link ReadOnlyTaskBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException;

    /**
     * @see #getTaskBookFilePath()
     */
    Optional<ReadOnlyTaskBook> readTaskBook(String filePath)
            throws DataConversionException, IOException, JAXBException;

    /**
     * Saves the given {@link ReadOnlyTaskBook} to the storage.
     * @param taskBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException, InvalidFileException;

    /**
     * @see #saveTaskBook(ReadOnlyTaskBook)
     */
    void saveTaskBook(ReadOnlyTaskBook taskBook, String filePath) throws IOException;

    /**
     * @see #exportTaskBook()
     */
    void exportTaskBook() throws ParserConfigurationException, IOException, TransformerException;

    /**
     * Backups the given {@link ReadOnlyTaskBook} to the storage.
     * @param taskBook cannot be null.
     * @throws IOException if there was any problem backing up the file.
     */
    void backupTaskBook(ReadOnlyTaskBook taskBook) throws IOException, InvalidFileException;

}

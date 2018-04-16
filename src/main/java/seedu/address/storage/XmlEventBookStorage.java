package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.ReadOnlyEventBook;

//@@author x3tsunayh

/**
 * A class to access TunedIn EventBook data stored as an xml file on the hard disk.
 */
public class XmlEventBookStorage implements EventBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlEventBookStorage.class);

    private String filePath;
    private String exportedPath;
    private String header;

    // default constructor
    public XmlEventBookStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getEventBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEventBook> readEventBook() throws IOException, JAXBException, DataConversionException {
        return readEventBook(filePath);
    }

    @Override
    public Optional<ReadOnlyEventBook> readEventBook(String filePath)
            throws FileNotFoundException, JAXBException, DataConversionException {
        requireNonNull(filePath);

        File eventBookFile = new File(filePath);

        if (!eventBookFile.exists()) {
            logger.info("EventBook file " + eventBookFile + " not found");
            return Optional.empty();
        }

        XmlSerializableEventBook eventBookOptional = XmlFileStorage.loadEventDataFromSaveFile(new File(filePath));
        try {
            return Optional.of(eventBookOptional.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + eventBookOptional + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        } catch (CommandException e) {
            logger.info("Invalid command in " + eventBookOptional + ": " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void saveEventBook(ReadOnlyEventBook eventBook) throws IOException {
        saveEventBook(eventBook, filePath);
    }

    @Override
    public void saveEventBook(ReadOnlyEventBook eventBook, String filePath) throws IOException {
        requireNonNull(eventBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableEventBook(eventBook));
    }

    @Override
    public void backupEventBook(ReadOnlyEventBook eventBook) throws IOException {
        saveEventBook(eventBook, filePath + "-backup");
    }

    @Override
    public void exportEventBook() throws ParserConfigurationException, IOException {
        //TODO BY V2.0
    }
}

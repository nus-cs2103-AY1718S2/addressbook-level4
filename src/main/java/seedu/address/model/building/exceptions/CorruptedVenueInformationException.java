package seedu.address.model.building.exceptions;

/**
 * Signals that some data in venueinformation.json file is corrupted
 */
public class CorruptedVenueInformationException extends Exception {
    public CorruptedVenueInformationException() {
        super("Unable to read from venueinformation.json, file is corrupted. Please re-download the file.");
    }
}

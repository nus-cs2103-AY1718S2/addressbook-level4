package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_CINEMA_DISPLAYED_INDEX = "The cinema index provided is invalid";
    public static final String MESSAGE_INVALID_MOVIE_DISPLAYED_INDEX = "The movie index provided is invalid";
    public static final String MESSAGE_CINEMAS_LISTED_OVERVIEW = "%1$d cinemas listed!";
    public static final String MESSAGE_MOVIES_LISTED_OVERVIEW = "%1$d movies listed!";
    public static final String MESSAGE_INVALID_THEATER_NUMBER = "The theater number provided is invalid";
    public static final String MESSAGE_INVALID_SCREEN_DATE_TIME = "Please provide a valid date and time"
            + " in the form DD/MM/YYYY HH/mm. With the minutes being in 5 minutes interval. E.g. 05/05/2015 23:05";
    public static final String MESSAGE_INVALID_YEAR = "Please provide a valid year. The year provided should be between"
            + " 2000 and 2030, inclusive of year 2000 and 2030.";
    public static final String MESSAGE_INVALID_SCREENING = "Please ensure that there is no conflict of screening"
            + " before and after this. \nPlease also make sure the screening date is after the movie release date.";
    public static final String MESSAGE_INVALID_DELETE_SCREENING_DATE_TIME = "Please ensure that the screening exist in "
            + "the given cinema. \nPlease also ensure that the time given is the exact time when the screening starts.";
    public static final String MESSAGE_ENCRYPTED_FILE_NOT_FOUND = "Encrypted file not found! Nothing to decrypt.";
    public static final String MESSAGE_FILE_NOT_FOUND = "movieplanner.xml not found. No file to encrypt!";
}

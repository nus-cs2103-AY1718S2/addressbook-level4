package seedu.address.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SCREEN_DATE_TIME;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.movie.MovieName;
import seedu.address.model.screening.Screening;

//@@author qwlai
/**
 * JAXB-friendly adapted version of the Screening.
 */
public class XmlAdaptedScreening {

    private static final int MINUTES_USED_IN_ROUNDING_OFF = 5;
    private static final String DATE_TIME_FORMAT = "dd/MM/uuuu HH:mm";

    @XmlElement(required = true, name = "movie")
    private String movieName;
    @XmlElement(required = true, name = "startDateTime")
    private String screeningDateTime;
    @XmlElement(required = true, name = "endDateTime")
    private String screeningEndDateTime;

    /**
     * Constructs an XmlAdaptedScreening.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedScreening() {}

    /**
     * Constructs a {@code XmlAdaptedScreening} with the given {@code theaternum}.
     */
    public XmlAdaptedScreening(String movieName, String screeningDateTime, String screeningEndDateTime) {
        this.movieName = movieName;
        this.screeningDateTime = screeningDateTime;
        this.screeningEndDateTime = screeningEndDateTime;
    }

    /**
     * Converts a given Screening into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedScreening(Screening source) {
        movieName = source.getMovieName();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        screeningDateTime = source.getScreeningDateTime().format(dtf);
        screeningEndDateTime = source.getScreeningEndDateTime().format(dtf);
    }

    /**
     * Returns a hashmap containing movieName as the key
     * startDateTime and endDateTime is stored in the arrayList and put as the value in the hashmap
     * @throws IllegalValueException if there were any data constraints violated in the adapted screening
     */
    public HashMap<String, ArrayList<LocalDateTime>> toScreening() throws IllegalValueException {
        HashMap<String, ArrayList<LocalDateTime>> screeningMap = new HashMap<>();

        if (!MovieName.isValidName(movieName)) {
            throw new IllegalValueException(MovieName.MESSAGE_MOVIENAME_CONSTRAINTS);
        }

        if (getValidDateTime().size() == 2) {
            screeningMap.put(movieName, getValidDateTime());
        }

        return screeningMap;
    }

    /**
     * Returns an ArrayList of LocalDateTime consisting of the startDateTime and endDateTime
     * @throws IllegalValueException if the date time given is not in the right format
     */
    private ArrayList<LocalDateTime> getValidDateTime() throws IllegalValueException {
        ArrayList<LocalDateTime> dateTimeDetailsList = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).withResolverStyle(ResolverStyle.STRICT);

        try {
            LocalDateTime dateTime = LocalDateTime.parse(screeningDateTime, dtf);
            LocalDateTime endDateTime = LocalDateTime.parse(screeningEndDateTime, dtf);

            if (dateTime.getMinute() % MINUTES_USED_IN_ROUNDING_OFF != 0
                    || endDateTime.getMinute() % MINUTES_USED_IN_ROUNDING_OFF != 0) {
                throw new IllegalValueException(Messages.MESSAGE_INVALID_SCREEN_DATE_TIME);
            }

            dateTimeDetailsList.add(dateTime);
            dateTimeDetailsList.add(endDateTime);

        } catch (DateTimeParseException dtpe) {
            throw new ParseException(MESSAGE_INVALID_SCREEN_DATE_TIME, dtpe);
        }
        return dateTimeDetailsList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedScreening)) {
            return false;
        }

        XmlAdaptedScreening otherScreening = (XmlAdaptedScreening) other;
        return Objects.equals(movieName, otherScreening.movieName)
                && Objects.equals(screeningDateTime, otherScreening.screeningDateTime)
                && Objects.equals(screeningEndDateTime, otherScreening.screeningEndDateTime);
    }
}

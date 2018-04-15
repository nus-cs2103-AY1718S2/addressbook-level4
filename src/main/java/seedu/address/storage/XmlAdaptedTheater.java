package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cinema.Theater;
import seedu.address.model.screening.Screening;

/**
 * JAXB-friendly adapted version of the Theater.
 */
public class XmlAdaptedTheater {

    @XmlAttribute(name = "number")
    private int theaterNumber;
    @XmlElement(name = "screening")
    private ArrayList<XmlAdaptedScreening> screenings = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedTheater.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTheater() {}

    //@@author qwlai
    /**
     * Constructs a {@code XmlAdaptedTheater} with the given {@code theaterNumber}.
     */
    public XmlAdaptedTheater(int theaterNumber, ArrayList<XmlAdaptedScreening> screenings) {
        this.theaterNumber = theaterNumber;
        if (screenings != null) {
            this.screenings = new ArrayList<>(screenings);
        }
    }

    /**
     * Converts a given Theater into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTheater(Theater source) {
        this.theaterNumber = source.getTheaterNumber();

        for (Screening screening : source.getScreeningList()) {
            screenings.add(new XmlAdaptedScreening(screening));
        }
    }


    /**
     * Converts this jaxb-friendly adapted theater object into the model's Theater object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted theater
     */
    public Theater toModelType() throws IllegalValueException {
        if (!Theater.isValidTheater(String.valueOf(theaterNumber))) {
            throw new IllegalValueException(Theater.MESSAGE_THEATER_CONSTRAINTS);
        }

        Theater theater = new Theater(theaterNumber);
        for (XmlAdaptedScreening s : screenings) {
            String movieName = s.toScreening().keySet().stream().findFirst().get();
            if (movieName != null) {
                LocalDateTime startDateTime = s.toScreening().get(movieName).get(0);
                LocalDateTime endDateTime = s.toScreening().get(movieName).get(1);
                Screening screening = new Screening(movieName, theater, startDateTime, endDateTime);
                theater.addScreeningToTheater(screening);
            }
        }

        return theater;
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTheater)) {
            return false;
        }

        XmlAdaptedTheater otherTheater = (XmlAdaptedTheater) other;
        return Objects.equals(theaterNumber, otherTheater.theaterNumber)
                && Objects.equals(screenings, otherTheater.screenings);
    }
}

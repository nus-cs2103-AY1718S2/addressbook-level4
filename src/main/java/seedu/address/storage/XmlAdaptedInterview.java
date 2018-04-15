//@@author deeheenguyen
package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.interview.Date;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.InterviewLocation;
import seedu.address.model.interview.InterviewTitle;
import seedu.address.model.person.Name;

/**
 * JAXB-friendly version of the Job.
 */
public class XmlAdaptedInterview {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Interview's %s field is missing!";

    @XmlElement(required = true)
    private String interviewTitle;
    @XmlElement(required = true)
    private String interviewLocation;
    @XmlElement(required = true)
    private String interviewee;
    @XmlElement (required = true)
    private String date;

    /**
     * Constructs an XmlAdaptedInterview.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedInterview() {}

    /**
     * Constructs an {@code XmlAdaptedInterview} with the given interview details.
     */
    public XmlAdaptedInterview(String interviewTitle, String interviewee, String date, String location) {
        this.interviewTitle = interviewTitle;
        this.interviewLocation = location;
        this.date = date;
        this.interviewee = interviewee;
    }

    /**
     * Converts a given Interview into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedInterview
     */
    public XmlAdaptedInterview(Interview source) {
        interviewTitle = source.getInterviewTitle().toString();
        interviewLocation = source.getInterviewLocation().toString();
        interviewee = source.getInterviewee().toString();
        date = source.getDate().toString();
    }

    /**
     * Converts this jaxb-friendly adapted interview object into the model's job object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted interview
     */
    public Interview toModelType() throws IllegalValueException {
        if (this.interviewTitle == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, InterviewTitle.class.getSimpleName()));
        }
        if (!InterviewTitle.isValidTitle(this.interviewTitle)) {
            throw new IllegalValueException(InterviewTitle.MESSAGE_TITLE_CONSTRAINTS);
        }
        final InterviewTitle interviewTitle = new InterviewTitle(this.interviewTitle);

        if (this.interviewee == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.interviewee)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name interviewee = new Name(this.interviewee);

        if (this.interviewLocation == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, InterviewLocation.class.getSimpleName()));
        }
        if (!InterviewLocation.isValidLocation(this.interviewLocation)) {
            throw new IllegalValueException(InterviewLocation.MESSAGE_LOCATION_CONSTRAINTS);
        }
        final InterviewLocation interviewLocation = new InterviewLocation(this.interviewLocation);

        if (this.date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(this.date)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        final Date date = new Date(this.date);

        return new Interview(interviewTitle, interviewee, date, interviewLocation);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedInterview)) {
            return false;
        }

        XmlAdaptedInterview otherInterview = (XmlAdaptedInterview) other;
        return Objects.equals(interviewTitle, otherInterview.interviewTitle)
                && Objects.equals(interviewee, otherInterview.interviewee)
                && Objects.equals(interviewLocation, otherInterview.interviewLocation)
                && Objects.equals(date, otherInterview.date);
    }
}


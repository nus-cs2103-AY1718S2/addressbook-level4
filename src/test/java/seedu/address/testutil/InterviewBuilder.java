//@@author deeheenguyen
package seedu.address.testutil;

import seedu.address.model.interview.Date;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.InterviewLocation;
import seedu.address.model.interview.InterviewTitle;
import seedu.address.model.person.Name;

/**
 * A utility class to help with building Person objects.
 */
public class InterviewBuilder {

    public static final String DEFAULT_INTERVIEW_TITLE = "Internship Interview";
    public static final String DEFAULT_INTERVIEWEE = "David";
    public static final String DEFAULT_INNTERVIEW_LOCATION = "NUS";
    public static final String DEFAULT_DATE = "01.01.2018";

    private Name interviewee;
    private InterviewTitle interviewTitle;
    private InterviewLocation interviewLocation;
    private Date date;

    public InterviewBuilder() {
        interviewee = new Name(DEFAULT_INTERVIEWEE);
        interviewTitle = new InterviewTitle(DEFAULT_INTERVIEW_TITLE);
        interviewLocation = new InterviewLocation(DEFAULT_INNTERVIEW_LOCATION);
        date = new Date(DEFAULT_DATE);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public InterviewBuilder(Interview interviewToCopy) {
        interviewTitle = interviewToCopy.getInterviewTitle();
        interviewee = interviewToCopy.getInterviewee();
        interviewLocation = interviewToCopy.getInterviewLocation();
        date = interviewToCopy.getDate();
    }

    /**
     * Sets the {@code Interviewee} of the {@code Interview} that we are building.
     */
    public InterviewBuilder withInterviewee(String name) {
        this.interviewee = new Name(name);
        return this;
    }


    /**
     * Sets the {@code InterviewLocation} of the {@code Person} that we are building.
     */
    public InterviewBuilder withInterviewLocation(String interviewLocation) {
        this.interviewLocation = new InterviewLocation(interviewLocation);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Interview} that
     * we are building.
     */
    public InterviewBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code InterviewTitle} of the {@code Interview} that we are building.
     */
    public InterviewBuilder withInterviewTitle(String interviewTitle) {
        this.interviewTitle = new InterviewTitle(interviewTitle);
        return this;
    }

    public Interview build() {
        return new Interview(interviewTitle, interviewee, date, interviewLocation);
    }

}
//author @deeheenguyen

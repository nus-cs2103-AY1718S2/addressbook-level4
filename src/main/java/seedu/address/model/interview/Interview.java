//@@author deeheenguyen
package seedu.address.model.interview;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.person.Name;

/**
 * Represents an Interview in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Interview {
    private InterviewTitle interviewTitle;
    private Name interviewee;
    private Date date;
    private InterviewLocation interviewLocation;

    public Interview(InterviewTitle interviewTitle, Name interviewee, Date date, InterviewLocation location) {
        requireAllNonNull(interviewTitle, interviewee, date, location);
        this.interviewTitle = interviewTitle;
        this.interviewee = interviewee;
        this.date = date;
        this.interviewLocation = location;
    }

    public InterviewTitle getInterviewTitle() {
        return this.interviewTitle;
    }

    public Name getInterviewee() {
        return this.interviewee;
    }

    public Date getDate() {
        return this.date;
    }

    public InterviewLocation getInterviewLocation() {
        return this.interviewLocation;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Interview)) {
            return false;
        }
        Interview otherInterview = (Interview) other;
        return otherInterview.getInterviewTitle().equals(this.getInterviewTitle())
                && otherInterview.getInterviewee().equals(this.getInterviewee())
                && otherInterview.getDate().equals(this.getDate())
                && otherInterview.getInterviewLocation().equals(this.getInterviewLocation());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(interviewTitle, interviewee, date, interviewLocation);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Interview Title: ")
                .append(getInterviewTitle())
                .append(" Interviewee: ")
                .append(getInterviewee())
                .append(" Date: ")
                .append(getDate())
                .append(" Interview Location: ")
                .append(getInterviewLocation());
        return builder.toString();
    }
}

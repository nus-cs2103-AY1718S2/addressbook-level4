package seedu.address.model.person;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

/**
 * Represents a Person's interview date in the address book.
 * Guarantees: immutable
 */
public class InterviewDate {
    public static final String MESSAGE_INTERVIEW_DATE_XML_ERROR =
            "Interview date must be in epoch format, failed to parse from XML";
    public static final ZoneOffset LOCAL_ZONE_OFFSET = ZoneId.systemDefault().getRules().getOffset(LocalDateTime.now());

    public final LocalDateTime dateTime;
    public final String value;

    /**
     * Constructs a {@code InterviewDate}.
     */
    public InterviewDate() {
        this((LocalDateTime) null);
    }

    /**
     * Constructs a {@code InterviewDate}.
     * @param timestamp A epoch timestamp
     */
    public InterviewDate(Long timestamp) {
        this(LocalDateTime.ofEpochSecond(timestamp, 0, LOCAL_ZONE_OFFSET));
    }

    /**
     * Constructs a {@code InterviewDate}.
     * @param dateTime of the person
     */
    public InterviewDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        if (dateTime != null) {
            this.value = String.valueOf(dateTime.toEpochSecond(LOCAL_ZONE_OFFSET));
        } else {
            this.value = null;
        }
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof InterviewDate)) {
            return false;
        }

        InterviewDate i = (InterviewDate) other;
        return Objects.equals(getDateTime(), i.getDateTime());
    }

    @Override
    public int hashCode() {
        return getDateTime().hashCode();
    }
}

package seedu.address.testutil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Remark;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Appointment Objects.
 */
public class AppointmentBuilder {
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_REMARK = "Requires home visit";
    public static final String DEFAULT_DATE = "2018-12-31 12:30";
    public static final String DEFAULT_TYPE = "surgery";

    private Name owner;
    private Remark remark;
    private LocalDateTime localDateTime;
    private Set<Tag> type;

    public AppointmentBuilder() {
        owner = new Name(DEFAULT_NAME);
        remark = new Remark(DEFAULT_REMARK);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        localDateTime = LocalDateTime.parse(DEFAULT_DATE, formatter);
        type = SampleDataUtil.getTagSet(DEFAULT_TYPE);
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code appointmentToCopy}.
     */
    public AppointmentBuilder(Appointment appointmentToCopy) {
        owner = appointmentToCopy.getOwner();
        remark = appointmentToCopy.getRemark();
        localDateTime = appointmentToCopy.getDateTime();
        type = new HashSet<>(appointmentToCopy.getType());
    }

    /**
     * Sets the {@code Name} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withOwner(String owner) {
        this.owner = new Name(owner);
        return this;
    }

    /**
     * Parses the {@code type} into a {@code Set<Tag>} and set it to the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withTags(String ... type) {
        this.type = SampleDataUtil.getTagSet(type);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withDateTime(String stringDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(stringDateTime, formatter);
        this.localDateTime = dateTime;
        return this;
    }

    public Appointment build() {
        return new Appointment(owner, remark, localDateTime, type);
    }
}

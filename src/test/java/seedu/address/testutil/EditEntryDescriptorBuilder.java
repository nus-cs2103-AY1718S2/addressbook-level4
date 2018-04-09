package seedu.address.testutil;
//@@author SuxianAlicia
import seedu.address.logic.commands.EditEntryCommand;
import seedu.address.logic.commands.EditEntryCommand.EditEntryDescriptor;
import seedu.address.model.event.CalendarEntry;
import seedu.address.model.event.EndDate;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.EntryTitle;
import seedu.address.model.event.StartDate;
import seedu.address.model.event.StartTime;

/**
 * A utility class to help with building EditEntryDescriptor objects.
 */
public class EditEntryDescriptorBuilder {
    private EditEntryCommand.EditEntryDescriptor descriptor;

    public EditEntryDescriptorBuilder() {
        descriptor = new EditEntryDescriptor();
    }

    public EditEntryDescriptorBuilder(EditEntryDescriptor descriptor) {
        this.descriptor = new EditEntryDescriptor(descriptor);
    }

    public EditEntryDescriptorBuilder(CalendarEntry entry) {
        descriptor = new EditEntryDescriptor();
        descriptor.setEntryTitle(entry.getEntryTitle());
        descriptor.setStartDate(entry.getStartDate());
        descriptor.setEndDate(entry.getEndDate());
        descriptor.setStartTime(entry.getStartTime());
        descriptor.setEndTime(entry.getEndTime());
    }

    /**
     * Sets the {@code EntryTitle} of the {@code EditEntryDescriptor} that we are building.
     */
    public EditEntryDescriptorBuilder withEntryTitle(String entryTitle) {
        descriptor.setEntryTitle(new EntryTitle(entryTitle));
        return this;
    }

    /**
     * Sets the {@code StartDate} of the {@code EditEntryDescriptor} that we are building.
     */
    public EditEntryDescriptorBuilder withStartDate(String startDate) {
        descriptor.setStartDate(new StartDate(startDate));
        return this;
    }

    /**
     * Sets the {@code EndDate} of the {@code EditEntryDescriptor} that we are building.
     */
    public EditEntryDescriptorBuilder withEndDate(String endDate) {
        descriptor.setEndDate(new EndDate(endDate));
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code EditEntryDescriptor} that we are building.
     */
    public EditEntryDescriptorBuilder withStartTime(String startTime) {
        descriptor.setStartTime(new StartTime(startTime));
        return this;
    }

    /**
     * Sets the {@code EndTime} of the {@code EditEntryDescriptor} that we are building.
     */
    public EditEntryDescriptorBuilder withEndTime(String endTime) {
        descriptor.setEndTime(new EndTime(endTime));
        return this;
    }

    public EditEntryDescriptor build() {
        return descriptor;
    }
}

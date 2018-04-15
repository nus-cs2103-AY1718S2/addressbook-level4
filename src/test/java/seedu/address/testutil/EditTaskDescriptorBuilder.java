package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.DateTime;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.Remark;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditTaskDescriptor objects.
 */
public class EditTaskDescriptorBuilder {

    private EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new EditTaskDescriptor();
    }

    public EditTaskDescriptorBuilder(EditCommand.EditTaskDescriptor descriptor) {
        this.descriptor = new EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code activity}'s details
     */
    public EditTaskDescriptorBuilder(Activity activity) {
        descriptor = new EditTaskDescriptor();
        descriptor.setName(activity.getName());
        descriptor.setDateTime(activity.getDateTime());
        descriptor.setRemark(activity.getRemark());
        descriptor.setTags(activity.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDateTime(String dateTime) {
        descriptor.setDateTime(new DateTime(dateTime));
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withRemark(String remark) {
        descriptor.setRemark(new Remark(remark));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditTaskDescriptor}
     * that we are building.
     */
    public EditTaskDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }
}

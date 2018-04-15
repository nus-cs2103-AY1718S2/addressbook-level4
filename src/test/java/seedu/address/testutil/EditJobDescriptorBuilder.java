package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditJobCommand;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobTitle;
import seedu.address.model.job.Location;
import seedu.address.model.skill.Skill;
import seedu.address.model.tag.Tag;

//@@author ChengSashankh
/**
 * A utility class to help with building EditJobDescriptor objects.
 */
public class EditJobDescriptorBuilder {
    private EditJobCommand.EditJobDescriptor descriptor;

    public EditJobDescriptorBuilder() {
        descriptor = new EditJobCommand.EditJobDescriptor();
    }

    public EditJobDescriptorBuilder(EditJobCommand.EditJobDescriptor descriptor) {
        this.descriptor = new EditJobCommand.EditJobDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditJobDescriptor} with fields containing {@code job}'s details
     */
    public EditJobDescriptorBuilder(Job job) {
        descriptor = new EditJobCommand.EditJobDescriptor();
        descriptor.setJobTitle(job.getJobTitle());
        descriptor.setLocation(job.getLocation());
        descriptor.setSkill(job.getSkills());
        descriptor.setTags(job.getTags());
    }

    /**
     * Sets the {@code JobTitle} of the {@code EditJobDescriptor} that we are building.
     */
    public EditJobDescriptorBuilder withJobTitle(String jobTitle) {
        descriptor.setJobTitle(new JobTitle(jobTitle));
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code EditJobDescriptor} that we are building.
     */
    public EditJobDescriptorBuilder withLocation(String location) {
        descriptor.setLocation(new Location(location));
        return this;
    }

    /**
     * Sets the {@code Skills} of the {@code EditJobDescriptor} that we are building.
     */
    public EditJobDescriptorBuilder withSkills(String skills) {
        descriptor.setSkill(new Skill(skills));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditJobDescriptor}
     * that we are building.
     */
    public EditJobDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditJobCommand.EditJobDescriptor build() {
        return descriptor;
    }
}
//@@author

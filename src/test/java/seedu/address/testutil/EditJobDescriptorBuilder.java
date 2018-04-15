// @@author kush1509
package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.job.JobEditCommand.EditJobDescriptor;
import seedu.address.model.job.Job;
import seedu.address.model.job.Location;
import seedu.address.model.job.NumberOfPositions;
import seedu.address.model.job.Position;
import seedu.address.model.job.Team;
import seedu.address.model.skill.Skill;

/**
 * A utility class to help with building EditJobDescriptor objects.
 */
public class EditJobDescriptorBuilder {

    private EditJobDescriptor descriptor;

    public EditJobDescriptorBuilder() {
        descriptor = new EditJobDescriptor();
    }

    public EditJobDescriptorBuilder(EditJobDescriptor descriptor) {
        this.descriptor = new EditJobDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditJobDescriptor} with fields containing {@code job}'s details
     */
    public EditJobDescriptorBuilder(Job job) {
        descriptor = new EditJobDescriptor();
        descriptor.setPosition(job.getPosition());
        descriptor.setTeam(job.getTeam());
        descriptor.setLocation(job.getLocation());
        descriptor.setNumberOfPositions(job.getNumberOfPositions());
        descriptor.setSkills(job.getSkills());
    }

    /**
     * Sets the {@code Position} of the {@code EditJobDescriptor} that we are building.
     */
    public EditJobDescriptorBuilder withPosition(String position) {
        descriptor.setPosition(new Position(position));
        return this;
    }

    /**
     * Sets the {@code Team} of the {@code EditJobDescriptor} that we are building.
     */
    public EditJobDescriptorBuilder withTeam(String team) {
        descriptor.setTeam(new Team(team));
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
     * Sets the {@code NumberOfPositions} of the {@code EditJobDescriptor} that we are building.
     */
    public EditJobDescriptorBuilder withNumberOfPositions(String numberOfPositions) {
        descriptor.setNumberOfPositions(new NumberOfPositions(numberOfPositions));
        return this;
    }

    /**
     * Parses the {@code skills} into a {@code Set<Skill>} and set it to the {@code EditJobDescriptor}
     * that we are building.
     */
    public EditJobDescriptorBuilder withSkills(String... skills) {
        Set<Skill> skillSet = Stream.of(skills).map(Skill::new).collect(Collectors.toSet());
        descriptor.setSkills(skillSet);
        return this;
    }

    public EditJobDescriptor build() {
        return descriptor;
    }
}

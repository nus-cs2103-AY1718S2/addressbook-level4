package seedu.address.testutil;

import seedu.address.logic.commands.RatingEditCommand.EditRatingDescriptor;
import seedu.address.model.person.Rating;

//@@author kexiaowen
/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditRatingDescriptorBuilder {
    private EditRatingDescriptor descriptor;

    public EditRatingDescriptorBuilder() {
        descriptor = new EditRatingDescriptor();
    }

    public EditRatingDescriptorBuilder(EditRatingDescriptor descriptor) {
        this.descriptor = new EditRatingDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditRatingDescriptor} with fields containing {@code rating}'s details
     */
    public EditRatingDescriptorBuilder(Rating rating) {
        descriptor = new EditRatingDescriptor();
        descriptor.setTechnicalSkillsScore(rating.getTechnicalSkillsScore());
        descriptor.setCommunicationSkillsScore(rating.getCommunicationSkillsScore());
        descriptor.setProblemSolvingSkillsScore(rating.getProblemSolvingSkillsScore());
        descriptor.setExperienceScore(rating.getExperienceScore());
    }

    /**
     * Sets the {@code technicalSkillsScore} of the {@code EditRatingDescriptor} that we are building.
     */
    public EditRatingDescriptorBuilder withTechnicalSkillsScore(double technicalSkillsScore) {
        descriptor.setTechnicalSkillsScore(technicalSkillsScore);
        return this;
    }

    /**
     * Sets the {@code communicationSkillsScore} of the {@code EditRatingDescriptor} that we are building.
     */
    public EditRatingDescriptorBuilder withCommunicationSkillsScore(double communicationSkillsScore) {
        descriptor.setCommunicationSkillsScore(communicationSkillsScore);
        return this;
    }

    /**
     * Sets the {@code problemSolvingSkillsScore} of the {@code EditRatingDescriptor} that we are building.
     */
    public EditRatingDescriptorBuilder withProblemSolvingSkillsScore(double problemSolvingSkillsScore) {
        descriptor.setProblemSolvingSkillsScore(problemSolvingSkillsScore);
        return this;
    }

    /**
     * Sets the {@code experienceScore} of the {@code EditRatingDescriptor} that we are building.
     */
    public EditRatingDescriptorBuilder withExperienceScore(double experienceScore) {
        descriptor.setExperienceScore(experienceScore);
        return this;
    }

    public EditRatingDescriptor build() {
        return descriptor;
    }
}

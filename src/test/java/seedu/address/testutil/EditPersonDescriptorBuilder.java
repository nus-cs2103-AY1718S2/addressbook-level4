package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Comment;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExpectedGraduationYear;
import seedu.address.model.person.GradePointAverage;
import seedu.address.model.person.JobApplied;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfileImage;
import seedu.address.model.person.Resume;
import seedu.address.model.person.University;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setUniversity(person.getUniversity());
        descriptor.setExpectedGraduationYear(person.getExpectedGraduationYear());
        descriptor.setMajor(person.getMajor());
        descriptor.setGradePointAverage(person.getGradePointAverage());
        descriptor.setJobApplied(person.getJobApplied());
        descriptor.setResume(person.getResume());
        descriptor.setProfileImage(person.getProfileImage());
        descriptor.setComment(person.getComment());
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code University} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withUniversity(String university) {
        descriptor.setUniversity(new University(university));
        return this;
    }


    /**
     * Sets the {@code ExpectedGraduationYear} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withExpectedGraduationYear(String expectedGraduationYear) {
        descriptor.setExpectedGraduationYear(new ExpectedGraduationYear(expectedGraduationYear));
        return this;
    }

    //@@author tanhengyeow
    /**
     * Sets the {@code Major} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withMajor(String major) {
        descriptor.setMajor(new Major(major));
        return this;
    }

    /**
     * Sets the {@code GradePointAverage} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withGradePointAverage(String gradePointAverage) {
        descriptor.setGradePointAverage(new GradePointAverage(gradePointAverage));
        return this;
    }

    //@@author
    /**
     * Sets the {@code JobApplied} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withJobApplied(String jobApplied) {
        descriptor.setJobApplied(new JobApplied(jobApplied));
        return this;
    }

    /**
     * Sets the {@code Resume} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withResume(String resume) {
        descriptor.setResume(new Resume(resume));
        return this;
    }

    //@@author Ang-YC
    /**
     * Sets the {@code ProfileImage} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withProfileImage(String profileImage) {
        descriptor.setProfileImage(new ProfileImage(profileImage));
        return this;
    }

    /**
     * Sets the {@code Comment} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withComment(String comment) {
        descriptor.setComment(new Comment(comment));
        return this;
    }
    //@@author

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}

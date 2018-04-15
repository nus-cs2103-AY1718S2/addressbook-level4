package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutee.EducationLevel;
import seedu.address.model.tutee.Grade;
import seedu.address.model.tutee.School;
import seedu.address.model.tutee.Subject;
import seedu.address.model.tutee.Tutee;

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
        descriptor.setTags(person.getTags());
    }

    //@@author ChoChihTun
    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code tutee}'s details
     */
    public EditPersonDescriptorBuilder(Tutee tutee) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(tutee.getName());
        descriptor.setPhone(tutee.getPhone());
        descriptor.setEmail(tutee.getEmail());
        descriptor.setAddress(tutee.getAddress());
        descriptor.setSubject(tutee.getSubject());
        descriptor.setGrade(tutee.getGrade());
        descriptor.setEducationLevel(tutee.getEducationLevel());
        descriptor.setSchool(tutee.getSchool());
        descriptor.setTags(tutee.getTags());
    }
    //@@author

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

    //@@author ChoChihTun
    /**
     * Sets the {@code Subject} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withSubject(String subject) {
        descriptor.setSubject(new Subject(subject));
        return this;
    }

    /**
     * Sets the {@code Grade} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withGrade(String grade) {
        descriptor.setGrade(new Grade(grade));
        return this;
    }


    /**
     * Sets the {@code EducationLevel} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEducationLevel(String educationLevel) {
        descriptor.setEducationLevel(new EducationLevel(educationLevel));
        return this;
    }


    /**
     * Sets the {@code School} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withSchool(String school) {
        descriptor.setSchool(new School(school));
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

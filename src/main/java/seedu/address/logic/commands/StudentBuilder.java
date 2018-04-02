package seedu.address.logic.commands;

import static seedu.address.model.student.miscellaneousinfo.ProfilePicturePath.DEFAULT_PROFILE_PICTURE;

import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.programminglanguage.ProgrammingLanguage;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Favourite;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.student.miscellaneousinfo.ProfilePicturePath;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_PROGRAMMING_LANGUAGE = "Java";
    public static final String DEFAULT_TAGS = "friends";
    public static final String DEFAULT_FAVOURITE = "false";
    public static final String DEFAULT_PROFILE_PICTURE_URL = DEFAULT_PROFILE_PICTURE;

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private ProgrammingLanguage programmingLanguage;
    private Set<Tag> tags;
    private Favourite favourite;
    private ProfilePicturePath profilePicturePath;

    public StudentBuilder() throws MalformedURLException {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        programmingLanguage = new ProgrammingLanguage(DEFAULT_PROGRAMMING_LANGUAGE);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
        favourite = new Favourite(DEFAULT_FAVOURITE);
        profilePicturePath = new ProfilePicturePath(DEFAULT_PROFILE_PICTURE_URL);
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
        email = studentToCopy.getEmail();
        address = studentToCopy.getAddress();
        programmingLanguage = studentToCopy.getProgrammingLanguage();
        tags = new HashSet<>(studentToCopy.getTags());
        favourite = studentToCopy.getFavourite();
        profilePicturePath = studentToCopy.getProfilePicturePath();
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     */
    public StudentBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Student} that we are building.
     */
    public StudentBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     */
    public StudentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Student} that we are building.
     */
    public StudentBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Favourite} of the {@code Student} that we are building.
     */
    public StudentBuilder withFavourite(boolean val) {
        this.favourite = new Favourite(val);
        return this;
    }

    /**
     * Sets the {@code programminglanguage} of the {@code Student} that we are building.
     */
    public StudentBuilder withProgrammingLanguage(String progLang) {
        this.programmingLanguage = new ProgrammingLanguage(progLang);
        return this;
    }

    /**
     * Sets the {@code profilePicturePath} of the {@code Student} that we are building.
     */
    public StudentBuilder withProfilePictureUrl(String url) throws MalformedURLException {
        this.profilePicturePath = new ProfilePicturePath(url);
        return this;
    }

    public Student build() throws MalformedURLException {
        return new Student(name, phone, email, address, programmingLanguage, tags, favourite, profilePicturePath);
    }

}

package seedu.address.testutil;

import static java.util.Objects.isNull;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExpectedGraduationYear;
import seedu.address.model.person.InterviewDate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Resume;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {
    private static final String RESUME_PATH = "src/test/resources/resume/";

    private static final String DEFAULT_NAME = "Alice Pauline";
    private static final String DEFAULT_PHONE = "85355255";
    private static final String DEFAULT_EMAIL = "alice@gmail.com";
    private static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    private static final String DEFAULT_EXPECTED_GRADUATION_YEAR = "2020";
    private static final String DEFAULT_TECHNICAL_SKILLS_SCORE = "-1";
    private static final String DEFAULT_COMMUNICATION_SKILLS_SCORE = "-1";
    private static final String DEFAULT_PROBLEM_SOLVING_SKILLS_SCORE = "-1";
    private static final String DEFAULT_EXPERIENCE_SCORE = "-1";
    private static final String DEFAULT_RESUME = "alice.pdf";
    private static final String DEFAULT_TAGS = "friends";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private ExpectedGraduationYear expectedGraduationYear;
    private Rating rating;
    private Resume resume;
    private InterviewDate interviewDate;

    private Set<Tag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        expectedGraduationYear = new ExpectedGraduationYear(DEFAULT_EXPECTED_GRADUATION_YEAR);
        rating = new Rating(Double.valueOf(DEFAULT_TECHNICAL_SKILLS_SCORE),
                Double.valueOf(DEFAULT_COMMUNICATION_SKILLS_SCORE),
                Double.valueOf(DEFAULT_PROBLEM_SOLVING_SKILLS_SCORE),
                Double.valueOf(DEFAULT_EXPERIENCE_SCORE));
        resume = new Resume(formPathFromFileName(DEFAULT_RESUME));
        interviewDate = new InterviewDate();
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        expectedGraduationYear = personToCopy.getExpectedGraduationYear();
        rating = personToCopy.getRating();
        resume = personToCopy.getResume();
        interviewDate = personToCopy.getInterviewDate();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }
    /**
     * Sets the {@code ExpectedGraduationYear} of the {@code Person} that we are building.
     */
    public PersonBuilder withExpectedGraduationYear(String expectedGraduationYear) {
        this.expectedGraduationYear = new ExpectedGraduationYear(expectedGraduationYear);
        return this;
    }

    /**
     * Sets the {@code Rating} of the {@code Person} that we are building.
     */
    public PersonBuilder withRating(String technicalSkillsScore, String communicationSkillsScore,
                                    String problemSolvingSkillsScore, String experienceScore) {
        this.rating = new Rating(Double.valueOf(technicalSkillsScore),
                Double.valueOf(communicationSkillsScore),
                Double.valueOf(problemSolvingSkillsScore),
                Double.valueOf(experienceScore));
        return this;
    }

    /**
     * Sets the {@code Resume} of the {@code Person} that we are building.
     */
    public PersonBuilder withResume(String resume) {
        this.resume = new Resume(resume);
        return this;
    }

    public PersonBuilder withInterviewDate(LocalDateTime interviewDate) {
        this.interviewDate = new InterviewDate(interviewDate);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, expectedGraduationYear, rating, resume, interviewDate, tags);
    }

    /**
     * Forms the resume path from the resume file name
     */
    private static String formPathFromFileName(String fileName) {
        if (isNull(fileName)) {
            return null;
        } else {
            return RESUME_PATH + fileName;
        }
    }

}

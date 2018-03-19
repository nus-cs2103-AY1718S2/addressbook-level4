package seedu.address.storage;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExpectedGraduationYear;
import seedu.address.model.person.InterviewDate;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Resume;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    //compulsory fields
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String expectedGraduationYear;
    @XmlElement(required = true)
    private String major;
    //optional fields
    @XmlElement(nillable = true)
    private String resume;
    @XmlElement(nillable = true)
    private String interviewDate;

    @XmlElement
    private String technicalSkillsScore;
    @XmlElement
    private String communicationSkillsScore;
    @XmlElement
    private String problemSolvingSkillsScore;
    @XmlElement
    private String experienceScore;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address, String expectedGraduationYear,
                            String major, String technicalSkillsScore, String communicationSkillsScore,
                            String problemSolvingSkillsScore, String experienceScore,
                            String resume, String interviewDate, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.expectedGraduationYear = expectedGraduationYear;
        this.major = major;
        this.technicalSkillsScore = technicalSkillsScore;
        this.communicationSkillsScore = communicationSkillsScore;
        this.problemSolvingSkillsScore = problemSolvingSkillsScore;
        this.experienceScore = experienceScore;
        this.resume = resume;
        this.interviewDate = interviewDate;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        expectedGraduationYear = source.getExpectedGraduationYear().value;
        major = source.getMajor().value;
        technicalSkillsScore = Double.toString(source.getRating().technicalSkillsScore);
        communicationSkillsScore = Double.toString(source.getRating().communicationSkillsScore);
        problemSolvingSkillsScore = Double.toString(source.getRating().problemSolvingSkillsScore);
        experienceScore = Double.toString(source.getRating().experienceScore);
        resume = source.getResume().value;
        interviewDate = source.getInterviewDate().toString();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name name = new Name(this.name);

        if (this.phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(this.phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone phone = new Phone(this.phone);

        if (this.email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(this.email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email email = new Email(this.email);

        if (this.address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(this.address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address address = new Address(this.address);

        if (this.expectedGraduationYear == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExpectedGraduationYear.class.getSimpleName()));
        }
        if (!ExpectedGraduationYear.isValidExpectedGraduationYear(this.expectedGraduationYear)) {
            throw new IllegalValueException(ExpectedGraduationYear.MESSAGE_EXPECTED_GRADUATION_YEAR_CONSTRAINTS);
        }
        final ExpectedGraduationYear expectedGraduationYear = new ExpectedGraduationYear(this.expectedGraduationYear);

        if (this.major == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Major.class.getSimpleName()));
        }
        if (!Major.isValidMajor(this.major)) {
            throw new IllegalValueException(Major.MESSAGE_MAJOR_CONSTRAINTS);
        }
        final Major major = new Major(this.major);

        if (technicalSkillsScore == null || communicationSkillsScore == null
                || technicalSkillsScore == null || experienceScore == null
                || !Rating.isValidOrDefaultScore(Double.valueOf(technicalSkillsScore))
                || !Rating.isValidOrDefaultScore(Double.valueOf(communicationSkillsScore))
                || !Rating.isValidOrDefaultScore(Double.valueOf(problemSolvingSkillsScore))
                || !Rating.isValidOrDefaultScore(Double.valueOf(experienceScore))) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        final Rating rating = new Rating(Double.valueOf(technicalSkillsScore), Double.valueOf(communicationSkillsScore),
                Double.valueOf(problemSolvingSkillsScore), Double.valueOf(experienceScore));

        if (!isNull(this.resume) && !Resume.isValidResume(this.resume)) {
            throw new IllegalValueException(Resume.MESSAGE_RESUME_CONSTRAINTS);
        }
        final Resume resume = new Resume(this.resume);

        InterviewDate interviewDate = new InterviewDate();
        if (!isNull(this.interviewDate)) {
            try {
                interviewDate = new InterviewDate(Long.parseLong(this.interviewDate, 10));
            } catch (NumberFormatException nfe) {
                throw new IllegalValueException(InterviewDate.MESSAGE_INTERVIEW_DATE_XML_ERROR);
            }
        }

        final Set<Tag> tags = new HashSet<>(personTags);
        return new Person(name, phone, email, address, expectedGraduationYear,
                major, rating, resume, interviewDate, tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedPerson otherPerson = (XmlAdaptedPerson) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(phone, otherPerson.phone)
                && Objects.equals(email, otherPerson.email)
                && Objects.equals(address, otherPerson.address)
                && Objects.equals(expectedGraduationYear, otherPerson.expectedGraduationYear)
                && Objects.equals(major, otherPerson.major)
                && Objects.equals(interviewDate, otherPerson.interviewDate)
                && tagged.equals(otherPerson.tagged);
    }
}

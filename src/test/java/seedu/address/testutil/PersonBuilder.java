package seedu.address.testutil;

import static java.util.Objects.isNull;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.parser.ResumeUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Comment;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExpectedGraduationYear;
import seedu.address.model.person.GradePointAverage;
import seedu.address.model.person.InterviewDate;
import seedu.address.model.person.JobApplied;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfileImage;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Resume;
import seedu.address.model.person.Status;
import seedu.address.model.person.University;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {
    private static final String RESUME_PATH = "src/test/resources/resume/";
    private static final String PROFILE_IMAGE_PATH = "src/test/resources/photos/";

    private static final String DEFAULT_NAME = "Alice Pauline";
    private static final String DEFAULT_PHONE = "85355255";
    private static final String DEFAULT_EMAIL = "alice@gmail.com";
    private static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    private static final String DEFAULT_UNIVERSITY = "NUS";
    private static final String DEFAULT_EXPECTED_GRADUATION_YEAR = "2020";
    private static final String DEFAULT_MAJOR = "Computer Science";
    private static final String DEFAULT_GRADE_POINT_AVERAGE = "4.96";
    private static final String DEFAULT_JOB_APPLIED = "Software Engineer";
    private static final String DEFAULT_TECHNICAL_SKILLS_SCORE = "-1";
    private static final String DEFAULT_COMMUNICATION_SKILLS_SCORE = "-1";
    private static final String DEFAULT_PROBLEM_SOLVING_SKILLS_SCORE = "-1";
    private static final String DEFAULT_EXPERIENCE_SCORE = "-1";
    private static final String DEFAULT_RESUME = "alice.pdf";
    private static final String DEFAULT_PROFILE_IMAGE = "gates.jpg";
    private static final String DEFAULT_COMMENT = "Some comments";
    private static final String DEFAULT_TAGS = "friends";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private University university;
    private ExpectedGraduationYear expectedGraduationYear;
    private Major major;
    private GradePointAverage gradePointAverage;
    private JobApplied jobApplied;
    private Rating rating;
    private Resume resume;
    private ProfileImage profileImage;
    private Comment comment;
    private InterviewDate interviewDate;
    private Status status;

    private Set<Tag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);

        address = new Address(DEFAULT_ADDRESS);
        university = new University(DEFAULT_UNIVERSITY);
        expectedGraduationYear = new ExpectedGraduationYear(DEFAULT_EXPECTED_GRADUATION_YEAR);
        major = new Major(DEFAULT_MAJOR);
        gradePointAverage = new GradePointAverage(DEFAULT_GRADE_POINT_AVERAGE);
        jobApplied = new JobApplied(DEFAULT_JOB_APPLIED);
        rating = new Rating(Double.valueOf(DEFAULT_TECHNICAL_SKILLS_SCORE),
                Double.valueOf(DEFAULT_COMMUNICATION_SKILLS_SCORE),
                Double.valueOf(DEFAULT_PROBLEM_SOLVING_SKILLS_SCORE),
                Double.valueOf(DEFAULT_EXPERIENCE_SCORE));
        resume = new Resume(formPathFromFileName(DEFAULT_RESUME));
        try {
            resume = ResumeUtil.process(resume);
        } catch (IOException ioe) {
            throw new AssertionError("Testing environment should not be read only!");
        }
        profileImage = new ProfileImage(formImagePathFromFileName(DEFAULT_PROFILE_IMAGE));
        comment = new Comment(DEFAULT_COMMENT);
        interviewDate = new InterviewDate();
        status = new Status();
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
        university = personToCopy.getUniversity();
        expectedGraduationYear = personToCopy.getExpectedGraduationYear();
        major = personToCopy.getMajor();
        gradePointAverage = personToCopy.getGradePointAverage();
        jobApplied = personToCopy.getJobApplied();
        rating = personToCopy.getRating();
        resume = personToCopy.getResume();
        profileImage = personToCopy.getProfileImage();
        comment = personToCopy.getComment();
        interviewDate = personToCopy.getInterviewDate();
        status = personToCopy.getStatus();
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
     * Sets the {@code University} of the {@code Person} that we are building.
     */
    public PersonBuilder withUniversity(String university) {
        this.university = new University(university);
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
     * Sets the {@code Major} of the {@code Person} that we are building.
     */
    public PersonBuilder withMajor(String major) {
        this.major = new Major(major);
        return this;
    }

    /**
     * Sets the {@code GradePointAverage} of the {@code Person} that we are building.
     */
    public PersonBuilder withGradePointAverage(String gradePointAverage) {
        this.gradePointAverage = new GradePointAverage(gradePointAverage);
        return this;
    }

    /**
     * Sets the {@code JobApplied} of the {@code Person} that we are building.
     */
    public PersonBuilder withJobApplied(String jobApplied) {
        this.jobApplied = new JobApplied(jobApplied);
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
        try {
            this.resume = ResumeUtil.process(this.resume);
        } catch (IOException ioe) {
            throw new AssertionError("Testing environment should not be read only!");
        }
        return this;
    }

    /**
     * Sets the {@code Resume} of the {@code Person} that we are building.
     */
    public PersonBuilder withResumeLazy(String resume) {
        this.resume = new Resume(resume);
        return this;
    }

    //@@author Ang-YC
    /**
     * Sets the {@code ProfileImage} of the {@code Person} that we are building.
     */
    public PersonBuilder withProfileImage(String profileImage) {
        this.profileImage = new ProfileImage(profileImage);
        return this;
    }

    /**
     * Sets the {@code Comment} of the {@code Person} that we are building.
     */
    public PersonBuilder withComment(String comment) {
        this.comment = new Comment(comment);
        return this;
    }

    /**
     * Sets the {@code InterviewDate} of the {@code Person} that we are building.
     */
    public PersonBuilder withInterviewDate(LocalDateTime interviewDate) {
        this.interviewDate = new InterviewDate(interviewDate);
        return this;
    }
    //@@author

    /**
     * Sets the {@code Status} of the {@code Person} that we are building.
     */
    public PersonBuilder withStatus(int statusIndex) {
        this.status = new Status(statusIndex);
        return this;
    }

    /**
     * Builds and returns a {@code Person}.
     */
    public Person build() {
        return new Person(name, phone, email, address, university, expectedGraduationYear, major, gradePointAverage,
                jobApplied, rating, resume, profileImage, comment, interviewDate, status, tags);
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

    /**
     * Forms the image path from image file name
     */
    private static String formImagePathFromFileName(String fileName) {
        if (isNull(fileName)) {
            return null;
        }
        return PROFILE_IMAGE_PATH + fileName;
    }
}

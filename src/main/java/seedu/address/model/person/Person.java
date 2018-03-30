package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;
    private final ExpectedGraduationYear expectedGraduationYear;
    private final Major major;
    private final GradePointAverage gradePointAverage;
    private final JobApplied jobApplied;
    private final Rating rating;
    private final Resume resume;
    private final ProfileImage profileImage;
    private final Comment comment;
    private final InterviewDate interviewDate;
    private final Status status;

    private final UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, ExpectedGraduationYear expectedGraduationYear,
                  Major major, GradePointAverage gradePointAverage, JobApplied jobApplied, Rating rating,
                  Resume resume, ProfileImage profileImage, Comment comment, InterviewDate interviewDate, Status status,
                  Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, expectedGraduationYear, major, gradePointAverage, jobApplied,
                rating, resume, profileImage, comment, interviewDate, status, tags);

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.expectedGraduationYear = expectedGraduationYear;
        this.major = major;
        this.gradePointAverage = gradePointAverage;
        this.jobApplied = jobApplied;
        this.rating = rating;
        this.resume = resume;
        this.profileImage = profileImage;
        this.comment = comment;
        this.interviewDate = interviewDate;
        this.status = status;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public ExpectedGraduationYear getExpectedGraduationYear() {
        return expectedGraduationYear;
    }

    public Major getMajor() {
        return major;
    }

    public GradePointAverage getGradePointAverage() {
        return gradePointAverage;
    }

    public JobApplied getJobApplied() {
        return jobApplied;
    }

    public Rating getRating() {
        return rating;
    }

    public Resume getResume() {
        return resume;
    }

    public ProfileImage getProfileImage() {
        return profileImage;
    }

    public Comment getComment() {
        return comment;
    }

    public InterviewDate getInterviewDate() {
        return interviewDate;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        // Resume and Profile image does not constitute the equality condition
        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(this.getName())
                && otherPerson.getPhone().equals(this.getPhone())
                && otherPerson.getEmail().equals(this.getEmail())
                && otherPerson.getAddress().equals(this.getAddress())
                && otherPerson.getExpectedGraduationYear().equals(this.getExpectedGraduationYear())
                && otherPerson.getMajor().equals(this.getMajor())
                && otherPerson.getGradePointAverage().equals(this.getGradePointAverage())
                && otherPerson.getComment().equals(this.getComment());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, expectedGraduationYear, major,
                gradePointAverage, jobApplied, rating, resume, profileImage, comment, interviewDate, status, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Expected graduation year: ")
                .append(getExpectedGraduationYear())
                .append(" Major: ")
                .append(getMajor())
                .append(" Grade point average: ")
                .append(getGradePointAverage())
                .append(" Job applied: ")
                .append(getJobApplied())
                .append(" Resume: ")
                .append(getResume())
                .append(" Profile image: ")
                .append(getProfileImage())
                .append(" Comment: ")
                .append(getComment())
                .append(" Status: ")
                .append(getStatus())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Compares the overall ratings of two {@code Person} objects.
     * @param p1
     * @param p2
     * @return 1 if p1 has a higher overall rating, 0 if p1 and p2 have equal overall rating and -1 otherwise.
     */
    public static int compareByOverallRating(Person p1, Person p2) {
        return Double.compare(p1.getRating().getOverallScore(),
                p2.getRating().getOverallScore());
    }
}

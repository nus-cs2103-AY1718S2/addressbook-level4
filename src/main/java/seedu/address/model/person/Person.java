package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.subject.Subject;
import seedu.address.model.subject.UniqueSubjectList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    private final Name name;
    private final Nric nric;

    private final UniqueTagList tags;
    private final UniqueSubjectList subjects;
    private final Remark remark;
    private final Cca cca;
    private final InjuriesHistory injuriesHistory;
    private final NextOfKin nextOfKin;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Nric nric, Set<Tag> tags, Set<Subject> subjects, Remark remark, Cca cca,
                  InjuriesHistory injuriesHistory, NextOfKin nextOfKin) {
        requireAllNonNull(name, nric, tags, subjects, cca);
        this.name = name;
        this.nric = nric;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.subjects = new UniqueSubjectList(subjects);
        this.remark = remark;
        this.cca = cca;
        this.injuriesHistory = injuriesHistory;
        this.nextOfKin = nextOfKin;
    }

    public Name getName() {
        return name;
    }

    public Nric getNric() {
        return nric;
    }

    public Remark getRemark() {
        return remark;
    }

    public Cca getCca() {
        return cca;
    }

    public InjuriesHistory getInjuriesHistory() {
        return injuriesHistory;
    }

    public NextOfKin getNextOfKin() {
        return nextOfKin;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    public List<Tag> getTagArray () {
        Set<Tag> tagSet = getTags();
        List<Tag> listTag = new ArrayList<>();
        listTag.addAll(tagSet);
        return listTag;
    }

    public Set<Subject> getSubjects() {
        return Collections.unmodifiableSet(subjects.toSet());
    }

    public List<Subject> getSubjectArray () {
        Set<Subject> set = getSubjects();
        List<Subject> list = new ArrayList<>();
        list.addAll(set);
        return list;
    }

    //@@author TeyXinHui
    /**
     * Calculates the lowest possible score from the grades of the subjects of the selected person.
     * @return L1R5 score
     */
    public int calculateL1R5() {
        int score = 0;
        Set<Subject> subjects = new HashSet<>(this.getSubjects());
        Set<Subject> subjectsToCheck = new HashSet<>();
        for (int i = 0; i < 6; i++) {
            for (Subject subject: subjects) {
                switch (i) {
                // Check if the subject is a L1 subject
                case 0:
                    if (Arrays.asList(Subject.L1_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                // Check if the subject is a R1 subject
                case 1:
                    if (Arrays.asList(Subject.R1_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                // Check if the subject is a R2 subject
                case 2:
                    if (Arrays.asList(Subject.R2_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                // Check if the subject is a R3 subject
                case 3:
                    if (Arrays.asList(Subject.R3_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                // Check if the subject is a R4 or R5 subject
                case 4:
                case 5:
                    if (Arrays.asList(Subject.R4_R5_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                default:
                    break;
                }
            }
            // Check that if the student has at least one subject in each L1R5 category, else return error message
            if (checkLowest(subjectsToCheck, subjects) == 10) {
                return 0;
            } else {
                score += checkLowest(subjectsToCheck, subjects);
            }
            subjectsToCheck.clear();
        }
        return score;
    }

    //@@author TeyXinHui
    /**
     * Calculates the lowest possible score from the grades of the subjects of the selected person.
     * @return L1B4-A score
     */
    public int calculateL1B4A() {
        int score = 0;
        Set<Subject> subjects = new HashSet<>(this.getSubjects());
        Set<Subject> subjectsToCheck = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            for (Subject subject: subjects) {
                switch (i) {
                // Check if the subject is a L1 subject
                case 0:
                    if (Arrays.asList(Subject.L1_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                // Check if the subject is a R1 subject
                case 1:
                    if (Arrays.asList(Subject.R1A_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                // Check if the subject is a R2 subject
                case 2:
                    if (Arrays.asList(Subject.R2A_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                // Check if the subject is a R3 or R4 subject
                case 3:
                case 4:
                    if (Arrays.asList(Subject.R3_R4_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                default:
                    break;
                }
            }
            // Check that if the student has at least one subject in each L1R5 category, else return error message
            if (checkLowest(subjectsToCheck, subjects) == 10) {
                return 0;
            } else {
                score += checkLowest(subjectsToCheck, subjects);
            }
            subjectsToCheck.clear();
        }
        return score;
    }
    //@@author TeyXinHui
    /**
     * Calculates the lowest possible score from the grades of the subjects of the selected person.
     * @return L1B4-B score
     */
    public int calculateL1B4B() {
        int score = 0;
        Set<Subject> subjects = new HashSet<>(this.getSubjects());
        Set<Subject> subjectsToCheck = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            for (Subject subject: subjects) {
                switch (i) {
                // Check if the subject is a L1 subject
                case 0:
                    if (Arrays.asList(Subject.L1_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                // Check if the subject is a R1 subject
                case 1:
                    if (Arrays.asList(Subject.R1BCD_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                // Check if the subject is a R2 subject
                case 2:
                    if (Arrays.asList(Subject.R2B_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                // Check if the subject is a R3 or R4 subject
                case 3:
                case 4:
                    if (Arrays.asList(Subject.R3_R4_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                default:
                    break;
                }
            }
            // Check that if the student has at least one subject in each L1R5 category, else return error message
            if (checkLowest(subjectsToCheck, subjects) == 10) {
                return 0;
            } else {
                score += checkLowest(subjectsToCheck, subjects);
            }
            subjectsToCheck.clear();
        }
        return score;
    }
    //@@author TeyXinHui
    /**
     * Calculates the lowest possible score from the grades of the subjects of the selected person.
     * @return L1B4-C score
     */
    public int calculateL1B4C() {
        int score = 0;
        Set<Subject> subjects = new HashSet<>(this.getSubjects());
        Set<Subject> subjectsToCheck = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            for (Subject subject: subjects) {
                switch (i) {
                // Check if the subject is a L1 subject
                case 0:
                    if (Arrays.asList(Subject.L1_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                // Check if the subject is a R1 subject
                case 1:
                    if (Arrays.asList(Subject.R1BCD_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                // Check if the subject is a R2 subject
                case 2:
                    if (Arrays.asList(Subject.R2C_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                // Check if the subject is a R3 or R4 subject
                case 3:
                case 4:
                    if (Arrays.asList(Subject.R3_R4_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                default:
                    break;
                }
            }
            // Check that if the student has at least one subject in each L1R5 category, else return 0
            if (checkLowest(subjectsToCheck, subjects) == 10) {
                return 0;
            } else {
                score += checkLowest(subjectsToCheck, subjects);
            }
            subjectsToCheck.clear();
        }
        return score;
    }
    //@@author TeyXinHui
    /**
     * Calculates the lowest possible score from the grades of the subjects of the selected person.
     * @return L1B4-D score
     */
    public int calculateL1B4D() {
        int score = 0;
        Set<Subject> subjects = new HashSet<>(this.getSubjects());
        Set<Subject> subjectsToCheck = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            for (Subject subject: subjects) {
                switch (i) {
                // Check if the subject is a L1 subject
                case 0:
                    if (Arrays.asList(Subject.L1_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                // Check if the subject is a R1 subject
                case 1:
                    if (Arrays.asList(Subject.R1BCD_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                // Check if the subject is a R2 subject
                case 2:
                    if (Arrays.asList(Subject.R2D_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                // Check if the subject is a R3 or R4 subject
                case 3:
                case 4:
                    if (Arrays.asList(Subject.R3_R4_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                default:
                    break;
                }
            }
            // Check that if the student has at least one subject in each L1R5 category, else return error message
            if (checkLowest(subjectsToCheck, subjects) == 10) {
                return 0;
            } else {
                score += checkLowest(subjectsToCheck, subjects);
            }
            subjectsToCheck.clear();
        }
        return score;
    }
    //@@author TeyXinHui
    /**
     * Takes in a set of subjects under the category of L1 or R5 and find the smallest grade score.
     * Removes the best subject from the full list of subjects of the student to prevent the same subject being
     * considered in the L1R5 score more than once.
     * @param subjectsToCheck
     * @return lowest grade score
     */
    public static int checkLowest(Set<Subject> subjectsToCheck, Set<Subject> subjects) {
        int lowest = 10;
        Subject bestSubject = new Subject();
        for (Subject subject: subjectsToCheck) {
            if (Character.getNumericValue(subject.subjectGrade.charAt(1)) < lowest) {
                lowest = Character.getNumericValue(subject.subjectGrade.charAt(1));
                bestSubject = subject;
            }
        }
        subjects.remove(bestSubject);
        return lowest;
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(this.getName())
                && otherPerson.getNric().equals(this.getNric());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, nric, tags, subjects, remark, cca, injuriesHistory, nextOfKin);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Nric: ")
                .append(getNric())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Subjects: ");
        getSubjects().forEach(builder::append);
        builder.append(" Remarks: ")
                .append(getRemark());
        builder.append(" Cca: ").append(getCca());
        builder.append(" InjuriesHistory: ").append(getInjuriesHistory());
        builder.append(" NameOfKin: ").append(getNextOfKin());
        return builder.toString();
    }

}

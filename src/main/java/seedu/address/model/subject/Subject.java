package seedu.address.model.subject;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a subject in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Subject {

    public static final String[] SUBJECT_NAME = new String[] {"English", "Chinese", "H.Chinese", "Chinese.SP",
                                                              "Chinese.B", "French", "German", "Spanish", "Hindi",
                                                              "Urdu", "Gujarati", "Panjabi", "Bengali", "Burmese",
                                                              "Thai", "Japanese", "B.Indonesia", "Tamil",
                                                              "H.Tamil", "Tamil.B", "Malay", "H.Malay", "Malay.B",
                                                              "Malay.SP", "Mathematics", "A.Mathematics",
                                                              "Physics", "Chemistry", "Biology", "Science",
                                                              "History", "Geography", "C.Humanities", "E.Literature",
                                                              "C.Literature", "M.Literature", "T.Literature", "Music",
                                                              "H.Music", "Art", "H.Art", "DnT", "Computing",
                                                              "F&N", "POA", "Economics", "Drama", "P.Education",
                                                              "Business.S", "Biotechnology", "Design.S"};
    public static final String MESSAGE_SUBJECT_NAME_CONSTRAINTS = "Subject names should be alphabetic and should be "
            + "one of the following: " + String.join(", ", SUBJECT_NAME) + ".";
    public static final String[] SUBJECT_GRADE = new String[] {"A1", "A2", "B3", "B4", "C5", "C6", "D7", "E8", "F9"};
    public static final String MESSAGE_SUBJECT_GRADE_CONSTRAINTS = "Subject grade should be alphanumeric and should be"
            + " one of the following: \n" + String.join(", ", SUBJECT_GRADE) + ".";

    // Use for the calculation of the L1R5 subjects
    public static final String[] L1_SUBJECT = {"English", "H.Chinese", "H.Tamil", "H.Malay"};
    public static final String[] R1_SUBJECT = {"History", "Geography", "C.Humanities",  "E.Literature", "C.Literature",
                                               "M.Literature", "T.Literature", "H.Art", "H.Music", "B.Indonesia",
                                               "Chinese.SP", "Malay.SP"};
    public static final String[] R2_SUBJECT = {"Mathematics", "A.Mathematics", "Physics", "Chemistry", "Biology",
                                               "Science"};
    public static final String[] R3_SUBJECT = {"History", "Geography", "C.Humanities",  "E.Literature", "C.Literature",
                                               "M.Literature", "T.Literature", "H.Art", "H.Music", "B.Indonesia",
                                               "Chinese.SP", "Malay.SP", "Mathematics", "A.Mathematics", "Physics",
                                               "Chemistry", "Biology", "Science"};
    public static final String[] R4_R5_SUBJECT = SUBJECT_NAME;

    public final String subjectName;
    public final String subjectGrade;

    /**
     * Default constructor of Subject Object
     */
    public Subject() {
        this.subjectName = "";
        this.subjectGrade = "";
    }
    /**
     * Constructs a {@code Subject}.
     *
     * @param subjectName A valid subject name.
     * @param subjectGrade A valid subject grade.
     */
    public Subject(String subjectName, String subjectGrade) {
        requireNonNull(subjectName);
        checkArgument(isValidSubjectName(subjectName), MESSAGE_SUBJECT_NAME_CONSTRAINTS);
        this.subjectName = subjectName;

        requireNonNull(subjectGrade);
        checkArgument(isValidSubjectGrade(subjectGrade), MESSAGE_SUBJECT_GRADE_CONSTRAINTS);
        this.subjectGrade = subjectGrade;
    }

    /**
     * Constructs a {@code Subject} by splitting the subject string into {@code subjectName}.
     *
     * @param subject A valid subject string.
     */
    public Subject(String subject) {
        requireNonNull(subject);
        String[] splitSubjectStr = subject.trim().split("\\s+");
        String subjectName = splitSubjectStr[0];
        String subjectGrade = splitSubjectStr[1];
        this.subjectName = subjectName;
        this.subjectGrade = subjectGrade;
    }

    /**
     * Returns true if a given string is a valid subject name.
     */
    public static boolean isValidSubjectName(String test) {
        for (String validSubjectName: SUBJECT_NAME) {
            if (test.equals(validSubjectName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if a given string is a valid subject grade.
     */

    public static boolean isValidSubjectGrade(String test) {
        for (String validSubjectGrade: SUBJECT_GRADE) {
            if (test.equals(validSubjectGrade)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        //return other == this // short circuit if same object
        //        || (other instanceof Subject // instanceof handles nulls
        //        && this.subjectName.equals(((Subject) other).subjectName)); // state check
        if (other == null) {
            return false;
        }
        if (this == other) { //same object
            return true;
        }
        if (!(other instanceof Subject)) {
            return false;
        }
        Subject object = (Subject) other;
        return this.subjectName == object.subjectName && this.subjectGrade == object.subjectGrade;
    }

    @Override
    public int hashCode() {
        //return subjectName.hashCode() && subjectGrade.hashCode();
        int hash = 17;
        hash = 37 * hash + subjectName.hashCode();
        hash = 37 * hash + subjectGrade.hashCode();
        return hash;
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + subjectName + ' ' + subjectGrade + ']';
    }

}

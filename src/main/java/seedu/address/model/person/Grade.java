package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.HashSet;
import java.util.Set;

//@@author shookshire
/**
 * Represents a Client's related Grade (the year of study eg. Primary 4, Secondary 3) in the TuitionCor.
 * Guarantees: immutable; is valid as declared in {@link #isValidGrade(String)}
 */
public class Grade {
    public static final String MESSAGE_WORD_KINDERGARTEN = "Kindergarten";
    public static final String MESSAGE_WORD_KINDERGARTEN_ALIAS = "K";
    public static final String MESSAGE_WORD_PRIMARY = "Primary";
    public static final String MESSAGE_WORD_PRIMARY_ALIAS = "P";
    public static final String MESSAGE_WORD_SECONDARY = "Secondary";
    public static final String MESSAGE_WORD_SECONDARY_ALIAS = "S";
    public static final String MESSAGE_WORD_TERTIARY = "Tertiary";
    public static final String MESSAGE_WORD_TERTIARY_ALIAS = "J";
    public static final String MESSAGE_WORD_UNIVERSITY = "University";
    public static final String MESSAGE_WORD_UNIVERSITY_ALIAS = "U";

    private static final String KINDERGARTEN_REGEX = "kindergarten";
    private static final String KINDERGARTEN_ALIAS_REGEX = "k";
    private static final String PRIMARY_REGEX = "primary";
    private static final String PRIMARY_ALIAS_REGEX = "p";
    private static final String SECONDARY_REGEX = "secondary";
    private static final String SECONDARY_ALIAS_REGEX = "s";
    private static final String TERTIARY_REGEX = "tertiary";
    private static final String TERTIARY_ALIAS_REGEX = "j";
    private static final String UNIVERSITY_REGEX = "university";
    private static final String UNIVERSITY_ALIAS_REGEX = "u";

    private static final String GRADE_VALIDATION_REGEX_KINDERGARTEN_FULL = "(?i)"
            + MESSAGE_WORD_KINDERGARTEN + "[1-3]";
    private static final String GRADE_VALIDATION_REGEX_KINDERGARTEN_ALIAS = "(?i)"
            + MESSAGE_WORD_KINDERGARTEN_ALIAS + "[1-3]";
    private static final String GRADE_VALIDATION_REGEX_PRIMARY_FULL = "(?i)"
            + MESSAGE_WORD_PRIMARY + "[1-6]";
    private static final String GRADE_VALIDATION_REGEX_PRIMARY_ALIAS = "(?i)"
            + MESSAGE_WORD_PRIMARY_ALIAS + "[1-6]";
    private static final String GRADE_VALIDATION_REGEX_SECONDARY_FULL = "(?i)"
            + MESSAGE_WORD_SECONDARY  + "[1-5]";
    private static final String GRADE_VALIDATION_REGEX_SECONDARY_ALIAS = "(?i)"
            + MESSAGE_WORD_SECONDARY_ALIAS + "[1-5]";
    private static final String GRADE_VALIDATION_REGEX_TERTIARY_FULL = "(?i)"
            + MESSAGE_WORD_TERTIARY  + "[1-2]";
    private static final String GRADE_VALIDATION_REGEX_TERTIARY_ALIAS = "(?i)"
            + MESSAGE_WORD_TERTIARY_ALIAS + "[1-2]";
    private static final String GRADE_VALIDATION_REGEX_UNIVERSITY_FULL = "(?i)"
            + MESSAGE_WORD_UNIVERSITY  + "[1-4]";
    private static final String GRADE_VALIDATION_REGEX_UNIVERSITY_ALIAS = "(?i)"
            + MESSAGE_WORD_UNIVERSITY_ALIAS + "[1-4]";

    public static final String MESSAGE_GRADE_CONSTRAINTS =
            "Grades accepted are " + MESSAGE_WORD_KINDERGARTEN + " (1-3)"
                    + ", " + MESSAGE_WORD_PRIMARY + " (1-6)"
                    + ", " + MESSAGE_WORD_SECONDARY + " (1-5)"
                    + ", " + MESSAGE_WORD_TERTIARY + " (1-2)"
                    + ", " + MESSAGE_WORD_UNIVERSITY + " (1-4).\n"
                    + "Alias acceptable are " + MESSAGE_WORD_KINDERGARTEN_ALIAS + " for " + MESSAGE_WORD_KINDERGARTEN
                    + ", " + MESSAGE_WORD_PRIMARY_ALIAS + " for " + MESSAGE_WORD_PRIMARY
                    + ", " + MESSAGE_WORD_SECONDARY_ALIAS + " for " + MESSAGE_WORD_SECONDARY
                    + ", " + MESSAGE_WORD_TERTIARY_ALIAS + " for " + MESSAGE_WORD_TERTIARY
                    + ", " + MESSAGE_WORD_UNIVERSITY_ALIAS + " for " + MESSAGE_WORD_UNIVERSITY + ".\n"
                    + "Examples of valid input for grade: " + MESSAGE_WORD_KINDERGARTEN  + "1"
                    + " or " + MESSAGE_WORD_KINDERGARTEN_ALIAS + "1" + ", "
                    + MESSAGE_WORD_TERTIARY  + "2"
                    + " or " + MESSAGE_WORD_TERTIARY_ALIAS + "2.\n"
                    + "multiple grades should be typed with a single space between them "
                    + "in decreasing order of preferences with no repetitions";

    private static final int levelIndex = 0;
    private static final int yearIndex = 1;

    public final String value;

    public final int valueWeightage; //Stores the int value weightage of only the first grade in the list

    /**
     * Constructs an {@code Grade}.
     *
     * @param grade A valid grade.
     */
    public Grade(String grade) {
        requireNonNull(grade);
        checkArgument(isValidGrade(grade), MESSAGE_GRADE_CONSTRAINTS);
        this.value = grade.trim().replaceAll(" +", " ");
        this.valueWeightage = getGradeIndex(this.value);
    }

    /**
     * @return an int value base on the grade or the first grade in a string of multiple grades
     */
    public static int getGradeIndex(String value) {
        final String levelField = getGradeFields(value)[levelIndex].toLowerCase();

        int tempIndex = 0;

        switch (levelField) {
        case KINDERGARTEN_REGEX:
            tempIndex += 1;
            break;

        case KINDERGARTEN_ALIAS_REGEX:
            tempIndex += 1;
            break;

        case PRIMARY_REGEX:
            tempIndex += 4;
            break;

        case PRIMARY_ALIAS_REGEX:
            tempIndex += 4;
            break;

        case SECONDARY_REGEX:
            tempIndex += 10;
            break;

        case SECONDARY_ALIAS_REGEX:
            tempIndex += 10;
            break;

        case TERTIARY_REGEX:
            tempIndex += 15;
            break;

        case TERTIARY_ALIAS_REGEX:
            tempIndex += 15;
            break;

        case UNIVERSITY_REGEX:
            tempIndex += 17;
            break;

        case UNIVERSITY_ALIAS_REGEX:
            tempIndex += 17;
            break;

        default:
            throw new AssertionError("It should not be possible to reach here");
        }

        tempIndex += (Integer.parseInt(getGradeFields(value)[yearIndex]) - 1);

        return tempIndex;
    }

    /**
     * Returns true if a given string is a valid client Grade.
     */
    public static boolean isValidGradeRegex(String test) {
        return test.matches(GRADE_VALIDATION_REGEX_KINDERGARTEN_ALIAS)
                || test.matches(GRADE_VALIDATION_REGEX_KINDERGARTEN_FULL)
                || test.matches(GRADE_VALIDATION_REGEX_PRIMARY_ALIAS)
                || test.matches(GRADE_VALIDATION_REGEX_PRIMARY_FULL)
                || test.matches(GRADE_VALIDATION_REGEX_SECONDARY_ALIAS)
                || test.matches(GRADE_VALIDATION_REGEX_SECONDARY_FULL)
                || test.matches(GRADE_VALIDATION_REGEX_TERTIARY_ALIAS)
                || test.matches(GRADE_VALIDATION_REGEX_TERTIARY_FULL)
                || test.matches(GRADE_VALIDATION_REGEX_UNIVERSITY_ALIAS)
                || test.matches(GRADE_VALIDATION_REGEX_UNIVERSITY_FULL);

    }

    /**
     * Returns true if all of the given string is a valid client Grade.
     */
    public static boolean isValidGrade(String test) {
        if (test.matches("\\s+")) {
            return false;
        }
        String[] splitGrade = test.split("\\s+");
        Set<String> isUnique = new HashSet<>();
        Set<Integer> isUniqueWeightage = new HashSet<>();

        boolean isValid = true;
        for (String ss : splitGrade) {
            if (isValid) {
                isValid = isValidGradeRegex(ss);
                isUnique.add(ss);
            }

            if (isValid) {
                isUniqueWeightage.add(getGradeIndex(ss));
            }
        }
        if (isUnique.size() != splitGrade.length || isUniqueWeightage.size() != splitGrade.length) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * @return gradeFields of only the first Grade in the string in terms of an array containing
     * Level(Primary,Secondary..) and Year(1,2...
     */
    private static String[] getGradeFields(String value) {
        String[] allGrades = value.split("\\s+");
        String[] gradeFields =  allGrades[0].split("(?=[\\d])");

        checkArgument(gradeFields.length == 2, "Error in grade fields format.");

        String temp = gradeFields[levelIndex];
        gradeFields[levelIndex] = temp.trim();
        gradeFields[yearIndex].trim();

        return gradeFields;
    }

    /**
     * @return an array containing all the grade weightage of the individual grades
     */
    public static int[] getAllGradeWeightage(String value) {
        String[] allGrades = value.split("\\s+");
        int[] allGradeWeightage = new int[allGrades.length];

        for (int i = 0; i < allGrades.length; i++) {
            allGradeWeightage[i] = getGradeIndex(allGrades[i]);
        }
        return allGradeWeightage;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Grade // instanceof handles nulls
                && this.value.equals(((Grade) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

package seedu.address.logic.commands.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.person.Grade.getAllGradeWeightage;
import static seedu.address.model.person.Grade.getGradeIndex;
import static seedu.address.model.person.Grade.isValidGrade;

//@@author olimhc
/**
 * Helper function for handling different format of grade
 */
public class GradeUtil {

    /**
     * Returns true if the {@code value} matches the {@code word} given that word is a valid grade
     *   <br>examples:<pre>
     *       A p3 grade should match primary3.
     *       A client with P3 P4 grades should match a p4 grade
     *       </pre>
     * @param value cannot be null, can be a string of multiple grades or just a grade
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsGradeIgnoreCase(String value, String word) {
        requireNonNull(value);
        requireNonNull(word);

        if (!isValidGrade(word)) {
            return false;
        }

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        int preppedWordValueWeightage = getGradeIndex(preppedWord);
        int[] getAllGradeWeightage = getAllGradeWeightage(value);

        for (int i : getAllGradeWeightage) {
            if (i == preppedWordValueWeightage) {
                return true;
            }
        }

        return false;
    }
}

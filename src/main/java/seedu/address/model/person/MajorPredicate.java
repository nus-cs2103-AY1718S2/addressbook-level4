package seedu.address.model.person;

import java.util.ArrayList;
import java.util.function.Predicate;

import seedu.address.logic.parser.PredicateUtil;

//@@author tanhengyeow
/**
 * Represents an Major predicate
 */
public class MajorPredicate implements FieldPredicate {

    private Predicate<Person> majorPredicate = null;

    /**
     * Constructs an {@code MajorPredicate}.
     * @param substringKeywords stores user argument that matches substring
     * @param exactKeywords stores user argument that matches exact keywords
     * @param prefixKeywords stores user argument that matches prefix
     * @param suffixKeywords stores user argument that matches suffix
     */
    public MajorPredicate(ArrayList<String> exactKeywords, ArrayList<String> substringKeywords,
                          ArrayList<String> prefixKeywords, ArrayList<String> suffixKeywords) {
        MajorContainsKeywordsPredicate majorContainsKeywordsPredicate = null;
        MajorContainsSubstringsPredicate majorContainsSubstringsPredicate = null;
        MajorContainsPrefixesPredicate majorContainsPrefixesPredicate = null;
        MajorContainsSuffixesPredicate majorContainsSuffixesPredicate = null;

        if (!exactKeywords.isEmpty()) {
            majorContainsKeywordsPredicate = new MajorContainsKeywordsPredicate(exactKeywords);
        }
        if (!substringKeywords.isEmpty()) {
            majorContainsSubstringsPredicate = new MajorContainsSubstringsPredicate(substringKeywords);
        }
        if (!prefixKeywords.isEmpty()) {
            majorContainsPrefixesPredicate = new MajorContainsPrefixesPredicate(prefixKeywords);
        }
        if (!suffixKeywords.isEmpty()) {
            majorContainsSuffixesPredicate = new MajorContainsSuffixesPredicate(suffixKeywords);
        }
        if (!exactKeywords.isEmpty() || !substringKeywords.isEmpty()
                || !prefixKeywords.isEmpty() || !suffixKeywords.isEmpty()) {
            this.majorPredicate = PredicateUtil.formOrPredicate(majorContainsKeywordsPredicate,
                    majorContainsSubstringsPredicate, majorContainsPrefixesPredicate,
                    majorContainsSuffixesPredicate);
        }
    }

    @Override
    public Predicate<Person> getPredicate() {
        return majorPredicate;
    }

    @Override
    public String toString() {
        return majorPredicate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MajorPredicate // instanceof handles nulls
                && this.majorPredicate.equals(((MajorPredicate) other).majorPredicate)); // state check
    }

    @Override
    public int hashCode() {
        return majorPredicate.hashCode();
    }

}

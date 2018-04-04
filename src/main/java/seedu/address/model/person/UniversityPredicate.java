package seedu.address.model.person;

import java.util.ArrayList;
import java.util.function.Predicate;

import seedu.address.logic.parser.PredicateUtil;

//@@author tanhengyeow
/**
 * Represents a University predicate
 */
public class UniversityPredicate implements FieldPredicate {

    private Predicate<Person> universityPredicate = null;

    /**
     * Constructs an {@code UniversityPredicate}.
     * @param substringKeywords stores user argument that matches substring
     * @param exactKeywords stores user argument that matches exact keywords
     * @param prefixKeywords stores user argument that matches prefix
     * @param suffixKeywords stores user argument that matches suffix
     */
    public UniversityPredicate(ArrayList<String> exactKeywords, ArrayList<String> substringKeywords,
                               ArrayList<String> prefixKeywords, ArrayList<String> suffixKeywords) {

        UniversityContainsKeywordsPredicate universityContainsKeywordsPredicate = null;
        UniversityContainsSubstringsPredicate universityContainsSubstringsPredicate = null;
        UniversityContainsPrefixesPredicate universityContainsPrefixesPredicate = null;
        UniversityContainsSuffixesPredicate universityContainsSuffixesPredicate = null;

        if (!exactKeywords.isEmpty()) {
            universityContainsKeywordsPredicate =
                    new UniversityContainsKeywordsPredicate(exactKeywords);
        }
        if (!substringKeywords.isEmpty()) {
            universityContainsSubstringsPredicate = new UniversityContainsSubstringsPredicate(substringKeywords);
        }
        if (!prefixKeywords.isEmpty()) {
            universityContainsPrefixesPredicate = new UniversityContainsPrefixesPredicate(prefixKeywords);
        }
        if (!suffixKeywords.isEmpty()) {
            universityContainsSuffixesPredicate = new UniversityContainsSuffixesPredicate(suffixKeywords);
        }
        if (!exactKeywords.isEmpty() || !substringKeywords.isEmpty()
                || !prefixKeywords.isEmpty() || !suffixKeywords.isEmpty()) {
            this.universityPredicate = PredicateUtil.formOrPredicate(universityContainsKeywordsPredicate,
                    universityContainsSubstringsPredicate, universityContainsPrefixesPredicate,
                    universityContainsSuffixesPredicate);
        }
    }

    @Override
    public Predicate<Person> getPredicate() {
        return universityPredicate;
    }

    @Override
    public String toString() {
        return universityPredicate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniversityPredicate // instanceof handles nulls
                && this.universityPredicate.equals(((UniversityPredicate) other).universityPredicate)); // state check
    }

    @Override
    public int hashCode() {
        return universityPredicate.hashCode();
    }

}

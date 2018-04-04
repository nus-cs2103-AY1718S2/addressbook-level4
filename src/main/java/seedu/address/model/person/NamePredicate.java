package seedu.address.model.person;

import java.util.ArrayList;
import java.util.function.Predicate;

import seedu.address.logic.parser.PredicateUtil;

//@@author tanhengyeow
/**
 * Represents an Name predicate
 */
public class NamePredicate implements FieldPredicate {

    private Predicate<Person> namePredicate = null;

    /**
     * Constructs an {@code NamePredicate}.
     * @param substringKeywords stores user argument that matches substring
     * @param exactKeywords stores user argument that matches exact keywords
     * @param prefixKeywords stores user argument that matches prefix
     * @param suffixKeywords stores user argument that matches suffix
     */
    public NamePredicate(ArrayList<String> exactKeywords, ArrayList<String> substringKeywords,
                         ArrayList<String> prefixKeywords, ArrayList<String> suffixKeywords) {

        NameContainsKeywordsPredicate nameContainsKeywordsPredicate = null;
        NameContainsSubstringsPredicate nameContainsSubstringsPredicate = null;
        NameContainsPrefixesPredicate nameContainsPrefixesPredicate = null;
        NameContainsSuffixesPredicate nameContainsSuffixesPredicate = null;

        if (!exactKeywords.isEmpty()) {
            nameContainsKeywordsPredicate = new NameContainsKeywordsPredicate(exactKeywords);
        }
        if (!substringKeywords.isEmpty()) {
            nameContainsSubstringsPredicate = new NameContainsSubstringsPredicate(substringKeywords);
        }
        if (!prefixKeywords.isEmpty()) {
            nameContainsPrefixesPredicate = new NameContainsPrefixesPredicate(prefixKeywords);
        }
        if (!suffixKeywords.isEmpty()) {
            nameContainsSuffixesPredicate = new NameContainsSuffixesPredicate(suffixKeywords);
        }
        if (!exactKeywords.isEmpty() || !substringKeywords.isEmpty()
                || !prefixKeywords.isEmpty() || !suffixKeywords.isEmpty()) {
            this.namePredicate = PredicateUtil.formOrPredicate(nameContainsKeywordsPredicate,
                    nameContainsSubstringsPredicate, nameContainsPrefixesPredicate,
                    nameContainsSuffixesPredicate);
        }
    }

    @Override
    public Predicate<Person> getPredicate() {
        return namePredicate;
    }

    @Override
    public String toString() {
        return namePredicate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NamePredicate // instanceof handles nulls
                && this.namePredicate.equals(((NamePredicate) other).namePredicate)); // state check
    }

    @Override
    public int hashCode() {
        return namePredicate.hashCode();
    }

}

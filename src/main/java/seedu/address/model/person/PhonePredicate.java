package seedu.address.model.person;

import java.util.ArrayList;
import java.util.function.Predicate;

import seedu.address.logic.parser.PredicateUtil;

//@@author tanhengyeow
/**
 * Represents an Phone predicate
 */
public class PhonePredicate implements FieldPredicate {

    private Predicate<Person> phonePredicate = null;

    /**
     * Constructs an {@code PhonePredicate}.
     * @param substringKeywords stores user argument that matches substring
     * @param exactKeywords stores user argument that matches exact keywords
     * @param prefixKeywords stores user argument that matches prefix
     * @param suffixKeywords stores user argument that matches suffix
     */
    public PhonePredicate(ArrayList<String> exactKeywords, ArrayList<String> substringKeywords,
                          ArrayList<String> prefixKeywords, ArrayList<String> suffixKeywords) {
        PhoneContainsKeywordsPredicate phoneContainsKeywordsPredicate = null;
        PhoneContainsSubstringsPredicate phoneContainsSubstringsPredicate = null;
        PhoneContainsPrefixesPredicate phoneContainsPrefixesPredicate = null;
        PhoneContainsSuffixesPredicate phoneContainsSuffixesPredicate = null;

        if (!exactKeywords.isEmpty()) {
            phoneContainsKeywordsPredicate = new PhoneContainsKeywordsPredicate(exactKeywords);
        }
        if (!substringKeywords.isEmpty()) {
            phoneContainsSubstringsPredicate = new PhoneContainsSubstringsPredicate(substringKeywords);
        }
        if (!prefixKeywords.isEmpty()) {
            phoneContainsPrefixesPredicate = new PhoneContainsPrefixesPredicate(prefixKeywords);
        }
        if (!suffixKeywords.isEmpty()) {
            phoneContainsSuffixesPredicate = new PhoneContainsSuffixesPredicate(suffixKeywords);
        }
        if (!exactKeywords.isEmpty() || !substringKeywords.isEmpty()
                || !prefixKeywords.isEmpty() || !suffixKeywords.isEmpty()) {
            this.phonePredicate = PredicateUtil.formOrPredicate(phoneContainsKeywordsPredicate,
                    phoneContainsSubstringsPredicate, phoneContainsPrefixesPredicate,
                    phoneContainsSuffixesPredicate);
        }
    }

    @Override
    public Predicate<Person> getPredicate() {
        return phonePredicate;
    }

    @Override
    public String toString() {
        return phonePredicate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhonePredicate // instanceof handles nulls
                && this.phonePredicate.equals(((PhonePredicate) other).phonePredicate)); // state check
    }

    @Override
    public int hashCode() {
        return phonePredicate.hashCode();
    }

}

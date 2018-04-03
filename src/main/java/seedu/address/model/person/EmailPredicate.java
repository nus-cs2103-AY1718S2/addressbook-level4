package seedu.address.model.person;

import java.util.ArrayList;
import java.util.function.Predicate;

import seedu.address.logic.parser.PredicateUtil;

//@@author tanhengyeow
/**
 * Represents an Email predicate
 */
public class EmailPredicate implements FieldPredicate {

    private Predicate<Person> emailPredicate = null;

    /**
     * Constructs an {@code EmailPredicate}.
     * @param substringKeywords stores user argument that matches substring
     * @param exactKeywords stores user argument that matches exact keywords
     * @param prefixKeywords stores user argument that matches prefix
     * @param suffixKeywords stores user argument that matches suffix
     */
    public EmailPredicate(ArrayList<String> exactKeywords, ArrayList<String> substringKeywords,
                          ArrayList<String> prefixKeywords, ArrayList<String> suffixKeywords) {

        EmailContainsKeywordsPredicate emailContainsKeywordsPredicate = null;
        EmailContainsSubstringsPredicate emailContainsSubstringsPredicate = null;
        EmailContainsPrefixesPredicate emailContainsPrefixesPredicate = null;
        EmailContainsSuffixesPredicate emailContainsSuffixesPredicate = null;

        if (!exactKeywords.isEmpty()) {
            emailContainsKeywordsPredicate = new EmailContainsKeywordsPredicate(exactKeywords);
        }
        if (!substringKeywords.isEmpty()) {
            emailContainsSubstringsPredicate = new EmailContainsSubstringsPredicate(substringKeywords);
        }
        if (!prefixKeywords.isEmpty()) {
            emailContainsPrefixesPredicate = new EmailContainsPrefixesPredicate(prefixKeywords);
        }
        if (!suffixKeywords.isEmpty()) {
            emailContainsSuffixesPredicate = new EmailContainsSuffixesPredicate(suffixKeywords);
        }
        if (!exactKeywords.isEmpty() || !substringKeywords.isEmpty()
                || !prefixKeywords.isEmpty() || !suffixKeywords.isEmpty()) {
            emailPredicate = PredicateUtil.formOrPredicate(emailContainsKeywordsPredicate,
                    emailContainsSubstringsPredicate, emailContainsPrefixesPredicate,
                    emailContainsSuffixesPredicate);
        }
    }

    @Override
    public Predicate<Person> getPredicate() {
        return emailPredicate;
    }

    @Override
    public String toString() {
        return emailPredicate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailPredicate // instanceof handles nulls
                && this.emailPredicate.equals(((EmailPredicate) other).emailPredicate)); // state check
    }

    @Override
    public int hashCode() {
        return emailPredicate.hashCode();
    }

}

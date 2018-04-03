package seedu.address.model.person;

import java.util.ArrayList;
import java.util.function.Predicate;

import seedu.address.logic.parser.PredicateUtil;

/**
 * Represents a JobApplied predicate
 */
public class JobAppliedPredicate implements FieldPredicate {

    private Predicate<Person> jobAppliedPredicate = null;

    /**
     * Constructs an {@code JobAppliedPredicate}.
     * @param substringKeywords stores user argument that matches substring
     * @param exactKeywords stores user argument that matches exact keywords
     * @param prefixKeywords stores user argument that matches prefix
     * @param suffixKeywords stores user argument that matches suffix
     */
    public JobAppliedPredicate(ArrayList<String> exactKeywords, ArrayList<String> substringKeywords,
                            ArrayList<String> prefixKeywords, ArrayList<String> suffixKeywords) {

        JobAppliedContainsKeywordsPredicate jobAppliedContainsKeywordsPredicate = null;
        JobAppliedContainsSubstringsPredicate jobAppliedContainsSubstringsPredicate = null;
        JobAppliedContainsPrefixesPredicate jobAppliedContainsPrefixesPredicate = null;
        JobAppliedContainsSuffixesPredicate jobAppliedContainsSuffixesPredicate = null;

        if (!exactKeywords.isEmpty()) {
            jobAppliedContainsKeywordsPredicate =
                    new JobAppliedContainsKeywordsPredicate(exactKeywords);
        }
        if (!substringKeywords.isEmpty()) {
            jobAppliedContainsSubstringsPredicate = new JobAppliedContainsSubstringsPredicate(substringKeywords);
        }
        if (!prefixKeywords.isEmpty()) {
            jobAppliedContainsPrefixesPredicate = new JobAppliedContainsPrefixesPredicate(prefixKeywords);
        }
        if (!suffixKeywords.isEmpty()) {
            jobAppliedContainsSuffixesPredicate = new JobAppliedContainsSuffixesPredicate(suffixKeywords);
        }
        if (!exactKeywords.isEmpty() || !substringKeywords.isEmpty()
                || !prefixKeywords.isEmpty() || !suffixKeywords.isEmpty()) {
            this.jobAppliedPredicate = PredicateUtil.formOrPredicate(jobAppliedContainsKeywordsPredicate,
                    jobAppliedContainsSubstringsPredicate, jobAppliedContainsPrefixesPredicate,
                    jobAppliedContainsSuffixesPredicate);
        }
    }

    @Override
    public Predicate<Person> getPredicate() {
        return jobAppliedPredicate;
    }

    @Override
    public String toString() {
        return jobAppliedPredicate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobAppliedPredicate // instanceof handles nulls
                && this.jobAppliedPredicate.equals(((JobAppliedPredicate) other).jobAppliedPredicate)); // state check
    }

    @Override
    public int hashCode() {
        return jobAppliedPredicate.hashCode();
    }

}

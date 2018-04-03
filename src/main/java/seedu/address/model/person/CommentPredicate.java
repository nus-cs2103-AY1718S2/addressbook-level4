package seedu.address.model.person;

import java.util.ArrayList;
import java.util.function.Predicate;

import seedu.address.logic.parser.PredicateUtil;

/**
 * Represents a Comment predicate
 */
public class CommentPredicate implements FieldPredicate {

    private Predicate<Person> commentPredicate = null;

    /**
     * Constructs an {@code CommentPredicate}.
     * @param substringKeywords stores user argument that matches substring
     * @param exactKeywords stores user argument that matches exact keywords
     * @param prefixKeywords stores user argument that matches prefix
     * @param suffixKeywords stores user argument that matches suffix
     */
    public CommentPredicate(ArrayList<String> exactKeywords, ArrayList<String> substringKeywords,
                            ArrayList<String> prefixKeywords, ArrayList<String> suffixKeywords) {

        CommentContainsKeywordsPredicate commentContainsKeywordsPredicate = null;
        CommentContainsSubstringsPredicate commentContainsSubstringsPredicate = null;
        CommentContainsPrefixesPredicate commentContainsPrefixesPredicate = null;
        CommentContainsSuffixesPredicate commentContainsSuffixesPredicate = null;

        if (!exactKeywords.isEmpty()) {
            commentContainsKeywordsPredicate =
                    new CommentContainsKeywordsPredicate(exactKeywords);
        }
        if (!substringKeywords.isEmpty()) {
            commentContainsSubstringsPredicate = new CommentContainsSubstringsPredicate(substringKeywords);
        }
        if (!prefixKeywords.isEmpty()) {
            commentContainsPrefixesPredicate = new CommentContainsPrefixesPredicate(prefixKeywords);
        }
        if (!suffixKeywords.isEmpty()) {
            commentContainsSuffixesPredicate = new CommentContainsSuffixesPredicate(suffixKeywords);
        }
        if (!exactKeywords.isEmpty() || !substringKeywords.isEmpty()
                || !prefixKeywords.isEmpty() || !suffixKeywords.isEmpty()) {
            this.commentPredicate = PredicateUtil.formOrPredicate(commentContainsKeywordsPredicate,
                    commentContainsSubstringsPredicate, commentContainsPrefixesPredicate,
                    commentContainsSuffixesPredicate);
        }
    }

    @Override
    public Predicate<Person> getPredicate() {
        return commentPredicate;
    }

    @Override
    public String toString() {
        return commentPredicate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CommentPredicate // instanceof handles nulls
                && this.commentPredicate.equals(((CommentPredicate) other).commentPredicate)); // state check
    }

    @Override
    public int hashCode() {
        return commentPredicate.hashCode();
    }

}

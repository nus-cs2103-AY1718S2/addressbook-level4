package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.util.GradeUtil;


//@@author Zhu-Jiahui
/**
 * Tests that a {@code Client}'s {@code Location, Grade and Subject} matches the entered {@code Client}'s
 * {@code Location, Grade and Subject}.
 */
public class MatchContainsKeywordsPredicate implements Predicate<Client> {
    private final Client client;

    public MatchContainsKeywordsPredicate(Client client) {
        this.client = client;
    }

    @Override
    public boolean test(Client other) {
        boolean isMatch = false;
        int rank = 0;

        if (StringUtil.containsWordIgnoreCase(other.getLocation().toString(), client.getLocation().toString())) {
            isMatch = true;
            other.setMatchedLocation(isMatch);
            rank++;
        }
        if (GradeUtil.containsGradeIgnoreCase(other.getGrade().value, client.getGrade().toString()
                .split("\\s+")[0])) {
            isMatch = true;
            other.setMatchedGrade(isMatch);
            rank++;
        }
        if (StringUtil.containsWordIgnoreCase(other.getSubject().value, client.getSubject().toString()
                .split("\\s+")[0])) {
            isMatch = true;
            other.setMatchedSubject(isMatch);
            rank++;
        }
        other.setRank(rank);
        return isMatch;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchContainsKeywordsPredicate // instanceof handles nulls
                && this.client.equals(((MatchContainsKeywordsPredicate) other).client)); // state check
    }

}
//@@author

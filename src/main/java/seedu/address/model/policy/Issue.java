package seedu.address.model.policy;

/**
 * Represents the Issues that could happen to a Person.
 * Note that this list is not complete and has to be extended.
 */
public enum Issue {
    THEFT, CAR_DAMAGE, HOUSE_DAMAGE, ILLNESS, CAR_ACCIDENT;

    private static String buildIssueConstraint() {
        StringBuilder sb = new StringBuilder();
        sb.append("Issue must be a correct issue. List of correct issues (can also be lower case) : ");
        for(Issue i : Issue.values()) {
            sb.append(i.toString()).append("   ");
        }
        return sb.toString();
    }

    public static final String ISSUE_CONSTRAINTS = buildIssueConstraint();

    public static boolean isValidIssueName (String issueName) {
        boolean match = false;
        for (Issue i : Issue.values()) {
            if(i.toString().equals(issueName)) {
                match = true;
            }
        }
        return match;
    }
}

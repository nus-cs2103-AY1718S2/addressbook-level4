package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.RuleBook;
import seedu.address.model.rule.Rule;
import seedu.address.model.rule.exceptions.DuplicateRuleException;

/**
 * A utility class containing a list of {@code Rule} objects to be used in tests.
 */
public class TypicalRules {

    public static final Rule ALICE = new NotificationRuleBuilder().withValue("c/AAA").build();
    public static final Rule BENSON = new NotificationRuleBuilder().withValue("c/BBB").build();
    public static final Rule CARL = new NotificationRuleBuilder().withValue("c/CCC").build();
    public static final Rule DANIEL = new NotificationRuleBuilder().withValue("c/DDD").build();
    public static final Rule ELLE = new NotificationRuleBuilder().withValue("c/EEE").build();
    public static final Rule FIONA = new NotificationRuleBuilder().withValue("c/FFF").build();
    public static final Rule GEORGE = new NotificationRuleBuilder().withValue("c/GGG").build();

    // Manually added
    public static final Rule HOON = new NotificationRuleBuilder().withValue("c/HHH").build();
    public static final Rule IDA = new NotificationRuleBuilder().withValue("c/III").build();

    private TypicalRules() {} // prevents instantiation

    /**
     * Returns an {@code RuleBook} with all the typical rules.
     */
    public static RuleBook getTypicalRuleBook() {
        RuleBook ab = new RuleBook();
        for (Rule rule : getTypicalRules()) {
            try {
                ab.addRule(rule);
            } catch (DuplicateRuleException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Rule> getTypicalRules() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}

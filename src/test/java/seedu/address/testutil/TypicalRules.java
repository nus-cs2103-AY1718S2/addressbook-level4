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

    public static final Rule ALIS = new NotificationRuleBuilder().withValue("c/ALIS").build();
    public static final Rule BTC = new NotificationRuleBuilder().withValue("c/BTC").build();
    public static final Rule CAS = new NotificationRuleBuilder().withValue("c/CAS").build();
    public static final Rule DADI = new NotificationRuleBuilder().withValue("c/DADI").build();
    public static final Rule ELIX = new NotificationRuleBuilder().withValue("c/ELIX").build();
    public static final Rule FIRE = new NotificationRuleBuilder().withValue("c/FIRE").build();
    public static final Rule GEO = new NotificationRuleBuilder().withValue("c/GEO").build();

    // Manually added
    public static final Rule EQUAL = new NotificationRuleBuilder().withValue("p/=10").build();
    public static final Rule INCR = new NotificationRuleBuilder().withValue("p/+>1").build();

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
        return new ArrayList<>(Arrays.asList(ALIS, BTC, CAS, DADI, ELIX, FIRE, GEO));
    }
}

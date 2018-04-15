package seedu.address.model.transaction;

//@@author steven-jia
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Transaction's split method in SmartSplit.
 */
public class SplitMethod {

    /**
     * Split methods that can be used
     */
    public enum Method {
        NOT_APPLICABLE,
        EVENLY,
        UNITS,
        PERCENTAGE
    }

    public static final String MESSAGE_SPLIT_METHOD_CONSTRAINTS =
            "Transaction split method can only be \"evenly\", \"units\", or \"percentage\"";

    public static final String SPLIT_METHOD_NOT_APPLICABLE = "not_applicable";
    public static final String SPLIT_METHOD_EVENLY = "evenly";
    public static final String SPLIT_METHOD_UNITS = "units";
    public static final String SPLIT_METHOD_PERCENTAGE = "percentage";

    public final Method method;

    /**
     * Constructs a {@code splitMethod}.
     *
     * @param splitMethod A valid split method.
     */
    public SplitMethod(String splitMethod) {
        requireNonNull(splitMethod);
        checkArgument(isValidSplitMethod(splitMethod), MESSAGE_SPLIT_METHOD_CONSTRAINTS);
        switch (splitMethod) {
        case SPLIT_METHOD_NOT_APPLICABLE:
            this.method = Method.NOT_APPLICABLE;
            break;
        case SPLIT_METHOD_EVENLY:
            this.method = Method.EVENLY;
            break;
        case SPLIT_METHOD_UNITS:
            this.method = Method.UNITS;
            break;
        case SPLIT_METHOD_PERCENTAGE:
            this.method = Method.PERCENTAGE;
            break;
        default:
            this.method = Method.EVENLY;
        }
    }

    /**
     * Returns true if a given string is a valid split method.
     */
    public static boolean isValidSplitMethod(String test) {
        return test.toLowerCase().matches(SPLIT_METHOD_EVENLY)
            || test.toLowerCase().matches(SPLIT_METHOD_UNITS)
            || test.toLowerCase().matches(SPLIT_METHOD_PERCENTAGE)
            || test.toLowerCase().matches(SPLIT_METHOD_NOT_APPLICABLE);
    }

    @Override
    public String toString() {
        return method.toString().toLowerCase();
    }

    @Override
    public int hashCode() {
        return method.hashCode();
    }

}

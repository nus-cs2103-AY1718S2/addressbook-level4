package seedu.address.model.Insurance;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Represents the commission recieved from a insurance plan in reInsurance.
 * Guarantees: immutable;
 */
public class Commission {

    private final String commission;

    /**
     * Every field must be present and not null.
     */
    public Commission(Insurance insurance) {
        requireNonNull(insurance);
        Insurance insurance1 = insurance;
        String insuranceName = insurance.toString();
        String commission = new String("0");
        Pattern p = Pattern.compile("\\{(.*?)\\}");
        Matcher m = p.matcher(insuranceName);

        while (m.find()) {
        if (commission.equals("0")) {
                commission = m.group().substring(1, m.group().length() - 1);
            }
        }
        this.commission = commission;

    }

    public String getCommission() {
        return commission;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Commission)) {
            return false;
        }

        Commission otherCommission = (Commission) other;
        return otherCommission.getCommission().equals(this.getCommission());
    }

    @Override
    public int hashCode() {
        return Objects.hash(commission);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getCommission());
        return builder.toString();
    }
}

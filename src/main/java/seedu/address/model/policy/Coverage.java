//@@author ValerianRey

package seedu.address.model.policy;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a Policy's coverage of issues.
 * Guarantees: immutable
 */
public class Coverage {
    private final List<Issue> coverage;

    public Coverage(List<Issue> coverage) {
        requireAllNonNull(coverage);
        this.coverage = new ArrayList<Issue>();
        for (Issue issue : coverage) {
            this.coverage.add(issue);
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        if (coverage.size() >= 1) {
            builder.append(coverage.get(0).name().toLowerCase().replaceAll("_", " "));
        }
        for (int i = 1; i < coverage.size(); ++i) {
            builder.append(", ").append(coverage.get(i).name().toLowerCase().replaceAll("_", " "));
        }
        return builder.toString();
    }

    public List<Issue> getIssues() {
        return Collections.unmodifiableList(coverage);
    }

}

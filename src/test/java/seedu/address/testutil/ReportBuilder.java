// @@author anh2111
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.report.Proportion;
import seedu.address.model.report.Report;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Report objects.
 */
public class ReportBuilder {

    public static final String DEFAULT_POPULATION = "SEIntern";
    public static final int DEFAULT_TOTAL_PERSONS = 5;
    public static final int DEFAULT_TOTAL_TAGS = 5;

    /** DEFAULT PORTION 1 */
    public static final int DEFAULT_VALUE_1 = 5;
    public static final String DEFAULT_TAG_NAME_1 = "Screening";
    public static final int DEFAULT_TOTAL_PERSONS_1 = 5;

    private Tag population;
    private  List<Proportion> allProportions;
    private  int totalTags;
    private  int totalPersons;

    public ReportBuilder() {
        population = new Tag(DEFAULT_POPULATION);
        allProportions = new ArrayList<>();
        allProportions.add(new Proportion(DEFAULT_TAG_NAME_1, DEFAULT_VALUE_1, DEFAULT_TOTAL_PERSONS_1));
        totalTags = DEFAULT_TOTAL_TAGS;
        totalPersons = DEFAULT_TOTAL_PERSONS;
    }

    /**
     * Sets the {@code Name} of the {@code Report} that we are building.
     */
    public ReportBuilder withPopulation(String tagName) {
        this.population = new Tag(tagName);
        return this;
    }

    public Report build() {
        return new Report(population, allProportions, totalPersons);
    }

}
// @@author

//@@author anh2111
package seedu.address.model.report;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.tag.Tag;

/**
 * Represents a Report in the address book.
 */
public class Report {

    private final Tag population;
    private final List<Proportion> allProportions;
    private final int totalTags;
    private final int totalPersons;
    private final Date date;

    /**
     * Every field must be present and not null.
     */
    public Report(Tag population, List<Proportion> allProportions, int totalPersons) {
        requireAllNonNull(population, allProportions);

        this.date = Calendar.getInstance().getTime();
        this.population = population;
        // protect internal allProportions from changes in the arg list
        this.allProportions = new ArrayList<>(allProportions);

        int sum = 0;
        for (Proportion p : allProportions) {
            sum += p.value;
        }
        this.totalTags = sum;

        this.totalPersons = totalPersons;
    }

    public Report(Tag population, List<Proportion> allProportions, int totalPersons, String date)
            throws ParseException {
        requireAllNonNull(population, allProportions);

        DateFormat formater = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        this.date = formater.parse(date);

        this.population = population;
        // protect internal allProportions from changes in the arg list
        this.allProportions = new ArrayList<>(allProportions);

        int sum = 0;
        for (Proportion p : allProportions) {
            sum += p.value;
        }
        this.totalTags = sum;

        this.totalPersons = totalPersons;
    }

    public Tag getPopulation() {
        return population;
    }

    public ObservableList<Proportion> getAllProportions() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(allProportions));
    }

    public int getTotalTags() {
        return totalTags;
    }

    public int getTotalPersons() {
        return totalPersons;
    }

    public String getDate() {
        DateFormat formater = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        return formater.format(date);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Report)) {
            return false;
        }

        Report otherPerson = (Report) other;
        return otherPerson.getPopulation().equals(this.getPopulation())
                && otherPerson.getAllProportions().equals(this.getAllProportions())
                && otherPerson.getPopulation().equals(this.getPopulation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(population, allProportions);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(population.tagName);
        return builder.toString();
    }

}
//@@author

// @@author anh2111
package seedu.address.storage;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.report.Proportion;
import seedu.address.model.report.Report;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Report;
 */
public class XmlAdaptedReport {

    @XmlElement(required = true)
    private XmlAdaptedTag population;
    @XmlElement(required = true, defaultValue = "null")
    private Integer totalTags;
    @XmlElement(required = true, defaultValue = "null")
    private Integer totalPersons;
    @XmlElement
    private List<XmlAdaptedProportion> proportions = new ArrayList<>();
    @XmlElement
    private String date;

    /**
     * Constructs an XmlAdaptedReport.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedReport() {}

    /**
     * Constructs an {@code XmlAdaptedReport} with the given Report details.
     */
    public XmlAdaptedReport(XmlAdaptedTag population, int totalTags, int totalPersons,
                                List<XmlAdaptedProportion> proportions, String date) {
        this.population = population;
        this.totalPersons = totalPersons;
        this.totalTags = totalTags;
        this.proportions = proportions;
        this.date = date;
    }

    /**
     * Converts a given Report into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedReport
     */
    public XmlAdaptedReport(Report source) {
        population = new XmlAdaptedTag(source.getPopulation());
        totalTags = source.getTotalTags();
        totalPersons = source.getTotalPersons();
        proportions = new ArrayList<>();
        for (Proportion p : source.getAllProportions()) {
            proportions.add(new XmlAdaptedProportion(p));
        }
        date = source.getDate();
    }

    /**
     * Converts this jaxb-friendly adapted Report object into the model's Report object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Proportion
     */
    public Report toModelType() throws IllegalValueException, ParseException {
        final List<Proportion> proportions = new ArrayList<>();
        for (XmlAdaptedProportion p : this.proportions) {
            proportions.add(p.toModelType());
        }

        final Tag population = this.population.toModelType();

        return new Report(population, proportions, totalPersons, date);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedReport)) {
            return false;
        }

        XmlAdaptedReport otherReport = (XmlAdaptedReport) other;
        return Objects.equals(population, otherReport.population)
                && proportions.equals(otherReport.proportions)
                && totalPersons == otherReport.totalPersons
                && totalTags == otherReport.totalTags;
    }
}
// @@author

package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.dashboard.Dashboard;
import seedu.address.model.student.dashboard.Milestone;
import seedu.address.model.student.dashboard.UniqueMilestoneList;

//@@author yapni
/**
 * JAXB-friendly adapted version of the Dashboard.
 */
public class XmlAdaptedDashboard {

    @XmlElement
    private List<XmlAdaptedMilestone> milestoneList;

    /**
     * Constructs an XmlAdaptedDashboard.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedDashboard() {}

    /**
     * Constructs an {@code XmlAdaptedDashboard} with the given dashboard details.
     */
    public XmlAdaptedDashboard(List<XmlAdaptedMilestone> milestoneList) {
        if (milestoneList != null) {
            this.milestoneList = new ArrayList<>(milestoneList);
        }
    }

    /**
     * Converts a given Dashboard into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedDashboard
     */
    public XmlAdaptedDashboard(Dashboard source) {
        milestoneList = new ArrayList<>();
        for (Milestone milestone : source.getMilestoneList()) {
            milestoneList.add(new XmlAdaptedMilestone(milestone));
        }
    }

    /**
     * Converts this jaxb-friendly adapted dashboard object into the model's Dashboard object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted dashboard
     */
    public Dashboard toModelType() throws IllegalValueException {
        final UniqueMilestoneList modelMilestoneList = new UniqueMilestoneList();
        if (milestoneList != null) {
            for (XmlAdaptedMilestone milestone : milestoneList) {
                modelMilestoneList.add(milestone.toModelType());
            }
        }

        return new Dashboard(modelMilestoneList);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedDashboard)) {
            return false;
        }

        XmlAdaptedDashboard otherDashboard = (XmlAdaptedDashboard) other;
        return milestoneList.equals(otherDashboard.milestoneList);
    }
}

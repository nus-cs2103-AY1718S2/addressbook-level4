package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Date;

import javafx.util.Pair;

//@@author wenhao53-unused
// Feature is working but was unable to solve the many tests that were failing.
/**
 * Represents a Person's weight history (in Kg) since the startup of the Personal Trainer Pro app.
 * Guarantees: contains weight data that is valid as declared in {@link #isValidWeight(String)}
 */
public class WeightLog {

    private ArrayList<Pair<Date, Double>> list;

    /**
     * Constructs a new {@code WeightLog}.
     */
    public WeightLog(Weight weight) {
        this.list = new ArrayList<>();
        addNewEntry(new Date(), weight);
    }

    /**
     * Returns the ArrayList that contains the information in the WeightLog.
     */
    public ArrayList<Pair<Date, Double>> getList() {
        return this.list;
    }

    /**
     * Adds a new weight entry into the log.
     * @param date A date representing the moment where the Person was created.
     * @param weight A valid weight (in Kg).
     */
    public void addNewEntry(Date date, Weight weight) {
        list.add(new Pair(date, getDoubleValueFromWeight(weight)));
    }

    /*
     * Returns the double value of the input weight from the Weight string.
     */
    private Double getDoubleValueFromWeight(Weight weight) {
        return Double.parseDouble(weight.toString());
    }

    @Override
    public String toString() {
        String toDisplayAsString = "";
        for (Pair entry:list) {
            toDisplayAsString += entry.getValue().toString() + " ";
        }
        return toDisplayAsString;
    }
}

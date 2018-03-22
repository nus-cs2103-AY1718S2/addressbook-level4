package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Medical Record in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Record {

    public static final String MESSAGE_RECORD_CONSTRAINTS =
            "Patient record can take any values, but each field must be populated";

    private final String date;
    private final String symptom;
    private final String illness;
    private final String treatment;

    /**
     * Every field must be present and not null.
     */
    public Record(String name, String symptom, String illness, String treatment) {
        requireAllNonNull(name, symptom, illness, treatment);
        this.date = name;
        this.symptom = symptom;
        this.illness = illness;
        this.treatment = treatment;
    }

    public Record(Record record) {
        this(record.getDate(), record.getSymptom(), record.getIllness(), record.getTreatment());
    }

    public String getDate() {
        return date;
    }

    public String getSymptom() {
        return symptom;
    }

    public String getIllness() {
        return illness;
    }

    public String getTreatment() {
        return treatment;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Record)) {
            return false;
        }

        Record otherPatient = (Record) other;
        return otherPatient.getDate().equals(this.getDate())
                && otherPatient.getSymptom().equals(this.getSymptom())
                && otherPatient.getIllness().equals(this.getIllness())
                && otherPatient.getTreatment().equals(this.getTreatment());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date, symptom, illness, treatment);
    }

    /**
     * Returns true if all fields of record are non-null.
     */
    public static boolean isValidRecord(Record test) {
        requireAllNonNull(test, test.getDate(), test.getIllness(), test.getSymptom(), test.getTreatment());
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Date")
                .append(getDate())
                .append(" Symptoms: ")
                .append(getSymptom())
                .append(" Illness: ")
                .append(getIllness())
                .append(" Treatment: ")
                .append(getTreatment());
        return builder.toString();
    }

}

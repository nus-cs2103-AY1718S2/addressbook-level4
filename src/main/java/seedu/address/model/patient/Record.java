package seedu.address.model.patient;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Medical Record in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Record {

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

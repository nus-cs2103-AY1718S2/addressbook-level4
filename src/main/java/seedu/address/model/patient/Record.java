package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ILLNESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SYMPTOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TREATMENT;

import java.util.Objects;

import seedu.address.logic.commands.RecordCommand;
import seedu.address.logic.parser.RecordCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;

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

    public Record(){
        this("", "", "", "");
    }

    /**
     * Every field must be present and not null.
     */
    public Record(String date, String symptom, String illness, String treatment) {
        requireAllNonNull(date, symptom, illness, treatment);
        this.date = date;
        this.symptom = symptom;
        this.illness = illness;
        this.treatment = treatment;
    }

    public Record(Record record) {
        this(record.getDate(), record.getSymptom(), record.getIllness(), record.getTreatment());
    }

    public Record(String string) throws ParseException {
        RecordCommand command = new RecordCommandParser().parse(string); //command will not be executed
        this.date = command.getRecord().getDate();
        this.symptom = command.getRecord().getSymptom();
        this.illness = command.getRecord().getIllness();
        this.treatment = command.getRecord().getTreatment();
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
        builder.append("Date: ")
                .append(getDate())
                .append(" Symptoms: ")
                .append(getSymptom())
                .append(" Illness: ")
                .append(getIllness())
                .append(" Treatment: ")
                .append(getTreatment());
        return builder.toString();
    }

    public String toCommandString(){
        final StringBuilder builder = new StringBuilder();
        builder.append("1 ") //as the command will not be executed, we will be placing a dummy index
                .append(PREFIX_DATE)
                .append(getDate())
                .append(" ")
                .append(PREFIX_SYMPTOM)
                .append(getSymptom())
                .append(" ")
                .append(PREFIX_ILLNESS)
                .append(getIllness())
                .append(" ")
                .append(PREFIX_TREATMENT)
                .append(getTreatment());
        return builder.toString();
    }

}

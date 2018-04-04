//@@author nhs-work
package seedu.address.model.patient;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ILLNESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SYMPTOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TREATMENT;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Stream;

import seedu.address.logic.commands.RecordCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Medical Record in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Record {

    public static final String MESSAGE_RECORD_CONSTRAINTS =
            "Patient record can take any values, but each field must be populated";

    private final DateOfBirth date;
    private final TextField symptom;
    private final TextField illness;
    private final TextField treatment;

    public Record() {
        this(new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "", "", "");
    }

    /**
     * Every field must be present and not null.
     */
    public Record(String date, String symptom, String illness, String treatment) {
        requireAllNonNull(date, symptom, illness, treatment);
        this.date = new DateOfBirth(date);
        this.symptom = new TextField(symptom);
        this.illness = new TextField(illness);
        this.treatment = new TextField(treatment);
    }

    public Record(Record record) {
        this(record.getDate(), record.getSymptom(),
                record.getIllness(), record.getTreatment());
    }

    public Record(String string) throws ParseException {
        Record temp = this.parse(string);
        this.date = new DateOfBirth(temp.getDate());
        this.symptom = new TextField(temp.getSymptom());
        this.illness = new TextField(temp.getIllness());
        this.treatment = new TextField(temp.getTreatment());
    }

    /**
     *
     * @param args Takes in a string that represents a medical record.
     * @return Returns the medical record that is represented by the string.
     * @throws ParseException
     */
    private Record parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_SYMPTOM, PREFIX_ILLNESS, PREFIX_TREATMENT);

        if (!arePrefixesPresent(argMultimap,
                PREFIX_SYMPTOM, PREFIX_ILLNESS, PREFIX_TREATMENT)
                || (argMultimap.getPreamble() == null)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordCommand.MESSAGE_USAGE));
        }

        //to nest following lines into try once the various classes are set up
        String date = argMultimap.getPreamble();
        String symptom = (argMultimap.getValue(PREFIX_SYMPTOM)).get();
        String illness = (argMultimap.getValue(PREFIX_ILLNESS)).get();
        String treatment = (argMultimap.getValue(PREFIX_TREATMENT)).get();

        return new Record(date, symptom, illness, treatment);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    public String getDate() {
        return date.toString();
    }

    public String getSymptom() {
        return symptom.toString();
    }

    public String getIllness() {
        return illness.toString();
    }

    public String getTreatment() {
        return treatment.toString();
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

    /**
     * The is an outdated method that was utilised when there was only 1 record per patient.
     * It returns the string that is equivalent to the command that created this class.
     */
    public String toCommandString() {
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

    /**
     * Returns a string that is used to generate a RecordList that has this Record.
     */
    public String toCommandStringRecordList() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDate())
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

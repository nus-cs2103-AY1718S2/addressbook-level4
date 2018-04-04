//@@author nhs-work
package seedu.address.model.patient;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Objects;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a list of Medical Records in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class RecordList {

    public static final String MESSAGE_RECORDLIST_CONSTRAINTS =
            "Patient record list can take any values, but each field must be populated";

    private ArrayList<Record> recordList;
    private int numRecord;

    public RecordList() {
        this.recordList = new ArrayList<Record>();
        recordList.add(new Record());
        this.numRecord = 1;
    }

    /**
     * Every field must be present and not null.
     */
    public RecordList(ArrayList<Record> recordList) {
        requireAllNonNull(recordList);
        this.recordList = recordList;
        this.numRecord = recordList.size();
    }

    public RecordList(String string) throws ParseException {
        if (string.isEmpty()) {
            this.recordList = new ArrayList<Record>();
            recordList.add(new Record());
            this.numRecord = 1;
        } else {
            this.recordList = new ArrayList<Record>();
            String[] lines = string.split("\\r?\\n");
            for (int i = 0; i < lines.length; i++) {
                recordList.add(new Record(lines[i]));
            }
            this.numRecord = lines.length;
        }
    }

    public int getNumberOfRecords() {
        return numRecord;
    }

    public ArrayList<Record> getRecordList() {
        return recordList;
    } //currently not very defensive

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RecordList)) {
            return false;
        }

        RecordList otherPatient = (RecordList) other;
        return otherPatient.getNumberOfRecords() == (this.getNumberOfRecords())
                && otherPatient.getRecordList().equals(this.getRecordList());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(numRecord, recordList);
    }

    /**
     * Returns true if all fields of record are non-null.
     */
    public static boolean isValidRecordList(RecordList test) {
        requireAllNonNull(test, test.getNumberOfRecords(), test);
        for (int i = 0; i < test.getNumberOfRecords(); i++) {
            requireAllNonNull(test.getRecordList().get(i));
        }
        return true;
    }

    public Record getRecord(int recordIndex) {
        return recordList.get(recordIndex);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numRecord; i++) {
            builder.append("Index: ")
                    .append((i + 1) + " ")
                    .append(recordList.get(i).toString())
                    .append("\n");
        }
        return builder.toString();
    }

    /**
     * Returns the string of this class.
     */
    public String toCommandString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numRecord; i++) {
            builder.append(recordList.get(i).toCommandStringRecordList())
                    .append("\n");
        }
        return builder.toString();
    }

    /**
     * Edits the list of records based on the arguments provided.
     */
    public void edit(int recordIndex, Record record) {
        if (this.numRecord > recordIndex) {
            this.set(recordIndex, record);
        } else { //will always add new record as long as index > numRecords
            this.recordList.add(record);
            this.numRecord += 1;
        }
    }

    public void set(int recordIndex, Record record) {
        recordList.set(recordIndex, record);
    }
}

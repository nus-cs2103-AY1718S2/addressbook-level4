package seedu.address.logic.record;

import seedu.address.model.patient.DateOfBirth;
import seedu.address.model.patient.Record;
import seedu.address.model.patient.TextField;

/**
 * RecordManager class to handle user inputs to medical record.
 */
public final class RecordManager {


    private static Record currRecord = null;

    private RecordManager() {
        currRecord = new Record();
    }

    public static Record getRecord() {
        if (currRecord == null) {
            return null;
        }
        Record temp = new Record(currRecord.getDate(), currRecord.getSymptom(),
                currRecord.getIllness(), currRecord.getTreatment());
        //reset current record so it cannot be duplicated to other patients
        currRecord = null;
        return temp;
    }

    /**
     * Check if all medical record fields are valid.
     * Current medical record will be updated.
     */
    public static boolean authenticate (String date, String symptom, String illness, String treatment) {
        if (!DateOfBirth.isValidDob(date) || !TextField.isValidTextField(symptom)
                || !TextField.isValidTextField(illness) || !TextField.isValidTextField(treatment)) {
            //currently all non-null strings are valid
            return false;
        }
        currRecord = new Record(date, symptom, illness, treatment);
        return true;
    }
}

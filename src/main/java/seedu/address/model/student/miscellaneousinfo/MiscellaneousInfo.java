package seedu.address.model.student.miscellaneousinfo;

import static seedu.address.model.student.miscellaneousinfo.Allergies.isValidAllergies;
import static seedu.address.model.student.miscellaneousinfo.NextOfKinName.isValidNextOfKinName;
import static seedu.address.model.student.miscellaneousinfo.NextOfKinPhone.isValidNextOfKinPhone;
import static seedu.address.model.student.miscellaneousinfo.Remarks.isValidRemarks;

//@@author samuelloh
/**
 * Represents a Student's miscellaneous information.
 * Guarantees: immutable; is valid as declared in {@link #isValidMiscellaneousInfo(MiscellaneousInfo)}
 */
public class MiscellaneousInfo {

    public static final String MESSAGE_MISCELLANEOUS_CONSTRAINTS =
            "Miscellaneous information should take in values according to the constraints of each of its individual"
                    + "components";

    private final Allergies allergies;
    private final NextOfKinName nextOfKinName;
    private final NextOfKinPhone nextOfKinPhone;
    private final Remarks remarks;

    /**
     * Constructs a {@code MiscellaneousInfo} object with initial default values
     */
    public MiscellaneousInfo() {
        this.allergies = new Allergies();
        this.nextOfKinName = new NextOfKinName();
        this.nextOfKinPhone = new NextOfKinPhone();
        this.remarks = new Remarks();
    }

    public MiscellaneousInfo(Allergies allergies, NextOfKinName nextOfKinName,
                             NextOfKinPhone nextOfKinPhone, Remarks remarks) {
        this.allergies = allergies;
        this.nextOfKinName = nextOfKinName;
        this.nextOfKinPhone = nextOfKinPhone;
        this.remarks = remarks;


    }

    /**
     * Returns true if a given string is a valid student allergies.
     */
    public static boolean isValidMiscellaneousInfo(MiscellaneousInfo test) {
        return isValidRemarks(test.remarks.toString()) && isValidNextOfKinPhone(test.nextOfKinPhone.toString())
                && isValidNextOfKinName(test.nextOfKinName.toString()) && isValidAllergies(test.allergies.toString());
    }

    @Override
    public String toString() {
        return "Allergies: " + allergies.toString() +  " "
                + "Next of kin name: " + nextOfKinName.toString() + " "
                + "Next of kin phone: " + nextOfKinPhone.toString() + " "
                + "Remarks: " + remarks.toString() + " ";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MiscellaneousInfo)) {
            return false;
        }

        MiscellaneousInfo otherMiscInfo = (MiscellaneousInfo) other;
        return otherMiscInfo.allergies.equals(this.allergies)
                && otherMiscInfo.nextOfKinPhone.equals(this.nextOfKinPhone)
                && otherMiscInfo.nextOfKinName.equals(this.nextOfKinName)
                && otherMiscInfo.remarks.equals(this.remarks);
    }

    public Allergies getAllergies() {
        return allergies;
    }

    public NextOfKinName getNextOfKinName() {
        return nextOfKinName;
    }

    public NextOfKinPhone getNextOfKinPhone() {
        return nextOfKinPhone;
    }

    public Remarks getRemarks() {
        return remarks;
    }

}
//@@author

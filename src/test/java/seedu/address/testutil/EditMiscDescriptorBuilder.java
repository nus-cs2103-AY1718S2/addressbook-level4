package seedu.address.testutil;

//@@author samuelloh

import seedu.address.logic.commands.EditMiscCommand.EditMiscDescriptor;
import seedu.address.model.student.Student;
import seedu.address.model.student.miscellaneousinfo.Allergies;
import seedu.address.model.student.miscellaneousinfo.NextOfKinName;
import seedu.address.model.student.miscellaneousinfo.NextOfKinPhone;
import seedu.address.model.student.miscellaneousinfo.Remarks;

/**
 * A utility class to help with building an EditMiscDescriptor object
 */
public class EditMiscDescriptorBuilder {

    private EditMiscDescriptor descriptor;

    public EditMiscDescriptorBuilder() {
        descriptor = new EditMiscDescriptor();
    }

    public EditMiscDescriptorBuilder(EditMiscDescriptor editMiscDescriptor) {
        this.descriptor = new EditMiscDescriptor(editMiscDescriptor);
    }

    /**
     * Returns an {@code EditMiscDescriptor} object with fields containing {@code student}'s miscellaneous details
     */
    public EditMiscDescriptorBuilder(Student student) {
        this.descriptor = new EditMiscDescriptor();
        this.descriptor.setAllergies(student.getMiscellaneousInfo().getAllergies());
        this.descriptor.setNextOfKinName(student.getMiscellaneousInfo().getNextOfKinName());
        this.descriptor.setNextOfKinPhone(student.getMiscellaneousInfo().getNextOfKinPhone());
        this.descriptor.setRemarks(student.getMiscellaneousInfo().getRemarks());
    }

    /**
     * Sets the {@code Allergies} of the {@code EditMiscDescriptor} that is being built
     * @param allergies
     */
    public EditMiscDescriptorBuilder withAllergies(String allergies) {
        this.descriptor.setAllergies(new Allergies(allergies));
        return this;
    }
    /**
     * Sets the {@code NextOfKinName} of the {@code EditMiscDescriptor} that is being built
     * @param nextOfKinName
     * */
    public EditMiscDescriptorBuilder withNextOfKinName(String nextOfKinName) {
        this.descriptor.setNextOfKinName(new NextOfKinName(nextOfKinName));
        return this;
    }
    /**
     * Sets the {@code NextOfKinPhone} of the {@code EditMiscDescriptor} that is being built
     * @param nextOfKinPhone
     */
    public EditMiscDescriptorBuilder withNextOfKinPhone(String nextOfKinPhone) {
        this.descriptor.setNextOfKinPhone(new NextOfKinPhone(nextOfKinPhone));
        return this;
    }
    /**
     * Sets the {@code Remarks} of the {@code EditMiscDescriptor} that is being built
     * @param remarks
     */
    public EditMiscDescriptorBuilder withRemarks(String remarks) {
        this.descriptor.setRemarks(new Remarks(remarks));
        return this;
    }


    public EditMiscDescriptor build() {
        return descriptor;
    }

}
//@author

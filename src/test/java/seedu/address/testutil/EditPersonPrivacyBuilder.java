package seedu.address.testutil;

//@@author lohtianwei
import seedu.address.logic.commands.TogglePrivacyCommand.EditPersonPrivacy;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building EditPersonPrivacy objects.
 */
public class EditPersonPrivacyBuilder {

    private EditPersonPrivacy epp;
    public EditPersonPrivacyBuilder() {
        epp = new EditPersonPrivacy();
    }

    public EditPersonPrivacyBuilder(EditPersonPrivacy epp) {
        this.epp = new EditPersonPrivacy(epp);
    }

    /**
     * Returns an {@code EditPersonPrivacy} with fields containing {@code person}'s privacy details
     */
    public EditPersonPrivacyBuilder(Person p) {
        epp = new EditPersonPrivacy();
        epp.setPrivateAddress(p.getAddress().isPrivate());
        epp.setPrivateEmail(p.getEmail().isPrivate());
        epp.setPrivatePhone(p.getPhone().isPrivate());
        epp.setPrivateRemark(p.getRemark().isPrivate());
        epp.setPrivateRating(p.getRating().isPrivate());
    }

    public EditPersonPrivacy build() {
        return epp;
    }
}

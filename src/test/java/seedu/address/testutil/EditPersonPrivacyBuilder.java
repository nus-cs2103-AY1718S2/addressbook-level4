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

    public EditPersonPrivacyBuilder setPhonePrivate(String phone) {
        if (phone.equals("Optional[true]") || phone.equals("true")) {
            epp.setPrivatePhone(true);
        } else if (phone.equals("Optional[false]") || phone.equals("false")) {
            epp.setPrivatePhone(false);
        } else {
            throw new IllegalArgumentException("Privacy of phone should be true or false");
        }
        return this;
    }

    public EditPersonPrivacyBuilder setEmailPrivate(String email) {
        if (email.equals("Optional[true]") || email.equals("true")) {
            epp.setPrivateEmail(true);
        } else if (email.equals("Optional[false]") || email.equals("false")) {
            epp.setPrivateEmail(false);
        } else {
            throw new IllegalArgumentException("Privacy of email should be true or false");
        }
        return this;
    }

    public EditPersonPrivacyBuilder setAddressPrivate(String address) {
        if (address.equals("Optional[true]") || address.equals("true")) {
            epp.setPrivateAddress(true);
        } else if (address.equals("Optional[false]") || address.equals("false")) {
            epp.setPrivateAddress(false);
        } else {
            throw new IllegalArgumentException("Privacy of address should be true or false");
        }
        return this;
    }

    public EditPersonPrivacyBuilder setRatingPrivate(String rating) {
        if (rating.equals("Optional[true]") || rating.equals("true")) {
            epp.setPrivateRating(true);
        } else if (rating.equals("Optional[false]") || rating.equals("false")) {
            epp.setPrivateRating(false);
        } else {
            throw new IllegalArgumentException("Privacy of rating should be true or false");
        }
        return this;
    }

    public EditPersonPrivacyBuilder setRemarkPrivate(String remark) {
        if (remark.equals("Optional[true]") || remark.equals("true")) {
            epp.setPrivateRemark(true);
        } else if (remark.equals("Optional[false]") || remark.equals("false")) {
            epp.setPrivateRemark(false);
        } else {
            throw new IllegalArgumentException("Privacy of remark should be true or false");
        }
        return this;
    }

    public EditPersonPrivacy build() {
        return epp;
    }
}

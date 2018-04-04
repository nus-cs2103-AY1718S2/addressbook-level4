package seedu.address.model.petpatient;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code PetPatient}'s {@code ownerNric} matches any of the keywords given.
 */
public class PetPatientOwnerNricContainsKeywordsPredicate implements Predicate<PetPatient> {
    private final List<String> keywords;

    public PetPatientOwnerNricContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(PetPatient petPatient) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(petPatient.getOwner().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PetPatientOwnerNricContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PetPatientOwnerNricContainsKeywordsPredicate) other).keywords));
        // state check
    }

}

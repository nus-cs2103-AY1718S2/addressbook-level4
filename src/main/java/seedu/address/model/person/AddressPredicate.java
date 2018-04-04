package seedu.address.model.person;

import java.util.ArrayList;
import java.util.function.Predicate;

import seedu.address.logic.parser.PredicateUtil;

//@@author tanhengyeow
/**
 * Represents a Address predicate
 */
public class AddressPredicate implements FieldPredicate {

    private Predicate<Person> addressPredicate = null;

    /**
     * Constructs an {@code AddressPredicate}.
     * @param substringKeywords stores user argument that matches substring
     * @param exactKeywords stores user argument that matches exact keywords
     * @param prefixKeywords stores user argument that matches prefix
     * @param suffixKeywords stores user argument that matches suffix
     */
    public AddressPredicate(ArrayList<String> exactKeywords, ArrayList<String> substringKeywords,
                            ArrayList<String> prefixKeywords, ArrayList<String> suffixKeywords) {

        AddressContainsKeywordsPredicate addressContainsKeywordsPredicate = null;
        AddressContainsSubstringsPredicate addressContainsSubstringsPredicate = null;
        AddressContainsPrefixesPredicate addressContainsPrefixesPredicate = null;
        AddressContainsSuffixesPredicate addressContainsSuffixesPredicate = null;

        if (!exactKeywords.isEmpty()) {
            addressContainsKeywordsPredicate =
                    new AddressContainsKeywordsPredicate(exactKeywords);
        }
        if (!substringKeywords.isEmpty()) {
            addressContainsSubstringsPredicate = new AddressContainsSubstringsPredicate(substringKeywords);
        }
        if (!prefixKeywords.isEmpty()) {
            addressContainsPrefixesPredicate = new AddressContainsPrefixesPredicate(prefixKeywords);
        }
        if (!suffixKeywords.isEmpty()) {
            addressContainsSuffixesPredicate = new AddressContainsSuffixesPredicate(suffixKeywords);
        }
        if (!exactKeywords.isEmpty() || !substringKeywords.isEmpty()
                || !prefixKeywords.isEmpty() || !suffixKeywords.isEmpty()) {
            this.addressPredicate = PredicateUtil.formOrPredicate(addressContainsKeywordsPredicate,
                    addressContainsSubstringsPredicate, addressContainsPrefixesPredicate,
                    addressContainsSuffixesPredicate);
        }
    }

    @Override
    public Predicate<Person> getPredicate() {
        return addressPredicate;
    }

    @Override
    public String toString() {
        return addressPredicate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressPredicate // instanceof handles nulls
                && this.addressPredicate.equals(((AddressPredicate) other).addressPredicate)); // state check
    }

    @Override
    public int hashCode() {
        return addressPredicate.hashCode();
    }

}

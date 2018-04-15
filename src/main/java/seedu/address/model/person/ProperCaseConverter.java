package seedu.address.model.person;

//@@author alexawangzi
/**
 * Helper class to change a String to proper class ("This Is An Example Of Proper Case.")
 */
class ProperCaseConverter {

    /**
     * convert the value string to proper case
     */
    public String convertToProperCase(String original) {
        StringBuilder properCase = new StringBuilder();
        boolean nextProperCase = true;

        original = original.toLowerCase();

        for (char c : original.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextProperCase = true;
            } else if (nextProperCase) {
                c = Character.toUpperCase(c);
                nextProperCase = false;
            }
            properCase.append(c);
        }
        return properCase.toString();
    }
}

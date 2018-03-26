package seedu.address.model.person;

/**
 * Helper class to change a string to proper class
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

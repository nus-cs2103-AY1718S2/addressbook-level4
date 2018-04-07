//@@author Eldon-Chung
package seedu.address.logic.parser;

/**
 * Represents the possible types a token can take, along with the regular expression it is specified by.
 */
public enum TokenType {
    BINARYBOOL("( OR | AND )", "BINARYBOOL"),
    UNARYBOOL("NOT ", "UNARYBOOL"),
    LEFTPARENTHESES("\\(", "LEFTPARENTHESES"),
    RIGHTPARENTHESES("\\)", "RIGHTPARENTHESES"),
    COMPARATOR("(>|=|<)+", "COMPARATOR"),
    PREFIX_AMOUNT("a/", "APREFIX"),
    PREFIX_BOUGHT("b/", "BPREFIX"),
    PREFIX_CODE("c/", "CPREFIX"),
    PREFIX_EARNED("e/", "EPREFIX"),
    PREFIX_HELD("h/", "HPREFIX"),
    PREFIX_MADE("m/", "MPREFIX"),
    PREFIX_NAME("n/", "NPREFIX"),
    PREFIX_PRICE("p/", "PPREFIX"),
    PREFIX_SOLD("s/", "SPREFIX"),
    PREFIX_TAG("t/", "TPREFIX"),
    PREFIX_WORTH("w/", "WPREFIX"),
    DECIMAL("[0-9]+\\.[0-9]+", "DECIMAL"),
    NUM("[1-9][0-9]*", "NUM"),
    STRING("[A-Za-z\\^\\-\\@\\./]+", "STRING"),
    SLASH("/", "SLASH"),
    WHITESPACE("\\s", "WHITESPACE"),
    NEWLINE("\\n", "NEWLINE"),
    ELSE(".+", "ELSE"),
    EOF("[^\\w\\W]", "EOF");

    final String typeName;
    final String regex;

    TokenType(final String regex, final String typeName) {
        this.regex = regex;
        this.typeName = typeName;
    }

    public String toString() {
        return this.regex;
    }

    /**
     * Checks if the {@code type} is a prefix type
     * @param type the type to be checked
     */
    public static boolean isPrefixType(TokenType type) {
        return type == PREFIX_AMOUNT
                || type == PREFIX_NAME
                || type == PREFIX_PRICE
                || type == PREFIX_EARNED
                || type == PREFIX_TAG;
    }
}

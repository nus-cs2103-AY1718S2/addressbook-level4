//@@author Eldon-Chung
package seedu.address.logic.parser;

/**
 * Represents the possible types a token can take, along with the regular expression it is specified by.
 */
public enum TokenType {
    BINARYBOOL("( OR | AND )"),
    UNARYBOOL("NOT "),
    LEFTPARENTHESES("\\("),
    RIGHTPARENTHESES("\\)"),
    COMPARATOR("(>|=|<)+"),
    PREFIXAMOUNT("a/"),
    PREFIXNAME("n/"),
    PREFIXPROFIT("p/"),
    PREFIXEMAIL("e/"),
    PREFIXTAG("t/"),
    DECIMAL("[0-9]+\\.[0-9]+"),
    NUM("[1-9][0-9]*"),
    STRING("[A-Za-z\\^\\-\\@\\./]+"),
    SLASH("/"),
    WHITESPACE("\\s"),
    NEWLINE("\\n"),
    ELSE(".+"),
    EOF("[^\\w\\W]");

    final String regex;

    TokenType(final String regex) {
        this.regex = regex;
    }

    public String toString() {
        return this.regex;
    }

    /**
     * Checks if the {@code type} is a prefix type
     * @param type the type to be checked
     */
    public static boolean isPrefixType(TokenType type) {
        return type == PREFIXAMOUNT
                || type == PREFIXNAME
                || type == PREFIXPROFIT
                || type == PREFIXEMAIL
                || type == PREFIXTAG;
    }
}

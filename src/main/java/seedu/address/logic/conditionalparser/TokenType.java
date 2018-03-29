//@@author Eldon-Chung
package seedu.address.logic.conditionalparser;

/**
 * Represents the possible types a token can take, along with the regular expression it is specified by.
 */
public enum TokenType {
    BINARYBOOL("( OR | AND )"),
    UNARYBOOL("NOT "),
    LEFTPARENTHESES("\\("),
    RIGHTPARENTHESES("\\)"),
    COMPARATOR("(>|=|<)+"),
    SPECIFIER("[a-z]/"),
    NUM("[0-9]+(\\.?[0-9]+)?"),
    STRING("[A-Za-z]+"),
    SLASH("/"),
    WHITESPACE("\\s"),
    NEWLINE("\\n"),
    ELSE(".+");

    final String regex;

    TokenType(final String regex) {
        this.regex = regex;
    }
}

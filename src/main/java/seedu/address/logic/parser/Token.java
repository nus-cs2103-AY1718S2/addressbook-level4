//@@author Eldon-Chung
package seedu.address.logic.parser;

/**
 * Represents the token type that that portions of the string can be grouped into.
 */
public class Token {
    private TokenType type;
    private String pattern;

    public Token(TokenType type, String pattern) {
        this.type = type;
        this.pattern = pattern;
    }

    public TokenType getType() {
        return this.type;
    }

    public boolean hasType(TokenType type) {
        return this.type == type;
    }

    public String getPattern() {
        return this.pattern;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)", pattern, this.type.name());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Token)) {
            return false;
        }

        Token otherToken = (Token) other;
        return otherToken.getType().equals(this.type)
                && otherToken.getPattern().equals(this.pattern);

    }
}

package seedu.address.logic.parser;

/**
 * Parses tokenized boolean logic statements to verify correctness
 */
//@@author Eldon-Chung
public class ConditionSyntaxParser {

    private TokenStack tokenStack;

    public ConditionSyntaxParser(TokenStack tokenStack) {
        this.tokenStack = tokenStack;
        this.tokenStack.resetStack();
    }

    public TokenType getExpectedType() {
        return this.tokenStack.getLastExpectedType();
    }

    public TokenType getActualType() {
        return this.tokenStack.getActualType();
    }

    /**
     * Parses the token stack against the boolean logic grammar loaded into the tokenStack
     * @return correctness of the tokenStack
     */
    public boolean parse() {
        return expression()
                && tokenStack.matchAndPopTokenType(TokenType.EOF);
    }

    /**
     * Matches the tokenStack against the expression grammar rule
     * @return true if the tokenStack was correct
     */
    boolean expression() {
        if (!term()) {
            return false;
        }
        while (tokenStack.matchAndPopTokenType(TokenType.BINARYBOOL)) {
            if (!term()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Matches the tokenStack against the term grammar rule
     * @return true if the tokenStack was correct
     */
    boolean term() {

        if (tokenStack.matchAndPopTokenType(TokenType.LEFTPARENTHESES)) {
            if (!expression()) {
                return false;
            }
            return tokenStack.matchAndPopTokenType(TokenType.RIGHTPARENTHESES);
        } else if (tokenStack.matchAndPopTokenType(TokenType.UNARYBOOL)) {
            return term();
        }
        return cond();
    }

    /**
     * Matches the tokenStack against the cond grammar rule
     * @return true if the tokenStack was correct
     */
    boolean cond() {
        if (!isPrefix()) {
            return false;
        }

        tokenStack.matchAndPopTokenType(TokenType.COMPARATOR);

        return tokenStack.matchAndPopTokenType(TokenType.NUM)
                || tokenStack.matchAndPopTokenType(TokenType.STRING)
                || tokenStack.matchAndPopTokenType(TokenType.DECIMAL);
    }

    /**
     * Checks if the top of the tokenStack is currently a prefix TokenType.
     */
    private boolean isPrefix() {
        if (!tokenStack.isEmpty() && TokenType.isPrefixType(tokenStack.getActualType())) {
            tokenStack.popToken();
            return true;
        }
        // If it was not a PREFIX, we do this to set the last expected type to some PREFIX type.
        tokenStack.matchTokenType(TokenType.PREFIX_AMOUNT);
        return false;
    }

}

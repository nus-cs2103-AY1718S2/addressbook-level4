package seedu.address.logic.parser;

//@@author Eldon-Chung
/**
 * Parses tokenized boolean logic statements to verify correctness
 */
public class ConditionSemanticParser {

    private TokenStack tokenStack;

    public ConditionSemanticParser(TokenStack tokenStack) {
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
     * Parses the input as a token stack semantically.
     * @return true if the input is semantically valid.
     */
    public boolean parse() {
        while (!tokenStack.isEmpty()) {
            TokenType peekType = tokenStack.popToken().getType();
            if (TokenType.isPrefixType(peekType) && !hasCorrectParameterType(peekType)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks to see each the prefix type is followed by the appropriate parameter types.
     * @param type
     * @return true if the prefix type is followed by the appropriate parameter types.
     */
    private boolean hasCorrectParameterType(TokenType type) {
        switch (type) {
        case PREFIX_HELD:
        case PREFIX_HELD_RISE:
        case PREFIX_HELD_FALL:
        case PREFIX_SOLD:
        case PREFIX_SOLD_RISE:
        case PREFIX_SOLD_FALL:
        case PREFIX_BOUGHT:
        case PREFIX_BOUGHT_RISE:
        case PREFIX_BOUGHT_FALL:
        case PREFIX_MADE:
        case PREFIX_MADE_RISE:
        case PREFIX_MADE_FALL:
        case PREFIX_PRICE:
        case PREFIX_PRICE_RISE:
        case PREFIX_PRICE_FALL:
        case PREFIX_WORTH:
        case PREFIX_WORTH_RISE:
        case PREFIX_WORTH_FALL:
            return hasNumericalParameter();
        case PREFIX_CODE:
        case PREFIX_TAG:
            return hasStringParameter();
        default:
            return false;
        }
    }

    /**
     * Checks if the next two tokens are a comparator followed by a number.
     * @return true if the next two tokens are a comparator followed by a number.
     */
    private boolean hasNumericalParameter() {
        return tokenStack.matchAndPopTokenType(TokenType.COMPARATOR)
                && (tokenStack.matchAndPopTokenType(TokenType.NUM)
                || tokenStack.matchAndPopTokenType(TokenType.DECIMAL));

    }

    /**
     * Checks if the next next token is a string.
     * @return true if the next token is a string.
     */
    private boolean hasStringParameter() {
        return tokenStack.matchAndPopTokenType(TokenType.STRING);
    }


}

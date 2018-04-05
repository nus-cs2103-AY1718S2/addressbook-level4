//@@author Eldon-Chung
package seedu.address.logic.parser;

import static seedu.address.logic.parser.TokenType.WHITESPACE;

import java.util.EmptyStackException;
import java.util.List;


/**
 * Represents stack of Token objects.
 */
public class TokenStack {
    private List<Token> tokenList;
    private TokenType lastExpectedType;
    private int tokenHeadIndex;

    public TokenStack(List<Token> tokenList) {
        tokenList.removeIf(t -> t.hasType(WHITESPACE));
        this.tokenList = tokenList;
        tokenHeadIndex = 0;
        lastExpectedType = null;
    }

    /**
     * Matches the type with the top token on the tokenList and pops it if they are they same.
     * @param type the TokenType to compare the top token with.
     * @return true if the types are the same, false if either the stack is expended or they are not the same type
     */
    public boolean matchAndPopTokenType(TokenType type) throws EmptyStackException {
        lastExpectedType = type;
        if (tokenHeadIndex >= tokenList.size()) {
            throw new EmptyStackException();
        }
        if (tokenList.get(tokenHeadIndex).getType() == type) {
            tokenHeadIndex++;
            return true;
        }
        return false;
    }

    /**
     * Matches the type with the top token on the tokenList.
     * @param type the TokenType to compare the top token with.
     * @return true if the types are the same.
     * @throws EmptyStackException if the stack is empty
     */
    public boolean matchTokenType(TokenType type) throws EmptyStackException {
        lastExpectedType = type;
        if (tokenHeadIndex >= tokenList.size()) {
            throw new EmptyStackException();
        }
        return tokenList.get(tokenHeadIndex).getType() == type;
    }

    /**
     * @return the {@code Token} at the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    public Token popToken() throws EmptyStackException {
        if (tokenHeadIndex >= tokenList.size()) {
            throw new EmptyStackException();
        }
        return tokenList.get(tokenHeadIndex++);
    }

    /**
     * Returns the Token at the top of the stack without removing it
     * @return the Token at the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    public Token peekToken() throws EmptyStackException {
        if (tokenHeadIndex >= tokenList.size()) {
            throw new EmptyStackException();
        }
        return tokenList.get(tokenHeadIndex);
    }

    /**
     * Resets the {@code TokenStack} to restore all the popped Tokens
     */
    public void resetStack() {
        tokenHeadIndex = 0;
    }

    public boolean isEmpty() {
        return tokenHeadIndex == tokenList.size();
    }

    public List<Token> getTokenList() {
        return tokenList;
    }

    /**
     * @return The last TokenType that was checked.
     */
    public TokenType getLastExpectedType() {
        return lastExpectedType;
    }

    /**
     * @return The TokenType of token currently on top of the stack.
     * @throws EmptyStackException if the stack is empty.
     */
    public TokenType getActualType() throws EmptyStackException {
        if (tokenHeadIndex >= tokenList.size()) {
            throw new EmptyStackException();
        }
        return tokenList.get(tokenHeadIndex).getType();
    }
}

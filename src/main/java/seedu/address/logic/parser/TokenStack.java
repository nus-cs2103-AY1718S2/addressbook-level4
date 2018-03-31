//@@author Eldon-Chung

package seedu.address.logic.parser;

import java.util.List;
import java.util.Stack;

import com.google.common.collect.Lists;

/**
 * Represents stack of Token objects.
 */
public class TokenStack {
    private Stack<Token> tokenStack;
    private TokenType lastExpectedType;

    public TokenStack(List<Token> tokenList) {
        tokenStack = new Stack<Token>();
        tokenStack.addAll(Lists.reverse(tokenList));
    }

    /**
     * Matches the type with the top token on the tokenStack and pops it if they are they same.
     * @param type the TokenType to compare the top token with.
     * @return true if the types are the same.
     */
    public boolean matchAndPopTokenType(TokenType type) {
        lastExpectedType = type;
        if (!tokenStack.isEmpty() && tokenStack.peek().getType() == type) {
            tokenStack.pop();
            return true;
        }
        return false;
    }

    /**
     * Matches the type with the top token on the tokenStack.
     * @param type the TokenType to compare the top token with.
     * @return true if the types are the same.
     */
    public boolean matchTokenType(TokenType type) {
        lastExpectedType = type;
        return tokenStack.peek().getType() == type;
    }

    public Token popToken() {
        return tokenStack.pop();
    }

    public Token peekToken() {
        return tokenStack.peek();
    }

    public boolean isEmpty() {
        return tokenStack.isEmpty();
    }

    public Stack<Token> getTokenStack() {
        return tokenStack;
    }

    /**
     * @return The last TokenType that was checked.
     */
    public TokenType getLastExpectedType() {
        return lastExpectedType;
    }

    /**
     * @return The TokenType of token currently on top of the stack.
     */
    public TokenType getActualType() {
        return tokenStack.peek().getType();
    }
}

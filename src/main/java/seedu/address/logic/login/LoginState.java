//@@author cxingkai
package seedu.address.logic.login;

/**
 * Stores login state
 */
public class LoginState {

    private int state;
    private String user;

    LoginState(int state, String user) {
        this.state = state;
        this.user = user;
    }

    public void updateState(int newState, String user) {
        this.state = newState;
        this.user = user;
    }

    public int getState() {
        return state;
    }

    public String getUser() { return user; }
}

package seedu.address.logic;

public final class LoginManager {
    public static final int NO_USER = 0;
    public static final int DOCTOR = 1;
    public static final int MEDICAL_STAFF = 2;

    private static LoginState currLoginState = new LoginState(NO_USER);

    private LoginManager() {
        currLoginState = new LoginState(NO_USER);
    }

    public static int getUserState() {
        return currLoginState.getState();
    }

    public static void changeUserState(int loginStateIndex){
        currLoginState.updateState(loginStateIndex);
    }

    public static void authenticate(String username, String password){
        if (true){
            changeUserState(DOCTOR);
        }
    }
}

package seedu.address.logic;

//@@author 592363789
/**
 * Manages the locked/unlocked state of the app and the password.
 */
public class LockManager {

    public static final String NO_PASSWORD = "";

    private static LockManager lockManager;

    private String password;
    private String oldPassword;
    private boolean isLocked;

    private LockManager() {
        oldPassword = NO_PASSWORD;
        password = NO_PASSWORD;
        isLocked = false;
    }

    /**
     * Sets up {@code LockManager} with {@code pw} as password.
     * The state is set to locked if there is a non-empty password.
     */
    public static void instantiate(String pw) {
        LockManager lockManager = getInstance();
        lockManager.oldPassword = NO_PASSWORD;
        lockManager.password = pw;
        if (!pw.equals(NO_PASSWORD)) {
            lockManager.isLocked = true;
        }
    }

    public static LockManager getInstance() {
        if (lockManager == null) {
            lockManager = new LockManager();
        }
        return lockManager;
    }

    /**
     * Changes the password to {@code newPw} if {@code oldPw} matches the old password.
     * @return whether the password is changed.
     */
    public boolean setPassword(String oldPw, String newPw) {
        if (!oldPw.equals(password)) {
            return false;
        }
        oldPassword = password;
        password = newPw;
        return true;
    }

    public String getPassword() {
        return password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * Locks the app.
     */
    public void lock() {
        isLocked = true;
    }

    /**
     * Unlocks the app if {@code password} matches the current password.
     * @return whether the app is unlocked.
     */
    public boolean unlock(String pw) {
        if (!isLocked() || !password.equals(pw)) {
            return false;
        }
        isLocked = false;
        return true;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public boolean isPasswordProtected() {
        return !password.equals(NO_PASSWORD);
    }

    public boolean wasPasswordProtected() {
        return !oldPassword.equals(NO_PASSWORD);
    }
}

package seedu.address.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public final class LoginManager {
    public static final int NO_USER = 0;
    public static final int DOCTOR_LOGIN = 1;
    public static final int MEDICAL_STAFF_LOGIN = 2;

    private static LoginState currLoginState = new LoginState(NO_USER);
    private static String passwordPath = "data/passwords.csv";

    private LoginManager() {
        currLoginState = new LoginState(NO_USER);
    }

    public static int getUserState() {
        return currLoginState.getState();
    }

    public static boolean authenticate (String username, String password){
        boolean match = false;
        int loginStateIndex = 0;

        File file = new File(passwordPath);

        try {
            Scanner inputStream = new Scanner(file);
            inputStream.useDelimiter(",");

            while (inputStream.hasNext()) {
                String fileUsername = inputStream.next();
                String filePassword = inputStream.next();

                System.out.println("***");
                System.out.println(username + ", " + password);
                System.out.println(fileUsername + ", " + filePassword);
                System.out.println("***");

                if (username.equals(fileUsername) && password.equals(filePassword)) {
                    int role = Integer.parseInt(inputStream.next());
                    loginStateIndex = role;
                    match = true;
                    break;
                }

                inputStream.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (match){
            currLoginState.updateState(loginStateIndex);
            System.out.println("Success");
            return true;
        }

        return false;
    }
}

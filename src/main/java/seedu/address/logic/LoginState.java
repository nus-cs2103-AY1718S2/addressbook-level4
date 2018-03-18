package seedu.address.logic;

public class LoginState {

    private int state;

    public LoginState(int state){
        this.state = state;
    }

    public void updateState(int newState){
        this.state = newState;
    }

    public int getState(){
        return state;
    }
}

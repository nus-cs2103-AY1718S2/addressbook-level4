# chweeee
###### \java\seedu\address\logic\commands\ConversationCommand.java
``` java
/**
 * purpose of this class is to be able to send userInput as text to the chatbot to be processed
 **/
public class ConversationCommand extends Command {

    public static final String WELCOME_MESSAGE = "Hello";
    private static Conversation service = null;

    public static void setUpAgent() {
        service = new Conversation("2018-02-16");
        service.setUsernameAndPassword("531ab0c0-8012-4e3b-8a37-eb1ca8fa1010", "ZkCeN4YqIH3W");
    }

    public static MessageResponse getMessageResponse(String userInput) {
        MessageResponse response = null;

        InputData input = new InputData.Builder(userInput).build();
        MessageOptions option = new MessageOptions.Builder("19f7b6f4-7944-419d-83a0-6bf9837ec333").input(input).build();
        response = service.message(option).execute();

        return response;
    }

    public static String getResponseText(MessageResponse response) {
        String text = response.getOutput().getText().toString();

        return text;
    }

    @Override
    public CommandResult execute() throws CommandException {
        return new CommandResult("Bye!");
    }
}
//@@
```

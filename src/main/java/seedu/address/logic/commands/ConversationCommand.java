package seedu.address.logic.commands;

import java.io.InputStreamReader;
import java.util.Map;
import java.lang.String;
import java.util.Scanner;

import com.ibm.watson.developer_cloud.service.WatsonService;
import com.ibm.watson.developer_cloud.conversation.v1.model.InputData;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.conversation.v1.model.OutputData;
import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.http.ServiceCallback;

import static java.lang.System.in;
import jersey.repackaged.jsr166e.CompletableFuture;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author chweeee
public class ConversationCommand extends Command {

    public static final String WELCOME_MESSAGE = "Hello";
    private static Conversation service = null;

    public static void setUpAgent(){
        service = new Conversation("2018-02-16");
        service.setUsernameAndPassword("531ab0c0-8012-4e3b-8a37-eb1ca8fa1010", "ZkCeN4YqIH3W");
    }

    public static MessageResponse getMessageResponse(String userInput){
        MessageResponse response = null;

        InputData input = new InputData.Builder(userInput).build();
        MessageOptions options = new MessageOptions.Builder("c2e2ef32-b020-40f3-88aa-1c7c40afb1e0").input(input).build();
        response = service.message(options).execute();

        return response;
    }

    public static String getResponseText(MessageResponse response){
        String text = response.getOutput().getText().toString();

        return text;
    }

    @Override
    public CommandResult execute() throws CommandException {
        return new CommandResult("Bye!");
    }
}


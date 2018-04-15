//@@author A0143487X
package seedu.address.logic.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.order.Order;

/**
 * Exports listed orders from the address book into file with specified filename.
 */
public class ExportListedOrdersCommand extends Command {
    public static final String COMMAND_WORD = "exportListedOrders";
    public static final String COMMAND_ALIAS = "exLO";

    //EDIT START
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + "FILENAME";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports all listed orders as a CSV file"
            + " with file name FILENAME as specified\n"
            + "Parameters: FILENAME\n"
            + "Example: " + COMMAND_WORD + " FILENAME";

    public static final String MESSAGE_SUCCESS = "Export success! Listed orders exported to";

    //EDIT END

    private final String pathName;

    public ExportListedOrdersCommand(String inputPath) {
        this.pathName = inputPath;
    }

    @Override
    public CommandResult execute() throws CommandException {

        //
        try {
            File outputFile = new File(pathName);

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile.getPath()));

            writer.write("Order Information,Order Status,"
                    + "Price,Quantity,Delivery Date" + System.getProperty("line.separator")); // headings

            //get data

            ObservableList<Order> orderList = model.getFilteredOrderList();
            int numOfEntries = orderList.size();
            String entryDetails;

            for (int currIdx = 0; currIdx < numOfEntries; currIdx++) {
                Order currOrder = orderList.get(currIdx);

                entryDetails = "\"" + currOrder.getOrderInformation() + "\",\""
                        + currOrder.getOrderStatus() + "\",\""
                        + currOrder.getPrice() + "\",\""
                        + currOrder.getQuantity() + "\",\""
                        + currOrder.getDeliveryDate() + "\"";
                writer.append(entryDetails + System.getProperty("line.separator"));
            }

            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return new CommandResult(MESSAGE_SUCCESS + " " + pathName);
    }
}

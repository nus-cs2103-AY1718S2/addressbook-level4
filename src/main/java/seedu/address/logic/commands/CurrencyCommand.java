package seedu.address.logic.commands;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONException;

import com.ritaja.xchangerate.api.CurrencyConverter;
import com.ritaja.xchangerate.api.CurrencyConverterBuilder;
import com.ritaja.xchangerate.api.CurrencyNotSupportedException;
import com.ritaja.xchangerate.endpoint.EndpointException;
import com.ritaja.xchangerate.service.ServiceException;
import com.ritaja.xchangerate.storage.StorageException;
import com.ritaja.xchangerate.util.Currency;
import com.ritaja.xchangerate.util.Strategy;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

/**
 * Lists all the commands entered by user from the start of app launch.
 */
public class CurrencyCommand extends Command {

    public static final String COMMAND_WORD = "convert";
    public static final String COMMAND_SHORTCUT = "cv";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[Current Currency Symbol]  "
            + "[New Currency Symbol]\n"
            + "Example: " + COMMAND_WORD + " 1" + " SGD" + " USD";
    public static final String MESSAGE_SUCCESS = "Here is your balance in the new currency";

    private String fromCurrency;
    private String toCurrency;
    private Index index;
    private Person convertedPerson;
    private Double convertedPersonBalance;
    private BigDecimal newAmount;

    private CurrencyConverter converter = new CurrencyConverterBuilder()
            .strategy(Strategy.YAHOO_FINANCE_FILESTORE)
            .buildConverter();

    public CurrencyCommand(Index index, String fromCurrency, String toCurrency) {
        this.index = index;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        converter.setRefreshRateSeconds(86400);

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        convertedPerson = lastShownList.get(index.getZeroBased());
        convertedPersonBalance = convertedPerson.getMoney().balance;

        try {
            newAmount = converter.convertCurrency(new BigDecimal(convertedPersonBalance),
            Currency.get(fromCurrency), Currency.get(toCurrency));
        } catch (CurrencyNotSupportedException cnse) {
            throw new AssertionError("Currency not supported");
        } catch (JSONException jsone) {
            throw new AssertionError("JSON Exception");
        } catch (StorageException se) {
            throw new AssertionError("Storage Exception");
        } catch (EndpointException ee) {
            throw new AssertionError("Endpoint Exception");
        } catch (ServiceException se) {
            throw new AssertionError("Service Exception");
        }
        return new CommandResult(convertedPerson.getName() + "'s balance in " + toCurrency + " is: " + newAmount);

    }
}

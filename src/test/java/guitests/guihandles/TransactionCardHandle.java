//@@author phmignot
package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a transaction card in the transaction list panel.
 */
public class TransactionCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#idDisplay";
    private static final String PAYER_FIELD_ID = "#payerName";
    private static final String AMOUNT_FIELD_ID = "#amount";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String PAYEES_FIELD_ID = "#payees";

    private final Label idLabel;
    private final Label payerLabel;
    private final Label amountLabel;
    private final Label descriptionLabel;
    private final List<Label> payeesLabel;

    public TransactionCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.payerLabel = getChildNode(PAYER_FIELD_ID);
        this.amountLabel = getChildNode(AMOUNT_FIELD_ID);
        this.descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
        Region payeesContainer = getChildNode(PAYEES_FIELD_ID);
        this.payeesLabel = payeesContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());

    }

    public String getId() {
        return idLabel.getText();
    }

    public String getPayer() {
        return payerLabel.getText();
    }

    public String getAmount() {
        return amountLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public String getPayees() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < payeesLabel.size(); i++) {
            builder.append(payeesLabel.get(i).getText());
        }
        return builder.toString();
    }
}


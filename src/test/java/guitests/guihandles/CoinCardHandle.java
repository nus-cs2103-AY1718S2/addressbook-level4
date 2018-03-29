package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a coin card in the coin list panel.
 */
public class CoinCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String CODE_FIELD_ID = "#code";
    private static final String AMOUNT_FIELD_ID = "#amount";
    private static final String PRICE_FIELD_ID = "#price";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label codeLabel;
    private final Label amountLabel;
    private final Label priceLabel;
    private final List<Label> tagLabels;

    public CoinCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.codeLabel = getChildNode(CODE_FIELD_ID);
        this.amountLabel = getChildNode(AMOUNT_FIELD_ID);
        this.priceLabel = getChildNode(PRICE_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return codeLabel.getText();
    }

    public String getAmount() {
        return amountLabel.getText();
    }

    public String getPrice() {
        return priceLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}

package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a rule card in the rule list panel.
 */
public class RuleCardHandle extends NodeHandle<Node> {
    private static final String VALUE_FIELD_ID = "#value";

    private final Label valueLabel;

    public RuleCardHandle(Node cardNode) {
        super(cardNode);

        this.valueLabel = getChildNode(VALUE_FIELD_ID);
    }

    public String getValue() {
        return valueLabel.getText();
    }
}

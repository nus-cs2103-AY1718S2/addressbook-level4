package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;

/**
 * A handle to the {@code EmailPanel} in the GUI.
 */
public class EmailPanelHandle extends NodeHandle<Node> {

    private static final String RECIPIENT_ID = "#to";
    private static final String BODY_ID = "#body";

    private final TextField to;
    private final HTMLEditor body;

    public EmailPanelHandle(Node emailPanelNode) {
        super(emailPanelNode);

        this.to = getChildNode(RECIPIENT_ID);
        this.body = getChildNode(BODY_ID);
    }

    public String getRecipient() {
        return to.getText();
    }

    public String getBody() {
        return body.getHtmlText().replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
    }

}

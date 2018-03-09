package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class BookCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String TITLE_FIELD_ID = "#title";
    private static final String AUTHORS_FIELD_ID = "#authors";
    private static final String CATEGORIES_FIELD_ID = "#categories";

    private final Label idLabel;
    private final Label titleLabel;
    private final List<Label> authorsLabel;
    private final List<Label> categoriesLabel;

    public BookCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.titleLabel = getChildNode(TITLE_FIELD_ID);

        Region authorsContainer = getChildNode(AUTHORS_FIELD_ID);
        this.authorsLabel = authorsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());

        Region categoriesContainer = getChildNode(CATEGORIES_FIELD_ID);
        this.categoriesLabel = categoriesContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public List<String> getAuthors() {
        return authorsLabel
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public List<String> getCategories() {
        return categoriesLabel
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}

package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Provides a handle for the {@code BookDetailsPanel} of the UI.
 */
public class BookDetailsPanelHandle extends NodeHandle<Node> {
    protected static final String BOOK_DETAILS_PANE_ID = "#bookDetailsPane";

    private static final String TITLE_FIELD_ID = "#title";
    private static final String ISBN_FIELD_ID = "#isbn";
    private static final String PUBLISHER_FIELD_ID = "#publisher";
    private static final String PUBLICATION_DATE_FIELD_ID = "#publicationDate";
    private static final String AUTHORS_FIELD_ID = "#authors";
    private static final String CATEGORIES_FIELD_ID = "#categories";

    private final Label titleLabel;
    private final Label isbnLabel;
    private final Label publisherLabel;
    private final Label publicationDateLabel;
    private final BookDescriptionViewHandle bookDescriptionViewHandle;
    private List<Label> authorsLabel;
    private List<Label> categoriesLabel;

    private String lastRememberedIsbn;
    private boolean lastRememberedVisibility;

    public BookDetailsPanelHandle(Node bookDetailsPanelNode) {
        super(bookDetailsPanelNode);

        this.titleLabel = getChildNode(TITLE_FIELD_ID);
        this.isbnLabel = getChildNode(ISBN_FIELD_ID);
        this.publisherLabel = getChildNode(PUBLISHER_FIELD_ID);
        this.publicationDateLabel = getChildNode(PUBLICATION_DATE_FIELD_ID);
        this.bookDescriptionViewHandle =
                new BookDescriptionViewHandle(getChildNode(BookDescriptionViewHandle.BOOK_DESCRIPTION_VIEW_ID));

        updateAuthorsLabel();
        updateCategoriesLabel();
    }

    public String getIsbn() {
        return isbnLabel.getText();
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public String getPublisher() {
        return publisherLabel.getText();
    }

    public String getPublicationDate() {
        return publicationDateLabel.getText();
    }

    public String getDescription() {
        return bookDescriptionViewHandle.getContent();
    }

    public List<String> getAuthors() {
        updateAuthorsLabel();
        return authorsLabel
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public List<String> getCategories() {
        updateCategoriesLabel();
        return categoriesLabel
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public boolean isVisible() {
        return getRootNode().isVisible();
    }

    public void rememberVisibility() {
        lastRememberedVisibility = isVisible();
    }

    public boolean isVisibilityChanged() {
        return !lastRememberedVisibility == isVisible();
    }

    public void rememberIsbn() {
        lastRememberedIsbn = getIsbn();
    }

    public boolean isIsbnChanged() {
        return !lastRememberedIsbn.equals(getIsbn());
    }

    /** Update the list of authors labels. */
    private void updateAuthorsLabel() {
        Region authorsContainer = getChildNode(AUTHORS_FIELD_ID);
        this.authorsLabel = authorsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    /** Update the list of categories labels. */
    private void updateCategoriesLabel() {
        Region categoriesContainer = getChildNode(CATEGORIES_FIELD_ID);
        this.categoriesLabel = categoriesContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }
}

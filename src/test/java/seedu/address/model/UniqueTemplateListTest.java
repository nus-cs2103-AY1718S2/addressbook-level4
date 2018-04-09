package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.email.Template;
import seedu.address.model.email.UniqueTemplateList;
import seedu.address.model.email.exceptions.DuplicateTemplateException;
import seedu.address.model.email.exceptions.TemplateNotFoundException;

//@@author ng95junwei

public class UniqueTemplateListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTemplateList uniqueTemplateList = new UniqueTemplateList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTemplateList.asObservableList().remove(0);
    }

    @Test
    public void addDuplicate_throwsDuplicateTemplateException() throws DuplicateTemplateException {
        Template template = new Template("test", "test", "test");
        UniqueTemplateList uniqueTemplateList = new UniqueTemplateList();
        thrown.expect(DuplicateTemplateException.class);
        uniqueTemplateList.add(template);
        uniqueTemplateList.add(template);
    }

    @Test
    public void asObservableList_modifyList_throwsTemplateNotFoundException() throws TemplateNotFoundException {
        UniqueTemplateList uniqueTemplateList = new UniqueTemplateList();
        thrown.expect(TemplateNotFoundException.class);
        uniqueTemplateList.remove(new Template("test", "test", "test"));
    }

}

//@@author

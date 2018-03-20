package seedu.address.ui;

import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * Controller for a journal page
 */
public class JournalWindow extends JFrame {
    public JournalWindow() {
        final JTextArea textArea = new JTextArea();
        getContentPane().add(textArea);
    }
    //do something

}

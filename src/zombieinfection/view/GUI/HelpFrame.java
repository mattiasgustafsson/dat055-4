package zombieinfection.view.GUI;

import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This class is responsible for the help frame.
 *
 * @author David.S
 * @version 2018-02-23
 */
public class HelpFrame extends JFrame {

    JTextArea textArea = new JTextArea();

    /**
     * Creates the help panel used in the JFrame.
     *
     * @throws IOException
     */
    public HelpFrame() throws IOException {
        makeHelpPanel();
    }

    /**
     * Makes help panel that reads from text file.
     *
     * @throws IOException
     */
    private void makeHelpPanel() throws IOException {
        setSize(540, 500);
        setTitle("Help");
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setFont(new Font("Dialog", 0, 16));

        JScrollPane scrollPane = new JScrollPane(textArea);

        try {
            InputStream text = getClass().getClassLoader().getResourceAsStream("txt/help.txt");
            textArea.read(new InputStreamReader(text),
                    null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.add(scrollPane);
        this.setVisible(true);

    }
}

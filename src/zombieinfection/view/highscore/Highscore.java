package zombieinfection.view.highscore;

import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import zombieinfection.model.GameEngine;

/**
 * This class is responsible for displaying the highscore window and inputting
 * new highscores.
 *
 * @author David.S
 * @version 2018-02-23
 */
public class Highscore extends JDialog implements ActionListener {

    Table tableManager;
    JTable table;
    JLabel nameText;
    JLabel scoreText;
    JTextField nameInput;
    JLabel scoreInput;
    JButton submit;
    JPanel panel;
    JPanel panel2;
    int score;

    /**
     * Creates a panel with a table of highscore data. Adds second panel with
     * input if player wins.
     *
     * @param score takes a score derived from the time elapsed.
     */
    public Highscore(int score) {
        super(GameEngine.getInstance().getGui());
        setModal(true);
        // set this window's position to the same position as the main window
        setLocation(GameEngine.getInstance().getGui().getLocation());

        setTitle("Highscore");
        setAlwaysOnTop(true);
        this.score = score;
        panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(30, 30, 0, 30));

        panel2 = new JPanel(new GridBagLayout());
        panel2.setBorder(new EmptyBorder(10, 0, 10, 0));

        setResizable(false);

        tableManager = new Table();
        tableManager.updateTable();

        table = new JTable(tableManager);
        table.setEnabled(false);
        table.setFont(new Font("Dialog", Font.PLAIN, 20));
        table.setRowHeight(25);
        table.setBorder(new LineBorder(Color.black, 1));
        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        panel.add(table, BorderLayout.NORTH);

        ///if not dead register name and score
        if (score > 0) {
            southPanelWinning(score, panel2);
        }

        displayHighscore();
    }

    /**
     * Creates the highscore list panel without the submit button.
     */
    public void displayHighscore() {
        add(panel);
        add(panel2, BorderLayout.SOUTH);
        pack();
        setVisible(true);

    }

    private void southPanelWinning(int score1, JPanel panel2) {
        GridBagConstraints c = new GridBagConstraints();
        nameText = new JLabel("Enter your name:");
        nameText.setFont(new Font("Dialog", Font.PLAIN, 17));
        scoreText = new JLabel("Your score is:");
        scoreText.setFont(new Font("Dialog", Font.PLAIN, 17));
        nameInput = new JTextField(10);

        scoreInput = new JLabel(String.format("%02d:%02d", score1 / 60, score1 % 60));
        submit = new JButton("Submit score");
        submit.setFont(new Font("Dialog", Font.PLAIN, 15));
        submit.setBounds(30, 200, 300, 30);
        submit.addActionListener(this);

        //panel.add(submit, BorderLayout.SOUTH);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.ipadx = 20;
        c.ipady = 10;
        c.insets = new Insets(2, 8, 2, 8);
        c.anchor = GridBagConstraints.EAST;
        panel2.add(scoreText, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.WEST;
        panel2.add(scoreInput, c);
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.EAST;
        panel2.add(nameText, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.WEST;
        panel2.add(nameInput, c);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        panel2.add(submit, c);
    }

    /**
     * When submit button is pressed and name fullfills all the critera it will
     * be inserted in to the highscore database.
     *
     * @param listens for click on submit button
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            String name = nameInput.getText();

            if (name.length() > 0) {
                try {
                    Connection conn;
                    conn = DriverManager.getConnection("jdbc:postgresql://176.126.70.189:22224/", "postgres", "<Hn$dY3._BG2M7#N");
                    String queryRegister = "INSERT INTO highscores VALUES (?,?)";
                    PreparedStatement st = conn.prepareStatement(queryRegister);
                    st.setString(1, name);
                    st.setInt(2, score);
                    st.executeUpdate();

                    conn.close();
                } catch (SQLException exception) {
                    System.out.println(exception.getMessage());
                }

                tableManager.updateTable();
                dispose();
            }
        }
    }

}

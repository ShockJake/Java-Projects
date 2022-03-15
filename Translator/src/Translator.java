import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;

public class Translator {
    private static JFrame window;
    private static JPanel mainPanel;

    static Toolkit toolkit = Toolkit.getDefaultToolkit();
    static Dimension dimension = toolkit.getScreenSize();

    private static JTextArea tArea1 = new JTextArea("", 2, 15);
    private static JTextArea tArea2 = new JTextArea("", 2, 15);

    private static JLabel label1;
    private static JLabel label2;

    private static SpringLayout layout;

    private static JButton translateButton = new JButton("Translate");
    private static JButton addButton = new JButton("Add");
    private static JButton editButton = new JButton("Edit");

    private static Connection c;

    private static ActionListener translate = new ActionListener() { // Action Listener for "Translate" button.
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("select * from translation");
                while (rs.next()) {
                    if (tArea1.getText().equalsIgnoreCase(rs.getString("polish"))) { // Finds given word.
                        tArea2.setText(rs.getString("english")); // Shows related translation.
                        rs.close();
                        stmt.close();
                        return;
                    }
                }
                tArea2.setText("-not found-"); // If word not found shows this message.
                rs.close();
                stmt.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    };

    private static boolean checkWord(String word) throws SQLException { // Function to check if given word exists in db.
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("select * from translation");
        while (rs.next()) {
            if (word.equalsIgnoreCase(rs.getString("polish"))) { // Finds given word.
                rs.close();
                stmt.close();
                return true;
            }
        }
        return false;
    }

    private static ActionListener add = new ActionListener() { // Action listener for "add" button in main menu.
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame addWindow = new JFrame("Add"); // Creating new window.
            addWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            addWindow.setBounds(dimension.width / 2, dimension.height / 2, 300, 150);
            addWindow.setVisible(true);

            JLabel pol_word = new JLabel("Polish: ");
            JLabel eng_word = new JLabel("English: ");

            JTextField pol_field = new JTextField(15);
            JTextField eng_field = new JTextField(15);

            JButton insertButton = new JButton("Insert");

            ActionListener insertNewWord = new ActionListener() { // Action listener for insertion button.
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (pol_field.getText().equals("") && eng_field.getText().equals("")) {
                        pol_field.setText("Please insert word");
                    } else {
                        try {
                            boolean isFound = checkWord(pol_field.getText());
                            if (isFound) {
                                pol_field.setText("Word is already exists...");
                                eng_field.setText("Word is already exists...");
                            } else {
                                Statement stmt = c.createStatement();
                                stmt.executeUpdate("insert into translation values (\'" + pol_field.getText() + "\', \'"
                                        + eng_field.getText() + "\')");
                                pol_field.setText("Updated...");
                                eng_field.setText("Updated...");
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            };

            SpringLayout addLayout = new SpringLayout();
            JPanel addPanel = new JPanel();
            addPanel.setLayout(addLayout);

            insertButton.addActionListener(insertNewWord);

            addPanel.add(pol_word);
            addPanel.add(pol_field);
            addPanel.add(eng_word);
            addPanel.add(eng_field);
            addPanel.add(insertButton);

            addLayout.putConstraint(SpringLayout.WEST, pol_word, 5, SpringLayout.WEST, addPanel);
            addLayout.putConstraint(SpringLayout.NORTH, pol_word, 8, SpringLayout.NORTH, addPanel);

            addLayout.putConstraint(SpringLayout.WEST, pol_field, 5, SpringLayout.EAST, pol_word);
            addLayout.putConstraint(SpringLayout.NORTH, pol_field, 5, SpringLayout.NORTH, addPanel);

            addLayout.putConstraint(SpringLayout.WEST, eng_word, 5, SpringLayout.WEST, addPanel);
            addLayout.putConstraint(SpringLayout.NORTH, eng_word, 10, SpringLayout.SOUTH, pol_word);

            addLayout.putConstraint(SpringLayout.WEST, eng_field, 5, SpringLayout.EAST, eng_word);
            addLayout.putConstraint(SpringLayout.NORTH, eng_field, 5, SpringLayout.SOUTH, pol_field);

            addLayout.putConstraint(SpringLayout.WEST, insertButton, 5, SpringLayout.WEST, addPanel);
            addLayout.putConstraint(SpringLayout.NORTH, insertButton, 25, SpringLayout.SOUTH, eng_word);

            addWindow.add(addPanel);
        }
    };

    private static ActionListener edit = new ActionListener() { // Action listener for edit button im main menu.

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame editFrame = new JFrame("Edit");

            editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            editFrame.setBounds(dimension.width / 2, dimension.height / 2, 300, 200);
            editFrame.setVisible(true);

            JLabel pol_word = new JLabel("Polish: " + tArea1.getText());
            JLabel eng_word = new JLabel("English: ");

            JTextField eng_field = new JTextField(15);

            JButton edit = new JButton("Edit");

            ActionListener editWord = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (eng_field.getText().equals("")) { // Checking if there is a word in field of translation.
                        eng_field.setText("Please insert word..."); // If there is no word, shows a message.
                    } else {
                        try {
                            Statement stmt = c.createStatement();
                            stmt.executeUpdate("update translation set english=\'" + eng_field.getText() +
                                    "\' where polish=\'" + tArea1.getText() + "\'"); // Updating translation.
                            eng_field.setText("Updated"); // Shows message that translation is updated.
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            };

            SpringLayout editLayout = new SpringLayout();
            JPanel editPanel = new JPanel();
            editPanel.setLayout(editLayout);

            edit.addActionListener(editWord);

            editPanel.add(pol_word);
            editPanel.add(eng_word);
            editPanel.add(eng_field);
            editPanel.add(edit);

            editLayout.putConstraint(SpringLayout.WEST, pol_word, 5, SpringLayout.WEST, editPanel);
            editLayout.putConstraint(SpringLayout.NORTH, pol_word, 8, SpringLayout.NORTH, editPanel);

            editLayout.putConstraint(SpringLayout.WEST, eng_word, 5, SpringLayout.WEST, editPanel);
            editLayout.putConstraint(SpringLayout.NORTH, eng_word, 28, SpringLayout.NORTH, pol_word);

            editLayout.putConstraint(SpringLayout.WEST, eng_field, 5, SpringLayout.EAST, eng_word);
            editLayout.putConstraint(SpringLayout.NORTH, eng_field, 0, SpringLayout.NORTH, eng_word);

            editLayout.putConstraint(SpringLayout.WEST, edit, 5, SpringLayout.WEST, editPanel);
            editLayout.putConstraint(SpringLayout.NORTH, edit, 25, SpringLayout.NORTH, eng_field);

            editFrame.add(editPanel);
        }
    };

    static void initDataBase(Connection c) throws SQLException, IOException {
        Statement stmt = c.createStatement();
        stmt.executeUpdate("create table translation(polish varchar(32), english varchar(32))"); // Creating db.
        BufferedReader br = new BufferedReader(new FileReader("translation.txt")); // Creating reader for initialyzing
                                                                                   // db.
        String str, firstWord, secondWord;
        while ((str = br.readLine()) != null) { // Reading the words.
            firstWord = "";
            secondWord = "";
            int second_wordPos = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == ' ') {
                    second_wordPos = i; // Finding spase between two words.
                    break;
                }
                firstWord = firstWord + str.charAt(i); // Reading first word.
            }
            for (int i = second_wordPos + 1; i < str.length(); i++) {
                if (str.charAt(i) == '\n') { // Finding the next line.
                    break;
                }
                secondWord = secondWord + str.charAt(i); // Reading second word.
            }
            stmt.executeUpdate("insert into translation values (\'" + firstWord + "\', \'" + secondWord + "\')"); // Inserting
                                                                                                                  // new
                                                                                                                  // element.
        }
        stmt.close();
        br.close();
    }

    static void createFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        window = new JFrame("Translator");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setResizable(false);

        mainPanel = new JPanel();
        layout = new SpringLayout();

        mainPanel.setLayout(layout);
        window.setBounds(dimension.width / 2 - 300, dimension.height / 2 - 125, 600, 250);

        label1 = new JLabel("Polish");
        label2 = new JLabel("English");
        label1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 45));
        label2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 45));

        tArea1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        tArea2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        tArea2.setEditable(false);

        translateButton.setPreferredSize(new Dimension(170, 55));
        translateButton.addActionListener(translate);

        addButton.setPreferredSize(new Dimension(170, 55));
        addButton.addActionListener(add);

        editButton.setPreferredSize(new Dimension(170, 55));
        editButton.addActionListener(edit);

        translateButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        addButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        editButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        mainPanel.add(label1);
        mainPanel.add(label2);
        mainPanel.add(tArea1);
        mainPanel.add(tArea2);
        mainPanel.add(translateButton);
        mainPanel.add(addButton);
        mainPanel.add(editButton);

        layout.putConstraint(SpringLayout.WEST, label1, 65, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, label1, 8, SpringLayout.NORTH, mainPanel);

        layout.putConstraint(SpringLayout.WEST, label2, 150, SpringLayout.EAST, label1);
        layout.putConstraint(SpringLayout.NORTH, label2, 8, SpringLayout.NORTH, mainPanel);

        layout.putConstraint(SpringLayout.WEST, tArea1, 20, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, tArea1, 55, SpringLayout.NORTH, label1);

        layout.putConstraint(SpringLayout.WEST, tArea2, 40, SpringLayout.EAST, tArea1);
        layout.putConstraint(SpringLayout.NORTH, tArea2, 55, SpringLayout.NORTH, label2);

        layout.putConstraint(SpringLayout.WEST, translateButton, 20, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, translateButton, 60, SpringLayout.NORTH, tArea1);

        layout.putConstraint(SpringLayout.WEST, addButton, 20, SpringLayout.EAST, translateButton);
        layout.putConstraint(SpringLayout.NORTH, addButton, 60, SpringLayout.NORTH, tArea1);

        layout.putConstraint(SpringLayout.WEST, editButton, 20, SpringLayout.EAST, addButton);
        layout.putConstraint(SpringLayout.NORTH, editButton, 60, SpringLayout.NORTH, tArea2);

        window.add(mainPanel);
    }

    public static void main(String[] args) throws Exception {
        try {
            // Trying to connect to existing data base.
            c = DriverManager.getConnection("jdbc:hsqldb:file:translationBase;shutdown=true;ifexists=true", "SA", "");
        } catch (SQLException e) {
            // Creating data base if not connected to existing one.
            c = DriverManager.getConnection("jdbc:hsqldb:file:translationBase;shutdown=true;", "SA", "");
            initDataBase(c);
        }
        createFrame();
    }
}
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Book {
    String title;
    boolean isIssued;

    Book(String title) {
        this.title = title;
        this.isIssued = false;
    }
}

public class LibraryGUI {

    static ArrayList<Book> books = new ArrayList<>();

    public static void main(String[] args) {

        JFrame frame = new JFrame("📚 Library Management System");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(30, 30, 47));

        // ===== TITLE =====
        JLabel title = new JLabel("Library Management System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        frame.add(title, BorderLayout.NORTH);

        // ===== INPUT PANEL =====
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(44, 44, 60));

        JTextField bookField = new JTextField(15);
        JLabel label = new JLabel("Book / Index:");
        label.setForeground(Color.WHITE);
        inputPanel.add(label);
        inputPanel.add(bookField);

        frame.add(inputPanel, BorderLayout.WEST);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 30, 47));

        JButton addBtn = new JButton("Add Book");
        JButton viewBtn = new JButton("View Books");
        JButton issueBtn = new JButton("Issue Book");
        JButton returnBtn = new JButton("Return Book");

        JButton searchBtn = new JButton("Search Book");

        JButton[] buttons = {addBtn, viewBtn, issueBtn, returnBtn, searchBtn};

        for (JButton btn : buttons) {
            btn.setBackground(new Color(76, 175, 80));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);

            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(56, 142, 60));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(76, 175, 80));
                }
            });

            buttonPanel.add(btn);
}

        frame.add(buttonPanel, BorderLayout.SOUTH);

        // ===== OUTPUT =====
        JTextArea output = new JTextArea();
        output.setEditable(false);
        output.setBackground(Color.BLACK);
        output.setForeground(Color.GREEN);

        JScrollPane scroll = new JScrollPane(output);
        scroll.setPreferredSize(new Dimension(550, 200));

        frame.add(scroll, BorderLayout.CENTER);

        // ===== FUNCTIONS =====

        // ADD BOOK
        addBtn.addActionListener(e -> {
            String titleText = bookField.getText();
            if (titleText.isEmpty()) {
                output.setText("Enter book name!");
                return;
            }
            books.add(new Book(titleText));
            output.setText("Book Added!");
            bookField.setText("");
        });

        // VIEW BOOKS
        viewBtn.addActionListener(e -> {
            String result = "";
            for (int i = 0; i < books.size(); i++) {
                Book b = books.get(i);
                result += (i + 1) + ". " + b.title +
                        (b.isIssued ? " (Issued)" : " (Available)") + "\n";
            }
            output.setText(result);
        });

        // ISSUE BOOK
        issueBtn.addActionListener(e -> {
            try {
                int index = Integer.parseInt(bookField.getText()) - 1;
                if (index >= 0 && index < books.size()) {
                    books.get(index).isIssued = true;
                    output.setText("Book Issued!");
                }
            } catch (Exception ex) {
                output.setText("Enter valid index!");
            }
        });

        // RETURN BOOK
        returnBtn.addActionListener(e -> {
            try {
                int index = Integer.parseInt(bookField.getText()) - 1;
                if (index >= 0 && index < books.size()) {
                    books.get(index).isIssued = false;

                    int fine = (int)(Math.random() * 50); // random fine
                    output.setText("Book Returned!\nFine: ₹" + fine);
                }
            } catch (Exception ex) {
                output.setText("Enter valid index!");
            }
        });
        // SEARCH BOOK
        searchBtn.addActionListener(e -> {
            String keyword = bookField.getText().toLowerCase();
            String result = "";

            for (Book b : books) {
                if (b.title.toLowerCase().contains(keyword)) {
                    result += b.title + "\n";
                }
            }

            if (result.isEmpty()) {
                output.setText("Book not found");
            } else {
                output.setText("Found:\n" + result);
            }
        });

        frame.setVisible(true);
    }
}

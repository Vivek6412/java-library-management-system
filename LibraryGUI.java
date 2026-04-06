import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
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
        JButton removeBtn = new JButton("Remove Book");
        JButton viewBtn = new JButton("View Books");
        JButton issueBtn = new JButton("Issue Book");
        JButton returnBtn = new JButton("Return Book");

        JButton searchBtn = new JButton("Search Book");

        JButton[] buttons = {addBtn, viewBtn, issueBtn, returnBtn, searchBtn, removeBtn};

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
            removeBtn.addActionListener(e -> {
                try {
                    int index = Integer.parseInt(bookField.getText()) - 1;

                    if (index >= 0 && index < books.size()) {
                        books.remove(index);
                        output.setText("Book removed!");
                    } else {
                        output.setText("Invalid index!");
                    }
                } catch (Exception ex) {
                    output.setText("Enter valid index!");
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

            String titleText = JOptionPane.showInputDialog("Enter book title:");
            String authorText = JOptionPane.showInputDialog("Enter author name:");

            if (titleText == null || authorText == null ||
                titleText.isEmpty() || authorText.isEmpty()) {

                output.setText("Enter valid details!");
                return;
            }

            books.add(new Book(titleText, authorText));
            output.setText("Book Added!");

        });

        // VIEW BOOKS
        viewBtn.addActionListener(e -> {
            String result = "";
            for (int i = 0; i < books.size(); i++) {
                Book b = books.get(i);

                result += (i + 1) + ". " + b.title + " by " + b.author;

                if (b.isIssued) {
                    result += " (Issued to " + b.issuedTo + ", Due: " + b.dueDate + ")";
                } else {
                    result += " (Available)";
                }

                result += "\n";
            }
            output.setText(result);
        });

        // ISSUE BOOK
        issueBtn.addActionListener(e -> {
            try {
                int index = Integer.parseInt(bookField.getText()) - 1;

                String user = JOptionPane.showInputDialog("Enter user name:");

                if (index >= 0 && index < books.size()) {
                    Book b = books.get(index);

                    b.isIssued = true;
                    b.issuedTo = user;
                    b.dueDate = LocalDate.now().plusDays(7);  // 7 days limit

                    output.setText("Book issued to " + user + "\nDue Date: " + b.dueDate);
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
                    Book b = books.get(index);

                    if (b.isIssued) {
                        LocalDate today = LocalDate.now();

                        int fine = 0;

                        if (today.isAfter(b.dueDate)) {
                            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(b.dueDate, today);
                            fine = (int) daysLate * 10;  // ₹10 per day
                        }

                        b.isIssued = false;
                        b.issuedTo = "";
                        b.dueDate = null;

                        output.setText("Book Returned!\nFine: ₹" + fine);
                    }
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
            if (b.title.toLowerCase().contains(keyword) ||
                b.author.toLowerCase().contains(keyword)) {

                result += b.title + " by " + b.author + "\n";
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

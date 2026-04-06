import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

class Book {
String title;
String author;
boolean isIssued;
String issuedTo;
LocalDate dueDate;


Book(String title, String author) {
    this.title = title;
    this.author = author;
    this.isIssued = false;
    this.issuedTo = "";
    this.dueDate = null;
}


}

public class LibraryGUI {


static ArrayList<Book> books = new ArrayList<>();

public static void main(String[] args) {

    JFrame frame = new JFrame("📚 Library Management System");
    frame.setSize(800, 550);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());

    // ===== TITLE =====
    JLabel title = new JLabel("Library Management System", JLabel.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 22));
    title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    frame.add(title, BorderLayout.NORTH);

    // ===== OUTPUT =====
    JTextArea output = new JTextArea();
    output.setEditable(false);
    output.setFont(new Font("Monospaced", Font.PLAIN, 14));
    JScrollPane scroll = new JScrollPane(output);
    frame.add(scroll, BorderLayout.CENTER);

    // ===== INPUT PANEL =====
    JPanel inputPanel = new JPanel(new FlowLayout());

    JTextField inputField = new JTextField(15);
    inputPanel.add(new JLabel("Book Index / Search:"));
    inputPanel.add(inputField);

    frame.add(inputPanel, BorderLayout.NORTH);

    // ===== BUTTON PANEL =====
    JPanel panel = new JPanel(new GridLayout(2, 4, 10, 10));

    JButton addBtn = new JButton("Add Book");
    JButton viewBtn = new JButton("View Books");
    JButton issueBtn = new JButton("Issue Book");
    JButton returnBtn = new JButton("Return Book");
    JButton searchBtn = new JButton("Search");
    JButton removeBtn = new JButton("Remove");
    JButton updateBtn = new JButton("Update");

    panel.add(addBtn);
    panel.add(viewBtn);
    panel.add(issueBtn);
    panel.add(returnBtn);
    panel.add(searchBtn);
    panel.add(removeBtn);
    panel.add(updateBtn);

    frame.add(panel, BorderLayout.SOUTH);

    // ===== FUNCTIONS =====

    // ADD BOOK
    addBtn.addActionListener(e -> {
        String titleText = JOptionPane.showInputDialog("Enter title:");
        String authorText = JOptionPane.showInputDialog("Enter author:");

        if (titleText != null && authorText != null) {
            books.add(new Book(titleText, authorText));
            output.setText("Book added!");
        }
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
            int index = Integer.parseInt(inputField.getText()) - 1;
            String user = JOptionPane.showInputDialog("Enter user name:");

            Book b = books.get(index);
            b.isIssued = true;
            b.issuedTo = user;
            b.dueDate = LocalDate.now().plusDays(7);

            output.setText("Issued to " + user + "\nDue: " + b.dueDate);
        } catch (Exception ex) {
            output.setText("Invalid input!");
        }
    });

    // RETURN BOOK
    returnBtn.addActionListener(e -> {
        try {
            int index = Integer.parseInt(inputField.getText()) - 1;
            Book b = books.get(index);

            int fine = 0;
            if (LocalDate.now().isAfter(b.dueDate)) {
                long days = java.time.temporal.ChronoUnit.DAYS.between(b.dueDate, LocalDate.now());
                fine = (int) days * 10;
            }

            b.isIssued = false;
            b.issuedTo = "";
            b.dueDate = null;

            output.setText("Returned! Fine: ₹" + fine);
        } catch (Exception ex) {
            output.setText("Invalid input!");
        }
    });

    // SEARCH
    searchBtn.addActionListener(e -> {
        String key = inputField.getText().toLowerCase();
        String result = "";

        for (Book b : books) {
            if (b.title.toLowerCase().contains(key) ||
                b.author.toLowerCase().contains(key) ||
                b.issuedTo.toLowerCase().contains(key)) {

                result += b.title + " by " + b.author + "\n";
            }
        }

        output.setText(result.isEmpty() ? "Not found" : result);
    });

    // REMOVE
    removeBtn.addActionListener(e -> {
        try {
            int index = Integer.parseInt(inputField.getText()) - 1;
            books.remove(index);
            output.setText("Book removed!");
        } catch (Exception ex) {
            output.setText("Invalid index!");
        }
    });

    // UPDATE
    updateBtn.addActionListener(e -> {
        try {
            int index = Integer.parseInt(inputField.getText()) - 1;
            Book b = books.get(index);

            String newTitle = JOptionPane.showInputDialog("New title:");
            String newAuthor = JOptionPane.showInputDialog("New author:");

            b.title = newTitle;
            b.author = newAuthor;

            output.setText("Book updated!");
        } catch (Exception ex) {
            output.setText("Invalid index!");
        }
    });

    frame.setVisible(true);
}


}

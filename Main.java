import java.util.*;
import java.time.LocalDate;

class Book {
    String title;
    String author;   // NEW
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
class User {
    String name;

    User(String name) {
        this.name = name;
    }
}

public class Main {

    static ArrayList<Book> books = new ArrayList<>();
    static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== LIBRARY SYSTEM =====");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Register User");
            System.out.println("4. Issue Book");
            System.out.println("5. Return Book");
            System.out.println("6. Search Book");
            System.out.println("7. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: addBook(sc); break;
                case 2: viewBooks(); break;
                case 3: registerUser(sc); break;
                case 4: issueBook(sc); break;
                case 5: returnBook(sc); break;
                case 6: searchBook(sc); break;
                case 7: return;
                default: System.out.println("Wrong choice");
            }
        }
    }
    static void addBook(Scanner sc) {
        System.out.print("Enter book title: ");
        String title = sc.nextLine();

        books.add(new Book(title));
        System.out.println("Book added!");
    }
    static void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            System.out.println((i+1) + ". " + b.title +
                (b.isIssued ? " (Issued)" : " (Available)"));
        }
    }
    static void registerUser(Scanner sc) {
        System.out.print("Enter user name: ");
        String name = sc.nextLine();

        users.add(new User(name));
        System.out.println("User registered!");
    }
    static void issueBook(Scanner sc) {
        viewBooks();
        System.out.print("Enter book number: ");
        int index = sc.nextInt() - 1;

        if (index >= 0 && index < books.size()) {
            Book b = books.get(index);

            if (!b.isIssued) {
                b.isIssued = true;
                System.out.println("Book issued!");
            } else {
                System.out.println("Already issued.");
            }
        } else {
            System.out.println("Invalid number.");
        }
    }
    static void returnBook(Scanner sc) {
        viewBooks();
        System.out.print("Enter book number: ");
        int index = sc.nextInt() - 1;

        if (index >= 0 && index < books.size()) {
            Book b = books.get(index);

            if (b.isIssued) {
                b.isIssued = false;

                // simple fine logic
                int fine = new Random().nextInt(50);
                System.out.println("Book returned!");
                System.out.println("Fine: ₹" + fine);

            } else {
                System.out.println("Book was not issued.");
            }
        } else {
            System.out.println("Invalid number.");
        }
    }
    static void searchBook(Scanner sc) {
        System.out.print("Enter book name: ");
        String name = sc.nextLine();

        for (Book b : books) {
            if (b.title.toLowerCase().contains(name.toLowerCase())) {
                System.out.println("Found: " + b.title);
                return;
            }
        }

        System.out.println("Book not found.");
    }
}
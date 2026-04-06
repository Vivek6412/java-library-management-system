import java.util.*;
import java.time.LocalDate;

class Book {
    String title;
    String author;
    boolean isIssued;
    String issuedTo;
    java.time.LocalDate dueDate;

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

        System.out.print("Enter author name: ");
        String author = sc.nextLine();

        books.add(new Book(title, author));
        System.out.println("Book added!");
    }
    static void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            System.out.println((i+1) + ". " + b.title + " by " + b.author +
                (b.isIssued ? " (Issued to " + b.issuedTo + ", Due: " + b.dueDate + ")" 
                            : " (Available)"));
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
        sc.nextLine();

        if (index >= 0 && index < books.size()) {
            Book b = books.get(index);

            if (!b.isIssued) {
                System.out.print("Enter user name: ");
                String user = sc.nextLine();

                b.isIssued = true;
                b.issuedTo = user;
                b.dueDate = LocalDate.now().plusDays(7);

                System.out.println("Book issued to " + user);
                System.out.println("Due date: " + b.dueDate);
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
                LocalDate today = LocalDate.now();
                int fine = 0;

                if (today.isAfter(b.dueDate)) {
                    long daysLate = java.time.temporal.ChronoUnit.DAYS
                            .between(b.dueDate, today);
                    fine = (int) daysLate * 10;
                }

                b.isIssued = false;
                b.issuedTo = "";
                b.dueDate = null;

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
        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine().toLowerCase();

        boolean found = false;

        for (Book b : books) {
            if (b.title.toLowerCase().contains(keyword) ||
                b.author.toLowerCase().contains(keyword)) {

                System.out.println("Found: " + b.title + " by " + b.author);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Book not found.");
        }
    }
}
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryGUI {

    private List<Book> books = new ArrayList<>();
    private List<Member> members = new ArrayList<>();

    public LibraryGUI() {
        JFrame frame = new JFrame("Library Management System");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 1, 10, 10));

        JButton addBookBtn = new JButton("Add Book");
        JButton viewBookBtn = new JButton("View Books");
        JButton addMemberBtn = new JButton("Add Member");
        JButton viewMemberBtn = new JButton("View Members");
        JButton issueBtn = new JButton("Issue Book");
        JButton returnBtn = new JButton("Return Book");

        frame.add(addBookBtn);
        frame.add(viewBookBtn);
        frame.add(addMemberBtn);
        frame.add(viewMemberBtn);
        frame.add(issueBtn);
        frame.add(returnBtn);

        // BUTTON ACTIONS
        addBookBtn.addActionListener(e -> addBook());
        viewBookBtn.addActionListener(e -> viewBooks());
        addMemberBtn.addActionListener(e -> addMember());
        viewMemberBtn.addActionListener(e -> viewMembers());
        issueBtn.addActionListener(e -> issueBook());
        returnBtn.addActionListener(e -> returnBook());

        frame.setVisible(true);
    }

    private void addBook() {
        int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Book ID:"));
        String title = JOptionPane.showInputDialog("Enter Title:");
        String author = JOptionPane.showInputDialog("Enter Author:");

        books.add(new Book(id, title, author));
        JOptionPane.showMessageDialog(null, "Book Added Successfully!");
    }

    private void viewBooks() {
        if (books.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Books Available!");
            return;
        }

        StringBuilder sb = new StringBuilder("Books:\n\n");
        for (Book b : books) sb.append(b).append("\n");

        JTextArea text = new JTextArea(sb.toString());
        text.setEditable(false);
        JOptionPane.showMessageDialog(null, new JScrollPane(text));
    }

    private void addMember() {
        int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Member ID:"));
        String name = JOptionPane.showInputDialog("Enter Member Name:");

        members.add(new Member(id, name));
        JOptionPane.showMessageDialog(null, "Member Added Successfully!");
    }

    private void viewMembers() {
        if (members.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Members Available!");
            return;
        }

        StringBuilder sb = new StringBuilder("Members:\n\n");
        for (Member m : members) sb.append(m).append("\n");

        JTextArea text = new JTextArea(sb.toString());
        text.setEditable(false);
        JOptionPane.showMessageDialog(null, new JScrollPane(text));
    }

    private void issueBook() {
        int bookId = Integer.parseInt(JOptionPane.showInputDialog("Enter Book ID:"));
        int memberId = Integer.parseInt(JOptionPane.showInputDialog("Enter Member ID:"));

        Book book = books.stream().filter(b -> b.getId() == bookId).findFirst().orElse(null);
        Member member = members.stream().filter(m -> m.getMemberId() == memberId).findFirst().orElse(null);

        if (book == null) { JOptionPane.showMessageDialog(null, "Book Not Found!"); return; }
        if (member == null) { JOptionPane.showMessageDialog(null, "Member Not Found!"); return; }
        if (book.isIssued()) { JOptionPane.showMessageDialog(null, "Book Already Issued!"); return; }

        book.issue();
        JOptionPane.showMessageDialog(null, "Book Issued to " + member.getName());
    }

    private void returnBook() {
        int bookId = Integer.parseInt(JOptionPane.showInputDialog("Enter Book ID:"));

        Book book = books.stream().filter(b -> b.getId() == bookId).findFirst().orElse(null);

        if (book == null) { JOptionPane.showMessageDialog(null, "Book Not Found!"); return; }
        if (!book.isIssued()) { JOptionPane.showMessageDialog(null, "Book is NOT Issued!"); return; }

        book.returnBook();
        JOptionPane.showMessageDialog(null, "Book Returned Successfully!");
    }
}

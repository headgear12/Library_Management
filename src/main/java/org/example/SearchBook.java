package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SearchBook extends JFrame implements ActionListener {

    JLabel name, author, language, genre;
    JTextField ename, eauthor, elanguage, egenre;
    String prn;
    JButton search, back;

    public SearchBook(String prn) {
        this.prn = prn;

        JLabel title = new JLabel("PRN: " + prn);
        title.setFont(new Font("Arial", Font.BOLD, 38));
        title.setBounds(120, 20, 600, 40);
        add(title);

        JLabel filter = new JLabel("Filter");
        filter.setFont(new Font("Arial", Font.BOLD, 38));
        filter.setBounds(270, 100, 600, 40);
        add(filter);

        name = new JLabel("Name:");
        name.setFont(new Font("Arial", Font.BOLD, 28));
        name.setBounds(150, 170, 200, 50);
        add(name);

        ename = new JTextField();
        ename.setFont(new Font("Arial", Font.PLAIN, 28));
        ename.setBounds(350, 170, 250, 50);
        add(ename);

        author = new JLabel("Author:");
        author.setFont(new Font("Arial", Font.BOLD, 28));
        author.setBounds(150, 240, 200, 50);
        add(author);

        eauthor = new JTextField();
        eauthor.setFont(new Font("Arial", Font.PLAIN, 28));
        eauthor.setBounds(350, 240, 250, 50);
        add(eauthor);

        language = new JLabel("Language:");
        language.setFont(new Font("Arial", Font.BOLD, 28));
        language.setBounds(150, 310, 200, 50);
        add(language);

        elanguage = new JTextField();
        elanguage.setFont(new Font("Arial", Font.PLAIN, 28));
        elanguage.setBounds(350, 310, 250, 50);
        add(elanguage);

        genre = new JLabel("Genre:");
        genre.setFont(new Font("Arial", Font.BOLD, 28));
        genre.setBounds(150, 380, 200, 50);
        add(genre);

        egenre = new JTextField();
        egenre.setFont(new Font("Arial", Font.PLAIN, 28));
        egenre.setBounds(350, 380, 250, 50);
        add(egenre);

        search = new JButton("Search");
        search.setFont(new Font("Arial", Font.BOLD, 34));
        search.setBounds(150, 450, 180, 50);
        add(search);

        back = new JButton("Back");
        back.setFont(new Font("Arial", Font.BOLD, 34));
        back.setBounds(350, 450, 200, 50);
        add(back);

        search.addActionListener(this);
        back.addActionListener(this);

        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocation(350, 50);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            setVisible(false);
            new Library(prn).setVisible(true);
        } else if (e.getSource() == search) {
            String bookName = ename.getText();
            String authorName = eauthor.getText();
            String bookLanguage = elanguage.getText();
            String bookGenre = egenre.getText();

            if (bookName.isEmpty() && authorName.isEmpty() && bookLanguage.isEmpty() && bookGenre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill at least one field to search.");
                return;
            }

            try {
                Conn c = new Conn();
                String query = "SELECT * FROM books WHERE Book LIKE ? AND Author LIKE ? AND language LIKE ? AND Genre LIKE ?";
                PreparedStatement pst = c.c.prepareStatement(query);
                pst.setString(1, "%" + bookName + "%");
                pst.setString(2, "%" + authorName + "%");
                pst.setString(3, "%" + bookLanguage + "%");
                pst.setString(4, "%" + bookGenre + "%");

                ResultSet rs = pst.executeQuery();
                StringBuilder result = new StringBuilder();
                result.append("Remember the book id to Issue book").append("\n");

                while (rs.next()) {
                    result.append("Book ID: ").append(rs.getString("Book_ID")).append("\n");
                    result.append("Book: ").append(rs.getString("Book")).append("\n");
                    result.append("Author: ").append(rs.getString("Author")).append("\n");
                    result.append("Language: ").append(rs.getString("language")).append("\n");
                    result.append("Genre: ").append(rs.getString("Genre")).append("\n\n");
                }

                if (result.length() == 35) {
                    JOptionPane.showMessageDialog(this, "No books found matching the criteria.");
                } else {
                    JOptionPane.showMessageDialog(this, result.toString());
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new SearchBook("").setVisible(true);
    }
}

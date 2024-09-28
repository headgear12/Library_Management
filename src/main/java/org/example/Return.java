package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Return extends JFrame implements ActionListener {
    String prn;
    JTextField ebookid;
    JButton return_book, back;

    public Return(String prn) {
        this.prn = prn;

        JLabel title = new JLabel("PRN: " + prn);
        title.setFont(new Font("Arial", Font.BOLD, 38));
        title.setBounds(120, 20, 600, 40);
        add(title);

        JLabel bookid = new JLabel("Book ID:");
        bookid.setFont(new Font("Arial", Font.BOLD, 28));
        bookid.setBounds(150, 90, 200, 50);
        add(bookid);

        ebookid = new JTextField();
        ebookid.setFont(new Font("Arial", Font.PLAIN, 28));
        ebookid.setBounds(300, 90, 250, 50);
        add(ebookid);

        return_book = new JButton("Return");
        return_book.setFont(new Font("Arial", Font.BOLD, 34));
        return_book.setBounds(150, 160, 180, 50);
        add(return_book);

        back = new JButton("Back");
        back.setFont(new Font("Arial", Font.BOLD, 34));
        back.setBounds(350, 160, 200, 50);
        add(back);

        return_book.addActionListener(this);
        back.addActionListener(this);

        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocation(350, 50);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            setVisible(false);
            new Library(prn).setVisible(true);
        } else if (e.getSource() == return_book) {
            try {
                Conn c = new Conn();
                String id = ebookid.getText();

                PreparedStatement pst = c.c.prepareStatement("SELECT * FROM issue WHERE Book_ID = ? and prn = ?");
                pst.setString(1, id);
                pst.setString(2, prn);

                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    PreparedStatement p = c.c.prepareStatement("Delete from issue where Book_ID = ? and prn = ?");
                    p.setString(1, id);
                    p.setString(2, prn);
                    int rowsAffected = p.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Book Returned Successfully");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error returning the book. Please try again.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Book Not Issued by You");
                }
                setVisible(false);
                new Library(prn).setVisible(true);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Return("").setVisible(true);
    }
}

package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class  Issue extends JFrame implements ActionListener {
    String prn;
    JTextField ebookid;
    JButton issue, back;

    public Issue(String prn) {
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

        issue = new JButton("Issue");
        issue.setFont(new Font("Arial", Font.BOLD, 34));
        issue.setBounds(150, 160, 180, 50);
        add(issue);

        back = new JButton("Back");
        back.setFont(new Font("Arial", Font.BOLD, 34));
        back.setBounds(350, 160, 200, 50);
        add(back);

        issue.addActionListener(this);
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
        } else if (e.getSource() == issue) {
            try {
                Conn c = new Conn();
                String id = ebookid.getText();

                PreparedStatement pst = c.c.prepareStatement("SELECT * FROM issue WHERE Book_ID = ?");
                pst.setString(1, id);

                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Book Not Available");
                } else {
                    PreparedStatement p = c.c.prepareStatement("INSERT INTO issue (Book_ID, prn) VALUES (?, ?)");
                    p.setString(1, id);
                    p.setString(2, prn);
                    int rowsAffected = p.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Book Issued Successfully");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error issuing the book. Please try again.");
                    }
                    setVisible(false);
                    new Library(prn).setVisible(true);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Issue("").setVisible(true);
    }
}

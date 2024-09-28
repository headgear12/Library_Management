package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Library extends JFrame implements ActionListener {
    String prn;
    JButton issueBookButton, returnBookButton, searchBookButton, viewIssuedBooksButton, exitButton;

    Library(String prn) {

        this.prn=prn;

        JLabel title = new JLabel("PRN: " + prn);
        title.setFont(new Font("Arial", Font.BOLD, 38));
        title.setBounds(120, 20, 600, 40);
        add(title);

        JLabel op = new JLabel("Select Operation:");
        op.setFont(new Font("Arial", Font.BOLD, 38));
        op.setBounds(100, 90, 600, 40);
        add(op);


        issueBookButton = new JButton("Issue Book");
        issueBookButton.setBounds(150, 170, 200, 50);
        add(issueBookButton);

        returnBookButton = new JButton("Return Book");
        returnBookButton.setBounds(150, 240, 200, 50);
        add(returnBookButton);

        searchBookButton = new JButton("Search Book");
        searchBookButton.setBounds(150, 310, 200, 50);
        add(searchBookButton);

        viewIssuedBooksButton = new JButton("View Issued Books");
        viewIssuedBooksButton.setBounds(150, 380, 200, 50);
        add(viewIssuedBooksButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(150, 450, 200, 50);
        add(exitButton);


        issueBookButton.addActionListener(this);
        returnBookButton.addActionListener(this);
        searchBookButton.addActionListener(this);
        viewIssuedBooksButton.addActionListener(this);
        exitButton.addActionListener(this);

        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocation(350, 50);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == issueBookButton) {
            setVisible(false);
            new Issue(prn).setVisible(true);
        } else if (e.getSource() == returnBookButton) {
            setVisible(false);
            new Return(prn).setVisible(true);
        } else if (e.getSource() == searchBookButton) {
            setVisible(false);
            new SearchBook(prn).setVisible(true);
        } else if (e.getSource() == viewIssuedBooksButton) {
            new ViewIssuedBook(prn).setVisible(true);
        }else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Library("").setVisible(true);
    }
}

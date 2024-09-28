package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Signup extends JFrame implements ActionListener {

    JButton register, clear, back;
    JLabel title, name, prn, year, pass, repass;
    JTextField ename, eprn;
    JPasswordField rpass, rrepass;
    JComboBox<String> yearDropdown;

    Signup() {
        setTitle("Sign Up");

        title = new JLabel("Sign Up");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setBounds(250, 40, 300, 45);
        add(title);

        name = new JLabel("Name :");
        name.setFont(new Font("Arial", Font.BOLD, 33));
        name.setBounds(70, 170, 200, 35);
        add(name);

        ename = new JTextField();
        ename.setFont(new Font("Arial", Font.PLAIN, 33));
        ename.setBounds(370, 170, 300, 35);
        add(ename);

        prn = new JLabel("Prn no :");
        prn.setFont(new Font("Arial", Font.BOLD, 33));
        prn.setBounds(70, 250, 200, 35);
        add(prn);

        eprn = new JTextField();
        eprn.setFont(new Font("Arial", Font.PLAIN, 33));
        eprn.setBounds(370, 250, 300, 35);
        add(eprn);

        year = new JLabel("Academic Year :");
        year.setFont(new Font("Arial", Font.BOLD, 33));
        year.setBounds(70, 330, 300, 35);
        add(year);

        String[] years = {"Select Academic Year", "1st Year", "2nd Year", "3rd Year", "4th Year"};
        yearDropdown = new JComboBox<>(years);
        yearDropdown.setFont(new Font("Arial", Font.BOLD, 25));
        yearDropdown.setBounds(370, 330, 300, 35);
        add(yearDropdown);

        pass = new JLabel("Create Pass :");
        pass.setFont(new Font("Arial", Font.BOLD, 33));
        pass.setBounds(70, 410, 300, 35);
        add(pass);

        rpass = new JPasswordField();
        rpass.setFont(new Font("Arial", Font.PLAIN, 33));
        rpass.setBounds(370, 410, 300, 35);
        add(rpass);

        repass = new JLabel("Re-enter :");
        repass.setFont(new Font("Arial", Font.BOLD, 33));
        repass.setBounds(70, 490, 300, 35);
        add(repass);

        rrepass = new JPasswordField();
        rrepass.setFont(new Font("Arial", Font.PLAIN, 33));
        rrepass.setBounds(370, 490, 300, 35);
        add(rrepass);

        register = new JButton("Register");
        register.setFont(new Font("Arial", Font.BOLD, 33));
        register.setBackground(Color.BLACK);
        register.setForeground(Color.WHITE);
        register.setBounds(70, 600, 300, 50);
        register.addActionListener(this);
        add(register);

        clear = new JButton("Clear");
        clear.setFont(new Font("Arial", Font.BOLD, 33));
        clear.setBackground(Color.BLACK);
        clear.setForeground(Color.WHITE);
        clear.setBounds(420, 600, 300, 50);
        clear.addActionListener(this);
        add(clear);

        back = new JButton("Back");
        back.setFont(new Font("Arial", Font.BOLD, 33));
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(70, 670, 650, 50);
        back.addActionListener(this);
        add(back);

        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setLocation(500, 10);
        setSize(800, 800);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == register) {
            String name = ename.getText();
            String prn = eprn.getText();
            String year = (String) yearDropdown.getSelectedItem(); // Get selected year
            String password = new String(rpass.getPassword());
            String confirmPassword = new String(rrepass.getPassword());

            if (password.isEmpty() || !password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            assert year != null;
            if (name.isEmpty() &&  year.equals("Select Academic Year")) {
                JOptionPane.showMessageDialog(this, "Please enter the data!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Conn conn = new Conn();
            String query1 = "INSERT INTO login (prn, pass) VALUES (?, ?)";
            String query2 = "INSERT INTO student (prn, name, year, pass) VALUES (?, ?, ?, ?)"; // Make sure the columns match your database schema

            try {
                conn.c.setAutoCommit(false);

                try (PreparedStatement pstmt = conn.c.prepareStatement(query1)) {
                    pstmt.setString(1, prn);
                    pstmt.setString(2, password);
                    pstmt.executeUpdate();
                }

                try (PreparedStatement pst = conn.c.prepareStatement(query2)) {
                    pst.setString(1, prn);
                    pst.setString(2, name);
                    pst.setString(3, year);
                    pst.setString(4, password);
                    pst.executeUpdate();
                }

                conn.c.commit();
                JOptionPane.showMessageDialog(this, "Registration successful!");
                setVisible(false);
                new Login().setVisible(true);
            } catch (SQLException ex) {
                try {
                    conn.c.rollback();
                } catch (SQLException rollbackEx) {
                    JOptionPane.showMessageDialog(this, "Rollback failed: " + rollbackEx.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showMessageDialog(this, "Registration failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    conn.c.setAutoCommit(true); // Restore default auto-commit mode
                } catch (SQLException autoCommitEx) {
                    JOptionPane.showMessageDialog(this, "Error restoring auto-commit mode: " + autoCommitEx.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == clear) {
            ename.setText("");
            eprn.setText("");
            yearDropdown.setSelectedIndex(0);
            rpass.setText("");
            rrepass.setText("");
        } else if (e.getSource() == back) {
            setVisible(false);
            new Login().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Signup().setVisible(true);
    }
}

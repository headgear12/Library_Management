package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewIssuedBook extends JFrame {
    String prn;

    public ViewIssuedBook(String prn) {
        this.prn = prn;
        StringBuilder result = new StringBuilder();

        try{
            Conn c = new Conn();
            PreparedStatement pst = c.c.prepareStatement("SELECT * FROM issue WHERE prn = ?");
            pst.setString(1, prn);

            try (ResultSet rs = pst.executeQuery()) {
                if (!rs.next()) {
                    result.append("No Issued Books Found");
                } else {
                    result.append("Issued book(s)").append("\n");
                    do {
                        String id = rs.getString("Book_ID");
                        try (PreparedStatement p = c.c.prepareStatement("SELECT * FROM books WHERE Book_ID = ?")) {
                            p.setString(1, id);
                            try (ResultSet r = p.executeQuery()) {
                                if (r.next()) {
                                    String name = r.getString("Book");
                                    result.append("Book ID: ").append(id).append("  ");
                                    result.append("Name: ").append(name).append("\n");
                                }
                            }
                        }
                    } while (rs.next());
                }
            }
            JOptionPane.showMessageDialog(this, result.toString());

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

public static void main(String[] args) {
        new ViewIssuedBook("").setVisible(true);
    }
}

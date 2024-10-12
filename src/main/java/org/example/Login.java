package org.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener{
    JLabel l1,l2,l3;
    JTextField tf;
    JPasswordField pf;
    JButton b1,b2,b3;

    Login(){
        setTitle("AUTOMATED TELLER MACHINE");

        l1 = new JLabel("WELCOME TO Library");
        l1.setFont(new Font("Osward", Font.BOLD, 38));
        l1.setBounds(150,40,450,40);
        add(l1);

        l2 = new JLabel("PRN:");
        l2.setFont(new Font("Raleway", Font.BOLD, 28));
        l2.setBounds(75,150,375,30);
        add(l2);

        tf = new JTextField(15);
        tf.setBounds(250,150,230,30);
        tf.setFont(new Font("Arial", Font.BOLD, 14));
        add(tf);

        l3 = new JLabel("PASS:");
        l3.setFont(new Font("Raleway", Font.BOLD, 28));
        l3.setBounds(75,220,375,30);
        add(l3);

        pf = new JPasswordField(15);
        pf.setFont(new Font("Arial", Font.BOLD, 14));
        pf.setBounds(250,220,230,30);
        add(pf);

        b1 = new JButton("SIGN IN");
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);

        b2 = new JButton("CLEAR");
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);

        b3 = new JButton("SIGN UP");
        b3.setBackground(Color.BLACK);
        b3.setForeground(Color.WHITE);

        setLayout(null);

        b1.setFont(new Font("Arial", Font.BOLD, 14));
        b1.setBounds(250,300,100,30);
        add(b1);

        b2.setFont(new Font("Arial", Font.BOLD, 14));
        b2.setBounds(380,300,100,30);
        add(b2);

        b3.setFont(new Font("Arial", Font.BOLD, 14));
        b3.setBounds(250,350,230,30);
        add(b3);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);

        setSize(700,480);
        setLocation(550,200);
        setVisible(true);

    }
    public void actionPerformed(ActionEvent ae){
        try{
            if(ae.getSource()==b1){
                Conn c = new Conn();
                String prn = tf.getText();
                String password = new String(pf.getPassword());

                PreparedStatement pst = c.c.prepareStatement("SELECT * FROM login WHERE prn = ? AND pass = ?");
                pst.setString(1,prn);
                pst.setString(2,password);

                if (pst.execute()) {
                    setVisible(false);
                    new Library(prn).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid prn or password!");
                }

            }else if(ae.getSource()==b2){
                tf.setText("");
                pf.setText("");
            }else if(ae.getSource()==b3){
                setVisible(false);
                new Signup().setVisible(true);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        new Login().setVisible(true);
    }

}

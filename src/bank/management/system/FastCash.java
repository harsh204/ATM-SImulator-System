package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {

    JButton cash1, cash2, cash3, cash4, cash5, cash6, exit;
    String pinNumber;

    FastCash(String pinNumber) {
        this.pinNumber = pinNumber;
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));

        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 900, 900);
        add(image);

        JLabel text = new JLabel("Select Withdrawal Amount: ");
        text.setBounds(215, 300, 700, 35);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("System", Font.BOLD, 16));
        image.add(text);

        cash1 = new JButton("Rs 100");
        cash1.setBounds(170, 415, 150, 30);
        cash1.addActionListener(this);
        image.add(cash1);

        cash2 = new JButton("Rs 500");
        cash2.setBounds(355, 415, 150, 30);
        cash2.addActionListener(this);
        image.add(cash2);

        cash3 = new JButton("Rs 1000");
        cash3.setBounds(170, 450, 150, 30);
        cash3.addActionListener(this);
        image.add(cash3);

        cash4 = new JButton("Rs 2000");
        cash4.setBounds(355, 450, 150, 30);
        cash4.addActionListener(this);
        image.add(cash4);

        cash5 = new JButton("Rs 5000");
        cash5.setBounds(170, 485, 150, 30);
        cash5.addActionListener(this);
        image.add(cash5);

        cash6 = new JButton("Rs 10000");
        cash6.setBounds(355, 485, 150, 30);
        cash6.addActionListener(this);
        image.add(cash6);

        exit = new JButton("BACK");
        exit.setBounds(355, 520, 150, 30);
        exit.addActionListener(this);
        image.add(exit);

        setLayout(null);
        setSize(900, 900);
        setLocation(300, 0);
//        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == exit) {
            setVisible(false);
            new Transactions(pinNumber).setVisible(true);
        } else {
            String amount = ((JButton) ae.getSource()).getText().substring(3);
            Conn conn = new Conn();
            try {
                ResultSet rs = conn.s.executeQuery("select * from bank where pin = '" + pinNumber + "'");
                int balance = 0;
                while (rs.next()) {
                    if (rs.getString("type").equals("Deposit")) {
                        balance += Integer.parseInt(rs.getString("amount"));
                    } else {
                        balance -= Integer.parseInt(rs.getString("amount"));
                    }
                }

                if (ae.getSource() != exit && balance < Integer.parseInt(amount)) {
                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                    return;
                } else {
                    Date date = new Date();
                    String query = "insert into bank values('" + pinNumber + "','" + date + "','Withdrawal','" + amount + "')";
                    conn.s.executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "Rs " + amount + "  Debited Successfully");
                    setVisible(false);
                    new Transactions(pinNumber).setVisible(true);
                }
            } catch (Exception e) {
                System.out.print(e);
            }
        }
    }

    public static void main(String args[]) {
        new FastCash("");
    }
}

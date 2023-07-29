package org.example;


import javax.swing.*;

public class Main extends JFrame {

    public Main() {
        setTitle("Змійка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(320, 345);
        setLocation(400, 100);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) { new Main(); }
}
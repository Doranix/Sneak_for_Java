package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {

    // Змінні, з якими працює гра
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private int appleX;
    private int appleY;
    private final int[] x = new int[ALL_DOTS];
    private final int[] y = new int[ALL_DOTS];
    private Image apple;
    private Image dot;
    private int dots;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    // Конструктор
    public GameField() {
        setBackground(Color.black);
        load_image();
        init_game();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    // Ініціалізація гри
    public void init_game () {
        dots = 3;

        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }

        Timer timer = new Timer(75, this);
        timer.start();
        create_apple();
    }

    // Створення яблука
    public void create_apple () {
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = new Random().nextInt(20) * DOT_SIZE;
    }

    // Загрузка зображень в гру
    public void load_image () {
        ImageIcon iia = new ImageIcon(ClassLoader.getSystemResource("apple.png"));
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon(ClassLoader.getSystemResource("dot.png"));
        dot = iid.getImage();
    }

    // Промальовка компонентів, (Графіка)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        } else {
            String str = "Гра закінчена !";
            g.setColor(Color.white);
            g.drawString(str, 125, SIZE / 2);
        }
    }

    // Рух змійки
    public void move () {
        // Рух тіла
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        // Рух голови
        if (left) x[0] -= DOT_SIZE;
        if (right) x[0] += DOT_SIZE;
        if (up) y[0] -= DOT_SIZE;
        if (down) y[0] += DOT_SIZE;
    }

    // Перевірка, чи не зіткнулась змійка з яблуком
    public void check_apple () {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            create_apple();
        }
    }

    // Перевірка, чи не зіткнулась змійка з рамками екрану
    public void check_collisions () {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
                break;
            }
        }

        if (x[0] < 0 || x[0] > SIZE || y[0] < 0 || y[0] > SIZE) {
            inGame = false;
        }
    }

    // Функція, яка визивається при кожній ітерації
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (inGame) {
            check_apple();
            check_collisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            } if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            } if (key == KeyEvent.VK_UP && !down) {
                up = true;
                left = false;
                right = false;
            } if (key == KeyEvent.VK_DOWN && !up) {
                down = true;
                left = false;
                right = false;
            }
        }
    }
}

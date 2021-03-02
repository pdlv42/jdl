/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import ui.GameInterface;

/**
 *
 * @author Dell
 */

public class Controller {

    GameInterface gi;
    private Timer timer;
    private int moveCounter = 0;
    private int timeCounter = 0;
    private int size;
    private JPanel panel;
    private JButton[] buttons;
    private int emptyButtonIndex;

    public Controller() {
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(javax.swing.JLabel label) {
        // tao dong ho moi vo luot choi moi
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                label.setText("Elapsed time: " + timeCounter + "s");
                timeCounter++;
            }
        });
        timer.start();
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    public void setMoveCounter(int moveCounter) {
        this.moveCounter = moveCounter;
    }

    public int getTimeCounter() {
        return timeCounter;
    }

    public void setTimeCounter(int timeCounter) {
        this.timeCounter = timeCounter;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public JButton[] getButtons() {
        return buttons;
    }

    public void setButtons(JButton[] buttons) {
        this.buttons = buttons;
    }

    public int getEmptyButtonIndex() {
        return emptyButtonIndex;
    }

    public void setEmptyButtonIndex(int emptyButtonIndex) {
        this.emptyButtonIndex = emptyButtonIndex;
    }
    //------------------------------------------------------------
    javax.swing.border.Border bored = BorderFactory.createLineBorder(Color.blue);

    public void addButtons(javax.swing.JPanel panel) {
        panel.removeAll();
        //GridLayout layout = new GridLayout(size, size);
        panel.setLayout(new GridLayout(size, size));
        buttons = new JButton[size * size];
        for (int i = 0; i < size * size; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Tahoma", 100/size, 100/size));
            //view.setPreferredSize(new Dimension(size + 700, size + 700));
            panel.add(buttons[i]);
        }
    }

    public void setButtonValues() {
        ArrayList<Integer> numberList = new ArrayList<>();
        do {

            for (int i = 1; i < size * size; i++) {
                numberList.add(i);
            }

            Random rand = new Random();
            emptyButtonIndex = rand.nextInt(size * size);
            buttons[emptyButtonIndex].setText("");

            for (int i = 0; i < size * size; i++) {
                if (i != emptyButtonIndex) {
                    int index = rand.nextInt(numberList.size());
                    int value = numberList.get(index);
                    buttons[i].setText(value + "");

                    numberList.remove(index);
                }
            }
        } while (!canPlay(size, emptyButtonIndex));
    }

    public boolean canPlay(int size, int emptyButtonIndex) {
        int checkSum = 0, number1, number2;

        if (size % 2 != 0) {
            for (int i = 0; i < size * size; i++) {
                try {
                    number1 = Integer.parseInt(buttons[i].getText());
                } catch (NumberFormatException numberFormatException) {
                    continue;
                }
                for (int j = i; j < size * size; j++) {
                    try {
                        number2 = Integer.parseInt(buttons[j].getText());
                        if (number1 > number2) {
                            checkSum++;
                        }
                    } catch (Exception e) {
                        continue;
                    }

                }
            }
        } else {
            for (int i = 0; i < size * size; i++) {
                //found the index of blankButton.

                try {
                    number1 = Integer.parseInt(buttons[i].getText());
                } catch (NumberFormatException numberFormatException) {
                    continue;
                }
                for (int j = i; j < size * size; j++) {
                    try {
                        number2 = Integer.parseInt(buttons[j].getText());
                        if (number1 > number2) {
                            checkSum++;
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
                if ((emptyButtonIndex / size) % 2 == 0) {
                    return checkSum % 2 != 0;
                }
            }
        }
        return checkSum % 2 == 0;

    }

    public boolean winCondition() {
        int textValue;
        for (int i = 1; i < size * size; i++) {
            try {
                textValue = Integer.parseInt(buttons[i - 1].getText());
            } catch (NumberFormatException e) {
                return false;
            }
            if (i != textValue) {
                return false;
            }
        }
        return true;
    }

    public void swapButtons(javax.swing.JLabel label) {
        System.out.println("abc");
        for (int i = 0; i < buttons.length; i++) {
            int buttonindex = i;
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("abc");
                    if ((buttonindex == emptyButtonIndex - 1 && buttonindex / size == emptyButtonIndex / size) || (emptyButtonIndex == buttonindex + size)
                            || (buttonindex == emptyButtonIndex + 1 && buttonindex / size == emptyButtonIndex / size) || (emptyButtonIndex == buttonindex - size)) {
                        buttons[emptyButtonIndex].setText(buttons[buttonindex].getText());
                        buttons[buttonindex].setText("");
                        emptyButtonIndex = buttonindex;
                        moveCounter++;
                        label.setText("Move count: " + moveCounter + "");

                    }

                    if (winCondition()) {
                        timer.stop();
                        JOptionPane.showMessageDialog(null, "You win");
                    }
                }
            });
        }
    }
}
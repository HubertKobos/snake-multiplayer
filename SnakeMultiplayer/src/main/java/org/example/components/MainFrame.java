package org.example.components;

import org.example.websockets.GameWebsocketClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.util.Random;

public class MainFrame {
    int width = 600, height = 600;
    private final Random random = new Random();
    private JFrame frame = new JFrame("Snake");
    private String randomCode;
    private boolean aloneinTheGameRoom = true; // is anybody in the gameRoom ?
    private GameWebsocketClient gameWebsocketClient;
    private JTextField jTextField;
    public MainFrame(){
        frame.setSize(new Dimension(width, height));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setRandomCode(generateRandomCode());
        initializeComponents();
//        connectToTheGameServer();
        frame.setLayout(null); // otherwise setBounds won't work on other objects
        frame.setVisible(true);
    }



    private void initializeComponents(){
        String codeMessage = new String("Your code: " + getRandomCode());

        JLabel jLabel = createJLabelWithCode(codeMessage);
        frame.add(jLabel);

        JTextField jTextField = createCodeJTextField();
        frame.add(jTextField);

        JLabel jLabel1 = createJLabelForCodeTextField("Pass the code ...");
        frame.add(jLabel1);

        JButton jButton = createJButton("Join the game");
        frame.add(jButton);

    }

    private JLabel createJLabelWithCode(String message){
        JLabel jLabel = new JLabel(message);
        jLabel.setFont(new Font(null,0,30));
        jLabel.setBounds(width/4, 0, 300, 100);
        return jLabel;
    }

    private JTextField createCodeJTextField(){
        this.jTextField = new JTextField();
        jTextField.setBounds(width/3, 150, 170, 30);
        return jTextField;
    }

    private JLabel createJLabelForCodeTextField(String message){
        JLabel jLabel2 = new JLabel(message);
        jLabel2.setFont(new Font(null,0,15));
        jLabel2.setBounds(width/3, 90, 300, 100);
        return jLabel2;
    }

    private JButton createJButton(String message){
        JButton jButton = new JButton(message);
        jButton.setBounds(width/3, 185, 170, 40);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(jTextField.getText().equals("")){
                    try {
                        connectToTheGameServer();
                    } catch (URISyntaxException ex) {
                        throw new RuntimeException(ex);
                    }
                }else{
                    System.out.println("here");
                    try {
                        gameWebsocketClient = new GameWebsocketClient(jTextField.getText(), frame);
                    } catch (URISyntaxException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });
        return jButton;
    }

    private void connectToTheGameServer() throws URISyntaxException {
        this.gameWebsocketClient = new GameWebsocketClient(getRandomCode(), frame);
    }
    private String generateRandomCode(){
        return String.valueOf(random.nextInt(1000, 2000)) + String.valueOf(random.nextInt(1000, 2000));
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }
}

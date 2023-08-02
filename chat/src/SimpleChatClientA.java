import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;
public class SimpleChatClientA {
    JTextArea incoming;
    JTextField outcoming;
    JButton send;
    BufferedReader reader;
    Socket sock;
    PrintWriter writer;
    JButton reset;
    public static void main(String[] args){
        SimpleChatClientA chat = new SimpleChatClientA();
        chat.go();
    }
    void go(){
        JFrame frame = new JFrame("Ludicrously Simple Chat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        reset = new JButton("Reset");
        reset.addActionListener(new MyresetListener());
        incoming = new JTextArea(15,20);
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);
        JScrollPane qscrollpane = new JScrollPane(incoming);
        qscrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qscrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outcoming = new JTextField(20);
        outcoming.addActionListener(new MyenterListener());
        send = new JButton("Send");
        send.addActionListener(new MysendListener());
        JPanel mainPanel = new JPanel();
        mainPanel.add(qscrollpane);
        mainPanel.add(outcoming);
        mainPanel.add(send);
        mainPanel.add(reset);
        setupNetworking();
        Thread t = new Thread(new Recieve());
        t.start();
        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
        frame.setSize(400,500);
        frame.setVisible(true);
    }
    void setupNetworking(){
        try{
            sock = new Socket("ef89-2405-4803-ff13-b040-992a-953b-6a09-ffa9.ngrok-free.app",80);
            InputStreamReader income = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(income);
            writer = new PrintWriter(sock.getOutputStream());
            System.out.println("networking established");
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    class MyresetListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            incoming.setText("");
        }
    }
    class Recieve implements Runnable {
        public void run(){
            String message;
            try {
                while((message = reader.readLine()) != null) {
                    System.out.println("read  " + message);
                    incoming.append(message + "\n");
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    class MysendListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Send();
        }
    }
    class MyenterListener implements ActionListener{
        public void actionPerformed(ActionEvent ev){
            Send();
        }
    }
    public void Send(){
        try {
            writer.println(outcoming.getText());
            writer.flush();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        outcoming.setText("");
        outcoming.requestFocus();
    }
}
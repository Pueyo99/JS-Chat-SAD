package XAT_GRAFIC;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class GUI{


    private static void createAndShowGUI(){

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        }catch(Exception e) {e.printStackTrace();}

        //Window decorations
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create window.
        JFrame frame = new JFrame("XAT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Output JPanel and JTextArea inside JScrollPane.
        JPanel output = new JPanel(new GridLayout(1,1));
        JTextArea messages = new JTextArea(20, 30);
        messages.setEditable(false);

        output.add(new JScrollPane(messages));

        //Input Jpanel and JTextField + JButton.
        JPanel input = new JPanel();
        JTextField text = new JTextField(25);
        JButton send = new JButton("Send");

        input.add(text);
        input.add(send);

        //User List
        JPanel userList = new JPanel(new GridLayout(1,2));

        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Jorge Pueyo");
        listModel.addElement("Martí Canyelles");
        listModel.addElement("Josep Guardiola");
        listModel.addElement("Mariano Rajoy");
        listModel.addElement("Pedro Sanchez");
        listModel.addElement("Pablo Casado");
        
        JList list = new JList<>(listModel);

        JScrollPane listScrollPane = new JScrollPane(list);

        userList.add(listScrollPane, BorderLayout.CENTER);





        //Add panels to main frame.
        frame.add(output, BorderLayout.CENTER);
        frame.add(input, BorderLayout.PAGE_END);
        frame.add(userList, BorderLayout.EAST);

       
        //Display the window.
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }


    public static void main(String[] args){

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                createAndShowGUI();
            }
       });
    }
    
}
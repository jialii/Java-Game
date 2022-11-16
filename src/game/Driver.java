/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package game;

/**
 *
 * @author abbyxu
 */
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Driver extends JFrame{
    static JFrame jf;
    static JTextField username;
    static JTextField ccolor;
    static JFrame jf2;
    static JButton enter;
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]){
        jf = new JFrame("Game");
        JLabel name = new JLabel("Enter your username: \n");
        username = new JTextField(20);
        JLabel carColor = new JLabel("Would you like a yellow car or purple car? \n");
        ccolor = new JTextField(20);
        enter = new JButton("Enter");
        JPanel firstpage = new JPanel();
        enter.addActionListener(new loginListener());
        JLabel instruction = new JLabel("Use the arrow keys to control your car  "
            + "(up, down, left, and right) \n");
        firstpage.add(name);
        firstpage.add(username);
        firstpage.add(carColor);
        firstpage.add(ccolor);
        firstpage.add(enter);
        firstpage.add(instruction);
        jf.add(firstpage);
       

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(500, 720);
        jf.setResizable(false);
        jf.setVisible(true);
    }
    static class loginListener implements ActionListener {
            loginListener() {}
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String name = username.getText();
                String co = ccolor.getText();
                if (name.length() != 0 && co.length() != 0) {
                    jf.dispose();
                    jf2 = new JFrame();
                    jf2.setSize(500, 720);
                    jf2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    
//                    try{
//                        String user = "root";
//                        String pass = "password"; 
//                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Game",user,pass);
//                        Statement st = conn.createStatement();
//                        String q = "INSERT INTO Game.user VALUES ('"+name+"');";
//                        st.executeUpdate(q);
//                    }
//                    catch(Exception ex){ex.printStackTrace();}
                    
                    Game g=new Game(co);
                    jf2.add(g);
                    jf2.setResizable(false);
                    jf2.setVisible(true);
                }
            }
    }
}


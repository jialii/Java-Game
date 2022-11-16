/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package game;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
/**
 *
 * @author abbyxu
 */
public class Game extends JPanel implements ActionListener,KeyListener{
    
    private int space;
    private int width=80;
    private int height=70;
    private int speed;
    private int WIDTH=500;
    private int HEIGHT=700;
    private int count=1;
    private Rectangle car;
    private ArrayList <Rectangle> rocks;
    private ArrayList <Rectangle> line;
    private Random rand;
    Boolean end = false;
    BufferedImage bg;
    BufferedImage user;
    URL purl = this.getClass().getResource("/images/purple.png");
    URL yurl = this.getClass().getResource("/images/yellow.png");
    URL rockurl = this.getClass().getResource("/images/rock.png");
    URL bgurl = this.getClass().getResource("/images/black.png");
    //String purple = "/Users/abbyxu/Desktop/NYU/Spring 2022/java and web design/Project/yellow.png";
    BufferedImage rk;
    //BufferedImage op2;
    Boolean linef=true;
    Timer t;
    public Game(String color){
        try {
            System.out.println(color);     
            if(color.charAt(0)=='p'){
                user=ImageIO.read(purl);}
            else{user=ImageIO.read(yurl);}
            rk=ImageIO.read(rockurl);
            bg=ImageIO.read(bgurl);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        t=new Timer(20,this);
        rand=new Random();
        rocks=new ArrayList<Rectangle>();
        line=new ArrayList<Rectangle>();
        car=new Rectangle(WIDTH/2-90,HEIGHT-100,width,height);

        space=300;
        speed=2;
        addKeyListener(this);
        setFocusable(true);
        for(int i = 0; i<6;i++){
            addrocks(true);
            addlines(true);
        }
        t.start();
    }
    public void addlines(Boolean bool){
        int x=WIDTH/2+10;
        int y=700;
        int width=4;
        int height=100;
        int sp=130;
        if(bool){
            line.add(new Rectangle(x,y-(line.size()*sp),width,height));
        }else{
            line.add(new Rectangle(x,line.get(line.size()-1).y-sp,width,height));
        }
    } 
    public void addrocks(boolean bool){
        int positionx=rand.nextInt()%2;
        int x=0;
        int y=0;
        int Width=width;
        int Height=height;
        if(positionx==0){
            x=WIDTH/2-80;
        }else{
            x=WIDTH/2+20;
        }
        if(bool){
            rocks.add(new Rectangle(x,y-100-(rocks.size()*space),Width,Height));
        }else{
            rocks.add(new Rectangle(x,rocks.get(rocks.size()-1).y-300,Width,Height));
        }
    }
    public void paintComponent(Graphics g){
        if(end == false){
            super.paintComponents(g);
            g.drawImage(bg, 0, 0, null);
            g.setColor(Color.WHITE);
            for(Rectangle rect:line){
                g.fillRect(rect.x, rect.y, rect.width, rect.height);
            }
            g.drawImage(user, car.x, car.y, null);

            for(Rectangle rect:rocks){
                if(rand.nextInt()%2==0){
                    g.drawImage(rk, rect.x, rect.y, null);
                }else{
                    g.drawImage(rk, rect.x, rect.y,null);
                }

            }
        }
        else{
            int space = 40;
            g.setColor(Color.BLUE);
            g.setFont(new Font("TimesRoman", Font.BOLD, 40));
            g.drawString("GAME OVER", 120, 100);
            g.drawString("Past players: ", 120, 200);
            try{
                String user = "root";
                String pass = "password"; 
                
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Game",user,pass);
                Statement st = conn.createStatement();
                ResultSet res = st.executeQuery("SELECT * FROM user");
               
                g.setColor(Color.black);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
                while(res.next()){
                    g.drawString(res.getString("Username"),120, 200+space);
                    space += 40;
                }
            }
            catch(Exception ex){ex.printStackTrace();}

        }
        
    }

    public void actionPerformed(ActionEvent e) {
        Rectangle rect;
        count++;
        for(Rectangle r:rocks){         //if car hit a rock
            if(r.intersects(car)){
               car.y=r.y+height;
               end = true;              //game over
            }
        }

        for(int i=0;i<rocks.size();i++){ //speed up the rocks in game  
            rect=rocks.get(i);
            if(count%1000==0){
               speed++;
            }
            rect.y+=speed;
        }
        
        for(int i=0;i<line.size();i++){   //speed up the lines in game    
            rect=line.get(i);
            if(count%1000==0){
                speed++;
            }
           rect.y+=speed;
        }
        for(int i=0;i<line.size();i++){ //adding more lines so it has the moving effect
            rect=line.get(i);
            if(rect.y>HEIGHT){
               line.remove(rect);
               addlines(false);
            }
         }
        repaint();
    }
    
    //moveingup
    public void moveup(){
        if(car.y-20>0)
            car.y-=20;
        
    }
    public void movedown(){
        if(car.y+20+car.height<=HEIGHT-1)
            car.y+=20;   
    }
   public void moveleft(){
        if(car.x-20>=WIDTH/2-90)
            car.x-=20;
    }
    public void moveright(){
        if(car.x+20<=WIDTH/2+30)
            car.x+=20;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int key=e.getKeyCode();
        switch(key){
            case KeyEvent.VK_UP:
                moveup();
                break;
            case KeyEvent.VK_DOWN:
                movedown();
                break;
            case KeyEvent.VK_LEFT:
                moveleft();
                break;
            case KeyEvent.VK_RIGHT:
                moveright();
               break;
            default:
                break ;
        }    
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key=e.getKeyCode();
        switch(key){
            case KeyEvent.VK_UP:
                moveup();
                break;
            case KeyEvent.VK_DOWN:
                movedown();
                break;
            case KeyEvent.VK_LEFT:
                moveleft();
                break;
            case KeyEvent.VK_RIGHT:
                moveright();
               break;
            default:
                break ;
        } 
    }
    
    
    }



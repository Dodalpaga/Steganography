package Menu;

import HideImage.InterfaceImages;
import HideMorse.InterfaceMorse;
import HideOTP.InterfaceOTP;
import HideASCII.InterfaceASCII;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;


public class Projet_Final implements ActionListener{
    
    
    public static void main(String[] args) throws IOException{
        JFrame frame = new JFrame("Colored Trails");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel label = new JLabel("by Dorian VOYDIE & Jean BAUMERT");
        frame.getContentPane().add(label, BorderLayout.SOUTH);
        JPanel Panel = new JPanel();
        Panel.setLayout(new BoxLayout(Panel, BoxLayout.Y_AXIS));
        

        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(new GridLayout(1, 4));
        JButton btn1,btn2,btn3,btn4;
        ImageIcon img1 = new ImageIcon(ImageIO.read(new File("Images\\StegoImages.png")));
        ImageIcon img2 = new ImageIcon(ImageIO.read(new File("Images\\StegoTextes.png")));
        ImageIcon img3 = new ImageIcon(ImageIO.read(new File("Images\\Walrus.png")));
        ImageIcon img4 = new ImageIcon(ImageIO.read(new File("Images\\Password.png")));
        
        
        
        btn1 = new JButton("Images",img1);
        btn1.setFont(new Font("Kristen ITC", Font.PLAIN, 50));
        btn1.setPreferredSize(new Dimension(100, 100));
        btn1.setHorizontalTextPosition(JButton.CENTER);
        btn1.setVerticalTextPosition(JButton.CENTER);
        firstPanel.add(btn1);
        
        
        
        
        btn2 = new JButton("ASCII", img2);
        btn2.setFont(new Font("Kristen ITC", Font.PLAIN, 50));
        btn2.setHorizontalTextPosition(JButton.CENTER);
        btn2.setPreferredSize(new Dimension(100, 100));
        btn2.setVerticalTextPosition(JButton.CENTER);
        firstPanel.add(btn2);
        
        
        
        
        btn3 = new JButton("Morse", img3);
        btn3.setFont(new Font("Kristen ITC", Font.PLAIN, 50));
        btn3.setHorizontalTextPosition(JButton.CENTER);
        btn3.setPreferredSize(new Dimension(100, 100));
        btn3.setVerticalTextPosition(JButton.CENTER);
        firstPanel.add(btn3);
        
        
        
        btn4 = new JButton("<html><center>"+"One Time"+"<br>"+"Password"+"</center></html>", img4);
        btn4.setFont(new Font("Kristen ITC", Font.PLAIN, 50));
        btn4.setHorizontalTextPosition(JButton.CENTER);
        btn4.setPreferredSize(new Dimension(100, 100));
        btn4.setVerticalTextPosition(JButton.CENTER);
        firstPanel.add(btn4);
        

        Panel.add(firstPanel);
        
        
        frame.setSize(1600,400);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
        
        TitledBorder border = BorderFactory.createTitledBorder("Que voulez-vous cacher ?");
        Panel.setBorder(border);
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        border.setTitleFont(new Font("Lucida Console", Font.ROMAN_BASELINE, 30));
        
        
        frame.add(Panel);
        frame.setVisible(true);
        
        btn1.addActionListener((ActionEvent e) -> {
            try {
                InterfaceImages window = new InterfaceImages();
                window.frame.setLocation(d.width/2 - window.frame.getWidth()/2, d.height/2 - window.frame.getHeight()/2);
                window.frame.setVisible(true);
            } catch (HeadlessException event) {
                event.printStackTrace();
            }
        });
        
        btn2.addActionListener((ActionEvent e) -> {
            try {
                InterfaceASCII window = new InterfaceASCII();
                window.frame.setLocation(d.width/2 - window.frame.getWidth()/2, d.height/2 - window.frame.getHeight()/2);
                window.frame.setVisible(true);
            } catch (HeadlessException event) {
                event.printStackTrace();
            }
        }); 
        
        btn3.addActionListener((ActionEvent e) -> {
            try {
                InterfaceMorse window = new InterfaceMorse();
                window.frame.setLocation(d.width/2 - window.frame.getWidth()/2, d.height/2 - window.frame.getHeight()/2);
                window.frame.setVisible(true);
            } catch (HeadlessException event) {
                event.printStackTrace();
            }
        }); 
        
        btn4.addActionListener((ActionEvent e) -> {
            try {
                InterfaceOTP window = new InterfaceOTP();
                window.frame.setLocation(d.width/2 - window.frame.getWidth()/2, d.height/2 - window.frame.getHeight()/2);
                window.frame.setVisible(true);
            } catch (HeadlessException event) {
                event.printStackTrace();
            }
        }); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
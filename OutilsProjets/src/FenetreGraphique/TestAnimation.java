/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FenetreGraphique;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
/**
 *
 * @author guillaume.laurent
 */
public class TestAnimation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        FenetreGraphique fenetre = new FenetreGraphique("Nyan cat", 300, 250);
        try{
            BufferedImage image = ImageIO.read(new File("nyancat.gif"));

            int x = -image.getWidth(null);
            while (true) {
                fenetre.effacer(new Color(0, 0, 100));
                fenetre.getGraphics2D().drawImage(image, x, 100, null);
                fenetre.actualiser(40);
                x = x + 5;
                if (x > 300) {
                    x = -image.getWidth(null);
                }
            }
        } catch(IOException e){
            
        }

    }

}

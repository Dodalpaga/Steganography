package Utilities;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Images extends JComponent{

    private int width, height;
    private BufferedImage imageCourante;
    private BufferedImage imageContenante;
    private BufferedImage imageToHide;
    private BufferedImage imageFinale;
    private static final String IMAGE_FORMAT = "PNG";

    
    public Images(){
        imageCourante = null;
        imageContenante = null;
        imageToHide = null;
        imageFinale = null;
    }
    public Images(BufferedImage imageSource, BufferedImage imageToHide) {
	this.imageContenante = imageSource;
	this.imageToHide = imageToHide;
	width = imageSource.getWidth();
	height = imageSource.getHeight();
	imageFinale = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }
    public void setCourante(BufferedImage image)    {
        if(image != null) {
            width = image.getWidth();
            height = image.getHeight();
            imageCourante = image;
            repaint();
        }
    }
    public void setSource(BufferedImage img){
        if(img != null) {
            width = img.getWidth();
            height = img.getHeight();
            imageContenante = img;
            repaint();
        }
    }
    public void setFinale(BufferedImage img){
        if(img != null) {
            width = img.getWidth();
            height = img.getHeight();
            imageFinale = img;
            repaint();
        }
    }
    public void setoHide(BufferedImage img){
        if(img != null) {
            width = img.getWidth();
            height = img.getHeight();
            imageToHide = img;
            repaint();
        }
    }
    public BufferedImage getFinale() {
	return imageFinale;
    }
    public BufferedImage getCourante(){
    	return imageCourante;
    }
    public void clearImageCourante(){
        Graphics imageGraphics = imageCourante.getGraphics();
        imageGraphics.setColor(Color.LIGHT_GRAY);
        imageGraphics.fillRect(0, 0, width, height);
        repaint();
    }
    
    
    ////Display
    @Override   
    public Dimension getPreferredSize(){
        return new Dimension(width, height);
    }
    @Override
    public void paintComponent(Graphics g){
        Dimension size = getSize();
        g.clearRect(0, 0, size.width, size.height);
        if(imageCourante != null) {
            g.drawImage(imageCourante, 0, 0, null);
        }
    }
    //Display
    
    
    ////Images
    //Vous pouvez effectuer des changement dans les methodes "echange" et "revelePixels"
    //afin de changer le nombre de bits significatifs.
    //En effet : on observe pas la meme qualité d'image entre 1 et 3 LSB.
    //Il faut alors compenser entre la qualité de l'image à cacher et celle 
    //de la source
    //La methode "echange" permet de cacher les bits de poids fort du pixel B
    //dans les bits de poids faible du pixel A au sein d'une image.
    
    public int echange(int PixelA, int PixelB){
        //return (PixelA & 0xFFFEFEFE) | (PixelB & 0x00808080)>>7; //Avec 1 LSB
        return (PixelA & 0xFFFCFCFC) | (PixelB & 0x00C0C0C0)>>6; //Avec 2 LSB
        //return (PixelA & 0xFFF8F8F8) | (PixelB & 0x00E0E0E0)>>5; // Avec 3 LSB
    }
    public void hideImage(){
        for(int x =0 ; x< imageContenante.getWidth();x++){
            for(int y =0 ; y< imageContenante.getHeight();y++){
                if(x<imageToHide.getWidth() && y<imageToHide.getHeight()){
                    imageFinale.setRGB(x, y, echange(
                    imageContenante.getRGB(x, y),imageToHide.getRGB(x, y))
                    );
                }
                else{
                    imageFinale.setRGB(x,y,imageContenante.getRGB(x, y));
                }
            }
        }
    }
    ////Images
    
    //PixelsManager
    public int blue(int x, int y){
        int pixel = imageCourante.getRGB(x, y);
        
        
        int blue = (pixel) & 0xff;
        return blue;
    }
    public int green(int x, int y){
        int pixel = imageCourante.getRGB(x, y);
        
        
        int blue = (pixel>>8) & 0xff;
        return blue;
    }
    public int red(int x, int y){
        int pixel = imageCourante.getRGB(x, y);
        
        
        int blue = (pixel>>16) & 0xff;
        return blue;
    }
    public int alpha(int x, int y){
        int pixel = imageCourante.getRGB(x, y);
        
        
        int blue = (pixel>>24) & 0xff;
        return blue;
    }
    public Pixel getPixel(int x , int y){
        return(new Pixel(red(x,y),green(x,y),blue(x,y),alpha(x,y)));
    }
    public int revelePixels(int pixel){
        //return (pixel & 0xFF010101)<<7; //Avec 1 LSB
        return (pixel & 0xFF030303)<<6; //Avec 2 LSB
        //return (pixel & 0xFF070707)<<5; //Avec 3 LSB
    }
    //PixelsManager    

    //ImageFilter
    public BufferedImage canalVert(BufferedImage img){
        imageCourante = img;
        for(int x =0 ; x <img.getWidth() ; x++){
            for(int y=0 ; y<img.getHeight() ; y++){
                Pixel p = getPixel(x,y);
                p.setR(0);
                p.setB(0);
                img.setRGB(x, y, p.toInt());
            }
        }
        return img;
    }
    public BufferedImage canalBleu(BufferedImage img){
        imageCourante = img;
        for(int x =0 ; x <img.getWidth() ; x++){
            for(int y=0 ; y<img.getHeight() ; y++){
                Pixel p = getPixel(x,y);
                p.setR(0);
                p.setV(0);
                img.setRGB(x, y, p.toInt());
            }
        }
        return img;
    }
    public BufferedImage canalRouge(BufferedImage img){
        imageCourante = img;
        for(int x =0 ; x <img.getWidth() ; x++){
            for(int y=0 ; y<img.getHeight() ; y++){
                Pixel p = getPixel(x,y);
                p.setV(0);
                p.setB(0);
                img.setRGB(x, y, p.toInt());
            }
        }
        return img;
    }
    public BufferedImage negatif(BufferedImage img){
        imageCourante = img;
        for(int x =0 ; x <img.getWidth() ; x++){
            for(int y=0 ; y<img.getHeight() ; y++){
                Pixel p = getPixel(x,y);
                p.setV(255-green(x,y));
                p.setB(255-blue(x,y));
                p.setR(255-red(x,y));
                img.setRGB(x, y, p.toInt());
            }
        }
        return img;
    }
    public BufferedImage canalGris(BufferedImage img){
        imageCourante = img;
        for(int x =0 ; x <img.getWidth() ; x++){
            for(int y=0 ; y<img.getHeight() ; y++){
                int luminance = (green(x,y)+blue(x,y)+red(x,y))/3;
                Pixel p = getPixel(x,y);
                p.setV(luminance);
                p.setB(luminance);
                p.setR(luminance);
                img.setRGB(x, y, p.toInt());
            }
        }
        return img;
    }
    public BufferedImage contour(BufferedImage img){
        imageCourante = img;
        int[][] tab = new int[img.getWidth()-1][img.getHeight()-1];
        for(int x =1 ; x < img.getWidth()-2 ; x++){
            for(int y=1 ; y< img.getHeight()-2 ; y++){
                int l = (int) Math.sqrt((blue(x-1,y)-blue(x+1,y))^2+(blue(x,y+1)-blue(x,y-1))^2);
                tab[x-1][y-1]=l*30; //Amplification
            }
        }
        for(int x =1 ; x < img.getWidth()-1 ; x++){
            for(int y=1 ; y< img.getHeight()-1 ; y++){
                int l  = tab[x-1][y-1];
                Pixel p = new Pixel(l,l,l,255);
                img.setRGB(x, y, p.toInt());
            }
        }
        
        return img;
    }
    public void decalage(){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
		imageCourante.setRGB(i, j,revelePixels(imageCourante.getRGB(i, j)));
            }
	}
    }
    //ImageFilter

    //Lecteur
    public static BufferedImage loadImage(File imageFile){
        try {
            BufferedImage image = ImageIO.read(imageFile);
            if(image == null) {
                return null;
            }
            return image;
        }
        catch(IOException e) {
            return null;
        }
    }
    //Sauveur
    public static void saveImage(BufferedImage image, File file){
        try {
            ImageIO.write(image, IMAGE_FORMAT, file);
        }
        catch(IOException exc) {
        }
    }
    
    
}
